package com.gizwits.noti2.client;

import com.google.gson.annotations.SerializedName;

/**
 * @author Feel
 * @date 2017/6/16
 * @email fye@gizwits.com
 * @since 0.0.1
 */
public enum DataCommand {

    @SerializedName("write_attrs")
    WRITE_ATTRS("write_attrs"),
    @SerializedName("write")
    WRITE("write"),
    @SerializedName("write_v1")
    WRITE_V1("write_v1");

    private String name;

    DataCommand(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static DataCommand getCmd(String name) {
        for (DataCommand cmd : values()) {
            if (cmd.getName().equals(name)) {
                return cmd;
            }
        }
        return null;
    }
}
