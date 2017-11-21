package com.gizwits.noti2.client;

import com.google.gson.annotations.SerializedName;

/**
 * @author Feel
 * @date 2017/6/16
 * @email fye@gizwits.com
 * @since 0.0.1
 */
public enum Command {

    @SerializedName("login_req")
    LOGIN("login_req"),//连接与登陆
    @SerializedName("login_res")
    LOGIN_RES("login_res"),//  登入回复
    @SerializedName("ping")
    PING("ping"),// 心跳
    @SerializedName("pong")
    PONG("pong"), //  心跳
    @SerializedName("remote_control_req")
    REMOTE_CONTROL_REQ("remote_control_req"),// 设备远程控制
    @SerializedName("remote_control_res")
    REMOTE_CONTROL_RES("remote_control_res"),
    @SerializedName("event_push")
    EVENT_PUSH("event_push"),//  推送事件
    @SerializedName("invalid_msg")
    INVALID_MSG("invalid_msg");
    private String name;

    Command(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Command getCmd(String name) {
        for (Command cmd : values()) {
            if (cmd.getName().equals(name)) {
                return cmd;
            }
        }
        return null;
    }

}
