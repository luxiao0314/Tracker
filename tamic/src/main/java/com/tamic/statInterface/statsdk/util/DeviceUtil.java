//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.tamic.statInterface.statsdk.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class DeviceUtil {
    public DeviceUtil() {
    }

    public static String getAppVersionName(Context context) {
        String versionName = "";

        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if(TextUtils.isEmpty(versionName)) {
                return "";
            }
        } catch (Exception var4) {
            ;
        }

        return versionName;
    }

    public static int getAppVersionCode(Context context) {
        int versionCode = 0;

        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionCode = pi.versionCode;
        } catch (Exception var4) {
            ;
        }

        return versionCode;
    }

    public static int getSdkCode() {
        return 2;
    }

    public static String getSdkName() {
        return "1.0.3";
    }

    public static String getMacAddress(Context context) {
        WifiManager wifi = (WifiManager)context.getSystemService("wifi");
        WifiInfo info = wifi.getConnectionInfo();
        return info != null?info.getMacAddress():"";
    }

    public static DisplayMetrics getScreenDisplay(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm;
    }

    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager)context.getSystemService("window");
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager)context.getSystemService("window");
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    public static float getScreenDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    public static String getPhoneModel() {
        return Build.MODEL;
    }

    public static String getSystemModel() {
        return Build.BRAND;
    }

    public static int getSystemVersion() {
        return VERSION.SDK_INT;
    }
}
