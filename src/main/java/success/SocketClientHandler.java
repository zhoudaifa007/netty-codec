package success;

/**
 * Created by zhoudf2 on 2018-2-9.
 */
import io.netty.buffer.ByteBuf;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class SocketClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("连接已经建立2");
        UnpooledByteBufAllocator allocator = new UnpooledByteBufAllocator(false);
        ByteBuf buffer = allocator.buffer(20);
        buffer.writeInt(8);
        buffer.writeBytes("head".getBytes());
        buffer.writeBytes("body".getBytes());

        ctx.writeAndFlush(buffer);
        System.out.println("数据已经发送完成");
    }
}