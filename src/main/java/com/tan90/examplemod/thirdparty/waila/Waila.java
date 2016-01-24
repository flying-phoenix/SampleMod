package com.tan90.examplemod.thirdparty.waila;

import com.tan90.examplemod.block.BlockTileEntityMine;
import mcp.mobius.waila.api.IWailaRegistrar;

public class Waila
{
    public static void onWailaCall(IWailaRegistrar registrar)
    {
        registrar.registerStackProvider(new WailaBlockMineHandler(), BlockTileEntityMine.class);
        registrar.registerBodyProvider(new WailaBlockMineHandler(), BlockTileEntityMine.class);
        registrar.registerNBTProvider(new WailaBlockMineHandler(), BlockTileEntityMine.class);
    }
}
