package com.tan90.examplemod.tileentity;

import com.tan90.examplemod.init.ModBlocks;
import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;

import java.util.ArrayList;
import java.util.List;

public class TileEntityMine extends TileEntityExampleMod implements ISidedInventory
{
    private int timer = 120;
    private ItemStack[] camouflageStack = new ItemStack[6];
    private String target = "";
    private String owner = "";
    public static List<ItemStack> blackList = new ArrayList<ItemStack>();

    public String getOwner()
    {
        return owner;
    }

    public void setOwner(String owner)
    {
        this.owner = owner;
    }

    public String getTarget()
    {
        return target;
    }

    public void setTarget(String target)
    {
        this.target = target;
    }

    public void setCamouflageStack(ItemStack camouflageStack, int side)
    {
        setInventorySlotContents(side, camouflageStack);
    }

    public ItemStack getCamouflageStack(int side)
    {
        return getStackInSlot(side);
    }

    public int getTimer()
    {
        return timer;
    }

    public void setTimer(int timer)
    {
        this.timer = timer;
    }

    @Override

    public void updateEntity()
    {
        if (timer > 0)
        {
            timer--;
        }


        if (timer == 0 && !this.worldObj.isRemote)
        {
            List<Entity> entities = worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(xCoord - 2, yCoord - 2, zCoord - 2, xCoord + 3, yCoord + 3, zCoord + 3));
            if (!entities.isEmpty())
            {
                for (Entity entity : entities)
                {
                    if (!entity.getCommandSenderName().equalsIgnoreCase(owner) && entity.getCommandSenderName().equalsIgnoreCase(target) || target.equalsIgnoreCase("all"))
                    {
                        worldObj.createExplosion(null, this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, 3, true);
                    }
                }

            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);
        timer = tag.getInteger("timer");
        target = tag.getString("target");
        owner = tag.getString("owner");

        camouflageStack = new ItemStack[6];
        NBTTagList camouflageStackTag = tag.getTagList("camouflageStackTab", 10);

        for (int i = 0; i < camouflageStackTag.tagCount(); i++)
        {
            NBTTagCompound t = camouflageStackTag.getCompoundTagAt(i);
            int index = t.getByte("index");
            if (index >= 0 && index < camouflageStack.length)
            {
                camouflageStack[index] = ItemStack.loadItemStackFromNBT(t);
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tag)
    {
        super.writeToNBT(tag);
        tag.setInteger("timer", timer);
        tag.setString("target", target);
        tag.setString("owner", owner);

        NBTTagList camouflageStackTag = new NBTTagList();
        for (int i = 0; i < camouflageStack.length; i++)
        {
            ItemStack stack = camouflageStack[i];
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

    @Override
    public void writeToPacket(ByteBuf buf)
    {
        for (ItemStack stack : camouflageStack)
        {
            ByteBufUtils.writeItemStack(buf, stack);
        }
    }

    @Override
    public void readFromPacket(ByteBuf buf)
    {
        for (int i = 0; i < camouflageStack.length; i++)
        {
            camouflageStack[i] = ByteBufUtils.readItemStack(buf);
        }
        worldObj.markBlockRangeForRenderUpdate(xCoord, yCoord, zCoord, xCoord, yCoord, zCoord);
    }

    @Override
    public void onGuiButtonPress(int id)
    {
        switch (id)
        {
            case 0:
                setTimer(-1);
                break;
            case 1:
                setTimer(120);
                break;
        }
    }

    @Override
    public void onTextFieldChange(int id, String text)
    {
        switch (id)
        {
            case 0:
                setTarget(text);
                break;
        }
    }

    /**
     * Returns the number of slots in the com.tan90.examplemod.inventory.
     */
    public int getSizeInventory()
    {
        return camouflageStack.length;
    }

    /**
     * Returns the stack in slot i
     */
    public ItemStack getStackInSlot(int slot)
    {
        return this.camouflageStack[slot];
    }

    /**
     * Removes from an com.tan90.examplemod.inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     */
    public ItemStack decrStackSize(int slot, int decreaseAmmount)
    {
        if (this.camouflageStack[slot] != null)
        {
            ItemStack itemstack;

            if (this.camouflageStack[slot].stackSize <= decreaseAmmount)
            {
                itemstack = this.camouflageStack[slot];
                setInventorySlotContents(slot, null);
                this.markDirty();
                return itemstack;
            } else
            {
                itemstack = this.camouflageStack[slot].splitStack(decreaseAmmount);

                if (this.camouflageStack[slot].stackSize == 0)
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
    }

    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     */
    public ItemStack getStackInSlotOnClosing(int slot)
    {
        if (this.camouflageStack[slot] != null)
        {
            ItemStack itemstack = this.camouflageStack[slot];
            this.camouflageStack[slot] = null;
            return itemstack;
        } else
        {
            return null;
        }
    }

    /**
     * Sets the given item stack to the specified slot in the com.tan90.examplemod.inventory (can be crafting or armor sections).
     */
    public void setInventorySlotContents(int slot, ItemStack item)
    {
        this.camouflageStack[slot] = item;

        if (item != null && item.stackSize > this.getInventoryStackLimit())
        {
            item.stackSize = this.getInventoryStackLimit();
        }

        this.markDirty();
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }

    /**
     * Returns the name of the com.tan90.examplemod.inventory
     */
    public String getInventoryName()
    {
        return ModBlocks.mineBlock.getUnlocalizedName() + ".name";
    }

    /**
     * Returns if the com.tan90.examplemod.inventory is named
     */
    public boolean hasCustomInventoryName()
    {
        return false;
    }


    /**
     * Returns the maximum stack size for a com.tan90.examplemod.inventory slot.
     */
    public int getInventoryStackLimit()
    {
        return 1;
    }

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    public boolean isUseableByPlayer(EntityPlayer player)
    {
        return player.getDistanceSq((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D, (double) this.zCoord + 0.5D) <= 64.0D;
    }

    public void openInventory()
    {
    }

    public void closeInventory()
    {
    }

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
     */
    public boolean isItemValidForSlot(int slot, ItemStack itemStack)
    {
        boolean isValid = itemStack != null && itemStack.getItem() instanceof ItemBlock;
        for (ItemStack blackListStack : blackList)
        {
            if (blackListStack.isItemEqual(itemStack))
            {
                return false;
            }
        }
        return isValid;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side)
    {
        return side == 0 ? new int[]{0, 1, 2, 3, 4, 5} : new int[]{side};
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side)
    {
        return isItemValidForSlot(slot, stack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack item, int side)
    {
        return true;
    }
}
