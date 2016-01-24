package com.tan90.examplemod.network;

import com.tan90.examplemod.entity.ExtendedPlayer;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class MessageSetSlotNumber extends MessageBase<MessageSetSlotNumber>
{
    private int slotNumber;

    public MessageSetSlotNumber()
    {
    }

    public MessageSetSlotNumber(int slotNumber)
    {

        this.slotNumber = slotNumber;
    }

    @Override
    protected void handleClientSide(MessageSetSlotNumber message, EntityPlayer clientPlayer)
    {
        ExtendedPlayer.get(clientPlayer).setCurrSlots(message.slotNumber);
    }

    @Override
    protected void handleServerSide(MessageSetSlotNumber message, EntityPlayer playerEntity)
    {
        //NOOP
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.slotNumber = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(slotNumber);
    }
}
