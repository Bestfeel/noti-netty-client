package com.gizwits.noti2.client;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by feel on 2017/6/16.
 * 消息封装
 */
public final class Message<T extends Data> {
    @SerializedName("cmd")
    private Command cmd;
    @SerializedName("msg_id")
    private String msgId;
    @SerializedName("prefetch_count")
    private int prefetchCount = 50;
    @SerializedName("data")
    private List<T> data;

    public Message() {
    }

    public Message(Command cmd, String msgId, int prefetchCount, List<T> data) {
        this.cmd = cmd;
        this.msgId = msgId;
        this.prefetchCount = prefetchCount;
        this.data = data;
    }

    public Command getCmd() {
        return cmd;
    }

    public void setCmd(Command cmd) {
        this.cmd = cmd;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public int getPrefetchCount() {
        return prefetchCount;
    }

    public void setPrefetchCount(int prefetchCount) {
        this.prefetchCount = prefetchCount;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}







