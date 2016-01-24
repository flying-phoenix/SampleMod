package com.tan90.examplemod.client.gui;

import com.tan90.examplemod.inventory.ContainerMine;
import com.tan90.examplemod.network.MessageHandleGuiButtonPress;
import com.tan90.examplemod.network.MessageHandleTextUpdate;
import com.tan90.examplemod.network.NetworkHandler;
import com.tan90.examplemod.tileentity.TileEntityMine;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import org.lwjgl.opengl.GL11;

public class GuiMine extends GuiExampleMod
{
    private TileEntityMine te;
    private GuiButton buttonReset;
    private GuiButton buttonRestart;
    private GuiTextField textField;

    public GuiMine(InventoryPlayer playerInventory, TileEntityMine te)
    {
        super(new ContainerMine(playerInventory, te), "blockMine", te);
        this.te = te;
    }

    @Override
    public void initGui()
    {
        super.initGui();
        buttonReset = new GuiButton(0, guiLeft + 5, guiTop + 5, 40, 20, I18n.format("gui.examplemod.mine.button.reset"));
        buttonRestart = new GuiButton(1, guiLeft + 5, guiTop + 25, 40, 20, I18n.format("gui.examplemod.mine.button.restart"));

        this.buttonList.add(buttonReset);
        this.buttonList.add(buttonRestart);

        this.textField = new GuiTextField(this.fontRendererObj, guiLeft + 100, guiTop + 58, 70, 12);
        this.textField.setMaxStringLength(40);
        this.textField.setText(te.getTarget());
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char chr, int keyCode)
    {
        if (this.textField.textboxKeyTyped(chr, keyCode))
        {
            NetworkHandler.sendToServer(new MessageHandleTextUpdate(te, 0, this.textField.getText()));
        } else
        {
            super.keyTyped(chr, keyCode);
        }
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int p_73864_1_, int p_73864_2_, int p_73864_3_)
    {
        super.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
        this.textField.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_)
    {
        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_BLEND);
        this.textField.drawTextBox();
    }

    @Override
    public void updateScreen()
    {
        int timer = te.getTimer();
        super.updateScreen();
        buttonReset.enabled = timer != -1;
        buttonReset.displayString = timer != -1 ? "asdf" : "fdsa";
        buttonRestart.enabled = timer == -1;
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        switch (button.id)
        {
            case 0:
                NetworkHandler.sendToServer(new MessageHandleGuiButtonPress(te, 0));
                break;
            case 1:
                NetworkHandler.sendToServer(new MessageHandleGuiButtonPress(te, 1));
                break;
        }
    }

    @Override
    public void onTextFieldUpdate(int id)
    {
        switch (id)
        {
            case 0:
                this.textField.setText(te.getTarget());
                break;
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        int timer = te.getTimer();
        this.fontRendererObj.drawString(I18n.format("gui.examplemod.mine.timer", (timer == -1 ? 6 : (timer / 20))), 110, 72, (timer == 0 ? 0xFFFF0000 : 0xFF000000));
    }
}
