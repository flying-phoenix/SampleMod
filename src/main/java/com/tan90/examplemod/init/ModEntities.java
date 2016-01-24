package com.tan90.examplemod.init;

import com.tan90.examplemod.ExampleMod;
import com.tan90.examplemod.entity.EntityDroid;
import com.tan90.examplemod.reference.Names;
import cpw.mods.fml.common.registry.EntityRegistry;

public class ModEntities
{
    public static void init()
    {
        EntityRegistry.registerModEntity(EntityDroid.class, Names.Enitity.DROID, 1, ExampleMod.instance, 80, 3, true);
    }
}
