package com.tan90.examplemod.client.renderer;

import com.tan90.examplemod.client.model.ModelDroid;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class RenderDroidItem implements IItemRenderer
{

    private ModelDroid model;

    public RenderDroidItem(ModelDroid model)
    {
        this.model = model;
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
    {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data)
    {
        switch (type)
        {
            case EQUIPPED:
                GL11.glTranslatef(0.4F, 0.5F, 0.4F);
                break;
            case EQUIPPED_FIRST_PERSON:
                GL11.glTranslatef(0, 0.2F, 0);
                break;
            case INVENTORY:
                GL11.glTranslatef(0, 0.12F, 0);
                break;
            default:
        }

        GL11.glPushMatrix();

        GL11.glScalef(-1F, -1F, 1F);
        Minecraft.getMinecraft().getTextureManager().bindTexture(RenderDroid.texture);
        model.render(null, 0F, 0F, -(float) Math.PI / 2, -6, 0, item.stackSize / 64, 0, 0.0625F);

        GL11.glPopMatrix();
    }
}
