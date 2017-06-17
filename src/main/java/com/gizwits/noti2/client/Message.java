package com.gizwits.noti2.client;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by feel on 2017/6/16.
 * 消息封装
 */
public final class Message {
    @SerializedName("cmd")
    private Command cmd;
    @SerializedName("msg_id")
    private String msg_id;
    @SerializedName("prefetch_count")
    private int prefetch_count = 50;
    @SerializedName("data")
    private List<Data> data;

    public Message() {
    }

    public Command getCmd() {
        return cmd;
    }

    public void setCmd(Command cmd) {
        this.cmd = cmd;
    }

    public String getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(String msg_id) {
        this.msg_id = msg_id;
    }

    public int getPrefetch_count() {
        return prefetch_count;
    }

    public void setPrefetch_count(int prefetch_count) {
        this.prefetch_count = prefetch_count;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Message{" +
                "cmd=" + cmd +
                ", msg_id='" + msg_id + '\'' +
                ", prefetch_count=" + prefetch_count +
                ", data=" + data +
                '}';
    }

}

class Data {
    @SerializedName("cmd")
    private DataCommand cmd;
    @SerializedName("product_key")
    private String product_key;
    @SerializedName("auth_id")
    private String auth_id;
    @SerializedName("auth_secret")
    private String auth_secret;
    @SerializedName("subkey")
    private String subkey;
    @SerializedName("events")
    private List<Events> events;
    @SerializedName("data")
    private ControlData data;
    @SerializedName("source")
    private String source = "noti";

    public Data() {
    }

    public DataCommand getCmd() {
        return cmd;
    }

    public void setCmd(DataCommand cmd) {
        this.cmd = cmd;
    }

    public String getProduct_key() {
        return product_key;
    }

    public void setProduct_key(String product_key) {
        this.product_key = product_key;
    }

    public String getAuth_id() {
        return auth_id;
    }

    public void setAuth_id(String auth_id) {
        this.auth_id = auth_id;
    }

    public String getAuth_secret() {
        return auth_secret;
    }

    public void setAuth_secret(String auth_secret) {
        this.auth_secret = auth_secret;
    }

    public String getSubkey() {
        return subkey;
    }

    public void setSubkey(String subkey) {
        this.subkey = subkey;
    }

    public List<Events> getEvents() {
        return events;
    }

    public void setEvents(List<Events> events) {
        this.events = events;
    }

    public ControlData getData() {
        return data;
    }

    public void setData(ControlData data) {
        this.data = data;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "Data{" +
                "cmd=" + cmd +
                ", product_key='" + product_key + '\'' +
                ", auth_id='" + auth_id + '\'' +
                ", auth_secret='" + auth_secret + '\'' +
                ", subkey='" + subkey + '\'' +
                ", events=" + events +
                ", data=" + data +
                ", source='" + source + '\'' +
                '}';
    }
}

/**
 * 远程控制设备信息
 */
class ControlData {

    @SerializedName("product_key")
    private String product_key;
    @SerializedName("mac")
    private String mac;
    @SerializedName("did")
    private String did;
    @SerializedName("attrs")
    private Map attrs;
    @SerializedName("raw")
    private Byte[] raw;

    public String getProduct_key() {
        return product_key;
    }

    public void setProduct_key(String product_key) {
        this.product_key = product_key;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public Map getAttrs() {
        return attrs;
    }

    public void setAttrs(Map attrs) {
        if (attrs != null) {
            this.attrs = attrs;
        }

    }

    public Byte[] getRaw() {
        return raw;
    }

    public void setRaw(Byte[] raw) {

        if (raw != null) {
            this.raw = raw;
        }

    }

    @Override
    public String toString() {
        return "ControlData{" +
                "product_key='" + product_key + '\'' +
                ", mac='" + mac + '\'' +
                ", did='" + did + '\'' +
                ", attrs=" + attrs +
                ", raw=" + Arrays.toString(raw) +
                '}';
    }
}


