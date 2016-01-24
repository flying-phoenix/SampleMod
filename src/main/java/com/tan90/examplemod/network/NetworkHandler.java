package com.tan90.examplemod.network;

import com.tan90.examplemod.reference.Reference;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayerMP;

public class NetworkHandler
{
    private static SimpleNetworkWrapper INSTANCE;

    public static void init()
    {
        INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID);

        INSTANCE.registerMessage(MessageExplode.class, MessageExplode.class, 0, Side.SERVER);
        INSTANCE.registerMessage(MessageHandleGuiButtonPress.class, MessageHandleGuiButtonPress.class, 1, Side.SERVER);
        INSTANCE.registerMessage(MessageHandleTextUpdate.class, MessageHandleTextUpdate.class, 2, Side.CLIENT);
        INSTANCE.registerMessage(MessageHandleTextUpdate.class, MessageHandleTextUpdate.class, 3, Side.SERVER);
        INSTANCE.registerMessage(MessageSetMachineBoolean.class, MessageSetMachineBoolean.class, 4, Side.SERVER);
        INSTANCE.registerMessage(MessageSetSlotNumber.class, MessageSetSlotNumber.class, 5, Side.CLIENT);
    }

    public static void sendToServer(IMessage message)
    {
        INSTANCE.sendToServer(message);
    }

    public static void sendTo(IMessage message, EntityPlayerMP playerMP)
    {
        INSTANCE.sendTo(message, playerMP);
    }

    public static void sendToAllAround(IMessage message, NetworkRegistry.TargetPoint point)
    {
        INSTANCE.sendToAllAround(message, point);
    }

    public static void sendToAll(IMessage message)
    {
        INSTANCE.sendToAll(message);
    }

    public static void sendToDimension(IMessage message, int dimensionID)
    {
        INSTANCE.sendToDimension(message, dimensionID);
    }
}
