package lengthfieldpojo.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lengthfieldpojo.common.LuckDecoder;
import lengthfieldpojo.common.LuckEncoder;
import lengthfieldstring.ServerHandler;

/**
 * Created by Daifa on 2018/2/8.
 */
public class NettyLuckInitializer extends ChannelInitializer<SocketChannel> {

    private static final LuckEncoder ENCODER = new LuckEncoder();

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {

        ChannelPipeline pipeline = channel.pipeline();

        // 添加编解码器, 由于ByteToMessageDecoder的子类无法使用@Sharable注解,
        // 这里必须给每个Handler都添加一个独立的Decoder.
        pipeline.addLast(new LengthFieldBasedFrameDecoder(10240, 0, 2, 0, 2))
                .addLast(new LuckDecoder())
                .addLast(new LengthFieldPrepender(2))
                .addLast(ENCODER)
                .addLast(new NettyLuckHandler());
    }
}
