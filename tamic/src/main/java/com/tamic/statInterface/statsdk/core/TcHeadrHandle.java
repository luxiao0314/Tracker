//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.tamic.statInterface.statsdk.core;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.tamic.statInterface.statsdk.model.header.AppInfo;
import com.tamic.statInterface.statsdk.model.header.DeviceInfo;
import com.tamic.statInterface.statsdk.model.header.HeaderInfo;
import com.tamic.statInterface.statsdk.model.header.NetworkInfo;
import com.tamic.statInterface.statsdk.util.DeviceUtil;
import com.tamic.statInterface.statsdk.util.NetworkUtil;
import java.util.List;
import java.util.Locale;

public class TcHeadrHandle {
    private static AppInfo appinfo;
    private static DeviceInfo deviceinfo;
    private static NetworkInfo networkinfo;
    private static TelephonyManager mTelephonyMgr;
    private static HeaderInfo headerInfo;
    private static boolean isInit;
    private static int appId;
    private static String mChannel;

    public TcHeadrHandle() {
    }

    protected static boolean initHeader(Context context, int AppId, String channel) {
        if(headerInfo == null) {
            appId = AppId;
            mChannel = channel;
            networkinfo = new NetworkInfo();
            headerInfo = new HeaderInfo(getAppInfo(context), getDeviceInfo(context), getNetWorkInfo(context));
            isInit = true;
        }

        return isInit;
    }

    public static boolean isInit() {
        return isInit;
    }

    protected static HeaderInfo getHeader(Context context) {
        return headerInfo == null?new HeaderInfo(getAppInfo(context), getDeviceInfo(context), getNetWorkInfo(context)):headerInfo;
    }

    private static AppInfo getAppInfo(Context context) {
        if(appinfo != null) {
            return appinfo;
        } else {
            appinfo = new AppInfo();
            PackageManager manager = context.getPackageManager();
            PackageInfo info = null;
            String var3 = "";

            try {
                info = manager.getPackageInfo(context.getPackageName(), 0);
                appinfo.setApp_id(String.valueOf(appId));
                if(info != null) {
                    appinfo.setApp_version(info.versionName);
                }

                appinfo.setApp_id(String.valueOf(appId));
                appinfo.setChannel(mChannel);
                appinfo.setSdk_version(DeviceUtil.getSdkCode());
                appinfo.setSdk_verson_name(DeviceUtil.getSdkName());
                return appinfo;
            } catch (NameNotFoundException var5) {
                var5.printStackTrace();
                return null;
            }
        }
    }

    @SuppressLint("MissingPermission")
    private static DeviceInfo getDeviceInfo(Context context) {
        if(deviceinfo != null) {
            return deviceinfo;
        } else {
            deviceinfo = new DeviceInfo();
            mTelephonyMgr = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
            if(mTelephonyMgr != null) {
                deviceinfo.setDevice_id(mTelephonyMgr.getDeviceId());
                deviceinfo.setImei(mTelephonyMgr.getDeviceId());
            }

            String openId;
            try {
                openId = Secure.getString(context.getContentResolver(), "android_id");
                deviceinfo.setAndroid_id(openId);
                if(TextUtils.isEmpty(deviceinfo.getImei())) {
                    deviceinfo.setImei(openId);
                }
            } catch (Exception var2) {
                ;
            }

            deviceinfo.setMac(DeviceUtil.getMacAddress(context));
            deviceinfo.setModel(Build.MODEL);
            deviceinfo.setOs("Android");
            deviceinfo.setOs_version(VERSION.RELEASE);
            openId = deviceinfo.getDevice_id();
            if(openId == null || openId.trim().length() == 0) {
                openId = deviceinfo.getAndroid_id();
            }

            if(openId == null || openId.trim().length() == 0) {
                openId = deviceinfo.getMac();
            }

            deviceinfo.setOpenudid(openId);
            deviceinfo.setResolution(DeviceUtil.getScreenWidth(context) + "*" + DeviceUtil.getScreenHeight(context));
            deviceinfo.setDensity(String.valueOf(DeviceUtil.getScreenDensity(context)));
            deviceinfo.setLocale(Locale.getDefault().getLanguage());
            return deviceinfo;
        }
    }

    protected static NetworkInfo getNetWorkInfo(Context context) {
        if(networkinfo == null) {
            networkinfo = new NetworkInfo();
        }

        networkinfo.setIp_addr(NetworkUtil.getLocalIpAddress());
        networkinfo.setWifi_ind(Boolean.valueOf(NetworkUtil.isWifi(context)));
        if(mTelephonyMgr.getSimState() == 5) {
            networkinfo.setCarrier(mTelephonyMgr.getSimOperatorName());
        }

        Location location = getLocation(context);
        if(location != null) {
            networkinfo.setLatitude(String.valueOf(location.getLatitude()));
            networkinfo.setLongitude(String.valueOf(location.getLongitude()));
        }

        return networkinfo;
    }

    @SuppressLint("MissingPermission")
    private static Location getLocation(Context context) {
        LocationManager locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        String locationProvider;
        if(providers.contains("gps")) {
            locationProvider = "gps";
        } else if(providers.contains("network")) {
            locationProvider = "network";
        } else {
            locationProvider = "gps";
        }

        return locationManager.getLastKnownLocation(locationProvider);
    }
}
