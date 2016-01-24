package com.tan90.examplemod.network;

import com.tan90.examplemod.tileentity.TileEntityExampleMod;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class MessageHandleGuiButtonPress extends MessageBase<MessageHandleGuiButtonPress>
{
    private int x;
    private int y;
    private int z;
    private int id;

    public MessageHandleGuiButtonPress()
    {

    }

    public MessageHandleGuiButtonPress(TileEntityExampleMod te, int id)
    {
        this.x = te.xCoord;
        this.y = te.yCoord;
        this.z = te.zCoord;
        this.id = id;

    }

    @Override
    protected void handleClientSide(MessageHandleGuiButtonPress message, EntityPlayer clientPlayer)
    {

    }

    @Override
    protected void handleServerSide(MessageHandleGuiButtonPress message, EntityPlayer playerEntity)
    {
        TileEntity te = playerEntity.worldObj.getTileEntity(message.x, message.y, message.z);
        if (te instanceof TileEntityExampleMod)
        {
            ((TileEntityExampleMod) te).onGuiButtonPress(message.id);
        }
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
        id = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeInt(id);
    }
}
