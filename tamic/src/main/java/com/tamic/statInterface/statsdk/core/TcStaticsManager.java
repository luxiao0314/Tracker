//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.tamic.statInterface.statsdk.core;

import android.content.Context;
import java.util.HashMap;

public interface TcStaticsManager {
    boolean onInit(int var1, String var2, String var3);

    void onSend();

    void onStore();

    void onRelease();

    void onRecordAppStart();

    void onRrecordPageEnd();

    void onRecordPageStart(Context var1);

    void onRrecordAppEnd();

    void onInitPage(String... var1);

    void onPageParameter(String... var1);

    void onInitEvent(String var1);

    void onEventParameter(String... var1);

    void onEvent(String var1, HashMap<String, String> var2);
}
