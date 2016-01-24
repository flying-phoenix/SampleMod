package com.tan90.examplemod.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityDroid extends Entity
{
    private double startY;
    private double targetY;
    private float coreRotation;
    private float panelRotation;
    private float outerPanelRotation = -(float) Math.PI / 2;
    private float capPosition;
    private float redColor;
    private float greenColor;
    private float blueColor;


    public float getBlueColor()
    {
        return blueColor;
    }

    public float getGreenColor()
    {
        return greenColor;
    }

    public float getRedColor()
    {
        return redColor;
    }

    public EntityDroid(World world)
    {
        super(world);

        redColor = world.rand.nextFloat();
        greenColor = world.rand.nextFloat();
        blueColor = world.rand.nextFloat();

    }

    public EntityDroid(World world, double x, double y, double z)
    {
        this(world);
        posX = x;
        posY = y;
        startY = y;
        posZ = z;
    }

    public float getCoreRotation()
    {
        return coreRotation;
    }

    @Override
    protected void entityInit()
    {
        dataWatcher.addObject(20, (byte) 0);
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound tag)
    {
        startY = tag.getDouble("startY");
        targetY = tag.getDouble("targetY");
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound tag)
    {
        tag.setDouble("startY", startY);
        tag.setDouble("targetY", targetY);
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        if (!worldObj.isRemote)
        {
            if (targetY == 0 || Math.abs(posY - targetY) < 0.25)
            {
                targetY = startY + worldObj.rand.nextDouble() * 5;
            }

            if (posY < targetY)
            {
                motionY = 0.05;
            } else
            {
                motionY = -0.05;
            }

            boolean light = worldObj.getBlockLightValue((int) posX, (int) posY, (int) posZ) == 15;
            dataWatcher.updateObject(20, light ? (byte) 1 : (byte) 0);
        } else
        {
            coreRotation += 0.05F;
            capPosition += 0.02F;

            if (dataWatcher.getWatchableObjectByte(20) != 0)
            {
                panelRotation = Math.min((float) Math.PI / 2, panelRotation + 0.02F);
                if (panelRotation == (float) Math.PI / 2)
                {
                    outerPanelRotation = Math.min(0, outerPanelRotation + 0.02F);
                }
            } else
            {
                outerPanelRotation = Math.max(-(float) Math.PI / 2, outerPanelRotation - 0.02F);
                if (outerPanelRotation == -(float) Math.PI / 2)
                {
                    panelRotation = Math.max(0, panelRotation - 0.02F);
                }
            }

        }

        setPosition(posX + motionX, posY + motionY, posZ + motionZ);
    }

    public float getPanelRotation()
    {
        return panelRotation;
    }

    public float getOuterPanelRotation()
    {
        return outerPanelRotation;
    }

    public float getCapPosition()
    {
        return (float) (-6 - Math.abs(Math.sin(capPosition)) * 5.5F);
    }
}
