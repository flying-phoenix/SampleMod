package com.tan90.examplemod.network;

import com.tan90.examplemod.client.gui.GuiExampleMod;
import com.tan90.examplemod.tileentity.TileEntityExampleMod;
import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class MessageHandleTextUpdate extends MessageBase<MessageHandleTextUpdate>
{
    public int x;
    private int y;
    private int z;
    private int id;
    private String text;

    public MessageHandleTextUpdate()
    {

    }

    public MessageHandleTextUpdate(TileEntityExampleMod te, int id, String text)
    {
        this.x = te.xCoord;
        this.y = te.yCoord;
        this.z = te.zCoord;
        this.id = id;
        this.text = text;

    }

    @Override
    protected void handleClientSide(MessageHandleTextUpdate message, EntityPlayer clientPlayer)
    {
        handleServerSide(message, clientPlayer);
        GuiScreen gui = Minecraft.getMinecraft().currentScreen;//<-- Warning, this will crash when tested on server, will be discussed next episode.
        if (gui instanceof GuiExampleMod)
        {
            ((GuiExampleMod) gui).onTextFieldUpdate(message.id);
        }
    }

    @Override
    protected void handleServerSide(MessageHandleTextUpdate message, EntityPlayer playerEntity)
    {
        TileEntity te = playerEntity.worldObj.getTileEntity(message.x, message.y, message.z);
        if (te instanceof TileEntityExampleMod)
        {
            ((TileEntityExampleMod) te).onTextFieldChange(message.id, message.text);
        }
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
        id = buf.readInt();
        text = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeInt(id);
        ByteBufUtils.writeUTF8String(buf, text);
    }
}
