package com.tan90.examplemod.handler;

import com.tan90.examplemod.client.gui.GuiBackpack;
import com.tan90.examplemod.client.gui.GuiMachine;
import com.tan90.examplemod.client.gui.GuiMine;
import com.tan90.examplemod.inventory.ContainerBackpack;
import com.tan90.examplemod.inventory.ContainerMachine;
import com.tan90.examplemod.inventory.ContainerMine;
import com.tan90.examplemod.inventory.InventoryBackpack;
import com.tan90.examplemod.reference.Guis;
import com.tan90.examplemod.tileentity.TileEntityMachine;
import com.tan90.examplemod.tileentity.TileEntityMine;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler
{
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        switch (Guis.values()[ID])
        {
            case BLOCK_MINE:
                return new ContainerMine(player.inventory, (TileEntityMine) world.getTileEntity(x, y, z));
            case BLOCK_MACHINE:
                return new ContainerMachine(player.inventory, (TileEntityMachine) world.getTileEntity(x, y, z));
            case ITEM_BACKPACK:
                return new ContainerBackpack(new InventoryBackpack(player.getHeldItem().getTagCompound().getString("UUID"), player), player.inventory);
        }

        throw new IllegalArgumentException("No GUI with id: " + ID);
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        switch (Guis.values()[ID])
        {
            case BLOCK_MINE:
                return new GuiMine(player.inventory, (TileEntityMine) world.getTileEntity(x, y, z));
            case BLOCK_MACHINE:
                return new GuiMachine(player.inventory, (TileEntityMachine) world.getTileEntity(x, y, z));
            case ITEM_BACKPACK:
                return new GuiBackpack(new InventoryBackpack(player.getHeldItem().getTagCompound().getString("UUID"), player), player.inventory);
        }

        throw new IllegalArgumentException("No GUI with id: " + ID);
    }
}
