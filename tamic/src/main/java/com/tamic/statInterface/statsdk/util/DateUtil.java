//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.tamic.statInterface.statsdk.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public DateUtil() {
    }

    public static long getCurrentTime() {
        long time = System.currentTimeMillis();
        return time;
    }

    public static String getDateString(long milliseconds, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(milliseconds));
    }
}
