package com.tan90.examplemod.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class MessageExplode extends MessageBase<MessageExplode>
{

    private float expPower;

    public MessageExplode()
    {

    }

    public MessageExplode(float expPower)
    {
        this.expPower = expPower;
    }

    @Override
    protected void handleClientSide(MessageExplode message, EntityPlayer player)
    {

    }

    @Override
    protected void handleServerSide(MessageExplode message, EntityPlayer player)
    {
        player.worldObj.createExplosion(player, player.posX, player.posY - 10, player.posZ, message.expPower, true);
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        expPower = buf.readFloat();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeFloat(expPower);
    }
}
