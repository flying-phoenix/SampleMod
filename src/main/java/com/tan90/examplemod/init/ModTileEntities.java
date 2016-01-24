package com.tan90.examplemod.init;

import com.tan90.examplemod.reference.Names;
import com.tan90.examplemod.tileentity.*;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModTileEntities
{
    public static void init()
    {
        GameRegistry.registerTileEntity(TileEntityMine.class, Names.TileEntityIds.MINE);
        GameRegistry.registerTileEntity(TileEntityModularStorage.class, Names.TileEntityIds.MODULAR_STORAGE);
        GameRegistry.registerTileEntity(TileEntityTreeFarm.class, Names.TileEntityIds.TREE_FARM);
        GameRegistry.registerTileEntity(TileEntityMachine.class, Names.TileEntityIds.MACHINE);

    }
}
