//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.tamic.statInterface.statsdk.presenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;
import com.tamic.statInterface.statsdk.constants.StaticsConfig;

public class TcNetworkObserver extends BroadcastReceiver {
    private static final boolean DEBUG;
    private static final String LOG_TAG;
    private Context mContext;
    private TcNetworkObserver.INetworkListener mListener;
    private boolean mIsNetworkAvailable;
    private boolean isRegistered;

    public TcNetworkObserver(Context aContext, TcNetworkObserver.INetworkListener aListener) {
        this.mContext = aContext;
        this.mListener = aListener;
        this.mIsNetworkAvailable = false;
        this.isRegistered = false;
    }

    public void start() {
        if(!this.isRegistered) {
            try {
                this.mContext.registerReceiver(this, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
                this.isRegistered = true;
            } catch (Exception var2) {
                this.isRegistered = false;
                if(DEBUG) {
                    Log.e(LOG_TAG, "Start Exception", var2);
                }
            }

        }
    }

    public void stop() {
        if(this.isRegistered) {
            try {
                this.mContext.unregisterReceiver(this);
                this.isRegistered = false;
            } catch (Exception var2) {
                if(DEBUG) {
                    Log.e(LOG_TAG, "Stop Exception", var2);
                }

                this.isRegistered = true;
            }

        }
    }

    public boolean isNetworkAvailable(Context aContext) {
        ConnectivityManager cm = (ConnectivityManager)aContext.getSystemService("connectivity");
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.isAvailable();
    }

    public void onReceive(Context aContext, Intent aIntent) {
        if(TextUtils.equals(aIntent.getAction(), "android.net.conn.CONNECTIVITY_CHANGE")) {
            boolean isAvailable = this.isNetworkAvailable(aContext);
            if(isAvailable) {
                if(!this.mIsNetworkAvailable && this.mListener != null) {
                    this.mListener.onNetworkConnected(aContext);
                }
            } else if(this.mListener != null) {
                this.mListener.onNetworkUnConnected(aContext);
            }

            this.mIsNetworkAvailable = isAvailable;
        }

    }

    static {
        DEBUG = StaticsConfig.DEBUG;
        LOG_TAG = TcNetworkObserver.class.getSimpleName();
    }

    public interface INetworkListener {
        void onNetworkConnected(Context var1);

        void onNetworkUnConnected(Context var1);
    }
}
