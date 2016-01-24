package com.tan90.examplemod.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public abstract class ContainerExampleMod extends Container
{
    protected void addPlayerSlots(InventoryPlayer playerInventory, int x, int y)
    {
        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, x + j * 18, y + i * 18));
            }
        }

        for (int i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(playerInventory, i, x + i * 18, y + 58));
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot)
    {
        Slot currSlot = getSlot(slot);
        if (currSlot != null && currSlot.getHasStack())
        {
            ItemStack stack = currSlot.getStack();
            ItemStack result = stack.copy();
            if (slot >= 36)
            {
                if (!mergeItemStack(stack, 0, 36, false))
                {
                    return null;
                }
            } else if (!stack.isItemEqual(new ItemStack(Blocks.anvil)) || !mergeItemStack(stack, 36, 36 + player.inventory.getSizeInventory(), false))
            {
                return null;
            }

            if (stack.stackSize == 0)
            {
                currSlot.putStack(null);
            } else
            {
                currSlot.onSlotChanged();
            }

            currSlot.onPickupFromSlot(player, stack);
            return result;
        }
        return null;
    }

}
