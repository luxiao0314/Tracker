//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.tamic.statInterface.statsdk.model.header;

public class DeviceInfo {
    private String device_id;
    private String idfa;
    private String idfv;
    private String openudid;
    private String imei;
    private String android_id;
    private String mac;
    private String locale;
    private String os = "Android";
    private String os_version;
    private String resolution;
    private String density;
    private String model;

    public DeviceInfo() {
    }

    public String getDevice_id() {
        return this.device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getIdfv() {
        return this.idfv;
    }

    public void setIdfv(String idfv) {
        this.idfv = idfv;
    }

    public String getIdfa() {
        return this.idfa;
    }

    public void setIdfa(String idfa) {
        this.idfa = idfa;
    }

    public String getOpenudid() {
        return this.openudid;
    }

    public void setOpenudid(String openudid) {
        this.openudid = openudid;
    }

    public String getImei() {
        return this.imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getAndroid_id() {
        return this.android_id;
    }

    public void setAndroid_id(String android_id) {
        this.android_id = android_id;
    }

    public String getMac() {
        return this.mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getLocale() {
        return this.locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getOs() {
        return this.os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getOs_version() {
        return this.os_version;
    }

    public void setOs_version(String os_version) {
        this.os_version = os_version;
    }

    public String getResolution() {
        return this.resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getDensity() {
        return this.density;
    }

    public void setDensity(String density) {
        this.density = density;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
