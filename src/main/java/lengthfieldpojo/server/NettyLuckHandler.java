package lengthfieldpojo.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lengthfieldpojo.common.LuckMessage;

/**
 * Created by Daifa on 2018/2/8.
 */
public class NettyLuckHandler extends SimpleChannelInboundHandler<LuckMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LuckMessage msg) throws Exception {
        // 简单地打印出server接收到的消息
        System.out.println(msg.toString());
    }

//    @Override
//    public void channelReadComplete(ChannelHandlerContext ctx) {
//        System.out.println("channelReadComplete");
//       // ctx.flush();
//    }
}

