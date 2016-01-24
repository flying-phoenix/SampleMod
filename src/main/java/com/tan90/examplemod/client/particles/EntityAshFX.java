package com.tan90.examplemod.client.particles;

import com.tan90.examplemod.block.BlockAsh;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

public class EntityAshFX extends EntityFX
{
    public EntityAshFX(World world, double x, double y, double z, double motionX, double motionY, double motionZ)
    {
        super(world, x, y, z, motionX, motionY, motionZ);
        setParticleIcon(BlockAsh.ashParticle);

        particleScale = Math.min(0.7F + rand.nextFloat(), 1F);
        particleAlpha = 1;

        particleRed = rand.nextFloat() * 0.5F + 0.5F;
        particleGreen = rand.nextFloat() * 0.5F;
        particleBlue = rand.nextFloat() * 0.5F;
        ;


    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

    }

    @Override
    public int getFXLayer()
    {
        return 1;
    }
}
