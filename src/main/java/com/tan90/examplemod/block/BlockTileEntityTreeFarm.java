package com.tan90.examplemod.block;

import com.tan90.examplemod.reference.Names;
import com.tan90.examplemod.tileentity.TileEntityTreeFarm;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockTileEntityTreeFarm extends BlockTileEntityExampleMod
{
    public BlockTileEntityTreeFarm()
    {
        super(Material.anvil);
        this.setBlockName(Names.Blocks.TREE_FARM);
        this.setHardness(1.0F);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata)
    {
        return new TileEntityTreeFarm();
    }
}
