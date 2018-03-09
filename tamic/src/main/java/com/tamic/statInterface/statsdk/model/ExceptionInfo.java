//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.tamic.statInterface.statsdk.model;

public class ExceptionInfo {
    private String phoneModel;
    private String systemModel;
    private String systemVersion;
    private String exceptionString;

    public ExceptionInfo() {
    }

    public ExceptionInfo(String phoneModel, String systemModel, String systemVersion, String exceptionString) {
        this.phoneModel = phoneModel;
        this.systemModel = systemModel;
        this.systemVersion = systemVersion;
        this.exceptionString = exceptionString;
    }

    public String getPhoneModel() {
        return this.phoneModel;
    }

    public void setPhoneModel(String phoneModel) {
        this.phoneModel = phoneModel;
    }

    public String getSystemModel() {
        return this.systemModel;
    }

    public void setSystemModel(String systemModel) {
        this.systemModel = systemModel;
    }

    public String getSystemVersion() {
        return this.systemVersion;
    }

    public void setSystemVersion(String systemVersion) {
        this.systemVersion = systemVersion;
    }

    public String getExceptionString() {
        return this.exceptionString;
    }

    public void setExceptionString(String exceptionString) {
        this.exceptionString = exceptionString;
    }
}
