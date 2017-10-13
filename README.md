###  SNoti 客户端

* 1、远程控制
* 2、接收服务端推送的事件
* 3、基于jdk8


###  客户端maven引用

```
 <dependency>
             <groupId>com.gizwits</groupId>
             <artifactId>noti-netty-client</artifactId>
             <version>0.1.5</version>
 </dependency>
        
```

### spring-boot-stater


```
 <dependency>
             <groupId>com.gizwits</groupId>
             <artifactId>snoti-spring-boot-starter</artifactId>
             <version>0.1.5</version>
 </dependency>
        
```


### 客户端使用


```

// 单个pk 登入
NotiClient notiClient = NotiClient
        .build()
        .setHost("snoti.gizwits.com")
        .setPort(2017)
        .setMaxFrameLength(8192)// 默认值是8192.可以作为扩展使用，默认可以不设置
        .login("419f2c6e9c374558b4e8da23466badc0", "AvTF3Bq9SMiHJKV5yIpdKw", "bf0bcifpQzesK/ZfOsXYAQ", "client", 50, Arrays.asList(Events.ONLINE, Events.OFFLINE, Events.STATUS_KV, Events.STATUS_RAW, Events.ATTR_ALERT, Events.ATTR_FAULT));



//多product_key登入
/*
 List<LoginData> loginData = Arrays.asList(new LoginData(String productKey, String authId, String authSecret, String subkey, List<Events> events))
             , new LoginData(new LoginData(String productKey, String authId, String authSecret, String subkey, List<Events> events)));

   NotiClient notiClient = NotiClient
                .build()
                .setHost("snoti.gizwits.com")
                .setPort(2017)
                .login(loginData);
                /*

//启动
notiClient.doStart();

// 发起远程控制
//notiClient.sendControlMessage(String productKey, String mac, String did, Map attrs);//发送kv控制指令
//notiClient.sendControlMessage(String productKey, String mac, String did, DataCommand cmd, Byte[] raw) // 发送byte数组控制指令
//notiClient.sendControlMessage(List<RemoteControlData> controlMessage) // 批量发送控制指令.可发送kv,byte数组


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


//订阅(接收)推送事件消息
Thread thread = new Thread(() -> {
    String messgae = null;
    while ((messgae = notiClient.reveiceMessgae()) != null) {
        System.out.println("实时接收snoti消息:" + messgae);
    }
});
thread.start();

// 监听客户端销毁回调事件
notiClient.addListener(event -> {

    if (event == NotiEvent.DESTORY) {
        try {

            while (!notiClient.messageNone()) {
                TimeUnit.SECONDS.sleep(1);
            }

            thread.interrupt();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

});
 
        
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
* [snoti-spring-boot-starter](https://github.com/Bestfeel/snoti-spring-boot-starter)