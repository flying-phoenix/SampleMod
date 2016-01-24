package com.tan90.examplemod.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import java.util.HashMap;

public class ServerProxy extends CommonProxy
{
    public HashMap<String, NBTTagCompound> extendedPlayerData = new HashMap<String, NBTTagCompound>();

    @Override
    public void initRenderingAndTextures()
    {
        //NOOP
    }

    @Override
    public void registerEventHandlers()
    {
        //NOOP
    }

    @Override
    public void registerKeybindings()
    {
        //NOOP
    }

    @Override
    public EntityPlayer getClientPlayer()
    {
        return null;
    }
}
