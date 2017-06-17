package com.gizwits.noti2.client;

import java.util.Arrays;
import java.util.Map;

/**
 * Created by feel on 2017/6/17.
 */
public class RemoteControlData {

    private String product_key;
    private String mac;
    private String did;
    private DataCommand cmd;
    private Byte[] raw;
    private Map attrs;

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

    public DataCommand getCmd() {
        return cmd;
    }

    public void setCmd(DataCommand cmd) {
        this.cmd = cmd;
    }

    public Byte[] getRaw() {
        return raw;
    }

    public void setRaw(Byte[] raw) {
        if (raw != null) {
            this.raw = raw;
        }
    }

    public Map getAttrs() {
        return attrs;
    }

    public void setAttrs(Map attrs) {

        if (attrs != null) {
            this.attrs = attrs;
        }

    }

    @Override
    public String toString() {
        return "RemoteControlData{" +
                "product_key='" + product_key + '\'' +
                ", mac='" + mac + '\'' +
                ", did='" + did + '\'' +
                ", cmd=" + cmd +
                ", raw=" + Arrays.toString(raw) +
                ", attrs=" + attrs +
                '}';
    }
}
