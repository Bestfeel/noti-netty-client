package com.gizwits.noti2.client;

import java.util.Map;

/**
 * @author Feel
 * @date 2017/6/16
 * @email fye@gizwits.com
 * @since 0.0.1
 */
public class RemoteControlData {

    private String productKey;
    private String mac;
    private String did;
    private DataCommand cmd;
    private Byte[] raw;
    private Map attrs;

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
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

}
