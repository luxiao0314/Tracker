//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.tamic.statInterface.statsdk.core;

import android.content.Context;
import java.util.HashMap;

public class TcStatSdk {
    private Context mContext;
    private static TcStatSdk sInstance;
    private static final String TAG = "TcStaInterface::StatSdk";
    private TcStaticsManager staticsManager;

    protected static synchronized TcStatSdk getInstance(Context aContext) {
        if(sInstance == null) {
            sInstance = new TcStatSdk(aContext, new TcStaticsManagerImpl(aContext));
        }

        return sInstance;
    }

    private TcStatSdk(Context aContext, TcStaticsManager aStaticsManager) {
        this.mContext = aContext;
        this.staticsManager = aStaticsManager;
    }

    protected void init(int appId, String channel, String fileName) {
        this.staticsManager.onInit(appId, channel, fileName);
    }

    protected void send() {
        this.staticsManager.onSend();
    }

    protected void store() {
        this.staticsManager.onStore();
    }

    protected void upLoad() {
        this.staticsManager.onSend();
    }

    protected void release() {
        this.staticsManager.onRelease();
    }

    protected void recordPageEnd() {
        this.staticsManager.onRrecordPageEnd();
    }

    protected void recordAppStart() {
        this.staticsManager.onRecordAppStart();
    }

    protected void recordAppEnd() {
        this.staticsManager.onRrecordAppEnd();
    }

    protected void recordPageStart(Context context) {
        this.staticsManager.onRecordPageStart(context);
    }

    protected void setPageParameter(String k, String v) {
        this.staticsManager.onPageParameter(new String[]{k, v});
    }

    protected void initEvent(String envntName) {
        this.staticsManager.onInitEvent(envntName);
    }

    protected void setEventParameter(String k, String v) {
        this.staticsManager.onEventParameter(new String[]{k, v});
    }

    protected void onEvent(String eventName, HashMap<String, String> parameters) {
        this.staticsManager.onEvent(eventName, parameters);
    }

    protected void initPage(String pageId, String referPageId) {
        this.staticsManager.onInitPage(new String[]{pageId, referPageId});
    }
}
