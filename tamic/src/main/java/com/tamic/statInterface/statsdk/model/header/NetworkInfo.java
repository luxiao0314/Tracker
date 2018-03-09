//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.tamic.statInterface.statsdk.model.header;

public class NetworkInfo {
    private String carrier;
    private Boolean wifi_ind;
    private String ip_addr;
    private String latitude;
    private String longitude;

    public NetworkInfo() {
    }

    public String getCarrier() {
        return this.carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getIp_addr() {
        return this.ip_addr;
    }

    public void setIp_addr(String ip_addr) {
        this.ip_addr = ip_addr;
    }

    public Boolean getWifi_ind() {
        return this.wifi_ind;
    }

    public void setWifi_ind(Boolean wifi_ind) {
        this.wifi_ind = wifi_ind;
    }

    public String getLatitude() {
        return this.latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
