package lengthfieldpojo.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import lengthfieldpojo.common.LuckDecoder;
import lengthfieldpojo.common.LuckEncoder;
import lengthfieldpojo.server.NettyLuckHandler;

/**
 * Created by Daifa on 2018/2/8.
 */
public class LuckClientInitializer extends ChannelInitializer<SocketChannel> {

    private static final LuckEncoder ENCODER = new LuckEncoder();

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {

        ChannelPipeline pipeline = channel.pipeline();

        pipeline.addLast(new LengthFieldBasedFrameDecoder(10240, 0, 2, 0, 2))
                .addLast(new LuckDecoder())
                .addLast(new LengthFieldPrepender(2))
                .addLast(ENCODER)
                .addLast(new LuckClientHandler());
    }
}
