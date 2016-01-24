package com.tan90.examplemod.client.gui;

import org.lwjgl.opengl.GL11;

public class GuiRectangle
{
    protected int x;
    protected int y;
    protected int w;
    protected int h;

    public GuiRectangle(int x, int y, int w, int h)
    {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    protected void render(GuiMachine gui, int offsetX, int offsetY, int mouseX, int mouseY, boolean selected)
    {
        if (selected)
        {
            offsetX += 8;
        }
        if (inRect(gui, mouseX, mouseY))
        {
            gui.drawTexturedModalRect(gui.getLeft() + x, gui.getTop() + y, offsetX, offsetY, w, h);
            gui.drawTexturedModalRect(gui.getLeft() + x - 1, gui.getTop() + y - 1, offsetX + (selected ? 8 : 16), offsetY, w + 2, h + 2);
        } else if (inLine(gui, mouseX, mouseY))
        {
            GL11.glColor4f(0.8F, 0.8F, 0.8F, 1F);
            gui.drawTexturedModalRect(gui.getLeft() + x, gui.getTop() + y, offsetX, offsetY, w, h);
            GL11.glColor4f(1F, 1F, 1F, 1F);
        } else
        {
            gui.drawTexturedModalRect(gui.getLeft() + x, gui.getTop() + y, offsetX, offsetY, w, h);
        }
    }

    protected boolean inRect(GuiMachine gui, int mouseX, int mouseY)
    {
        mouseX -= gui.getLeft();
        mouseY -= gui.getTop();

        return mouseX >= x && mouseX < x + w && mouseY >= y && mouseY < y + h;
    }

    protected boolean inLine(GuiMachine gui, int mouseX, int mouseY)
    {
        mouseX -= gui.getLeft();
        mouseY -= gui.getTop();

        return mouseX >= 7 && mouseX < 7 + 9 * 7 && mouseY >= 55 && mouseY < 55 + 9 * 7 && (mouseX >= x && mouseX < x + w || mouseY >= y && mouseY < y + h);
    }
}