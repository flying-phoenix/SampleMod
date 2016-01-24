package com.tan90.examplemod.item;

import com.tan90.examplemod.entity.EntityDroid;
import com.tan90.examplemod.reference.Names;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemDroid extends ItemExampleMod
{
    public ItemDroid()
    {
        super();
        this.setUnlocalizedName(Names.Items.DROID);
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        if (!world.isRemote)
        {
            world.spawnEntityInWorld(new EntityDroid(world, x + 0.5, y + 1.5, z + 0.5));
            stack.stackSize--;
            return true;
        } else
        {
            return false;
        }
    }
}
