//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.tamic.statInterface.statsdk.model;

public class KeyValueBean {
    private String name;
    private String value;

    public KeyValueBean() {
    }

    public KeyValueBean(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String toString() {
        return "KeyValueBean{name='" + this.name + '\'' + ", value='" + this.value + '\'' + '}';
    }
}
