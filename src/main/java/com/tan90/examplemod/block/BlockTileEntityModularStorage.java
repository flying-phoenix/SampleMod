package com.tan90.examplemod.block;

import com.tan90.examplemod.reference.Names;
import com.tan90.examplemod.reference.Textures;
import com.tan90.examplemod.tileentity.TileEntityModularStorage;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockTileEntityModularStorage extends BlockTileEntityExampleMod
{
    public IIcon slaveTexture;
    public IIcon masterTexture;

    public BlockTileEntityModularStorage()
    {
        super(Material.wood);
        this.setBlockName(Names.Blocks.MODULAR_STORAGE);
        this.setHardness(0.5F);
    }


    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        slaveTexture = iconRegister.registerIcon(Textures.Blocks.MODULAR_SLAVE);
        masterTexture = iconRegister.registerIcon(Textures.Blocks.MODULAR_MASTER);
    }

    @Override
    public IIcon getIcon(int side, int metadata)
    {
        return metadata == 0 ? slaveTexture : masterTexture;
    }

    @Override
    public int colorMultiplier(IBlockAccess world, int x, int y, int z)
    {
        return 0xBBBBBB;
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
    {
        return new TileEntityModularStorage();
    }
}
