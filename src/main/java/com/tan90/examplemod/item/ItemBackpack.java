package com.tan90.examplemod.item;

import com.tan90.examplemod.ExampleMod;
import com.tan90.examplemod.reference.Guis;
import com.tan90.examplemod.reference.Names;
import com.tan90.examplemod.reference.TextColors;
import com.tan90.examplemod.util.LogHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.List;
import java.util.UUID;

public class ItemBackpack extends ItemExampleMod
{
    public ItemBackpack()
    {
        super();
        this.setUnlocalizedName(Names.Items.BACKPACK);
        this.setMaxStackSize(1);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        if(!world.isRemote)
        {
            boolean firstUse = false;

            if (stack.getTagCompound() == null)
            {
                stack.setTagCompound(new NBTTagCompound());
                firstUse = true;
            }

            NBTTagCompound stackTag = stack.getTagCompound();

            if (!stackTag.hasKey("owner"))
            {
                stackTag.setString("owner", player.getCommandSenderName());
                firstUse = true;
            }

            if(!stackTag.hasKey("UUID"))
            {
                stackTag.setString("UUID", UUID.randomUUID().toString());
                firstUse = true;
            }

            if(!firstUse)
            {
                player.openGui(ExampleMod.instance, Guis.ITEM_BACKPACK.ordinal(), world, (int) player.posX, (int) player.posY, (int) player.posZ);
            }
        }

        return stack;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean parBool)
    {
        if(stack != null && stack.getTagCompound() != null)
        {
            NBTTagCompound stackTag = stack.getTagCompound();

            if (stackTag.hasKey("owner"))
            {
                list.add(TextColors.ORANGE + "Owner: " + stackTag.getString("owner"));
            }
        }
    }
}
