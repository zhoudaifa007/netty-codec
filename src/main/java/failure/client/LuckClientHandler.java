package failure.client;

import failure.common.LuckMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by Daifa on 2018/2/8.
 */
public class LuckClientHandler extends SimpleChannelInboundHandler<LuckMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LuckMessage message) throws Exception {
        System.out.println(message);
    }
}
