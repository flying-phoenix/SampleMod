package com.tan90.examplemod.client.gui;

import com.tan90.examplemod.init.ModBlocks;
import com.tan90.examplemod.inventory.ContainerMachine;
import com.tan90.examplemod.network.MessageHandleGuiButtonPress;
import com.tan90.examplemod.network.MessageSetMachineBoolean;
import com.tan90.examplemod.network.NetworkHandler;
import com.tan90.examplemod.reference.Reference;
import com.tan90.examplemod.tileentity.TileEntityMachine;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiMachine extends GuiContainer
{
    private TileEntityMachine teMachine;
    private GuiButton onOff;
    private static final GuiRectangle[] rectangles;

    static
    {
        rectangles = new GuiRectangle[49];

        for (int i = 0; i < 7; i++)
        {
            for (int j = 0; j < 7; j++)
            {
                rectangles[i * 7 + j] = new GuiRectangle(7 + 9 * i, 55 + 9 * j, 8, 8);
            }
        }
    }

    public GuiMachine(InventoryPlayer inventoryPlayer, TileEntityMachine teMachine)
    {
        super(new ContainerMachine(inventoryPlayer, teMachine));
        this.teMachine = teMachine;
        xSize = 176;
        ySize = 236;
    }

    private static final ResourceLocation texture = new ResourceLocation(Reference.LOWERCASE_MOD_ID + ":textures/gui/machine.png");

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int x, int y)
    {
        GL11.glColor4f(1, 1, 1, 1);

        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        int mode = teMachine.getBlockMetadata() / 2;
        int xOffset = 5 * mode;
        int yOffset = ySize;
        drawTexturedModalRect(guiLeft + 75, guiTop + 6, xOffset, yOffset, 5, 5);

        float filled = teMachine.getAnvils() / 192F;
        int barHeight = (int) (filled * 18);

        if (barHeight > 0)
        {
            drawTexturedModalRect(guiLeft + 165, guiTop + 130 + (18 - barHeight), xSize, 18 - barHeight, 2, barHeight);
        }

        int meta = teMachine.getWorldObj().getBlockMetadata(teMachine.xCoord, teMachine.yCoord, teMachine.zCoord);

        if (mode == 3)
        {
            xOffset = 0;
            yOffset = ySize + 5;

            for (int i = 0; i < rectangles.length; i++)
            {
                GuiRectangle rect = rectangles[i];
                rect.render(this, xOffset, yOffset, x, y, teMachine.customSetup[i]);
            }
        }


        Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
        drawTexturedModelRectFromIcon(guiLeft + 80, guiTop + 15, ModBlocks.machineBlock.getIcon(1, meta), 16, 16);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y)
    {
        fontRendererObj.drawString("VSWE's Machine", 85, 5, 0x404040);

        int mode = teMachine.getBlockMetadata() / 2;

        String str = "";
        boolean invalid = true;

        int count = 0;
        switch (mode)
        {
            case 0:
                count = 16;
                break;
            case 1:
                count = 9;
                break;
            case 2:
                count = 25;
                break;
            case 3:
                for (boolean anv : teMachine.customSetup)
                {
                    if (anv)
                    {
                        count++;
                    }
                }
                break;
        }

        if (teMachine.getAnvils() >= count)
        {
            invalid = false;
        }

        str = "Requires " + count + " anvils";
        int color = invalid ? 0xD34040 : 0x404040;

        fontRendererObj.drawSplitString(str, 7, 135, 70, color);
    }

    @Override
    public void initGui()
    {
        super.initGui();
        buttonList.clear();

        onOff = new GuiButton(0, guiLeft + 100, guiTop + 14, 62, 20, teMachine.getBlockMetadata() % 2 != 0 ? "Disable" : "Enable");

        buttonList.add(onOff);
    }


    @Override
    protected void actionPerformed(GuiButton button)
    {
        switch (button.id)
        {
            case 0:
                NetworkHandler.sendToServer(new MessageHandleGuiButtonPress(teMachine, 0));
                button.displayString = button.displayString.equals("Disable") ? "Enable" : "Disable";
                break;
        }
    }

    protected int getLeft()
    {
        return guiLeft;
    }

    protected int getTop()
    {
        return guiTop;
    }

    @Override
    protected void mouseClicked(int x, int y, int button)
    {
        super.mouseClicked(x, y, button);

        for (int i = 0; i < rectangles.length; i++)
        {
            if (rectangles[i].inRect(this, x, y))
            {
                NetworkHandler.sendToServer(new MessageSetMachineBoolean(teMachine, i));
                break;
            }
        }
    }
}
