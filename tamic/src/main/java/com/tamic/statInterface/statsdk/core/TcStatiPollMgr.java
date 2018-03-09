//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.tamic.statInterface.statsdk.core;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.format.Time;
import com.tamic.statInterface.statsdk.constants.StaticsConfig;

public class TcStatiPollMgr {
    private static final boolean DEBUG;
    private static final String LOG_TAG;
    private static final int MSG_TIMEOUT = 1;
    private TcStaticsManagerImpl staticsManagerImpl;
    private long mCardiacCycle;
    private long mDefaultCycle;
    private static final ThreadLocal<Handler> sPrivateHandler;

    public TcStatiPollMgr(TcStaticsManagerImpl staticsManager) {
        this.staticsManagerImpl = staticsManager;
    }

    public void start(long aCardiacCycle) {
        this.mDefaultCycle = aCardiacCycle;
        this.mCardiacCycle = aCardiacCycle;
        this.checkDateChanging();
        this.stop();
        this.loop();
    }

    public void stop() {
        ((Handler)sPrivateHandler.get()).removeMessages(1);
    }

    private void loop() {
        Message msg = ((Handler)sPrivateHandler.get()).obtainMessage(1, this);
        ((Handler)sPrivateHandler.get()).sendMessageDelayed(msg, this.mCardiacCycle);
    }

    public void onTimeOut() {
        this.staticsManagerImpl.onScheduleTimeOut();
    }

    private void checkDateChanging() {
        Time time = new Time();
        time.setToNow();
        int hour = time.hour;
        int minute = time.minute;
        if(hour == 23) {
            int cycle = 61 - minute;
            long timeSchedule = (long)cycle * 60000L;
            if(timeSchedule < this.mCardiacCycle) {
                this.mCardiacCycle = timeSchedule;
            }
        } else if(this.mCardiacCycle != this.mDefaultCycle) {
            this.mCardiacCycle = this.mDefaultCycle;
        }

    }

    static {
        DEBUG = StaticsConfig.DEBUG;
        LOG_TAG = TcStatiPollMgr.class.getSimpleName();
        sPrivateHandler = new ThreadLocal<Handler>() {
            protected Handler initialValue() {
                return new Handler(Looper.getMainLooper()) {
                    public void handleMessage(Message msg) {
                        switch(msg.what) {
                        case 1:
                            TcStatiPollMgr schedule = (TcStatiPollMgr)msg.obj;
                            if(schedule != null) {
                                schedule.onTimeOut();
                                schedule.checkDateChanging();
                                schedule.loop();
                            }
                        default:
                        }
                    }
                };
            }
        };
    }
}
