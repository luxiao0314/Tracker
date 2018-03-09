//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.tamic.statInterface.statsdk.db.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.tamic.statInterface.statsdk.db.TcNote;
import java.util.ArrayList;

public class ReadDataBaseAccess {
    private DataBaseHandler handler = null;
    private static ReadDataBaseAccess readAccess = null;
    private static boolean isConnectionBusy = false;

    protected ReadDataBaseAccess(Context context) {
        this.handler = DataBaseHandler.readInstance(context);
    }

    public static synchronized ReadDataBaseAccess shareInstance(Context context) {
        readAccess = new ReadDataBaseAccess(context);
        return readAccess;
    }

    public ArrayList<TcNote> loadAll() {
        SQLiteDatabase connection = this.handler.getReadConnection(Thread.currentThread().getStackTrace()[2].getMethodName());
        Cursor cursor = connection.rawQuery("select * from T_Note", (String[])null);
        ArrayList notes = new ArrayList();

        while(cursor.moveToNext()) {
            TcNote note = new TcNote();
            note.setFirstCloumn(cursor.getString(1));
            note.setSecondCloumn(cursor.getString(2));
            note.setThirdCloumn(cursor.getString(3));
            note.setForthCloumn(cursor.getString(4));
            notes.add(note);
        }

        cursor.close();
        this.handler.closeConnection(connection, Thread.currentThread().getStackTrace()[2].getMethodName());
        return notes;
    }
}