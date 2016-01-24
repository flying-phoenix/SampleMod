package com.tan90.examplemod.tileentity;

import com.tan90.examplemod.init.ModBlocks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class TileEntityMachine extends TileEntityExampleMod implements IInventory
{
    private ItemStack[] inventory;
    private int anvils = -1;
    public boolean[] customSetup;

    public TileEntityMachine()
    {
        inventory = new ItemStack[3];
        customSetup = new boolean[49];
    }

    public void setCustomSetup(int index)
    {
        customSetup[index] = !customSetup[index];
        markDirty();
    }

    /* gui button */

    @Override
    public void onGuiButtonPress(int id)
    {
        if (!worldObj.isRemote)
        {
            switch (id)
            {
                case 0:
                    int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
                    if (meta % 2 == 0)
                    {
                        worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, meta + 1, 3);
                    } else
                    {
                        worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, meta - 1, 3);
                    }
                    break;
            }
        }
    }

    /* nbt */

    @Override
    public void writeToNBT(NBTTagCompound tag)
    {
        super.writeToNBT(tag);

        for (int i = 0; i < customSetup.length; i++)
        {
            tag.setBoolean("custom" + i, customSetup[i]);
        }

        NBTTagList inventory = new NBTTagList();
        for (int i = 0; i < getSizeInventory(); i++)
        {
            ItemStack stack = getStackInSlot(i);
            if (stack != null)
            {
                NBTTagCompound stackTag = new NBTTagCompound();
                stackTag.setByte("slot", (byte) i);
                stack.writeToNBT(stackTag);
                inventory.appendTag(stackTag);
            }
        }

        tag.setTag("inventory", inventory);


    }

    @Override
    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);


        for (int i = 0; i < customSetup.length; i++)
        {
            customSetup[i] = tag.getBoolean("custom" + i);
        }

        NBTTagList inventory = tag.getTagList("inventory", 10);
        for (int i = 0; i < inventory.tagCount(); i++)
        {
            NBTTagCompound stackTag = inventory.getCompoundTagAt(i);
            int slot = stackTag.getByte("slot");

            if (slot >= 0 && slot < getSizeInventory())
            {
                setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(stackTag));
            }
        }
    }

    /* IInventory */

    @Override
    public int getSizeInventory()
    {
        return inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int slot)
    {
        return inventory[slot];
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount)
    {
        ItemStack itemStack = getStackInSlot(slot);

        if (itemStack != null)
        {
            if (itemStack.stackSize <= amount)
            {
                setInventorySlotContents(slot, null);
            } else
            {
                itemStack = itemStack.splitStack(amount);
                markDirty();
            }
        }

        return itemStack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot)
    {
        ItemStack itemStack = getStackInSlot(slot);
        setInventorySlotContents(slot, null);

        return itemStack;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack)
    {
        inventory[slot] = stack;

        if (stack != null && stack.stackSize > getInventoryStackLimit())
        {
            stack.stackSize = getInventoryStackLimit();
        }

        markDirty();
    }

    @Override
    public String getInventoryName()
    {
        return ModBlocks.machineBlock.getUnlocalizedName() + ".name";
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
        return player.getDistanceSq(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5) <= 64;
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
    public boolean isItemValidForSlot(int slot, ItemStack item)
    {
        return item.isItemEqual(new ItemStack(Blocks.anvil));
    }

    @Override
    public void markDirty()
    {
        super.markDirty();
        anvils = -1;
    }

    public int getAnvils()
    {
        if (anvils == -1)
        {
            calculateAnvilCount();
        }

        return anvils;
    }

    private void calculateAnvilCount()
    {
        anvils = 0;
        for (int i = 0; i < getSizeInventory(); i++)
        {
            ItemStack stack = getStackInSlot(i);
            if (stack != null)
            {
                anvils += stack.stackSize;
            }
        }
    }
}
