package success;

/**
 * Created by zhoudf2 on 2018-2-9.
 */
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class BusinessServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Receive msg");
        ByteBuf buf = (ByteBuf)msg;
        int length = buf.readInt();
        assert length == (8);

        byte[] head = new byte[4];
        buf.readBytes(head);
        String headString = new String(head);
        assert  "head".equals(headString);

        byte[] body = new byte[4];
        buf.readBytes(body);
        String bodyString = new String(body);
        assert  "body".equals(bodyString);
    }
}