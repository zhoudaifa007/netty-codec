package failure.server;

import failure.common.LuckDecoder;
import failure.common.LuckEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

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
        pipeline.addLast(ENCODER);
        pipeline.addLast(new LuckDecoder());

        // 添加逻辑控制层
        pipeline.addLast(new NettyLuckHandler());

    }
}
