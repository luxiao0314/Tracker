package com.tamic.statInterface.statsdk.core;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.KeyguardManager;
import android.content.Context;
import android.text.TextUtils;

import com.tamic.statInterface.statsdk.constants.StaticsConfig;
import com.tamic.statInterface.statsdk.db.helper.DataConstruct;
import com.tamic.statInterface.statsdk.presenter.TcDeblockObserver;
import com.tamic.statInterface.statsdk.presenter.TcDeblockObserver.IKeyguardListener;
import com.tamic.statInterface.statsdk.presenter.TcNetworkObserver;
import com.tamic.statInterface.statsdk.presenter.TcNetworkObserver.INetworkListener;
import com.tamic.statInterface.statsdk.presenter.TcScreenObserver;
import com.tamic.statInterface.statsdk.presenter.TcScreenObserver.IScreenListener;
import com.tamic.statInterface.statsdk.util.StatLog;

import java.util.List;

public class TcObserverPresenter implements INetworkListener, IScreenListener, IKeyguardListener {
    private TcNetworkObserver mNetworkObserver;
    private TcScreenObserver mScreenObserver;
    private TcDeblockObserver mKeyguardObserver;
    private boolean isForeground;
    private boolean isInit;
    private String mPackageName;
    private boolean isTopTask;
    private boolean isScreenOff;
    private boolean isScreenLocked;
    public static final char APP_STATUS_FOREGROUND = '0';
    public static final char APP_STATUS_BACKGROUND = '1';
    private static final String LOG_TAG = "TamicStat::ObserverPresenter";
    private TcObserverPresenter.ScheduleListener scheduleListener;

    public TcObserverPresenter(TcObserverPresenter.ScheduleListener listener) {
        this.scheduleListener = listener;
    }

    public void init(Context context) {
        if(!this.isInit) {
            this.mPackageName = context.getPackageName();
            this.isTopTask = true;
            this.isScreenOff = false;
            this.isScreenLocked = false;
            this.isForeground = true;
            this.registerObserver(context);
            this.isInit = true;
        }

    }

    public boolean isForeground() {
        return this.isForeground;
    }

    public char getAppStatus() {
        return (char)(this.isForeground?'0':'1');
    }

    public void onStart(Context aContext) {
        if(!this.isTopTask) {
            StatLog.d("TamicStat::ObserverPresenter", "onStart,false-->onForegroundChanged");
            this.isTopTask = true;
            this.onForegroundChanged(aContext, true);
        }

    }

    public void onPause(Context aContext) {
        StatLog.d("TamicStat::ObserverPresenter", "onPause");
        if(this.isTopTask) {
            RunningTaskInfo taskInfo = this.getRunningTaskInfo(aContext);
            if(taskInfo != null && taskInfo.topActivity != null) {
                String packageName = taskInfo.topActivity.getPackageName();
                if(!TextUtils.isEmpty(packageName) && !packageName.equals(this.mPackageName)) {
                    this.isTopTask = false;
                    StatLog.d("TamicStat::ObserverPresenter", "onPause --> onForegroundChanged(false)");
                    this.onForegroundChanged(aContext, false);
                }
            }
        }

    }

    public void onStop(Context aContext) {
        StatLog.d("TamicStat::ObserverPresenter", "onStop");
        if(this.isTopTask) {
            RunningTaskInfo taskInfo = this.getRunningTaskInfo(aContext);
            if(taskInfo != null && taskInfo.topActivity != null) {
                String packageName = taskInfo.topActivity.getPackageName();
                if(!TextUtils.isEmpty(packageName) && !packageName.equals(this.mPackageName)) {
                    this.isTopTask = false;
                    StatLog.d("TamicStat::ObserverPresenter", "onStop --> onForegroundChanged(false)");
                    this.onForegroundChanged(aContext, false);
                }
            }
        }

    }

    private void registerObserver(Context aContext) {
        if(this.mScreenObserver == null) {
            this.mScreenObserver = new TcScreenObserver(aContext, this);
        }

        this.mScreenObserver.start();
        if(this.mNetworkObserver == null) {
            this.mNetworkObserver = new TcNetworkObserver(aContext, this);
        }

        this.mNetworkObserver.start();
        if(this.mKeyguardObserver == null) {
            this.mKeyguardObserver = new TcDeblockObserver(aContext, this);
        }

        this.mKeyguardObserver.start();
    }

