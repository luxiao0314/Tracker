//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.tamic.statInterface.statsdk.service;

import android.os.Build.VERSION;
import android.util.Log;
import java.util.concurrent.Executor;

public class Platform {
    private static final Platform PLATFORM = findPlatform();

    public Platform() {
    }

    public static Platform get() {
        Log.v("TcStatInterfacePlatform", PLATFORM.getClass().toString());
        return PLATFORM;
    }

    private static Platform findPlatform() {
        try {
            Class.forName("android.os.Build");
            if(VERSION.SDK_INT != 0) {
                return new Platform.Android();
            }
        } catch (ClassNotFoundException var1) {
            ;
        }

        return new Platform();
    }

    public Executor defaultCallbackExecutor() {
        return (new TcHandleThreadPool()).getExecutor();
    }

    public Object execute(Runnable runnable) {
        this.defaultCallbackExecutor().execute(runnable);
        return null;
    }

    static class Android extends Platform {
        Android() {
        }

        public Executor defaultCallbackExecutor() {
            return (new TcHandleThreadPool()).getExecutor();
        }
    }
}
