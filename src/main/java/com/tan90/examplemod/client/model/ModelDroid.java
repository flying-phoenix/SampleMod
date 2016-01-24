package com.tan90.examplemod.client.model;

import com.tan90.examplemod.entity.EntityDroid;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public class ModelDroid extends ModelBase
{
    private ArrayList<ModelRenderer> modelParts;
    ModelRenderer core;
    private ArrayList<ModelRenderer> panels;
    private ArrayList<ModelRenderer> outerPanels;
    private ModelRenderer pillars;

    public ModelDroid()
    {
        modelParts = new ArrayList<ModelRenderer>();
        textureHeight = 64;
        textureWidth = 64;

        ModelRenderer main = new ModelRenderer(this);
        main.addBox(-5, -5, -5, 10, 10, 10);
        main.setRotationPoint(0, 0, 0);
        modelParts.add(main);

        pillars = new ModelRenderer(this, 0, 20);
        for (int x = -1; x <= 1; x += 2)
        {
            for (int z = -1; z <= 1; z += 2)
            {
                pillars.addBox(-1 + x * 3.995F, -1, -1 + z * 3.995F, 2, 8, 2);
            }
        }
        pillars.setRotationPoint(0, -6, 0);
        modelParts.add(pillars);

        ModelRenderer cap = new ModelRenderer(this, 0, 20);
        cap.addBox(-5, -1, -5, 10, 2, 10);
        cap.setRotationPoint(0, -2, 0);
        pillars.addChild(cap);

        panels = new ArrayList<ModelRenderer>();
        outerPanels = new ArrayList<ModelRenderer>();
        for (float r = 0; r <= Math.PI * 2; r += Math.PI / 2)
        {
            ModelRenderer side = new ModelRenderer(this, 0, 32);
            side.setRotationPoint(0, 0, 0);
            side.addBox(-4, -2.5F, -6F, 8, 5, 1);
            side.rotateAngleY = r;
            modelParts.add(side);

            ModelRenderer panel = new ModelRenderer(this, 18, 32);
            panel.addBox(-4, -0.5F, -0.5F, 8, 5, 1);
            panel.setRotationPoint(0, -2, 6.5F);
            side.addChild(panel);
            panels.add(panel);

            ModelRenderer panelOuter = new ModelRenderer(this, 18, 32);
            panelOuter.addBox(-4, -0.5F, -0.5F, 8, 5, 1);
            panelOuter.setRotationPoint(0, 5, 0);
            panel.addChild(panelOuter);
            outerPanels.add(panelOuter);
        }

        core = new ModelRenderer(this, 30, 0);
        core.addBox(-3, -1, -3, 6, 2, 6);
        core.setRotationPoint(0, -6, 0);
    }

    public void render(Entity entity, float coreRotation, float panelRotation, float panelOuterRotation, float capPosition, float r, float g, float b, float mult)
    {

        EntityDroid droid = (EntityDroid) entity;

        core.rotateAngleY = coreRotation;

        for (ModelRenderer panel : panels)
        {
            panel.rotateAngleX = panelRotation;
        }

        for (ModelRenderer panelOuter : outerPanels)
        {
//                panelOuter.rotateAngleX = droid.getPanelRotation() - (float) Math.PI / 2;
            panelOuter.rotateAngleX = panelOuterRotation;
        }

        pillars.rotationPointY = capPosition;


        GL11.glColor4f(1F, 1F, 1F, 1F);
        for (ModelRenderer part : modelParts)
        {
            part.render(mult);
        }
        GL11.glColor4f(r, g, b, 1F);

        core.render(mult);
    }
}
