//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.tamic.statInterface.statsdk.model.header;

public class AppInfo {
    private String app_id;
    private String app_version;
    private int sdk_version_code;
    private String sdk_verson_name;
    private String channel;

    public AppInfo() {
    }

    public String getApp_id() {
        return this.app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getApp_version() {
        return this.app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }

    public int getSdk_version() {
        return this.sdk_version_code;
    }

    public void setSdk_version(int sdk_version) {
        this.sdk_version_code = sdk_version;
    }

    public String getChannel() {
        return this.channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getSdk_verson_name() {
        return this.sdk_verson_name;
    }

    public void setSdk_verson_name(String sdk_verson_name) {
        this.sdk_verson_name = sdk_verson_name;
    }
}
