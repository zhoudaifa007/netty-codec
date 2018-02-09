package success;

/**
 * Created by zhoudf2 on 2018-2-9.
 */
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class SocketClient {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .handler(new SocketClientInitializer());

            ChannelFuture channelFuture = bootstrap.connect("localhost", 8899).sync();
            System.out.println("连接已经建立1");
            channelFuture.channel().closeFuture().sync();
        }
        finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
}