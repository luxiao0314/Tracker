//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.tamic.statInterface.statsdk.db.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;
import java.util.concurrent.locks.ReentrantLock;

public class DataBaseHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "statlog.db";
    private static final int DB_VERSION = 1;
    private static DataBaseHandler mDataBaseHandler = null;
    private ReentrantLock lock;

    protected DataBaseHandler(Context context, String dbName, int dbVersion) {
        super(context, dbName, (CursorFactory)null, dbVersion);
        this.lock = null;
    }

    protected DataBaseHandler(Context context) {
        this(context, "statlog.db", 1);
    }

    protected DataBaseHandler(Context context, int dbVersion) {
        this(context, "statlog.db", dbVersion);
    }

    public static DataBaseHandler readInstance(Context context) {
        return new DataBaseHandler(context);
    }

    public static synchronized DataBaseHandler writeInstance(Context context) {
        if(mDataBaseHandler == null) {
            mDataBaseHandler = new DataBaseHandler(context);
        }

        return mDataBaseHandler;
    }

    public void onCreate(SQLiteDatabase db) {
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public SQLiteDatabase getReadConnection(String methodName) {
        Log.e("dblog", methodName + " read begin");
        SQLiteDatabase connection = null;

        try {
            connection = this.getReadableDatabase();
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        Log.e("dblog", methodName + " read end");
        return connection;
    }

    public synchronized SQLiteDatabase getWriteConnection(String methodName) {
        Log.e("dblog", methodName + " write begin");
        if(this.lock == null) {
            this.lock = new ReentrantLock();
        }

        this.lock.lock();
        SQLiteDatabase connection = null;

        try {
            connection = this.getWritableDatabase();
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        Log.e("dblog", methodName + " write end");
        return connection;
    }

    public void closeConnection(SQLiteDatabase connection, String methodName) {
        Log.e("dblog", methodName + " close begin");
        if(connection.isOpen()) {
            connection.close();
        }

        if(this.lock != null) {
            this.lock.unlock();
        }

        Log.e("dblog", methodName + " close end");
    }

    public void close() {
        super.close();
    }

    public boolean createTableWithSQL(String sql) {
        boolean result = false;
        SQLiteDatabase db = this.getWriteConnection("createTableWithSQL");
        if(db != null) {
            db.execSQL(sql);
            result = true;
        } else {
            result = false;
        }

        this.closeConnection(db, "createTableWithSQL");
        return result;
    }
}
