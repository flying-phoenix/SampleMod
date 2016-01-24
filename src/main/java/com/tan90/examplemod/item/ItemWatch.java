package com.tan90.examplemod.item;

import com.tan90.examplemod.entity.ExtendedPlayer;
import com.tan90.examplemod.reference.Names;
import com.tan90.examplemod.util.LogHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemWatch extends ItemExampleMod
{
    public ItemWatch()
    {
        super();
        this.setUnlocalizedName(Names.Items.WATCH);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        if(!world.isRemote)
        {
            ExtendedPlayer properties = ExtendedPlayer.get(player);
            if (player.isSneaking())
            {
                properties.setCurrSlots(properties.getCurrSlots() - 1);
            } else
            {
                properties.setCurrSlots(properties.getCurrSlots() + 1);
            }

            LogHelper.info(String.valueOf(properties.getCurrSlots()));
        }
        return stack;
    }
}
