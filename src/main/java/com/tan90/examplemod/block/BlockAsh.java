package com.tan90.examplemod.block;

import com.tan90.examplemod.client.particles.Particles;
import com.tan90.examplemod.reference.Names;
import com.tan90.examplemod.reference.Textures;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.Random;

public class BlockAsh extends BlockExampleMod
{
    public BlockAsh()
    {
        super(Material.clay);
        this.setBlockName(Names.Blocks.ASH);
        this.setHardness(0.5F);
        this.setTickRandomly(true);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
        if (world.isRemote) //(FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
        {
//            world.createExplosion(player, x, y, z, 1.0F, false);
        }
        world.playSoundEffect(x, y, z, "examplemod:heal", 1.0F, player.getRNG().nextFloat());
        return true;
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random random)
    {
        world.playSoundEffect(x, y, z, "mob.enderdragon.wings", 1.0F, random.nextFloat());
        //LogHelper.info(String.format("Sound played at: %d, %d, %d", x, y, z));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random random)
    {
//        for(int i = 0; i < 4; i++)
        {
            float particleX = x + random.nextFloat();
            float particleY = y + random.nextFloat();
            float particleZ = z + random.nextFloat();

            float particleMotionX = (-0.5F + random.nextFloat()) / 2;
            float particleMotionY = (-0.5F + random.nextFloat()) / 2;
            float particleMotionZ = (-0.5F + random.nextFloat()) / 2;

            Particles.ASH.spawnParticle(world, particleX, particleY, particleZ, particleMotionX, particleMotionY, particleMotionZ);
        }
    }

    @SideOnly(Side.CLIENT)
    public static IIcon ashParticle;

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        super.registerBlockIcons(iconRegister);

        ashParticle = iconRegister.registerIcon(Textures.Particle.ASH_FX);

    }
}
