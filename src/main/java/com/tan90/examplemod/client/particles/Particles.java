package com.tan90.examplemod.client.particles;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

public enum Particles
{
    ASH;

    public void spawnParticle(World world, double x, double y, double z, double motionX, double motionY, double motionZ)
    {
        Minecraft mc = Minecraft.getMinecraft();

        if (mc != null && mc.renderViewEntity != null && mc.effectRenderer != null)
        {
            int particleSetting = mc.gameSettings.particleSetting;
            double distanceX = mc.renderViewEntity.posX - x;
            double distanceY = mc.renderViewEntity.posY - y;
            double distanceZ = mc.renderViewEntity.posZ - z;

            double maxDistance = 16;

            if (distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ > maxDistance * maxDistance || particleSetting == 2)
            {
                return;
            }

            EntityFX particle = null;


            switch (this)
            {
                case ASH:
                    particle = new EntityAshFX(world, x, y, z, motionX, motionY, motionZ);
                    break;
            }


            if (particle != null)
            {
                switch (particleSetting)
                {

                    case 1:
                        mc.effectRenderer.addEffect(particle);
                        break;
                    case 0:
                        for (int i = 0; i < 2; i++)
                        {
                            mc.effectRenderer.addEffect(particle);
                        }
                        break;
                }
            }
        }
    }
}
