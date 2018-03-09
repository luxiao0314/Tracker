//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.tamic.statInterface.statsdk.db;

public class TcNote {
    private Long id;
    private String firstCloumn;
    private String secondCloumn;
    private String thirdCloumn;
    private String forthCloumn;

    public TcNote() {
    }

    public TcNote(Long id) {
        this.id = id;
    }

    public TcNote(Long id, String firstCloumn, String secondCloumn, String thirdCloumn, String forthCloumn) {
        this.id = id;
        this.firstCloumn = firstCloumn;
        this.secondCloumn = secondCloumn;
        this.thirdCloumn = thirdCloumn;
        this.forthCloumn = forthCloumn;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstCloumn() {
        return this.firstCloumn;
    }

    public void setFirstCloumn(String firstCloumn) {
        this.firstCloumn = firstCloumn;
    }

    public String getSecondCloumn() {
        return this.secondCloumn;
    }

    public void setSecondCloumn(String secondCloumn) {
        this.secondCloumn = secondCloumn;
    }

    public String getThirdCloumn() {
        return this.thirdCloumn;
    }

    public void setThirdCloumn(String thirdCloumn) {
        this.thirdCloumn = thirdCloumn;
    }

    public String getForthCloumn() {
        return this.forthCloumn;
    }

    public void setForthCloumn(String forthCloumn) {
        this.forthCloumn = forthCloumn;
    }
}
