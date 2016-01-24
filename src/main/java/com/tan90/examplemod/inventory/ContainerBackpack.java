package com.tan90.examplemod.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerBackpack extends Container
{
    InventoryBackpack inventoryBackpack;

    public ContainerBackpack(InventoryBackpack inventoryBackpack, InventoryPlayer inventoryPlayer)
    {

        this.inventoryBackpack = inventoryBackpack;
        int x = 8;
        int y = 151;
        /* player slots */
        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                if (inventoryPlayer.getStackInSlot(j + i * 9) == inventoryBackpack.parentStack)
                {
                    this.addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, x + j * 18, y + i * 18)
                    {
                        @Override
                        public boolean canTakeStack(EntityPlayer player)
                        {
                            return false;
                        }
                    });
                } else
                {
                    this.addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, x + j * 18, y + i * 18));
                }
            }
        }

        /* hotbar */
        for (int i = 0; i < 9; ++i)
        {
            if (inventoryPlayer.getStackInSlot(i) == inventoryBackpack.parentStack)
            {
                this.addSlotToContainer(new Slot(inventoryPlayer, i, x + i * 18, y + 58)
                {
                    @Override
                    public boolean canTakeStack(EntityPlayer player)
                    {
                        return false;
                    }
                });
            } else
            {
                this.addSlotToContainer(new Slot(inventoryPlayer, i, x + i * 18, y + 58));
            }
        }

        /* backpack slots*/

        x = 37;
        y = 50;
        for (int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 6; j++)
            {
                this.addSlotToContainer(new SlotBackpack(inventoryBackpack, j + i * 6, x + j * 18, y + i * 18));
            }
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityPlayer)
    {
        return inventoryBackpack.isUseableByPlayer(entityPlayer);
    }

    private class SlotBackpack extends Slot
    {
        public SlotBackpack(IInventory iInventory, int index, int x, int y)
        {
            super(iInventory, index, x, y);
        }

        @Override
        public boolean isItemValid(ItemStack itemStack)
        {
            return inventoryBackpack.isItemValidForSlot(this.getSlotIndex(), itemStack);
        }
    }

    @Override
    public void onContainerClosed(EntityPlayer p_75134_1_)
    {
        inventoryBackpack.saveInventory();
        super.onContainerClosed(p_75134_1_);
    }
}
