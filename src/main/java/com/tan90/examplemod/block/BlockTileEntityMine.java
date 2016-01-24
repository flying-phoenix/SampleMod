package com.tan90.examplemod.block;

import com.tan90.examplemod.ExampleMod;
import com.tan90.examplemod.reference.Guis;
import com.tan90.examplemod.reference.Names;
import com.tan90.examplemod.tileentity.TileEntityMine;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockTileEntityMine extends BlockTileEntityExampleMod
{
    public BlockTileEntityMine()
    {
        super(Material.anvil);
        this.setBlockName(Names.Blocks.MINE);
        this.setHardness(0.7F);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata)
    {
        return new TileEntityMine();
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack)
    {
        super.onBlockPlacedBy(world, x, y, z, player, stack);
        TileEntityMine te = (TileEntityMine) world.getTileEntity(x, y, z);
        te.setOwner(player.getCommandSenderName());
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
        if (!world.isRemote)
        {
            TileEntityMine te = (TileEntityMine) world.getTileEntity(x, y, z);
            if (player.isSneaking())
            {
                player.openGui(ExampleMod.instance, Guis.BLOCK_MINE.ordinal(), world, x, y, z);
            } else
            {
                if (te.getCamouflageStack(side) != null)
                {
                    ItemStack camoStack = te.getCamouflageStack(side);
                    te.setCamouflageStack(null, side);
                    EntityItem itemEntity = new EntityItem(world, x, y, z, camoStack);
                    world.spawnEntityInWorld(itemEntity);
                } else
                {
                    ItemStack playerItem = player.getHeldItem();
                    if (playerItem != null)
                    {
                        ItemStack camoStack = playerItem.splitStack(1);
                        te.setCamouflageStack(camoStack, side);
                    }
                }
            }
        }
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side)
    {
        TileEntityMine te = (TileEntityMine) world.getTileEntity(x, y, z);
        ItemStack camouflageStack = te.getCamouflageStack(side);

        if (camouflageStack == null || !(camouflageStack.getItem() instanceof ItemBlock))
        {
            return super.getIcon(world, x, y, z, side);
        } else
        {
            Block block = ((ItemBlock) camouflageStack.getItem()).field_150939_a;
            return block.getIcon(side, camouflageStack.getItemDamage());
        }
    }
}
