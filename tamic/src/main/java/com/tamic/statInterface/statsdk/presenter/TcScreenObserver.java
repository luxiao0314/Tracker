//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.tamic.statInterface.statsdk.presenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PowerManager;
import android.util.Log;
import com.tamic.statInterface.statsdk.constants.StaticsConfig;
import com.tamic.statInterface.statsdk.core.TcIntentManager;

public class TcScreenObserver extends BroadcastReceiver {
    private static final boolean DEBUG;
    private static final String LOG_TAG;
    private Context mContext;
    private TcScreenObserver.IScreenListener mListener;

    public TcScreenObserver(Context aContext, TcScreenObserver.IScreenListener aListener) {
        this.mContext = aContext;
        this.mListener = aListener;
    }

    public void start() {
        try {
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.intent.action.SCREEN_ON");
            filter.addAction("android.intent.action.SCREEN_OFF");
            this.mContext.registerReceiver(this, filter);
            if(this.isScreenOn(this.mContext)) {
                if(this.mListener != null) {
                    this.mListener.onScreenOn(this.mContext);
                }
            } else if(this.mListener != null) {
                this.mListener.onScreenOff(this.mContext);
            }
        } catch (Exception var2) {
            if(DEBUG) {
                Log.w(LOG_TAG, "start Exception", var2);
            }
        }

    }

    public void stop() {
        try {
            this.mContext.unregisterReceiver(this);
        } catch (Exception var2) {
            if(DEBUG) {
                Log.w(LOG_TAG, "stop Exception", var2);
            }
        }

    }

    public boolean isScreenOn(Context aContext) {
        PowerManager pm = (PowerManager)aContext.getSystemService("power");
        return pm.isScreenOn();
    }

    public void onReceive(Context aContext, Intent aIntent) {
        if(TcIntentManager.getInstance().isScreenOnIntent(aIntent)) {
            if(this.mListener != null) {
                this.mListener.onScreenOn(aContext);
            }
        } else if(TcIntentManager.getInstance().isScreenOffIntent(aIntent) && this.mListener != null) {
            this.mListener.onScreenOff(aContext);
        }

    }

    static {
        DEBUG = StaticsConfig.DEBUG;
        LOG_TAG = TcScreenObserver.class.getSimpleName();
    }

    public interface IScreenListener {
        void onScreenOn(Context var1);

        void onScreenOff(Context var1);
    }
}
