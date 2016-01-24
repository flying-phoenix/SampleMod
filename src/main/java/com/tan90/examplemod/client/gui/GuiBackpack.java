package com.tan90.examplemod.client.gui;

import com.tan90.examplemod.inventory.ContainerBackpack;
import com.tan90.examplemod.inventory.InventoryBackpack;
import com.tan90.examplemod.reference.Textures;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiBackpack extends GuiContainer
{

    private InventoryBackpack backpack;

    public GuiBackpack(InventoryBackpack inventoryBakcpack, InventoryPlayer inventoryPlayer)
    {
        super(new ContainerBackpack(inventoryBakcpack, inventoryPlayer));
        this.xSize = 176;
        this.ySize = 233;
        this.backpack = inventoryBakcpack;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int x, int y)
    {
        Minecraft mc = Minecraft.getMinecraft();
        mc.getTextureManager().bindTexture(Textures.Guis.BACKPACK);

        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }

    @Override
    public void onGuiClosed()
    {
//        backpack.saveInventory();

        super.onGuiClosed();
    }
}
