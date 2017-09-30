package com.gizwits.noti2.client;

import com.google.gson.annotations.SerializedName;

/**
 * Created by feel on 2017/9/30.
 * noti客户端生命周期的回调事件
 */
public enum NotiEvent {

    @SerializedName("init")
    INIT("init"),
    @SerializedName("start")
    SATRT("start"),
    @SerializedName("restart")
    RESATRT("restart"),
    @SerializedName("destory")
    DESTORY("destory");

    private String name;

    NotiEvent(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static NotiEvent getEvent(String name) {
        for (NotiEvent event : values()) {
            if (event.getName().equals(name)) {
                return event;
            }
        }
        return null;
    }

}
