package com.tan90.examplemod.client.renderer;

import com.tan90.examplemod.client.model.ModelDroid;
import com.tan90.examplemod.entity.EntityDroid;
import com.tan90.examplemod.reference.Textures;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderDroid extends Render
{
    public static final ResourceLocation texture = Textures.Entities.DROID;
    private ModelDroid model;

    public RenderDroid(ModelDroid model)
    {
        this.model = model;
        shadowSize = 0.5F;
    }

    public void renderDroid(EntityDroid droid, double x, double y, double z, float yaw, float partialTickTime)
    {
        GL11.glPushMatrix();

        GL11.glTranslatef((float) x, (float) y, (float) z);
        GL11.glScalef(-1F, -1F, 1F);
        bindEntityTexture(droid);
        model.render(droid, droid.getCoreRotation(), droid.getPanelRotation(), droid.getOuterPanelRotation(), droid.getCapPosition(), droid.getRedColor(), droid.getGreenColor(), droid.getBlueColor(), 0.0625F);

        GL11.glPopMatrix();
    }

    @Override
    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTickTime)
    {
        renderDroid((EntityDroid) entity, x, y, z, yaw, partialTickTime);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return texture;
    }
}
