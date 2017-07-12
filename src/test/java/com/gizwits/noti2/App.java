package com.gizwits.noti2;

import com.gizwits.noti2.client.Events;
import com.gizwits.noti2.client.NotiClient;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by feel on 2017/6/16.
 * <p>
 * 参考link:http://docs.gizwits.com/zh-cn/Cloud/NotificationAPI.html
 */
public class App {

    public static void main(String[] args) throws InterruptedException {


        NotiClient notiClient = NotiClient
                .build()
                .setHost("snoti.gizwits.com")
                .setPort(2017)
                .login("419f2c6e9c374558b4e8da23466badc0", "AvTF3Bq9SMiHJKV5yIpdKw", "bf0bcifpQzesK/ZfOsXYAQ", "client", 50, Arrays.asList(Events.ONLINE, Events.OFFLINE, Events.STATUS_KV, Events.STATUS_RAW, Events.ATTR_ALERT, Events.ATTR_FAULT));

        //启动
        notiClient.doStart();

        // 发起远程控制
        //notiClient.sendControlMessage(String product_key, String mac, String did, Map attrs);
        //notiClient.sendControlMessage(String product_key, String mac, String did, DataCommand cmd, Byte[] raw)
        //notiClient.sendControlMessage(List<RemoteControlData> controlMessage)


        //test send message
        Map attrs = new HashMap();
        attrs.put("Wind_Velocity", "睡眠");
        attrs.put("Switch_Plasma", false);
        attrs.put("LED_Air_Quality", true);

        notiClient.sendControlMessage("419f2c6e9c374558b4e8da23466badc0", "virtual:site", "gSFMEh75kSQ46ecqAYiJ4h", attrs);
        TimeUnit.SECONDS.sleep(1);
        notiClient.sendControlMessage("419f2c6e9c374558b4e8da23466badc0", "virtual:site", "gSFMEh75kSQ46ecqAYiJ4h", attrs);
        TimeUnit.SECONDS.sleep(1);
        notiClient.sendControlMessage("419f2c6e9c374558b4e8da23466badc0", "virtual:site", "gSFMEh75kSQ46ecqAYiJ4h", attrs);
        notiClient.startPushMessage();

        //订阅(接收)推送事件消息
        String messgae = null;
        while ((messgae = notiClient.reveiceMessgae()) != null) {
            System.out.println("实时接收snoti消息:" + messgae);

        }

    }

}
