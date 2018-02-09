package failure.client;

import failure.common.LuckHeader;
import failure.common.LuckMessage;
import failure.server.NettyLuckInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.UUID;

/**
 * Created by Daifa on 2018/2/8.
 */
public class LuckClient {

    public static void main(String args[]) throws InterruptedException {

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new NettyLuckInitializer());

            // Start the connection attempt.
            Channel ch = b.connect("127.0.0.1", 8888).sync().channel();

            int version = 1;
            String sessionId = UUID.randomUUID().toString();
            String content = "I'm the luck protocol!";

            LuckHeader header = new LuckHeader(version, content.length(), sessionId);
            LuckMessage message = new LuckMessage(header, content);


            for(int i = 0; i < 1000; i++) {
                ch.writeAndFlush(message).addListener(new ChannelFutureListener() {
                    public void operationComplete(ChannelFuture future) throws Exception {
                        if (future.isSuccess()) {
                            System.out.println("发送成功");
                        }
                    }
                });
                System.out.println("sended " + i);
            }

            System.out.println("关闭通道！！");

            ch.close();

        } finally {
            group.shutdownGracefully();
        }
    }
}

