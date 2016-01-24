package com.tan90.examplemod.util;

import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.common.util.ForgeDirection;

public class ExampleModUtil
{
    public static ForgeDirection getDirectionFacing(EntityLivingBase entity, boolean faceUpDown)
    {
        if (faceUpDown)
        {
            double pitch = entity.rotationPitch;
            if (pitch > 45)
            {
                return ForgeDirection.DOWN;
            } else if (pitch < -45)
            {
                return ForgeDirection.UP;
            }
        }

        double yaw = entity.rotationYaw;

        while (yaw < 0)
        {
            yaw += 360;
        }
        yaw = yaw % 360;

        if(yaw < 45)
        {
            return ForgeDirection.SOUTH;
        }
        else if(yaw < 135)
        {
            return ForgeDirection.WEST;
        }
        else if(yaw < 225)
        {
            return ForgeDirection.NORTH;
        }else if(yaw < 315)
        {
            return ForgeDirection.EAST;
        }
        return ForgeDirection.SOUTH;
    }
}
