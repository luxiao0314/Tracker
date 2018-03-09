//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.tamic.statInterface.statsdk.core;

import android.content.Context;
import com.tamic.statInterface.statsdk.constants.NetConfig;
import java.util.HashMap;

public final class TcStatInterface {
    protected static final int UPLOAD_POLICY_REALTIME = 0;
    protected static final int UPLOAD_POLICY_WIFI_ONLY = 2;
    protected static final int UPLOAD_POLICY_BATCH = 3;
    protected static final int UPLOAD_POLICY_INTERVAL = 4;
    protected static final int UPLOAD_POLICY_DEVELOPMENT = 5;
    protected static final int UPLOAD_POLICY_WHILE_INITIALIZE = 6;
    public static final int UPLOAD_INTERVAL_REALTIME = 0;
    public static final int UPLOAD_TIME_ONE = 1;
    public static final int UPLOAD_TIME_THREAD = 3;
    public static final int UPLOAD_TIME_TEN = 10;
    public static final int UPLOAD_TIME_TWENTY = 20;
    public static final int UPLOAD_TIME_THIRTY = 30;
    protected static TcStatInterface.UploadPolicy uploadPolicy;
    private static int intervalRealtime = 3;
    private static Context context;

    private TcStatInterface() {
    }

    public static void initialize(Context aContext, int appId, String channel, String fileName) {
        context = aContext;
        TcStatSdk.getInstance(aContext).init(appId, channel, fileName);
    }

    public static void setUploadPolicy(TcStatInterface.UploadPolicy policy, int time) {
        if(policy == null) {
            uploadPolicy = TcStatInterface.UploadPolicy.UPLOAD_POLICY_INTERVA;
        } else {
            if(time > 0 || time <= 60) {
                intervalRealtime = time;
            }

            uploadPolicy = policy;
        }
    }

    public static int getIntervalRealtime() {
        return intervalRealtime;
    }

    public static void setIntervalRealtime(int intervalRealtime) {
        intervalRealtime = intervalRealtime;
    }

    public static void setUrl(String url) {
        NetConfig.ONLINE_URL = url;
    }

    public static void recordPageStart(Context context) {
        TcStatSdk.getInstance(context).recordPageStart(context);
    }

    public static void recordPageEnd() {
        TcStatSdk.getInstance(context).recordPageEnd();
    }

    public static void recordAppStart() {
        TcStatSdk.getInstance(context).recordAppStart();
    }

    public static void recordAppEnd() {
        TcStatSdk.getInstance(context).recordAppEnd();
        TcStatSdk.getInstance(context).release();
    }

    protected static void report() {
        TcStatSdk.getInstance(context).send();
    }

    public static void reportData() {
        if(uploadPolicy != TcStatInterface.UploadPolicy.UPLOAD_POLICY_DEVELOPMENT) {
            throw new RuntimeException("call reportData(), you must will UploadPolicy set : UPLOAD_POLICY_DEVELOPMENT!");
        } else {
            report();
        }
    }

    public static void onPageParameter(String k, String v) {
        TcStatSdk.getInstance(context).setPageParameter(k, v);
    }

    public static void initEvent(String eventName) {
        TcStatSdk.getInstance(context).initEvent(eventName);
    }

    public static void onEventParameter(String k, String v) {
        TcStatSdk.getInstance(context).setEventParameter(k, v);
    }

    public static void onEvent(String eventName, String k, String v) {
        initEvent(eventName);
        onEventParameter(k, v);
    }

    public static void onEvent(String eventName, HashMap<String, String> parameters) {
        TcStatSdk.getInstance(context).onEvent(eventName, parameters);
    }

    public static enum UploadPolicy {
        UPLOAD_POLICY_REALTIME,
        UPLOAD_POLICY_WIFI_ONLY,
        UPLOAD_POLICY_BATCH,
        UPLOAD_POLICY_INTERVA,
        UPLOAD_POLICY_DEVELOPMENT,
        UPLOAD_POLICY_WHILE_INITIALIZE;

        private UploadPolicy() {
        }
    }
}
