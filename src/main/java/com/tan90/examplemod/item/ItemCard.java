package com.tan90.examplemod.item;

import com.tan90.examplemod.reference.Names;
import com.tan90.examplemod.reference.Reference;
import com.tan90.examplemod.reference.Textures;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;

import java.util.List;

public class ItemCard extends ItemExampleMod
{
    private IIcon[] icons;

    public ItemCard()
    {
        super();
        this.setMaxStackSize(16);
        this.setHasSubtypes(true);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs creativeTabs, List list)
    {
        for (int dmg = 0; dmg < Names.Items.CARD_SUBTYPES.length; dmg++)
        {
            list.add(new ItemStack(this, 1, dmg));
        }
    }

    @Override
    public void registerIcons(IIconRegister iconRegister)
    {
        icons = new IIcon[Names.Items.CARD_SUBTYPES.length + 1];
        icons[0] = iconRegister.registerIcon(Textures.RESOURCE_PREFIX + Names.Items.CARD);

        for (int i = 0; i < Names.Items.CARD_SUBTYPES.length; i++)
        {
            icons[i + 1] = iconRegister.registerIcon(Textures.RESOURCE_PREFIX + Names.Items.CARD + Names.Items.CARD_SUBTYPES[i]);
        }
    }

    @Override
    public String getUnlocalizedName()
    {
        return "item." + Reference.LOWERCASE_MOD_ID + ":" + Names.Items.CARD;
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack)
    {
        return "item." + Reference.LOWERCASE_MOD_ID + ":" + Names.Items.CARD + "." + Names.Items.CARD_SUBTYPES[itemStack.getItemDamage()];
    }


    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }

    @Override
    public IIcon getIcon(ItemStack stack, int renderPass)
    {
        if (renderPass == 0)
        {
            return icons[0];
        } else
        {
            return icons[1 + MathHelper.clamp_int(stack.getItemDamage(), 0, 3)];
        }
    }
}
