//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.tamic.statInterface.statsdk.sp;

import android.content.Context;
import android.content.SharedPreferences;
import com.tamic.statInterface.statsdk.util.JsonUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SharedPreferencesHelper {
    private static SharedPreferencesHelper sInstance;
    private static SharedPreferences sSettings;

    public static synchronized SharedPreferencesHelper getInstance(Context outContext) {
        if(sInstance == null) {
            sInstance = new SharedPreferencesHelper(outContext);
        }

        return sInstance;
    }

    private SharedPreferencesHelper(Context outContext) {
        String OUT_APP_PACKAGENAME = outContext.getPackageName();
        sSettings = outContext.getSharedPreferences(OUT_APP_PACKAGENAME, 0);
    }

    public void putString(String key, String value) {
        SharedPreferences var3 = sSettings;
        synchronized(sSettings) {
            sSettings.edit().putString(key, value).commit();
        }
    }

    public String getString(String key) {
        SharedPreferences var2 = sSettings;
        synchronized(sSettings) {
            return sSettings.getString(key, "");
        }
    }

    public String getString(String key, String defValue) {
        SharedPreferences var3 = sSettings;
        synchronized(sSettings) {
            return sSettings.getString(key, defValue);
        }
    }

    public Double getDouble(String key) {
        String retStr = this.getString(key, (String)null);
        Double ret = null;

        try {
            ret = Double.valueOf(Double.parseDouble(retStr));
        } catch (Exception var5) {
            ;
        }

        return ret;
    }

    public void putBoolean(String key, boolean bool) {
        SharedPreferences var3 = sSettings;
        synchronized(sSettings) {
            sSettings.edit().putBoolean(key, bool).commit();
        }
    }

    public void putInteger(String key, int integer) {
        SharedPreferences var3 = sSettings;
        synchronized(sSettings) {
            sSettings.edit().putInt(key, integer).commit();
        }
    }

    public void putLong(String key, long lon) {
        SharedPreferences var4 = sSettings;
        synchronized(sSettings) {
            sSettings.edit().putLong(key, lon).commit();
        }
    }

    public Boolean getBoolean(String key, boolean defValue) {
        SharedPreferences var3 = sSettings;
        synchronized(sSettings) {
            return Boolean.valueOf(sSettings.getBoolean(key, defValue));
        }
    }

    public int getInteger(String key, int defValue) {
        SharedPreferences var3 = sSettings;
        synchronized(sSettings) {
            return sSettings.getInt(key, defValue);
        }
    }

    public long getLong(String key, long defValue) {
        SharedPreferences var4 = sSettings;
        synchronized(sSettings) {
            return sSettings.getLong(key, defValue);
        }
    }

    public void putHashMap(String key, HashMap<String, String> map) {
        JSONObject ret = new JSONObject(map);
        SharedPreferences var4 = sSettings;
        synchronized(sSettings) {
            sSettings.edit().putString(key, ret.toString()).commit();
        }
    }

    public HashMap<String, String> getHashMap(String key) {
        return this.getHashMapByKey(key);
    }

    public HashMap<String, String> getHashMapByKey(String key) {
        HashMap<String, String> ret = new HashMap();
        String mapStr = this.getString(key, "{}");
        JSONObject mapJson = null;

        try {
            mapJson = new JSONObject(mapStr);
        } catch (Exception var8) {
            return ret;
        }

        if(mapJson != null) {
            Iterator it = mapJson.keys();

            while(it.hasNext()) {
                String theKey = (String)it.next();
                String theValue = mapJson.optString(theKey);
                ret.put(theKey, theValue);
            }
        }

        return ret;
    }

    public void putArrayList(String key, ArrayList<String> list) {
        JSONArray ret = new JSONArray(list);
        this.putString(key, ret.toString());
    }

    public ArrayList<String> getArrayList(String key) {
        ArrayList<String> ret = new ArrayList();
        String listStr = this.getString(key, "{}");
        JSONArray listJson = null;

        try {
            listJson = new JSONArray(listStr);
        } catch (Exception var7) {
            return ret;
        }

        if(listJson != null) {
            for(int i = 0; i < listJson.length(); ++i) {
                String temp = listJson.optString(i);
                ret.add(temp);
            }
        }

        return ret;
    }

    public void removeByKey(String key) {
        SharedPreferences var2 = sSettings;
        synchronized(sSettings) {
            sSettings.edit().remove(key).commit();
        }
    }

    public void putJsonArray(String key, JSONArray value) {
        this.putString(key, value.toString());
    }

    public JSONArray getJsonArray(String key) {
        JSONArray ret = null;
        String jsonArrayStr = this.getString(key);

        try {
            ret = new JSONArray(jsonArrayStr);
        } catch (JSONException var5) {
            ret = null;
        }

        return ret;
    }

    public void putObject(String key, Object obj) {
        String toSave = JsonUtil.toJSONString(obj);
        this.putString(key, toSave);
    }

    public <T> T getObject(String key, Class<T> cla) {
        String temp = this.getString(key);
        return temp != null && temp.trim().length() != 0?JsonUtil.parseObject(temp, cla):null;
    }
}
