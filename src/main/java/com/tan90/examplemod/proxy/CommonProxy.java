package com.tan90.examplemod.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import java.util.HashMap;

public class CommonProxy
{
    private HashMap<String, NBTTagCompound> playerData = new HashMap<String, NBTTagCompound>();

    public void initRenderingAndTextures()
    {
        //NOOP
    }

    public void registerEventHandlers()
    {
        //NOOP
    }

    public void registerKeybindings()
    {
        //NOOP
    }

    public void setPlayerData(String key, NBTTagCompound tag)
    {
        playerData.put(key, tag);
    }

    public NBTTagCompound getPlayerData(String key)
    {
        return playerData.get(key);
    }

    public EntityPlayer getClientPlayer()
    {
        return null;
    }
}
