package com.tan90.examplemod.inventory;

import com.tan90.examplemod.item.ItemBackpack;
import com.tan90.examplemod.util.LogHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class InventoryBackpack implements IInventory
{
    private final int SIZE = 18;
    private final String NAME = "gui.examplemod.backpack";

    private ItemStack[] inventory;
    protected ItemStack parentStack;
    private String parentStackUUID;
    private EntityPlayer player;

    public InventoryBackpack(String parentStackUUID, EntityPlayer player)
    {
        this.inventory = new ItemStack[SIZE];
        this.player = player;
        this.parentStackUUID = parentStackUUID;
        this.parentStack = getParentStack();

        NBTTagCompound parentTag = parentStack.getTagCompound();
        if(parentTag.hasKey("inventory"))
        {
            loadInventoryFromTag(parentTag);
        }
    }

    private void loadInventoryFromTag(NBTTagCompound parentTag)
    {
        NBTTagList inventory = parentTag.getTagList("inventory", 10);

        for(int i = 0; i < inventory.tagCount(); i++)
        {
            NBTTagCompound item = inventory.getCompoundTagAt(i);
            int slot = item.getInteger("slot");
            setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(item));
        }

        LogHelper.info("load");
    }

    public void saveInventory()
    {
        parentStack = getParentStack();
        NBTTagCompound parentTag = parentStack.getTagCompound();
        NBTTagList inventory = new NBTTagList();

        for(int i = 0; i < getSizeInventory(); i++)
        {
            ItemStack stack = getStackInSlot(i);

            if(stack != null)
            {
                NBTTagCompound item = new NBTTagCompound();
                item.setInteger("slot", i);
                stack.writeToNBT(item);
                inventory.appendTag(item);
            }
        }

        parentTag.setTag("inventory", inventory);
        LogHelper.info("save");
    }


    private ItemStack getParentStack()
    {
        for(int i = 0; i < player.inventory.getSizeInventory(); i++)
        {
            ItemStack itemStack = player.inventory.getStackInSlot(i);

            if(itemStack != null)
            {
                if(itemStack.getTagCompound() != null)
                {
                    if(itemStack.getTagCompound().getString("UUID").equals(parentStackUUID))
                    {
                        LogHelper.info("found parent " + i);
                        return itemStack;
                    }
                }
            }
        }

        return null;
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
        ItemStack itemStack = this.getStackInSlot(slot);

        if(itemStack != null)
        {
            if(itemStack.stackSize <= amount)
            {
                setInventorySlotContents(slot, null);
            }else
            {
                itemStack = itemStack.splitStack(amount);
            }
        }

        markDirty();

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
    public void setInventorySlotContents(int slot, ItemStack itemStack)
    {
        if(itemStack != null)
        {
            if (itemStack.stackSize > getInventoryStackLimit())
            {
                itemStack.stackSize = getInventoryStackLimit();
            }
        }
        this.inventory[slot] = itemStack;

        markDirty();
    }

    @Override
    public String getInventoryName()
    {
        return I18n.format(NAME);
    }

    @Override
    public boolean hasCustomInventoryName()
    {
        return getInventoryName() != null && getInventoryName().length() > 0;
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
    public void markDirty()
    {
//        saveInventory();
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player)
    {
        return parentStack.getTagCompound().getString("owner").equals(player.getCommandSenderName());
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
    public boolean isItemValidForSlot(int slot, ItemStack itemStack)
    {
        return ! (itemStack.getItem() instanceof ItemBackpack);
    }
}
