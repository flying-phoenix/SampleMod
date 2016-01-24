package com.tan90.examplemod.tileentity;

import com.tan90.examplemod.init.ModBlocks;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class TileEntityModularStorage extends TileEntityExampleMod implements IInventory
{
    public static final int SLOTS_PER_BLOCK = 1;

    private ItemStack[] inventory;
    private TileEntityModularStorage master;
    private boolean isMaster;
    private boolean updateMultiblock = true;

    public void setUpdateMultiblock(boolean updateMultiblock)
    {
        this.updateMultiblock = updateMultiblock;
    }

    public TileEntityModularStorage getMaster()
    {
        return master;
    }

    public void setMaster(TileEntityModularStorage master)
    {
        this.master = master;
    }

    public boolean isMaster()
    {
        return isMaster;
    }

    public void setIsMaster(boolean isMaster)
    {
        this.isMaster = isMaster;
        worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, isMaster ? 1 : 0, 3);
        worldObj.markBlockRangeForRenderUpdate(xCoord, yCoord, zCoord, xCoord, yCoord, zCoord);
    }

    public ItemStack[] getInventory()
    {
        return inventory;
    }

    public void setInventory(ItemStack[] inventory)
    {
        this.inventory = inventory;
    }

    private ArrayList<ItemStack> mergeStackInList(ArrayList<ItemStack> list, ItemStack stack)
    {
        for (ItemStack listStack : list)
        {
            if (listStack.isItemEqual(stack))
            {
                listStack.stackSize += stack.stackSize;
            }
        }
        return list;
    }

    public void handleInventory(List<TileEntityModularStorage> multiblock)
    {
        ArrayList<ItemStack> newInventory = new ArrayList<ItemStack>()
        {
            @Override
            public boolean contains(Object stack)
            {
                for (ItemStack listStack : this)
                {
                    if (listStack.isItemEqual((ItemStack) stack)) ;
                    {
                        return true;
                    }
                }
                return false;
            }
        };

        int inventorySize = SLOTS_PER_BLOCK * multiblock.size();
        int currIndex = 0;

        for (TileEntityModularStorage currStorage : multiblock)
        {
            ItemStack[] currInventory = currStorage.getInventory();
            if (currInventory != null)
            {
                for (ItemStack stack : currInventory)
                {
                    if (stack != null)
                    {
                        if (currIndex < inventorySize)
                        {
                            if (newInventory.contains(stack))
                            {
                                mergeStackInList(newInventory, stack);
                            } else
                            {
                                newInventory.add(stack);
                                currIndex++;
                            }
                        } else
                        {
                            worldObj.spawnEntityInWorld(new EntityItem(worldObj, currStorage.xCoord, currStorage.yCoord, currStorage.zCoord, stack));
                        }
                    }
                }
                currStorage.setInventory(null);
            }
        }
        inventory = new ItemStack[inventorySize];
        inventory = newInventory.toArray(inventory);
    }


    @Override
    public void updateEntity()
    {
        super.updateEntity();

        if (updateMultiblock)
        {
            updateMultiblock();
            updateMultiblock = false;
        }
    }

    private void updateMultiblock()
    {
        List<TileEntityModularStorage> multiblock = new ArrayList<TileEntityModularStorage>();
        Stack<TileEntityModularStorage> toVisit = new Stack<TileEntityModularStorage>();

        setMaster(this);
        setIsMaster(true);
        toVisit.add(this);

        while (!toVisit.isEmpty())
        {
            TileEntityModularStorage currentBlock = toVisit.pop();
            multiblock.add(currentBlock);
            if (currentBlock != this && currentBlock.isMaster())
            {
                setMaster(currentBlock);
                setIsMaster(false);
            }

            for (ForgeDirection d : ForgeDirection.VALID_DIRECTIONS)
            {
                TileEntity currentChecking = worldObj.getTileEntity(currentBlock.xCoord + d.offsetX, currentBlock.yCoord + d.offsetY, currentBlock.zCoord + d.offsetZ);
                if (currentChecking != null && currentChecking instanceof TileEntityModularStorage && !multiblock.contains(currentChecking) && !toVisit.contains(currentChecking))
                {
                    toVisit.add((TileEntityModularStorage) currentChecking);
                }
            }
        }

        for (TileEntityModularStorage onMultiblock : multiblock)
        {
            if (onMultiblock != master)
            {
                onMultiblock.setMaster(master);
                onMultiblock.setIsMaster(false);
            } else
            {
                onMultiblock.handleInventory(multiblock);
            }
        }
    }

    @Override
    public void invalidate()
    {
        super.invalidate();


        for (ForgeDirection d : ForgeDirection.VALID_DIRECTIONS)
        {
            TileEntity currentChecking = worldObj.getTileEntity(xCoord + d.offsetX, yCoord + d.offsetY, zCoord + d.offsetZ);
            if (currentChecking != null && currentChecking instanceof TileEntityModularStorage)
            {
                ((TileEntityModularStorage) currentChecking).setUpdateMultiblock(true);
                if (this.isMaster() && this.getInventory() != null)
                {
                    ((TileEntityModularStorage) currentChecking).setInventory(this.getInventory());
                    this.setInventory(null);
                }
            }
        }
    }

    @Override
    public int getSizeInventory()
    {
        return isMaster() ? inventory.length : getMaster().getSizeInventory();
    }

    @Override
    public ItemStack getStackInSlot(int slot)
    {
        return isMaster() ? inventory[slot] : getMaster().getStackInSlot(slot);
    }

    @Override
    public ItemStack decrStackSize(int slot, int decreaseAmount)
    {
        if (isMaster())
        {
            if (this.inventory[slot] != null)
            {
                ItemStack itemstack;

                if (this.inventory[slot].stackSize <= decreaseAmount)
                {
                    itemstack = this.inventory[slot];
                    setInventorySlotContents(slot, null);
                    this.markDirty();
                    return itemstack;
                } else
                {
                    itemstack = this.inventory[slot].splitStack(decreaseAmount);

                    if (this.inventory[slot].stackSize == 0)
                    {
                        setInventorySlotContents(slot, null);
                    }

                    this.markDirty();
                    return itemstack;
                }
            } else
            {
                return null;
            }
        } else
        {
            return getMaster().decrStackSize(slot, decreaseAmount);
        }
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot)
    {
        if (isMaster())
        {

            if (this.inventory[slot] != null)
            {
                ItemStack itemstack = this.inventory[slot];
                this.inventory[slot] = null;
                return itemstack;
            } else
            {
                return null;
            }
        } else
        {
            return getMaster().getStackInSlotOnClosing(slot);
        }
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack)
    {
        if (isMaster())
        {
            this.inventory[slot] = stack;

            if (stack != null && stack.stackSize > this.getInventoryStackLimit())
            {
                stack.stackSize = this.getInventoryStackLimit();
            }

            this.markDirty();
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        } else
        {
            getMaster().setInventorySlotContents(slot, stack);
        }
    }

    /* common to master and slave */

    /* common IInventory stuff*/
    @Override
    public String getInventoryName()
    {
        return ModBlocks.modularStorageBlock.getUnlocalizedName() + ".name";
    }

    @Override
    public boolean hasCustomInventoryName()
    {
        return false;
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player)
    {
        return player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) <= 64;
    }

    @Override
    public void openInventory()
    {

    }

    @Override
    public void closeInventory()
    {

    }

    @Override
    public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_)
    {
        return true;
    }

    /* NBT handler */
    @Override
    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);

        isMaster = tag.getBoolean("isMaster");

        inventory = new ItemStack[6];
        NBTTagList camouflageStackTag = tag.getTagList("camouflageStackTab", 10);

        for (int i = 0; i < camouflageStackTag.tagCount(); i++)
        {
            NBTTagCompound t = camouflageStackTag.getCompoundTagAt(i);
            int index = t.getByte("index");
            if (index >= 0 && index < inventory.length)
            {
                inventory[index] = ItemStack.loadItemStackFromNBT(t);
            }
        }

    }

    @Override
    public void writeToNBT(NBTTagCompound tag)
    {
        super.writeToNBT(tag);

        tag.setBoolean("isMaster", isMaster);

        NBTTagList camouflageStackTag = new NBTTagList();
        for (int i = 0; i < inventory.length; i++)
        {
            ItemStack stack = inventory[i];
            if (stack != null)
            {
                NBTTagCompound t = new NBTTagCompound();
                stack.writeToNBT(t);
                t.setByte("index", (byte) i);
                camouflageStackTag.appendTag(t);
            }
        }
        tag.setTag("camouflageStackTab", camouflageStackTag);
    }
}
