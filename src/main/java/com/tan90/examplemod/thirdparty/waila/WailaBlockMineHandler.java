package com.tan90.examplemod.thirdparty.waila;

import com.tan90.examplemod.init.ModBlocks;
import com.tan90.examplemod.tileentity.TileEntityMine;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.List;

public class WailaBlockMineHandler implements IWailaDataProvider
{
    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        TileEntityMine te = (TileEntityMine) accessor.getTileEntity();
        ItemStack stack = te.getStackInSlot(accessor.getSide().ordinal());

        if (!te.getTarget().equalsIgnoreCase("all") && (stack == null || accessor.getPlayer().getCommandSenderName().equalsIgnoreCase(te.getOwner())))
        {
            return new ItemStack(ModBlocks.mineBlock);
        }
        return stack;
    }

    @Override
    public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        return currenttip;
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        TileEntityMine te = (TileEntityMine) accessor.getTileEntity();
        if (!te.getTarget().equalsIgnoreCase(""))
        {
            currenttip.add(I18n.format("waila.examplemod.minetarget", accessor.getNBTData().getString("target")));
        }
        currenttip.add(I18n.format("waila.examplemod.mineowner", accessor.getNBTData().getString("owner")));
        currenttip.add(I18n.format("waila.examplemod.minetimer", accessor.getNBTData().getInteger("timer") / 20));
        return currenttip;
    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        return currenttip;
    }

    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x, int y, int z)
    {
        TileEntityMine tile = (TileEntityMine) te;
        tag.setString("target", tile.getTarget());
        tag.setString("owner", tile.getOwner());
        tag.setInteger("timer", tile.getTimer());
        return tag;
    }
}
