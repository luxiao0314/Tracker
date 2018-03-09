//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.tamic.statInterface.statsdk.db.database;

import android.content.Context;
import com.tamic.statInterface.statsdk.db.TcNote;
import java.util.ArrayList;

public class DataAccess {
    private static DataBaseHandler handler = null;
    private static DataAccess access = null;
    private static ReadDataBaseAccess readAccess = null;
    private static WriteDataBaseAccess writeAccess = null;
    private static Context appContext;

    public DataAccess() {
    }

    public static synchronized DataAccess shareInstance(Context context) {
        appContext = context;
        readAccess = ReadDataBaseAccess.shareInstance(appContext);
        if(access == null) {
            access = new DataAccess();
            handler = DataBaseHandler.writeInstance(appContext);
            writeAccess = WriteDataBaseAccess.shareInstance(appContext);
        }

        return access;
    }

    public void closeDataBase() {
        handler.close();
    }

    public void createAllTables() {
        this.createNoteTable();
        this.createCustomerTable();
    }

    private void createNoteTable() {
        boolean isCreatedSec = handler.createTableWithSQL("create table if not exists T_Note(_id integer primary key autoincrement,firstcloumn text,secondcloumn text,thirdcloumn text,forthcloumn text)");
        if(!isCreatedSec) {
            System.out.println("create table T_Note failure!");
        }

    }

    private void createCustomerTable() {
        boolean isCreatedSec = handler.createTableWithSQL("create table if not exists T_Customer(_id integer primary key autoincrement,name text)");
        if(!isCreatedSec) {
            System.out.println("create table T_Customer failure!");
        }

    }

    public boolean insertData(Object obj) {
        return writeAccess.insertData(obj);
    }

    public boolean insertNotes(ArrayList<?> notes) {
        return writeAccess.insertDatas(notes);
    }

    public boolean insertNote(TcNote note) {
        return writeAccess.insertData(note);
    }
}
