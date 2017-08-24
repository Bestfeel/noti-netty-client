###  SNoti 客户端

基于netty 封装的客户端
* 1、远程控制
* 2、接收服务端推送的事件
* 3、基于jdk8


###  maven 引用

```
 <dependency>
             <groupId>com.gizwits</groupId>
             <artifactId>noti-netty-client</artifactId>
             <version>0.1.1</version>
 </dependency>
        
```

### 客户端使用


```

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
 
        
```

接收消息日志


```
15:15:41.836 [main] INFO  c.g.noti2.client.BoostrapClient -  NotiClient is already  init
15:15:45.334 [--client---2-1] INFO  c.g.noti2.client.BoostrapClient - Started NotiClient port: 2017
15:15:45.674 [--client---2-1] INFO  c.g.noti2.client.MessageHandler - client login success
实时接收snoti消息:{"event_type":"device_status_kv","product_key":"419f2c6e9c374558b4e8da23466badc0","data":{"Switch_Plasma":0,"Air_Quality":"优","Alert_Air_Quality":0,"Air_Sensitivity":"0","Dust_Air_Quality":0,"Fault_Dust_Sensor":0,"Filter_Life":0,"Child_Security_Lock":0,"Peculiar_Air_Quality":0,"Alert_Filter_Life":0,"Wind_Velocity":"睡眠","Fault_Motor":0,"LED_Air_Quality":1,"Fault_Air_Sensors":0},"delivery_id":1,"created_at":1499843747.79399991035,"cmd":"event_push","did":"gSFMEh75kSQ46ecqAYiJ4h","mac":"virtual:site"}
实时接收snoti消息:{"event_type":"device_status_kv","product_key":"419f2c6e9c374558b4e8da23466badc0","data":{"Switch_Plasma":0,"Air_Quality":"优","Alert_Air_Quality":0,"Air_Sensitivity":"0","Dust_Air_Quality":0,"Fault_Dust_Sensor":0,"Filter_Life":0,"Child_Security_Lock":0,"Peculiar_Air_Quality":0,"Alert_Filter_Life":0,"Wind_Velocity":"睡眠","Fault_Motor":0,"LED_Air_Quality":1,"Fault_Air_Sensors":0},"delivery_id":2,"created_at":1499843747.98399996758,"cmd":"event_push","did":"gSFMEh75kSQ46ecqAYiJ4h","mac":"virtual:site"}

```



* [机智云官方SNoti API文档](http://docs.gizwits.com/zh-cn/Cloud/NotificationAPI.html)
* [snoti与spring-boot集成](https://github.com/Bestfeel/gizwits-iot-course)