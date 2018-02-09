package lengthfieldstring;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zhoudf2 on 2018-2-9.
 */
public class ClientHandler extends SimpleChannelInboundHandler<String> {

    //key is sequence ID，value is response message.
    private Map<Integer,String> response = new ConcurrentHashMap<Integer, String>();

    //key is sequence ID，value is request thread.
    private final Map<Integer,Thread> waiters = new ConcurrentHashMap<Integer, Thread>();

    private final AtomicInteger sequence = new AtomicInteger();


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //当channel就绪后。
        System.out.println("client channel is ready!");
        //ctx.writeAndFlush("started");//阻塞知道发送完毕
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String message) throws Exception {
        JSONObject  json = JSONObject.parseObject(message);
        Integer id = json.getInteger("id");
        response.put(id,json.getString("md5Hex"));

        Thread thread = waiters.remove(id);//读取到response后，从waiters中移除并唤醒线程。
        synchronized (thread) {
            thread.notifyAll();
        }
    }


    public String call(String message,Channel channel) throws Exception {
        int id = sequence.incrementAndGet();//产生一个ID，并与当前request绑定
        Thread current = Thread.currentThread();
        waiters.put(id,current);
        JSONObject json = new JSONObject();
        json.put("id",id);
        json.put("source",message);
        channel.writeAndFlush(json.toString());
        while (!response.containsKey(id)) {
            synchronized (current) {
                current.wait();//阻塞请求调用者线程，直到收到响应响应
            }
        }
        waiters.remove(id);
        return response.remove(id);

    }

}