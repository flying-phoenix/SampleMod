package com.tan90.examplemod.client.gui;

import com.tan90.examplemod.entity.ExtendedPlayer;
import com.tan90.examplemod.util.LogHelper;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.Tessellator;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import org.lwjgl.opengl.GL11;

import java.lang.annotation.ElementType;


@SideOnly(Side.CLIENT)
public class GuiSlotNumberBar extends Gui
{
    private Minecraft mc;
    private final int BAR_WIDTH = 15;
    private final int BAR_HEIGTH = 50;
    private final int BAR_X = 5;
    private final int BAR_Y = 5;

    public GuiSlotNumberBar(Minecraft mc)
    {
        super();
        this.mc = mc;
    }

    public void sync()
    {
        renderBar();
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onRenderGuiOverlay(RenderGameOverlayEvent event)
    {
        renderBar();
    }

    public void renderBar()
    {
        int currSlots = ExtendedPlayer.get(mc.thePlayer).getCurrSlots();
        int maxSlots = ExtendedPlayer.get(mc.thePlayer).getMAX_SLOTS();

        Tessellator tess = Tessellator.instance;

        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glColor4f(0.3F, 0.3F, 0.3F, 1);
        tess.startDrawingQuads();
        tess.addVertex(BAR_X, BAR_Y, 0);
        tess.addVertex(BAR_X, BAR_Y + BAR_HEIGTH, 0);
        tess.addVertex(BAR_X + BAR_WIDTH, BAR_Y + BAR_HEIGTH, 0);
        tess.addVertex(BAR_X + BAR_WIDTH, BAR_Y, 0);
        tess.draw();


        tess.startDrawingQuads();
        GL11.glColor4f(0.3F, 0.8F, 0.3F, 1);
        tess.addVertex(BAR_X, BAR_Y + BAR_HEIGTH, 0);
        tess.addVertex(BAR_X + BAR_WIDTH, BAR_Y + BAR_HEIGTH, 0);
        tess.addVertex(BAR_X + BAR_WIDTH, (BAR_Y + BAR_HEIGTH) - BAR_HEIGTH * currSlots / (float) maxSlots , 0);
        tess.addVertex(BAR_X, (BAR_Y + BAR_HEIGTH) - BAR_HEIGTH * currSlots / (float) maxSlots , 0);

//        tess.addVertex(BAR_X + BAR_HEIGTH, BAR_Y + BAR_HEIGTH, 0);
//        tess.addVertex(BAR_X + BAR_WIDTH, BAR_Y + BAR_HEIGTH * currSlots / (float) maxSlots, 0);
//        tess.addVertex(BAR_X + BAR_WIDTH, BAR_Y + (BAR_HEIGTH -  BAR_HEIGTH * currSlots / (float) maxSlots), 0);
//        tess.addVertex(BAR_X + BAR_WIDTH, BAR_Y, 0);
        tess.draw();

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glColor4f(1, 1, 1, 1);

        GL11.glPopMatrix();
    }
}
