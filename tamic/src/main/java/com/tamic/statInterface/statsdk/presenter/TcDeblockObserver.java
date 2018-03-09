//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.tamic.statInterface.statsdk.presenter;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.tamic.statInterface.statsdk.constants.StaticsConfig;
import com.tamic.statInterface.statsdk.core.TcIntentManager;
import com.tamic.statInterface.statsdk.util.StatLog;

public class TcDeblockObserver extends BroadcastReceiver {
    private static final boolean DEBUG;
    private static final String StatLog_TAG;
    private Context mContext;
    private TcDeblockObserver.IKeyguardListener mListener;

    public TcDeblockObserver(Context aContext, TcDeblockObserver.IKeyguardListener aListener) {
        this.mContext = aContext;
        this.mListener = aListener;
    }

    public void start() {
        try {
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.intent.action.USER_PRESENT");
            this.mContext.registerReceiver(this, filter);
            if(!this.isScreenLocked(this.mContext) && this.mListener != null) {
                this.mListener.onKeyguardGone(this.mContext);
            }
        } catch (Exception var2) {
            if(DEBUG) {
                StatLog.w(StatLog_TAG, "start Exception", var2);
            }
        }

    }

    public void stop() {
        try {
            this.mContext.unregisterReceiver(this);
        } catch (Exception var2) {
            if(DEBUG) {
                StatLog.w(StatLog_TAG, "stop Exception", var2);
            }
        }

    }

    public boolean isScreenLocked(Context aContext) {
        KeyguardManager km = (KeyguardManager)aContext.getSystemService("keyguard");
        return km.inKeyguardRestrictedInputMode();
    }

    public void onReceive(Context aContext, Intent aIntent) {
        if(TcIntentManager.getInstance().isUserPresentIntent(aIntent) && this.mListener != null) {
            this.mListener.onKeyguardGone(aContext);
        }

    }

    static {
        DEBUG = StaticsConfig.DEBUG;
        StatLog_TAG = TcDeblockObserver.class.getSimpleName();
    }

    public interface IKeyguardListener {
        void onKeyguardGone(Context var1);
    }
}
