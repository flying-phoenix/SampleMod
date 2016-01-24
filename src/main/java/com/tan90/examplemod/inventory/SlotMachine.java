package com.tan90.examplemod.inventory;

import com.tan90.examplemod.tileentity.TileEntityMachine;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotMachine extends Slot
{
    TileEntityMachine te;
    int id;

    public SlotMachine(IInventory inventory, int id, int x, int y)
    {
        super(inventory, id, x, y);
        this.te = (TileEntityMachine) inventory;
        this.id = id;
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        return te.isItemValidForSlot(id, stack);
    }
}
