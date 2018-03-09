//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.tamic.statInterface.statsdk.model;

import java.util.List;

public class DataBlock {
    private List<AppAction> app_action;
    private List<Page> page;
    private List<Event> event;
    private List<ExceptionInfo> exceptionInfos;

    public DataBlock() {
    }

    public List<ExceptionInfo> getExceptionInfos() {
        return this.exceptionInfos;
    }

    public void setExceptionInfos(List<ExceptionInfo> exceptionInfos) {
        this.exceptionInfos = exceptionInfos;
    }

    public List<AppAction> getApp_action() {
        return this.app_action;
    }

    public void setApp_action(List<AppAction> app_action) {
        this.app_action = app_action;
    }

    public List<Page> getPage() {
        return this.page;
    }

    public void setPage(List<Page> page) {
        this.page = page;
    }

    public List<Event> getEvent() {
        return this.event;
    }

    public void setEvent(List<Event> event) {
        this.event = event;
    }
}
