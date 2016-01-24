package com.tan90.examplemod.network;

import com.tan90.examplemod.ExampleMod;
import com.tan90.examplemod.reference.Reference;
import com.tan90.examplemod.tileentity.TileEntityExampleMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.minecraft.tileentity.TileEntity;

@Sharable
public class DescriptionPacketHandler extends SimpleChannelInboundHandler<FMLProxyPacket>
{
    public static final String CHANNEL = Reference.MOD_ID + "Description";

    static
    {
        NetworkRegistry.INSTANCE.newChannel(CHANNEL, new DescriptionPacketHandler());
    }

    public static void init()
    {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FMLProxyPacket msg) throws Exception
    {
        ByteBuf buf = msg.payload();
        int x = buf.readInt();
        int y = buf.readInt();
        int z = buf.readInt();
        TileEntity te = ExampleMod.proxy.getClientPlayer().worldObj.getTileEntity(x, y, z);

        if (te instanceof TileEntityExampleMod)
        {
            ((TileEntityExampleMod) te).readFromPacket(buf);
        }
    }
}
