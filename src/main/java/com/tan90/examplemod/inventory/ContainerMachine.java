package com.tan90.examplemod.inventory;

import com.tan90.examplemod.tileentity.TileEntityMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ContainerMachine extends Container
{
    TileEntityMachine machine;

    public ContainerMachine(InventoryPlayer inventoryPlayer, TileEntityMachine teMachine)
    {
        this.machine = teMachine;

        for (int x = 0; x < 9; x++)
        {
            //142
            addSlotToContainer(new Slot(inventoryPlayer, x, 8 + 18 * x, 212));
        }

        for (int y = 0; y < 3; y++)
        {
            for (int x = 0; x < 9; x++)
            {
                addSlotToContainer(new Slot(inventoryPlayer, x + y * 9 + 9, 8 + 18 * x, 154 + 18 * y));
            }
        }

        for (int x = 0; x < 3; x++)
        {
            addSlotToContainer(new SlotMachine(teMachine, x, 7 + 18 * x, 7));
        }
    }

    @Override
    public void addCraftingToCrafters(ICrafting player)
    {
        super.addCraftingToCrafters(player);

        for (int i = 0; i < machine.customSetup.length; i++)
        {
            player.sendProgressBarUpdate(this, i, machine.customSetup[i] ? 1 : 0);
        }
    }

    private boolean[] oldSetup = new boolean[49];

    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < machine.customSetup.length; i++)
        {
            if (machine.customSetup[i] != oldSetup[i])
            {
                for (ICrafting crafter : (List<ICrafting>) crafters)
                {
                    crafter.sendProgressBarUpdate(this, i, machine.customSetup[i] ? 1 : 0);
                }
            }
            oldSetup[i] = machine.customSetup[i];
        }

//        oldSetup = Arrays.copyOf(machine.customSetup, machine.customSetup.length);
    }

    @Override
    public void updateProgressBar(int id, int data)
    {
        super.updateProgressBar(id, data);

        machine.customSetup[id] = data == 1;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
        return machine.isUseableByPlayer(player);
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
            } else if (!stack.isItemEqual(new ItemStack(Blocks.anvil)) || !mergeItemStack(stack, 36, 36 + machine.getSizeInventory(), false))
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
