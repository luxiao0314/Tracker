//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.tamic.statInterface.statsdk.model;

import java.util.List;

public class Page {
    private String page_id;
    private String referer_page_id;
    private String page_start_time;
    private String page_end_time;
    private String city_id;
    private String uid;
    private List<KeyValueBean> parameter;

    public Page() {
    }

    public String getPage_id() {
        return this.page_id;
    }

    public void setPage_id(String page_id) {
        this.page_id = page_id;
    }

    public String getReferer_page_id() {
        return this.referer_page_id;
    }

    public void setReferer_page_id(String referer_page_id) {
        this.referer_page_id = referer_page_id;
    }

    public String getPage_start_time() {
        return this.page_start_time;
    }

    public void setPage_start_time(String page_start_time) {
        this.page_start_time = page_start_time;
    }

    public String getPage_end_time() {
        return this.page_end_time;
    }

    public void setPage_end_time(String page_end_time) {
        this.page_end_time = page_end_time;
    }

    public String getCity_id() {
        return this.city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public List<KeyValueBean> getParameter() {
        return this.parameter;
    }

    public void setParameter(List<KeyValueBean> parameter) {
        this.parameter = parameter;
    }

    public String getUid() {
        return this.uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
