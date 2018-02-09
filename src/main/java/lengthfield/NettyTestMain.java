package lengthfield;

import java.util.Random;

/**
 * Created by zhoudf2 on 2018-2-9.
 */
public class NettyTestMain {

    public static void main(String[] args) throws Exception {
        NettyServer nettyServer = new NettyServer();
        nettyServer.start();//启动server
        Thread.sleep(3000);

        NettyClient nettyClient = new NettyClient();
        nettyClient.start();

        try {
            for (int i = 0; i < 5000; i++) {
                String response = nettyClient.send("123456");
                System.out.println("response:" + response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           // nettyClient.close();
        }

        Thread.sleep(5000);
        nettyServer.close();

    }
}