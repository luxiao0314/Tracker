//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.tamic.statInterface.statsdk.core;

import android.content.Intent;
import android.text.TextUtils;
import java.util.Set;

public final class TcIntentManager {
    private boolean mNotMainIntent;
    private static TcIntentManager sInstance;

    public static synchronized TcIntentManager getInstance() {
        if(sInstance == null) {
            sInstance = new TcIntentManager();
        }

        return sInstance;
    }

    private TcIntentManager() {
    }

    public boolean isNotMainIntent() {
        return this.mNotMainIntent;
    }

    public void setNotMainIntent(boolean aFlag) {
        this.mNotMainIntent = aFlag;
    }

    public static boolean isActionValidate(Intent aIntent) {
        return aIntent != null && !TextUtils.isEmpty(aIntent.getAction());
    }

    public boolean isMainIntent(Intent aIntent) {
        return this.isActionAs(aIntent, "android.intent.action.MAIN");
    }

    public boolean isViewIntent(Intent aIntent) {
        return this.isActionAs(aIntent, "android.intent.action.VIEW");
    }

    public boolean isSearchIntent(Intent aIntent) {
        return this.isActionAs(aIntent, "android.intent.action.SEARCH");
    }

    public boolean isWebSearchIntent(Intent aIntent) {
        return this.isActionAs(aIntent, "android.intent.action.WEB_SEARCH");
    }

    public boolean isDateChangedIntent(Intent aIntent) {
        return this.isActionAs(aIntent, "android.intent.action.DATE_CHANGED");
    }

    public boolean isUserPresentIntent(Intent aIntent) {
        return this.isActionAs(aIntent, "android.intent.action.USER_PRESENT");
    }

    public boolean isScreenOnIntent(Intent aIntent) {
        return this.isActionAs(aIntent, "android.intent.action.SCREEN_ON");
    }

    public boolean isScreenOffIntent(Intent aIntent) {
        return this.isActionAs(aIntent, "android.intent.action.SCREEN_OFF");
    }

    public boolean isDeviceStorageLowIntent(Intent aIntent) {
        return this.isActionAs(aIntent, "android.intent.action.DEVICE_STORAGE_LOW");
    }

    public boolean isCloseSystemDialogsIntent(Intent aIntent) {
        return this.isActionAs(aIntent, "android.intent.action.CLOSE_SYSTEM_DIALOGS");
    }

    public boolean isBatteryChangedIntent(Intent aIntent) {
        return this.isActionAs(aIntent, "android.intent.action.BATTERY_CHANGED");
    }

    public boolean isHeadsetPlugIntent(Intent aIntent) {
        return this.isActionAs(aIntent, "android.intent.action.HEADSET_PLUG");
    }

    public boolean isBootCompletedIntent(Intent aIntent) {
        return this.isActionAs(aIntent, "android.intent.action.BOOT_COMPLETED");
    }

    public boolean isConnectivityIntent(Intent aIntent) {
        return this.isActionAs(aIntent, "android.net.conn.CONNECTIVITY_CHANGE");
    }

    private boolean isActionAs(Intent aIntent, String aAction) {
        String action = getAction(aIntent);
        return action != null && aAction != null && action.equals(aAction);
    }

    private static String getAction(Intent aIntent) {
        return aIntent == null?null:aIntent.getAction();
    }

    private static boolean isCategoryAs(Intent aIntent, String aCategory) {
        Set<String> categories = getCategories(aIntent);
        return categories != null && aCategory != null && categories.contains(aCategory);
    }

    private static Set<String> getCategories(Intent aIntent) {
        return aIntent == null?null:aIntent.getCategories();
    }
}
