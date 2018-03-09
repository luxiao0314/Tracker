//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.tamic.statInterface.statsdk.util;

import android.os.Environment;
import android.util.Log;
import com.tamic.statInterface.statsdk.constants.StaticsConfig;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public final class StatLog {
    private static boolean sDebug;
    public static final String LOG_TAG = "StatLog";
    public static final boolean DEBUG_DEBUG = true;
    public static final boolean DEBUG_ERROR = true;
    public static final boolean DEBUG_PERFORMENCE = true;
    public static final boolean DEBUG_INFO = true;
    public static final boolean DEBUG_VERBOSE = true;
    public static final boolean DEBUG_WARN = true;
    public static final boolean DEBUG_EXCEPT = true;
    private static FileOutputStream mOutfilestream;
    private static FileOutputStream mLogcaOutfilestream;
    private static boolean mIsLogToFile;
    private static String mFolderName;
    private static String mLogFileName;
    private static String mLogFileNameLogcat;

    private StatLog() {
    }

    public static void setDebug(boolean aDebug) {
        sDebug = aDebug;
    }

    public static boolean isDebug() {
        return sDebug;
    }

    public static void d(String aTag, String aMessage) {
        if(sDebug) {
            doLog(StatLog.LogLevel.DEBUG, aTag, aMessage, (Throwable)null);
        }

    }

    public static void d(String aMessage) {
        if(sDebug) {
            doLog(StatLog.LogLevel.DEBUG, "StatLog", aMessage, (Throwable)null);
        }

    }

    public static void d(String aMessage, Throwable aThrow) {
        if(sDebug) {
            doLog(StatLog.LogLevel.DEBUG, "StatLog", aMessage, aThrow);
        }

    }

    public static void d(String aTag, String aMessage, Throwable aThrow) {
        if(sDebug) {
            doLog(StatLog.LogLevel.DEBUG, aTag, aMessage, aThrow);
        }

    }

    public static void p(String aMessage) {
        if(sDebug) {
            doLog(StatLog.LogLevel.DEBUG, "StatLog", aMessage, (Throwable)null);
        }

    }

    public static void p(String aTag, String aMessage) {
        if(sDebug) {
            doLog(StatLog.LogLevel.DEBUG, aTag, aMessage, (Throwable)null);
        }

    }

    public static void e(String aTag, String aMessage) {
        doLog(StatLog.LogLevel.ERROR, aTag, aMessage, (Throwable)null);
    }

    public static void e(String aMessage) {
        doLog(StatLog.LogLevel.ERROR, "StatLog", aMessage, (Throwable)null);
    }

    public static void e(String aMessage, Throwable aThrow) {
        doLog(StatLog.LogLevel.ERROR, "StatLog", aMessage, aThrow);
    }

    public static void i(String aTag, String aMessage) {
        if(sDebug) {
            doLog(StatLog.LogLevel.INFO, aTag, aMessage, (Throwable)null);
        }

    }

    public static void i(String aMessage) {
        if(sDebug) {
            doLog(StatLog.LogLevel.INFO, "StatLog", aMessage, (Throwable)null);
        }

    }

    public static void i(String aMessage, Throwable aThrow) {
        if(sDebug) {
            doLog(StatLog.LogLevel.INFO, "StatLog", aMessage, aThrow);
        }

    }

    public static void v(String aTAG, String aMessage) {
        if(sDebug) {
            doLog(StatLog.LogLevel.VERBOSE, aTAG, aMessage, (Throwable)null);
        }

    }

    public static void v(String aMessage) {
        if(sDebug) {
            doLog(StatLog.LogLevel.VERBOSE, "StatLog", aMessage, (Throwable)null);
        }

    }

    public static void v(String aMessage, Throwable aThrow) {
        if(sDebug) {
            doLog(StatLog.LogLevel.VERBOSE, "StatLog", aMessage, aThrow);
        }

    }

    public static void w(String aMessage) {
        if(sDebug) {
            doLog(StatLog.LogLevel.WARN, "StatLog", aMessage, (Throwable)null);
        }

    }

    public static void w(String aTag, String aMessage) {
        if(sDebug) {
            doLog(StatLog.LogLevel.WARN, aTag, aMessage, (Throwable)null);
        }

    }

    public static void w(String aTag, String aMessage, Throwable aThrow) {
        if(sDebug) {
            doLog(StatLog.LogLevel.WARN, aTag, aMessage, aThrow);
        }

    }

    public static void w(String aMessage, Throwable aThrow) {
        if(sDebug) {
            doLog(StatLog.LogLevel.WARN, "StatLog", aMessage, aThrow);
        }

    }

    public static void printStackTrace(Exception aException) {
        if(sDebug) {
            aException.printStackTrace();
        }

    }

    private static void doLog(StatLog.LogLevel aLevel, String aTag, String aMessage, Throwable aThrow) {
        if(aMessage == null) {
            aMessage = "";
        }

        switch(null.$SwitchMap$com$tamic$statInterface$statsdk$util$StatLog$LogLevel[aLevel.ordinal()]) {
        case 1:
            if(aThrow == null) {
                Log.d(aTag, aMessage);
            } else {
                Log.d(aTag, aMessage, aThrow);
            }
            break;
        case 2:
            if(aThrow == null) {
                Log.e(aTag, aMessage);
            } else {
                Log.e(aTag, aMessage, aThrow);
            }
            break;
        case 3:
            if(aThrow == null) {
                Log.i(aTag, aMessage);
            } else {
                Log.i(aTag, aMessage, aThrow);
            }
            break;
        case 4:
            if(aThrow == null) {
                Log.v(aTag, aMessage);
            } else {
                Log.v(aTag, aMessage, aThrow);
            }
            break;
        case 5:
            if(aThrow == null) {
                Log.w(aTag, aMessage);
            } else {
                Log.w(aTag, aMessage, aThrow);
            }
        }

        if(mIsLogToFile) {
            flushToFile(aTag, aMessage);
        }

    }

    public static void dumpLogcat() {
        BufferedReader localBufferedReader = null;

        try {
            if(Environment.getExternalStorageState().equals("mounted")) {
                File folder = new File(mFolderName);
                if(!folder.exists()) {
                    folder.mkdirs();
                }

                if(null == mLogcaOutfilestream) {
                    mLogcaOutfilestream = new FileOutputStream(mLogFileNameLogcat);
                }

                Process localProcess = Runtime.getRuntime().exec("logcat -v time -d");
                InputStream localInputStream = localProcess.getInputStream();
                InputStreamReader localInputStreamReader = new InputStreamReader(localInputStream);
                localBufferedReader = new BufferedReader(localInputStreamReader);

                for(String str1 = localBufferedReader.readLine(); str1 != null; str1 = localBufferedReader.readLine()) {
                    mLogcaOutfilestream.write(str1.getBytes("UTF-8"));
                    mLogcaOutfilestream.write("\n".getBytes());
                }

                return;
            }
        } catch (Exception var15) {
            var15.printStackTrace();
            return;
        } finally {
            try {
                if(localBufferedReader != null) {
                    localBufferedReader.close();
                }

                if(mLogcaOutfilestream != null) {
                    mLogcaOutfilestream.close();
                    mLogcaOutfilestream = null;
                }
            } catch (IOException var14) {
                var14.printStackTrace();
            }

        }

    }

    private static void flushToFile(String aTag, String aMessage) {
        if(Environment.getExternalStorageState().equals("mounted")) {
            try {
                File folder = new File(mFolderName);
                if(!folder.exists()) {
                    folder.mkdirs();
                }

                if(null == mOutfilestream) {
                    mOutfilestream = new FileOutputStream(mLogFileName);
                }

                String output = aTag + " : " + aMessage;
                mOutfilestream.write(output.getBytes("UTF-8"));
                mOutfilestream.write("\n".getBytes());
            } catch (Exception var4) {
                var4.printStackTrace();
            }

        }
    }

    public static void setWriteToFile(boolean bWritetoFile) {
        mIsLogToFile = bWritetoFile;
    }

    public static void logException(String aTag, Exception aException) {
        try {
            if(null == aException) {
                return;
            }

            if(sDebug) {
                aException.printStackTrace();
            }

            d(aTag, "========================= Exception Happened !!================================");
            d(aTag, aException.getMessage());
            StackTraceElement[] stack = aException.getStackTrace();

            for(int i = 0; i < stack.length; ++i) {
                d(aTag, stack[i].toString());
            }

            d(aTag, "========================= Exception Ended !!================================");
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    public static void printInvokeTrace(String aTag) {
        StackTraceElement[] stackTrace = (new Throwable()).getStackTrace();

        for(int i = 1; i < stackTrace.length; ++i) {
            d(aTag + ":  " + stackTrace[i].toString());
        }

    }

    public static void printInvokeTrace(String aTag, int aMax) {
        StackTraceElement[] stackTrace = (new Throwable()).getStackTrace();
        int n = Math.min(aMax, stackTrace.length);

        for(int i = 1; i < n; ++i) {
            d(aTag + ":  " + stackTrace[i].toString());
        }

    }

    static {
        sDebug = StaticsConfig.DEBUG;
        mIsLogToFile = false;
        mFolderName = Environment.getExternalStorageDirectory() + File.separator + "TaStatSdk" + File.separator + "StatLog" + File.separator + "log" + File.separator;
        mLogFileName = mFolderName + "TamicStatStat_log.txt";
        mLogFileNameLogcat = mFolderName + "TamicStat_lasttime_log.txt";
    }

    private static enum LogLevel {
        DEBUG,
        ERROR,
        INFO,
        VERBOSE,
        WARN;

        private LogLevel() {
        }
    }
}
