//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.tamic.statInterface.statsdk.model.header;

public class HeaderInfo {
    private AppInfo appinfo;
    private DeviceInfo deviceinfo;
    private NetworkInfo networkinfo;

    public HeaderInfo() {
    }

    public HeaderInfo(AppInfo appinfo, DeviceInfo deviceinfo, NetworkInfo networkinfo) {
        this.appinfo = appinfo;
        this.deviceinfo = deviceinfo;
        this.networkinfo = networkinfo;
    }

    public AppInfo getAppinfo() {
        return this.appinfo;
    }

    public void setAppinfo(AppInfo appinfo) {
        this.appinfo = appinfo;
    }

    public DeviceInfo getDeviceinfo() {
        return this.deviceinfo;
    }

    public void setDeviceinfo(DeviceInfo deviceinfo) {
        this.deviceinfo = deviceinfo;
    }

    public NetworkInfo getNetworkinfo() {
        return this.networkinfo;
    }

    public void setNetworkinfo(NetworkInfo networkinfo) {
        this.networkinfo = networkinfo;
    }
}
