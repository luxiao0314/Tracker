//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.tamic.statInterface.statsdk.core;

import android.content.Context;
import android.os.Process;
import android.util.Log;
import com.tamic.statInterface.statsdk.db.helper.StaticsAgent;
import com.tamic.statInterface.statsdk.model.ExceptionInfo;
import com.tamic.statInterface.statsdk.util.DeviceUtil;
import java.lang.Thread.UncaughtExceptionHandler;

public class TcCrashHandler implements UncaughtExceptionHandler {
    private Context context;
    public static TcCrashHandler INSTANCE;
    private UncaughtExceptionHandler uncaughtExceptionHandler;

    private TcCrashHandler() {
    }

    public void init(Context context) {
        this.context = context;
        this.uncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public static TcCrashHandler getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new TcCrashHandler();
        }

        return INSTANCE;
    }

    public void uncaughtException(Thread thread, Throwable ex) {
        if(ex != null) {
            StackTraceElement[] stackTraceElements = ex.getStackTrace();
            Log.i("jiangTest", stackTraceElements.length + "---");
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(ex.getMessage()).append("\n");

            for(int i = stackTraceElements.length - 1; i >= 0; --i) {
                stringBuffer.append(stackTraceElements[i].getFileName()).append(":").append(stackTraceElements[i].getClassName()).append(stackTraceElements[i].getMethodName()).append("(").append(stackTraceElements[i].getLineNumber()).append(")").append("\n");
            }

            Log.i("jiangTest", stringBuffer.toString());
            StaticsAgent.storeObject(new ExceptionInfo(DeviceUtil.getPhoneModel(), DeviceUtil.getSystemModel(), String.valueOf(DeviceUtil.getSystemVersion()), stringBuffer.toString()));
        }

        Process.killProcess(Process.myPid());
        System.exit(0);
    }
}
