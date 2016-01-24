package com.tan90.examplemod.block;

import com.tan90.examplemod.ExampleMod;
import com.tan90.examplemod.item.ItemCard;
import com.tan90.examplemod.reference.Guis;
import com.tan90.examplemod.reference.Names;
import com.tan90.examplemod.reference.Textures;
import com.tan90.examplemod.tileentity.TileEntityMachine;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockTileEntityMachine extends BlockTileEntityExampleMod
{
    private static final String[] machineIcons = {"machine", "machineTopOff", "machineTopOn", "machineSideBox", "machineSideCross", "machineSideSquare", "machineSideCustom"};
    private static final int RANGE = 2;
    private IIcon[] icons = new IIcon[machineIcons.length];

    public BlockTileEntityMachine()
    {
        super(Material.anvil);
        this.setBlockName(Names.Blocks.MACHINE);
        this.setHardness(1.0F);
    }

    @Override
    public IIcon getIcon(int side, int meta)
    {
        if (side == 1)
        {
            return meta % 2 == 0 ? icons[1] : icons[2];
        } else if (side == 0)
        {
            return icons[0];
        } else
        {
            switch (meta)
            {
                case 0:
                case 1:
                    return icons[3];
                case 2:
                case 3:
                    return icons[4];
                case 4:
                case 5:
                    return icons[5];
                case 6:
                case 7:
                    return icons[6];
                default:
                    return icons[0];
            }
        }
    }

    @Override
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        for (int i = 0; i < machineIcons.length; i++)
        {
            icons[i] = iconRegister.registerIcon(Textures.RESOURCE_PREFIX + machineIcons[i]);
        }
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xPos, float yPos, float zPos)
    {
        if (!world.isRemote)
        {
            if (!player.isSneaking())
            {
                int meta = world.getBlockMetadata(x, y, z);
                if (side == 1)
                {
                    if (meta % 2 == 0)
                    {
                        world.setBlockMetadataWithNotify(x, y, z, meta + 1, 3);
                    } else
                    {
                        world.setBlockMetadataWithNotify(x, y, z, meta - 1, 3);
                    }

                } else
                {
                    ItemStack playerItem = player.getHeldItem();
                    if (playerItem != null && playerItem.getItem() instanceof ItemCard)
                    {
                        int cardType = playerItem.getItemDamage();

                        switch (cardType)
                        {
                            case 0:
                                world.setBlockMetadataWithNotify(x, y, z, meta % 2 == 0 ? 0 : 1, 3);
                                break;
                            case 1:
                                world.setBlockMetadataWithNotify(x, y, z, meta % 2 == 0 ? 2 : 3, 3);
                                break;
                            case 2:
                                world.setBlockMetadataWithNotify(x, y, z, meta % 2 == 0 ? 4 : 5, 3);
                                break;
                            case 3:
                                world.setBlockMetadataWithNotify(x, y, z, meta % 2 == 0 ? 6 : 7, 3);
                                break;
                        }
                        world.markBlockRangeForRenderUpdate(x, y, z, x, y, z);
                        playerItem.stackSize--;
                    }
                }
            } else
            {
                FMLNetworkHandler.openGui(player, ExampleMod.instance, Guis.BLOCK_MACHINE.ordinal(), world, x, y, z);
            }
        }
        return true;
    }

    @Override
    public void onEntityWalking(World world, int x, int y, int z, Entity entity)
    {
        super.onEntityWalking(world, x, y, z, entity);
        int meta = world.getBlockMetadata(x, y, z);
        boolean active = meta % 2 != 0;
        int type = meta / 2;
        IInventory inventory = (IInventory) world.getTileEntity(x, y, z);
        if (active)
        {
            int offsetX;
            int offsetZ;
            switch (type)
            {
                case 0:

                    offsetX = RANGE;
                    for (offsetZ = -RANGE; offsetZ <= RANGE; offsetZ++)
                    {
                        spawnAnvil(world, inventory, x + offsetX, y, z + offsetZ);
                    }

                    offsetX = -RANGE;
                    for (offsetZ = -RANGE; offsetZ <= RANGE; offsetZ++)
                    {
                        spawnAnvil(world, inventory, x + offsetX, y, z + offsetZ);
                    }

                    offsetZ = RANGE;
                    for (offsetX = -RANGE; offsetX <= RANGE; offsetX++)
                    {
                        spawnAnvil(world, inventory, x + offsetX, y, z + offsetZ);
                    }

                    offsetZ = -RANGE;
                    for (offsetX = -RANGE; offsetX <= RANGE; offsetX++)
                    {
                        spawnAnvil(world, inventory, x + offsetX, y, z + offsetZ);
                    }

                    break;
                case 1:

                    for (offsetZ = -RANGE; offsetZ <= RANGE; offsetZ++)
                    {
                        spawnAnvil(world, inventory, x, y, z + offsetZ);
                    }

                    for (offsetX = -RANGE; offsetX <= RANGE; offsetX++)
                    {
                        spawnAnvil(world, inventory, x + offsetX, y, z);
                    }
                    break;
                case 2:

                    for (offsetX = -2; offsetX <= 2; offsetX++)
                    {
                        for (offsetZ = -2; offsetZ <= 2; offsetZ++)
                        {
                            spawnAnvil(world, inventory, x + offsetX, y, z + offsetZ);
                        }
                    }
                    break;
                case 3:
                    TileEntityMachine te = (TileEntityMachine) world.getTileEntity(x, y, z);
                    for (int i = 0; i < te.customSetup.length; i++)
                    {
                        if (te.customSetup[i])
                        {
                            int dropX = x + i % 7 - 3;
                            int dropZ = z + i / 7 - 3;
                            spawnAnvil(world, te, dropX, y, dropZ);
                        }
                    }
                    break;
            }
        }
    }

    private void spawnAnvil(World world, IInventory inventory, int x, int y, int z)
    {
        for (int i = 0; i < inventory.getSizeInventory(); i++)
        {
            ItemStack stack = inventory.getStackInSlot(i);
            if (stack != null && stack.isItemEqual(new ItemStack(Blocks.anvil)))
            {
                inventory.decrStackSize(i, 1);
                world.setBlock(x, y + 20, z, Blocks.anvil, 0, 2);
                return;
            }
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileEntityMachine();
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta)
    {
        TileEntityMachine teMachine = (TileEntityMachine) world.getTileEntity(x, y, z);
        if (teMachine != null)
        {
            for (int i = 0; i < teMachine.getSizeInventory(); i++)
            {
                ItemStack stack = teMachine.getStackInSlotOnClosing(i);
                if (stack != null)
                {
                    EntityItem droppedItem = new EntityItem(world, x + world.rand.nextFloat(), y + world.rand.nextFloat(), z + world.rand.nextFloat(), stack);

                    float mult = 0.05F;
                    droppedItem.motionX = (-0.5 + world.rand.nextFloat()) * mult;
                    droppedItem.motionX = (4 + world.rand.nextFloat()) * mult;
                    droppedItem.motionX = (-0.5 + world.rand.nextFloat()) * mult;
                    world.spawnEntityInWorld(droppedItem);
                }
            }
        }
        super.breakBlock(world, x, y, z, block, meta);
    }
}
