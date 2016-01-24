package com.tan90.examplemod.reference;

public class Names
{
    public static final class Blocks
    {
        public static final String ASH = "ashBlock";
        public static final String MINE = "mineBlock";
        public static final String MODULAR_STORAGE = "modularStorageBlock";
        public static final String TREE_FARM = "treeFarmBlock";
        public static final String MACHINE = "machineBlock";
        public static final String MAGMA_WELDER = "magmaWelderBlock";
    }

    public static final class Items
    {
        public static final String WATCH = "watchItem";
        public static final String DROID = "droidItem";
        public static final String CARD = "cardBase";
        public static final String[] CARD_SUBTYPES = {"Box", "Cross", "Square", "Custom"};
        public static final String BACKPACK = "backpack";
        public static final String WRENCH = "wrench";
    }

    public static final class Keys
    {
        public static final String CATEGORY = "keys.examplemod.category";
        public static final String EXPLODE = "keys.examplemod.explode";
        public static final String BIG_EXPLODE = "keys.examplemod.bigexplode";
    }

    public static final class TileEntityIds
    {
        public static final String MINE = teNameHelper("teMine");
        public static final String MODULAR_STORAGE = teNameHelper("teModularStorage");
        public static final String TREE_FARM = teNameHelper("teTreeFarm");
        public static final String MACHINE = teNameHelper("teMachine");
        public static final String MAGMA_WELDER = teNameHelper("teMagmaWelder");

        private static String teNameHelper(String name)
        {
            return Reference.LOWERCASE_MOD_ID + ":" + name;
        }
    }

    public static final class Enitity
    {
        public static final String DROID = "entityDroid";
    }

    public static final class ExtendedProperties
    {
        public static final String PLAYER = "ExtendedPlayer";
    }
}
