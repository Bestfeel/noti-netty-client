package com.gizwits.noti2.client;

import com.google.gson.annotations.SerializedName;

import java.util.Map;


/**
 * 远程控制设备消息
 *
 * @author Feel
 * @date 2017/6/16
 * @email fye@gizwits.com
 * @since 0.0.1
 */
public final class Payload {

    @SerializedName("product_key")
    private String productKey;
    @SerializedName("mac")
    private String mac;
    @SerializedName("did")
    private String did;
    @SerializedName("attrs")
    private Map attrs;
    @SerializedName("raw")
    private Byte[] raw;

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

}