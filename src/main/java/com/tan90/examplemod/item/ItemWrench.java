package com.tan90.examplemod.item;

import com.tan90.examplemod.api.IWrenchable;
import com.tan90.examplemod.reference.Names;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class ItemWrench extends ItemExampleMod
{
    public ItemWrench()
    {
        super();
        this.setUnlocalizedName(Names.Items.WRENCH);
        this.setMaxStackSize(1);
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        if(!world.isRemote)
        {
            Block block = world.getBlock(x, y, z);

            if (block instanceof IWrenchable)
            {
                if (player.isSneaking())
                {
                    ItemStack blockStack = new ItemStack(block.getItem(world, x, y, z), 1, 0);
                    world.spawnEntityInWorld(new EntityItem(world, x, y, z, blockStack));
                    world.setBlockToAir(x, y, z);
                } else
                {
                    if (((IWrenchable) block).canRotate())
                    {
                        if(!((IWrenchable) block).canRotateUpAndDown())
                        {
                            if(side == 0 || side == 1)
                            {
                                return false;
                            }
                        }
                        world.setBlockMetadataWithNotify(x, y, z, ForgeDirection.OPPOSITES[side], 2);
                    }

                }

                return true;
            }
        }

        return false;
    }
}
