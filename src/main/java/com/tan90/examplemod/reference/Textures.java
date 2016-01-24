package com.tan90.examplemod.reference;

import net.minecraft.util.ResourceLocation;

public final class Textures
{
    public static final String RESOURCE_PREFIX = Reference.LOWERCASE_MOD_ID + ":";
//    public static final String PARTICLES_FOLDER = "textures/particles/";

    public static final class Blocks
    {
        public static final String MODULAR_SLAVE = RESOURCE_PREFIX + "modularStorageSlave";
        public static final String MODULAR_MASTER = RESOURCE_PREFIX + "modularStorageMaster";
        public static final String MAGMA_WELDER_SIDE = RESOURCE_PREFIX + "magmaWelderSide";
        public static final String MAGMA_WELDER_FRONT = RESOURCE_PREFIX + "magmaWelderFront";


    }

    public static final class Entities
    {
        public static final String MODEL_LOCATION = "textures/models/";
        public static final ResourceLocation DROID = new ResourceLocation(Reference.LOWERCASE_MOD_ID, MODEL_LOCATION + "droid.png");
    }

    public static final class Particle
    {
        public static final String ASH_FX = RESOURCE_PREFIX + "particleAsh";
    }

    public static final class Guis
    {
        public static final ResourceLocation BACKPACK = new ResourceLocation(Reference.LOWERCASE_MOD_ID, "textures/gui/guiBackpack.png");
        public static final ResourceLocation MAGMA_WELDER = new ResourceLocation(Reference.LOWERCASE_MOD_ID, "textures/gui/magmaWelder.png");
    }
}
