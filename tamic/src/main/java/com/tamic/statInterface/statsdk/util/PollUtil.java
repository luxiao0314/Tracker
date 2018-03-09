//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.tamic.statInterface.statsdk.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import com.tamic.statInterface.statsdk.core.TcUploadCoreReceiver;

public class PollUtil {
    static TcUploadCoreReceiver receiver;

    public PollUtil() {
    }

    public static void startPollingService(Context context, int seconds, Class<?> cls, String action) {
        AlarmManager manager = (AlarmManager)context.getSystemService("alarm");
        Intent intent = new Intent(context, cls);
        intent.setAction(action);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 134217728);
        long triggerAtTime = SystemClock.elapsedRealtime();
        manager.setRepeating(3, triggerAtTime, (long)(seconds * 1000), pendingIntent);
    }

    public static void stopPollingService(Context context, Class<?> cls, String action) {
        AlarmManager manager = (AlarmManager)context.getSystemService("alarm");
        Intent intent = new Intent(context, cls);
        intent.setAction(action);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 134217728);
        manager.cancel(pendingIntent);
    }
}
