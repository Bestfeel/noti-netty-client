package com.gizwits.noti2;

import com.gizwits.noti2.client.NotiClient;

/**
 * Created by feel on 2017/6/16.
 * <p>
 * 参考link:http://docs.gizwits.com/zh-cn/Cloud/NotificationAPI.html
 */
public class App {

    public static void main(String[] args) {


        NotiClient notiClient = NotiClient
                .build()
                .setHost("snoti.gizwits.com")
                .setPort(2017)
                .login("xx", "xx", "xx", "client", 50);

        // 启动
        notiClient.doStart();


        // 发送远程控制
        //notiClient.sendContorlMessage(String product_key, String mac, String did, Map attrs);
        //notiClient.sendContorlMessage(String product_key, String mac, String did, Map attrs)
        //notiClient.sendContorlMessage(List< RemoteControlData > contorlMessage)


        //  订阅(接收)推送事件消息
        while (true) {

            String messgae = notiClient.reveiceMessgae();
            System.out.println(messgae);

        }

    }

}
