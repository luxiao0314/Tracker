//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.tamic.statInterface.statsdk.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class TcUploadCoreReceiver extends BroadcastReceiver {
    public static final String REPORT_ACTION = "action.com.pinganfang.base.send_report";
    private static final String TAG = "TcUploadCoreReceiver";

    public TcUploadCoreReceiver() {
    }

    public void onReceive(Context context, Intent intent) {
        Log.d("TcUploadCoreReceiver", "pollSever is started");
        if(context != null && intent != null) {
            TcStatSdk.getInstance(context).send();
        }
    }
}
