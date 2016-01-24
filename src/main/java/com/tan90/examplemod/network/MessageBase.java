package com.tan90.examplemod.network;

import com.tan90.examplemod.ExampleMod;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;

public abstract class MessageBase<REQ extends IMessage> implements IMessage, IMessageHandler<REQ, REQ>
{
    @Override
    public REQ onMessage(REQ message, MessageContext ctx)
    {
        if (ctx.side == Side.SERVER)
        {
            handleServerSide(message, ctx.getServerHandler().playerEntity);
        } else
        {
            handleClientSide(message, ExampleMod.proxy.getClientPlayer());
        }

        return null;
    }

    protected abstract void handleClientSide(REQ message, EntityPlayer clientPlayer);

    protected abstract void handleServerSide(REQ message, EntityPlayer playerEntity);
}
