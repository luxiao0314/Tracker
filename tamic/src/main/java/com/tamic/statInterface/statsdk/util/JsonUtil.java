//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.tamic.statInterface.statsdk.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import java.lang.reflect.Type;
import java.util.List;

public class JsonUtil {
    public JsonUtil() {
    }

    public static <T> T parseObject(String jsonStr, Class<T> entityClass) {
        Object ret = null;

        try {
            ret = JSON.parseObject(jsonStr, entityClass);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return (T) ret;
    }

    public static <T> T parseObject(String jsonStr, Type type) {
        Object obj = null;

        try {
            obj = JSON.parseObject(jsonStr, type, new Feature[]{Feature.AutoCloseSource});
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return (T) obj;
    }

    public static <T> T parseObject(String jsonStr, TypeReference<T> tf) {
        Object obj = null;

        try {
            obj = JSON.parseObject(jsonStr, tf, new Feature[]{Feature.AutoCloseSource});
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return (T) obj;
    }

    public static <T> List<T> parseList(String jsonStr, Class<T> entityClass) {
        List ret = null;

        try {
            ret = JSON.parseArray(jsonStr, entityClass);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return ret;
    }

    public static String toJSONString(Object obj) {
        String ret = null;

        try {
            ret = JSON.toJSONString(obj);
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        return ret;
    }
}
