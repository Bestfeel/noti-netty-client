package com.gizwits.noti2.client;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Feel
 * @date 2017/6/16
 * @email fye@gizwits.com
 * @since 0.0.1
 */
public final class LoginData extends Data {

    @SerializedName("product_key")
    private String productKey;
    @SerializedName("auth_id")
    private String authId;
    @SerializedName("auth_secret")
    private String authSecret;
    @SerializedName("subkey")
    private String subkey;
    @SerializedName("events")
    private List<Events> events;

    public LoginData() {
    }

    public LoginData(String productKey, String authId, String authSecret, String subkey, List<Events> events) {
        this.productKey = productKey;
        this.authId = authId;
        this.authSecret = authSecret;
        this.subkey = subkey;
        this.events = events;
    }

    public LoginData(DataCommand cmd, String source, String productKey, String authId, String authSecret, String subkey, List<Events> events) {
        super(cmd, source);
        this.productKey = productKey;
        this.authId = authId;
        this.authSecret = authSecret;
        this.subkey = subkey;
        this.events = events;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public String getAuthSecret() {
        return authSecret;
    }

    public void setAuthSecret(String authSecret) {
        this.authSecret = authSecret;
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
}
