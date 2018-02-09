package lengthfield;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by zhoudf2 on 2018-2-9.
 */
public class ServerHandler extends SimpleChannelInboundHandler<String> {

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String message) throws Exception {
        System.out.println("from client:" + message);
        JSONObject json = JSONObject.parseObject(message);
        String source = json.getString("source");

        String md5 = "hello";
        //解析成JSON
        json.put("md5Hex",md5);
       // ctx.writeAndFlush(json.toString());//write bytes to socket,and flush(clear) the buffer cache.
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}