package com.gizwits.noti2.client;

import com.google.gson.annotations.SerializedName;

/**
 * 参考机智云相关协议文档：http://docs.gizwits.com/zh-cn/Cloud/NotificationAPI.html
 *
 * @author Feel
 * @date 2017/6/16
 * @email fye@gizwits.com
 * @since 0.0.1
 */
public enum Events {
    @SerializedName("device.attr_fault")
    ATTR_FAULT("device.attr_fault"),//设备故障事件
    @SerializedName("device.attr_alert")
    ATTR_ALERT("device.attr_alert"),//设备报警事件
    @SerializedName("device.online")
    ONLINE("device.online"),//设备上线消息
    @SerializedName("device.offline")
    OFFLINE("device.offline"),//设备下线消息
    @SerializedName("device.status.raw")
    STATUS_RAW("device.status.raw"),//设备上报自定义透传业务指令
    @SerializedName("device.status.kv")
    STATUS_KV("device.status.kv"),//设备上报数据点业务指令
    @SerializedName("datapoints.changed")
    CHANGED("datapoints.changed"),//数据点编辑事件
    @SerializedName("center_control.sub_device_added")
    SUB_DEVICE_ADD("center_control.sub_device_added"),//中控添加子设备事件
    @SerializedName("center_control.sub_device_deleted")
    SUB_DEVICE_DELETE("center_control.sub_device_deleted"),//中控删除子设备事件
    @SerializedName("device.bind")
    BIND("device.bind"),//设备绑定消息
    @SerializedName("device.unbind")
    UNBIND("device.unbind"),//设备解绑消息
    @SerializedName("device.reset")
    RESET("device.reset");//设备重置消息

    private String name;

    Events(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Events getEvent(String name) {
        for (Events event : values()) {
            if (event.getName().equals(name)) {
                return event;
            }
        }
        return null;
    }
}