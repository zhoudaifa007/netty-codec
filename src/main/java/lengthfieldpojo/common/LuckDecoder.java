package lengthfieldpojo.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by Daifa on 2018/2/8.
 */
public class LuckDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        System.out.println(ctx.channel().toString());
        //少于44个字节，不读取
        if (in.readableBytes() < (4 + 4 + 36)) {
            return;
        }
        //标识readerIndex的位置
        in.markReaderIndex();
        // 获取协议的版本
        int version = in.readInt();
        // 获取消息长度
        int contentLength = in.readInt();
        // 获取SessionId
        byte[] sessionByte = new byte[36];
        in.readBytes(sessionByte);
        String sessionId = new String(sessionByte);
        // 组装协议头
        LuckHeader header = new LuckHeader(version, contentLength, sessionId);
        if(in.readableBytes() < contentLength) {
            in.resetReaderIndex();
            return;
        }
        // 读取消息内容
        byte[] content = new byte[contentLength];
        in.readBytes(content);
        LuckMessage message = new LuckMessage(header, new String(content));
        out.add(message);
    }
}
