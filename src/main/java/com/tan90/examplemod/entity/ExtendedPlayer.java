package com.tan90.examplemod.entity;

import com.tan90.examplemod.network.MessageSetSlotNumber;
import com.tan90.examplemod.network.NetworkHandler;
import com.tan90.examplemod.reference.Names;
import com.tan90.examplemod.util.LogHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class ExtendedPlayer implements IExtendedEntityProperties
{

    private int currSlots;
    private final int MAX_SLOTS = 20;

    EntityPlayer player;
    World world;

    public int getCurrSlots()
    {
        return currSlots;
    }

    public ExtendedPlayer(EntityPlayer player, World world)
    {
        this.currSlots = 10;
        this.player = player;
        this.world = world;
    }

    public static final void register(EntityPlayer player, World world)
    {
        player.registerExtendedProperties(Names.ExtendedProperties.PLAYER, new ExtendedPlayer(player, world));
    }

    public static final ExtendedPlayer get(EntityPlayer player)
    {
        return (ExtendedPlayer) player.getExtendedProperties(Names.ExtendedProperties.PLAYER);
    }

    /* handling NBT **/

    @Override
    public void saveNBTData(NBTTagCompound compound)
    {
        LogHelper.info("Saving extended data to NBT.");
        NBTTagCompound properties = new NBTTagCompound();
        properties.setInteger("currSlots", currSlots);

        compound.setTag(Names.ExtendedProperties.PLAYER, properties);

    }

    @Override
    public void loadNBTData(NBTTagCompound compound)
    {
        NBTTagCompound properties = compound.getCompoundTag(Names.ExtendedProperties.PLAYER);
        this.currSlots = properties.getInteger("currSlots");
        LogHelper.info("Extended player properties -> currSlots = " + this.currSlots);

    }

    @Override
    public void init(Entity entity, World world)
    {

    }

    /* variable management */
    public void addSlot()
    {
        this.currSlots++;
    }

    public void setCurrSlots(int currSlots)
    {
        if (currSlots > 20)
        {
            this.currSlots = 20;
        } else if (currSlots < 0)
        {
            this.currSlots = 0;
        } else
        {
            this.currSlots = currSlots;
        }
        if (!world.isRemote)
        {
            NetworkHandler.sendTo(new MessageSetSlotNumber(currSlots), (net.minecraft.entity.player.EntityPlayerMP) player);
        }
    }

    public int getMAX_SLOTS()
    {
        return MAX_SLOTS;
    }
}