    private void unregisterObserver() {
        if(this.mScreenObserver != null) {
            this.mScreenObserver.stop();
        }

        if(this.mKeyguardObserver != null) {
            this.mKeyguardObserver.stop();
        }

        if(this.mNetworkObserver != null) {
            this.mNetworkObserver.stop();
        }

    }

    public void destroy() {
        this.unregisterObserver();
        this.mScreenObserver = null;
        this.mKeyguardObserver = null;
        this.mNetworkObserver = null;
        this.isInit = false;
    }

    private RunningTaskInfo getRunningTaskInfo(Context aContext) {
        ActivityManager manager = (ActivityManager)aContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> taskList = manager.getRunningTasks(1);
        return taskList != null && !taskList.isEmpty()?(RunningTaskInfo)taskList.get(0):null;
    }

    private boolean isScreenLocked(Context aContext) {
        KeyguardManager km = (KeyguardManager)aContext.getSystemService(Context.KEYGUARD_SERVICE);
        return km.inKeyguardRestrictedInputMode();
    }

    private synchronized void onForegroundChanged(Context aContext, boolean aIsForeground) {
        this.isForeground = aIsForeground;
        this.reportData(aContext);
        if(aIsForeground) {
            if(StaticsConfig.DEBUG) {
                StatLog.d("TamicStat::ObserverPresenter", "onForeground true");
            }

            DataConstruct.storeAppAction("3");
            this.scheduleStart();
        } else {
            if(StaticsConfig.DEBUG) {
                StatLog.d("TamicStat::ObserverPresenter", "onForeground false");
            }

            TcStatSdk.getInstance(aContext).send();
            this.scheduleStop();
        }

    }

    private void reportData(Context context) {
        TcStatSdk.getInstance(context).send();
    }

    private void scheduleStart() {
        if(this.scheduleListener != null) {
            this.scheduleListener.onStart();
        }

    }

    private void scheduleStop() {
        if(this.scheduleListener != null) {
            this.scheduleListener.onStop();
        }

    }

    private void scheduleReStart() {
        if(this.scheduleListener != null) {
            this.scheduleListener.onReStart();
        }

    }

    public void onNetworkConnected(Context aContext) {
        StatLog.d("TamicStat::ObserverPresenter", "onNetworkConnected");
        TcHeadrHandle.getHeader(aContext).setNetworkinfo(TcHeadrHandle.getNetWorkInfo(aContext));
        if(this.isForeground) {
            StatLog.d("TamicStat::ObserverPresenter", "onNetworkConnected send data");
            this.reportData(aContext);
            this.scheduleReStart();
        } else {
            this.scheduleStop();
        }

    }

    public void onNetworkUnConnected(Context aContext) {
        StatLog.d("TamicStat::ObserverPresenter", "onNetworkUnConnected");
        this.scheduleStop();
    }

    public void onScreenOn(Context aContext) {
        StatLog.d("TamicStat::ObserverPresenter", "onScreenOn");
        if(this.isTopTask && this.isScreenOff) {
            this.isScreenOff = false;
            if(this.isScreenLocked(aContext)) {
                this.isScreenLocked = true;
            } else {
                StatLog.d("TamicStat::ObserverPresenter", "onScreenOn-->onForegroundChanged(true)");
                this.isScreenLocked = false;
                this.onForegroundChanged(aContext, true);
            }
        }

    }

    public void onScreenOff(Context aContext) {
        StatLog.d("TamicStat::ObserverPresenter", "onScreenOff");
        if(this.isTopTask && !this.isScreenOff) {
            this.isScreenOff = true;
            if(!this.isScreenLocked) {
                StatLog.d("TamicStat::ObserverPresenter", "onScreenOff-->onForegroundChanged(false)");
                this.onForegroundChanged(aContext, false);
            }
        }

    }

    public void onKeyguardGone(Context aContext) {
        StatLog.d("TamicStat::ObserverPresenter", "onKeyGuardGone");
        if(this.isTopTask) {
            StatLog.d("TamicStat::ObserverPresenter", "onKeyGuardGone foreground");
            if(this.isScreenLocked) {
                this.isScreenLocked = false;
                this.onForegroundChanged(aContext, true);
            }
        }

    }

    public interface ScheduleListener {
        void onStart();

        void onStop();

        void onReStart();
    }
}
