package com.tan90.examplemod.tileentity;

import com.tan90.examplemod.util.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.Stack;

public class TileEntityTreeFarm extends TileEntity
{
    private class BlockInWorld
    {
        public int x;
        public int y;
        public int z;
        public Block block;

        public BlockInWorld(int x, int y, int z, Block block)
        {
            this.x = x;
            this.y = y;
            this.z = z;
            this.block = block;
        }
    }

    private int farmTimer = -1;
    private boolean isFormed = false;
    private IInventory inventory;

    @Override
    public void updateEntity()
    {
        super.updateEntity();
        if (!worldObj.isRemote)
        {
            if (farmTimer == -1)
            {
                farmTimer = 0;
            }

            if (farmTimer == 0)
            {
                this.isFormed = checkFormed();
                if (this.isFormed)
                {
                    runFarm();
                }
                LogHelper.info("Formed: " + isFormed);
            }
            farmTimer++;
            farmTimer %= 200;
        }
    }

    private void runFarm()
    {
        if (inventory != null)
        {
            /* planting saplings */
            if (worldObj.isAirBlock(xCoord, yCoord + 2, zCoord))
            {
                for (int i = 0; i < inventory.getSizeInventory(); i++)
                {
                    ItemStack stack = inventory.getStackInSlot(i);

                    if (stack != null && stack.getItem() instanceof ItemBlock)
                    {

                        Block block = ((ItemBlock) stack.getItem()).field_150939_a;
                        if (block == Blocks.sapling)
                        {
                            worldObj.setBlock(xCoord, yCoord + 2, zCoord, block, stack.getItemDamage(), 3);
                            inventory.decrStackSize(i, 1);
                            break;
                        }
                    }
                }
            }

            /* cutting down trees */

            ArrayList<BlockInWorld> tree = new ArrayList<BlockInWorld>()
            {
                @Override
                public boolean contains(Object o)
                {
                    BlockInWorld comparing = (BlockInWorld) o;

                    for (BlockInWorld blockInWorld : this)
                    {
                        if (blockInWorld.x == comparing.x && blockInWorld.y == comparing.y && blockInWorld.z == comparing.z && blockInWorld.block == comparing.block)
                        {
                            return true;
                        }
                    }

                    return false;
                }
            };
            Stack<BlockInWorld> toVisit = new Stack<BlockInWorld>()
            {
                @Override
                public boolean contains(Object o)
                {
                    BlockInWorld comparing = (BlockInWorld) o;

                    for (BlockInWorld blockInWorld : this)
                    {
                        if (blockInWorld.x == comparing.x && blockInWorld.y == comparing.y && blockInWorld.z == comparing.z && blockInWorld.block == comparing.block)
                        {
                            return true;
                        }
                    }

                    return false;
                }
            };

            Block block = worldObj.getBlock(xCoord, yCoord + 2, zCoord);
            if (block == Blocks.log)
            {
                toVisit.add(new BlockInWorld(xCoord, yCoord + 2, zCoord, block));
            }

            while (!toVisit.isEmpty())
            {
                BlockInWorld currBlock = toVisit.pop();
                tree.add(currBlock);

                for (ForgeDirection d : ForgeDirection.VALID_DIRECTIONS)
                {
                    block = worldObj.getBlock(currBlock.x + d.offsetX, currBlock.y + d.offsetY, currBlock.z + d.offsetZ);
                    BlockInWorld checking = new BlockInWorld(currBlock.x + d.offsetX, currBlock.y + d.offsetY, currBlock.z + d.offsetZ, block);
                    if (!tree.contains(checking) && !toVisit.contains(checking) && (block == Blocks.log || block == Blocks.leaves))
                    {
                        toVisit.add(checking);
                    }
                }
                LogHelper.info("Tree Size: " + tree.size());
            }

            for (BlockInWorld blockInWorld : tree)
            {
                ArrayList<ItemStack> drops = blockInWorld.block.getDrops(worldObj, blockInWorld.x, blockInWorld.y, blockInWorld.z, worldObj.getBlockMetadata(blockInWorld.x, blockInWorld.y, blockInWorld.z), 0);
                if (drops == null)
                {
                    continue;
                }

                for (ItemStack stack : drops)
                {
                    ItemStack remainder = TileEntityHopper.func_145889_a(inventory, stack, 0);

                    if (remainder != null)
                    {
                        worldObj.spawnEntityInWorld(new EntityItem(worldObj, xCoord, yCoord + 2, zCoord, remainder));
                    }
                }

                worldObj.setBlockToAir(blockInWorld.x, blockInWorld.y, blockInWorld.z);
            }


        }
    }

    private boolean checkFormed()
    {
        int thirdLayerEmerald = 0;
        int inventories = 0;
        for (int yOffset = -1; yOffset <= 1; yOffset++)
        {
            for (int xOffset = -1; xOffset <= 1; xOffset++)
            {
                for (int zOffset = -1; zOffset <= 1; zOffset++)
                {
                    Block block = worldObj.getBlock(xCoord + xOffset, yCoord + yOffset, zCoord + zOffset);

                    switch (yOffset)
                    {
                        case -1:
                            if (block != Blocks.quartz_block)
                            {
                                return false;
                            }
                            break;
                        case 0:
                            if (!(xOffset == 0 && zOffset == 0))
                            {
                                if (xOffset != 0 ^ zOffset != 0)
                                {
                                    if (block != Blocks.emerald_block)
                                    {
                                        return false;
                                    }
                                } else
                                {
                                    if (block != Blocks.quartz_block)
                                    {
                                        return false;
                                    }
                                }
                            }
                            break;
                        case 1:
                            if (xOffset == 0 && zOffset == 0)
                            {
                                if (block != Blocks.dirt && block != Blocks.grass)
                                {
                                    return false;
                                }
                            } else if (xOffset == 0 ^ zOffset == 0)
                            {
                                if (block == Blocks.emerald_block)
                                {
                                    thirdLayerEmerald++;
                                } else
                                {
                                    TileEntity te = worldObj.getTileEntity(xCoord + xOffset, yCoord + yOffset, zCoord + zOffset);
                                    if (te instanceof IInventory)
                                    {
                                        this.inventory = (IInventory) te;
                                        inventories++;
                                    }
                                }
                            }
                            break;
                    }
                }
            }
        }

        if (thirdLayerEmerald != 3 || inventories != 1)
        {
            return false;
        }
        return true;
    }

    @Override
    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);

        farmTimer = tag.getInteger("farmTimer");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag)
    {
        super.writeToNBT(tag);

        tag.setInteger("farmTimer", farmTimer);
    }
}
