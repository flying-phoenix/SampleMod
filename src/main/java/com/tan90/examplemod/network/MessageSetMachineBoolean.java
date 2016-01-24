package com.tan90.examplemod.network;

import com.tan90.examplemod.tileentity.TileEntityMachine;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class MessageSetMachineBoolean extends MessageBase<MessageSetMachineBoolean>
{
    private int x;
    private int y;
    private int z;
    private int index;


    public MessageSetMachineBoolean()
    {

    }

    public MessageSetMachineBoolean(TileEntityMachine te, int index)
    {
        this.x = te.xCoord;
        this.y = te.yCoord;
        this.z = te.zCoord;
        this.index = index;
    }

    @Override
    protected void handleClientSide(MessageSetMachineBoolean message, EntityPlayer clientPlayer)
    {
    }

    @Override
    protected void handleServerSide(MessageSetMachineBoolean message, EntityPlayer playerEntity)
    {
        TileEntity te = playerEntity.worldObj.getTileEntity(message.x, message.y, message.z);
        if (te instanceof TileEntityMachine)
        {
            ((TileEntityMachine) te).setCustomSetup(message.index);
        }
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
        index = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeInt(index);
    }
}
