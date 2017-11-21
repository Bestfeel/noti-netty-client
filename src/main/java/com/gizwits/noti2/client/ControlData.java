package com.gizwits.noti2.client;

import com.google.gson.annotations.SerializedName;

/**
 * @author Feel
 * @date 2017/6/16
 * @email fye@gizwits.com
 * @since 0.0.1
 */
public final class ControlData extends Data {

    @SerializedName("data")
    private Payload data;

    public ControlData() {
    }

    public ControlData(Payload data) {
        this.data = data;
    }

    public ControlData(DataCommand cmd, String source, Payload data) {
        super(cmd, source);
        this.data = data;
    }

    public Payload getData() {
        return data;
    }

    public void setData(Payload data) {
        this.data = data;
    }
}