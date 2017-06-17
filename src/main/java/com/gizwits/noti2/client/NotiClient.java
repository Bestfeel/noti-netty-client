package com.gizwits.noti2.client;

import io.netty.util.concurrent.DefaultThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by feel on 2017/6/16.
 */
public class NotiClient implements IService {

    private static final Logger log = LoggerFactory.getLogger(NotiClient.class);
    private int port = 2017;
    private String host = "snoti.gizwits.com";
    private BoostrapClient client;
    private Message message;
    private BlockingQueue<Message> queue;

    private BlockingQueue<String> receiveQueue;

    public NotiClient() {

        init();
    }

    public static NotiClient build() {

        return new NotiClient();

    }

    public NotiClient setPort(int port) {
        this.port = port;
        return this;
    }

    public NotiClient setHost(String host) {
        this.host = host;
        return this;
    }


    @Override
    public void init() {
        queue = new ArrayBlockingQueue<Message>(50000);
        receiveQueue = new ArrayBlockingQueue<String>(50000);
    }


    @Override
    public void doStart() {

        this.client = new BoostrapClient(this).setOption(host, port).login(message);

        ExecutorService executorService = Executors.newCachedThreadPool(new DefaultThreadFactory("--clientThread-"));

        executorService.execute(this.client);
    }

    @Override
    public void doStop() {
        destory();
    }


    @Override
    public void restart() {
        if (client != null) {
            client.restart();
        }
    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public void destory() {
        if (client != null) {
            client.doStop();
        }
    }


    /**
     * @param msg
     * @return
     */
    public boolean dispatch(String msg) {
        if (msg != null) {
            try {
                receiveQueue.put(msg);
                return true;
            } catch (InterruptedException e) {

                e.printStackTrace();
            }
        }

        return false;
    }

    /**
     * 接收推送事件消息
     *
     * @return
     */
    public String reveiceMessgae() {
        try {
            return receiveQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Message message() {

        try {
            return queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * @param msg
     * @return
     */
    public boolean putMessage(Message msg) {
        if (msg != null) {
            try {
                queue.put(msg);
                return true;

            } catch (InterruptedException e) {

                e.printStackTrace();
            }
        }

        return false;
    }


    public NotiClient login(String product_key, String auth_id, String auth_secret, String subkey, int prefetch_count, List<Events> events) {
        Data data = new Data();
        data.setProduct_key(product_key);
        data.setAuth_id(auth_id);
        data.setAuth_secret(auth_secret);
        data.setSubkey(subkey);
        data.setEvents(events);
        Message message = new Message();
        message.setPrefetch_count(prefetch_count);
        message.setCmd(Command.LOGIN);
        message.setData(Arrays.asList(data));
        this.message = message;
        return this;
    }

    /**
     * 发送远程设备控制
     *
     * @param product_key
     * @param mac
     * @param did
     * @param attrs
     */
    public void sendControlMessage(String product_key, String mac, String did, Map attrs) {
        Message message = new Message();
        message.setCmd(Command.REMOTE_CONTROL_RES);

        Data data = new Data();
        data.setCmd(DataCommand.WRITE_ATTRS);

        ControlData controlData = new ControlData();
        controlData.setProduct_key(product_key);
        controlData.setMac(mac);
        controlData.setDid(did);
        controlData.setAttrs(attrs);
        data.setData(controlData);
        message.setData(Arrays.asList(data));
        this.putMessage(message);
    }

    /**
     * 发送远程设备控制
     *
     * @param product_key
     * @param mac
     * @param did
     * @param cmd
     * @param raw
     */
    public void sendControlMessage(String product_key, String mac, String did, DataCommand cmd, Byte[] raw) {
        Message message = new Message();
        message.setCmd(Command.REMOTE_CONTROL_RES);
        Data data = new Data();
        data.setCmd(cmd);
        ControlData controlData = new ControlData();
        controlData.setProduct_key(product_key);
        controlData.setMac(mac);
        controlData.setDid(did);
        controlData.setRaw(raw);
        data.setData(controlData);
        message.setData(Arrays.asList(data));
        this.putMessage(message);

    }

    /**
     * 发送远程设备控制
     *
     * @param controlMessage
     */
    public void sendControlMessage(List<RemoteControlData> controlMessage) {

        Message message = new Message();
        message.setCmd(Command.REMOTE_CONTROL_RES);
        ArrayList<Data> listData = new ArrayList<>();
        for (RemoteControlData remoteControlData : controlMessage) {
            Data data = new Data();
            data.setCmd(remoteControlData.getCmd());
            ControlData controlData = new ControlData();
            controlData.setProduct_key(remoteControlData.getProduct_key());
            controlData.setMac(remoteControlData.getMac());
            controlData.setDid(remoteControlData.getDid());
            controlData.setRaw(remoteControlData.getRaw());
            controlData.setAttrs(remoteControlData.getAttrs());
            data.setData(controlData);
            listData.add(data);
        }
        message.setData(listData);
        this.putMessage(message);

    }


}
