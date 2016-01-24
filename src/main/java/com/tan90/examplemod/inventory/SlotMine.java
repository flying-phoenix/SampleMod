package com.tan90.examplemod.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotMine extends Slot
{
    public SlotMine(IInventory inventory, int invIndex, int x, int y)
    {
        super(inventory, invIndex, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        return this.inventory.isItemValidForSlot(getSlotIndex(), stack);
    }
}
