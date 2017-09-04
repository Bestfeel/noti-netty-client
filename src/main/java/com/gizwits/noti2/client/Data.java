package com.gizwits.noti2.client;

import com.google.gson.annotations.SerializedName;

/**
 * Created by feel on 2017/9/4.
 */
public abstract class Data {

    @SerializedName("cmd")
    private DataCommand cmd;
    @SerializedName("source")
    private String source = "noti";

    public Data() {
    }

    public Data(DataCommand cmd, String source) {
        this.cmd = cmd;
        this.source = source;
    }

    public DataCommand getCmd() {
        return cmd;
    }

    public void setCmd(DataCommand cmd) {
        this.cmd = cmd;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }


}
