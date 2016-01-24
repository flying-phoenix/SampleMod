package com.tan90.examplemod.inventory;

import com.tan90.examplemod.network.MessageHandleTextUpdate;
import com.tan90.examplemod.network.NetworkHandler;
import com.tan90.examplemod.tileentity.TileEntityMine;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ContainerMine extends ContainerExampleMod
{
    private TileEntityMine teMine;
    private int lastTimer = -1;
    private String lastTarget = "";

    public ContainerMine(InventoryPlayer playerInventory, TileEntityMine teMine)
    {
        this.teMine = teMine;

        this.addSlotToContainer(new SlotMine(teMine, 0, 80, 58));
        this.addSlotToContainer(new SlotMine(teMine, 1, 80, 22));
        this.addSlotToContainer(new SlotMine(teMine, 2, 98, 40));
        this.addSlotToContainer(new SlotMine(teMine, 3, 62, 40));
        this.addSlotToContainer(new SlotMine(teMine, 4, 99, 21));
        this.addSlotToContainer(new SlotMine(teMine, 5, 61, 59));

        this.addPlayerSlots(playerInventory, 8, 84);
    }

    @Override
    public void detectAndSendChanges()
    {
        if (lastTimer != teMine.getTimer())
        {
            for (ICrafting crafter : (List<ICrafting>) this.crafters)
            {
                crafter.sendProgressBarUpdate(this, 0, teMine.getTimer());
            }
            lastTimer = teMine.getTimer();
        }
        if (!lastTarget.equals(teMine.getTarget()))
        {
            for (ICrafting crafter : (List<ICrafting>) this.crafters)
            {
                if (crafter instanceof EntityPlayerMP)
                {
                    NetworkHandler.sendTo(new MessageHandleTextUpdate(teMine, 0, teMine.getTarget()), (EntityPlayerMP) crafter);
                }
            }
            lastTarget = teMine.getTarget();
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int value)
    {
        super.updateProgressBar(id, value);
        switch (id)
        {
            case 0:
                teMine.setTimer(value);
                break;
            default:
                throw new IllegalArgumentException("No progress bar update with id: " + id);
        }
    }

    /**
     * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
     */

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int inventoryIndex)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.inventorySlots.get(inventoryIndex);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (inventoryIndex < 6)
            {
                if (!this.mergeItemStack(itemstack1, 6, 42, true))
                {
                    return null;
                }
            } else if (itemstack1.stackSize == 1)
            {
                for (int i = 0; i < 6; i++)
                {
                    Slot currSlot = (Slot) inventorySlots.get(i);
                    if (!(currSlot.getHasStack()) && currSlot.isItemValid(itemstack1))
                    {
                        mergeItemStack(itemstack1, i, i + 1, false);
                    }
                }
            }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack) null);
            } else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize)
            {
                return null;
            }

            slot.onPickupFromSlot(player, itemstack1);
        }

        return itemstack;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
        return teMine.isUseableByPlayer(player);
    }
}
