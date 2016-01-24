package com.tan90.examplemod.creativetab;

import com.tan90.examplemod.init.ModItems;
import com.tan90.examplemod.reference.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTabExampleMod
{
    public static final CreativeTabs EXAMPLE_MOD_TAB = new CreativeTabs(Reference.LOWERCASE_MOD_ID)
    {
        @Override
        public Item getTabIconItem()
        {
            return ModItems.watchItem;
        }
    };
}
