package com.tan90.examplemod.init;

import com.tan90.examplemod.block.*;
import com.tan90.examplemod.reference.Names;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModBlocks
{
    public static final BlockExampleMod ashBlock = new BlockAsh();

    public static final BlockTileEntityMachine machineBlock = new BlockTileEntityMachine();
    public static final BlockTileEntityMine mineBlock = new BlockTileEntityMine();
    public static final BlockTileEntityModularStorage modularStorageBlock = new BlockTileEntityModularStorage();
    public static final BlockTileEntityTreeFarm treeFarmBlock = new BlockTileEntityTreeFarm();

    public static void init()
    {
        GameRegistry.registerBlock(ashBlock, Names.Blocks.ASH);
        GameRegistry.registerBlock(machineBlock, Names.Blocks.MACHINE);

        GameRegistry.registerBlock(mineBlock, Names.Blocks.MINE);
        GameRegistry.registerBlock(modularStorageBlock, Names.Blocks.MODULAR_STORAGE);
        GameRegistry.registerBlock(treeFarmBlock, Names.Blocks.TREE_FARM);
    }
}
