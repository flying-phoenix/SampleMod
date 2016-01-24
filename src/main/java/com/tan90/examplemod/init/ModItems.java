package com.tan90.examplemod.init;

import com.tan90.examplemod.item.*;
import com.tan90.examplemod.reference.Names;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModItems
{
    public static final ItemExampleMod watchItem = new ItemWatch();
    public static final ItemExampleMod droidItem = new ItemDroid();
    public static final ItemExampleMod cardItem = new ItemCard();
    public static final ItemExampleMod backpackItem = new ItemBackpack();
    public static final ItemExampleMod wrenchItem = new ItemWrench();

    public static void init()
    {
        GameRegistry.registerItem(watchItem, Names.Items.WATCH);
        GameRegistry.registerItem(droidItem, Names.Items.DROID);
        GameRegistry.registerItem(cardItem, Names.Items.CARD);
        GameRegistry.registerItem(backpackItem, Names.Items.BACKPACK);
        GameRegistry.registerItem(wrenchItem, Names.Items.WRENCH);
    }
}
