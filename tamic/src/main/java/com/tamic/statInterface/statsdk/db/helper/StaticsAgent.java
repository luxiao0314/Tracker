//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.tamic.statInterface.statsdk.db.helper;

import android.content.Context;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.tamic.statInterface.statsdk.db.TcNote;
import com.tamic.statInterface.statsdk.db.database.DataAccess;
import com.tamic.statInterface.statsdk.db.database.ReadDataBaseAccess;
import com.tamic.statInterface.statsdk.db.database.WriteDataBaseAccess;
import com.tamic.statInterface.statsdk.model.AppAction;
import com.tamic.statInterface.statsdk.model.DataBlock;
import com.tamic.statInterface.statsdk.model.Event;
import com.tamic.statInterface.statsdk.model.ExceptionInfo;
import com.tamic.statInterface.statsdk.model.Page;
import com.tamic.statInterface.statsdk.util.JsonUtil;
import java.util.ArrayList;
import java.util.List;

public class StaticsAgent {
    private static Context mContext;
    private static TcNote note;

    public StaticsAgent() {
    }

    public static void init(Context context) {
        mContext = context;
        DataAccess.shareInstance(context).createAllTables();
    }

    public static void storeAppAction(String appAction) {
        if(TextUtils.isEmpty(appAction)) {
            throw new NullPointerException("appAction is null");
        } else {
            storeData(appAction, "", "");
        }
    }

    public static void storePage(String pageString) {
        if(TextUtils.isEmpty(pageString)) {
            throw new NullPointerException("pageString is null");
        } else {
            storeData("", pageString, "");
        }
    }

    public static void storeEvent(String eventString) {
        if(TextUtils.isEmpty(eventString)) {
            throw new NullPointerException("eventString is null");
        } else {
            storeData("", "", eventString);
        }
    }

    public static void storeException(String exceptionInfo) {
        if(TextUtils.isEmpty(exceptionInfo)) {
            throw new NullPointerException("exceptionInfo is null");
        } else {
            storeData("", "", "", exceptionInfo);
        }
    }

    public static DataBlock getDataBlock() {
        DataBlock dataBlock = new DataBlock();
        List<TcNote> list = ReadDataBaseAccess.shareInstance(mContext).loadAll();
        new AppAction();
        new Page();
        new Event();
        new ExceptionInfo();
        List<AppAction> actionList = new ArrayList();
        List<Page> pageList = new ArrayList();
        List<Event> eventList = new ArrayList();
        List<ExceptionInfo> exceptionInfos = new ArrayList();

        for(int i = 0; i < list.size(); ++i) {
            if(!TextUtils.isEmpty(((TcNote)list.get(i)).getFirstCloumn())) {
                AppAction appAction = (AppAction)JsonUtil.parseObject(((TcNote)list.get(i)).getFirstCloumn(), AppAction.class);
                actionList.add(appAction);
            }

            if(!TextUtils.isEmpty(((TcNote)list.get(i)).getSecondCloumn())) {
                Page page = (Page)JsonUtil.parseObject(((TcNote)list.get(i)).getSecondCloumn(), Page.class);
                pageList.add(page);
            }

            if(!TextUtils.isEmpty(((TcNote)list.get(i)).getThirdCloumn())) {
                Event event = (Event)JsonUtil.parseObject(((TcNote)list.get(i)).getThirdCloumn(), Event.class);
                eventList.add(event);
            }

            if(!TextUtils.isEmpty(((TcNote)list.get(i)).getForthCloumn())) {
                ExceptionInfo exceptionInfo = (ExceptionInfo)JsonUtil.parseObject(((TcNote)list.get(i)).getForthCloumn(), ExceptionInfo.class);
                exceptionInfos.add(exceptionInfo);
            }
        }

        dataBlock.setApp_action(actionList);
        dataBlock.setPage(pageList);
        dataBlock.setExceptionInfos(exceptionInfos);
        dataBlock.setEvent(eventList);
        return dataBlock;
    }

    public static void storeData(String firstcloumn, String secondcloumn, String thirdcloumn) {
        storeData(firstcloumn, secondcloumn, thirdcloumn, (String)null);
    }

    public static void storeData(String firstcloumn, String secondcloumn, String thirdcloumn, String forthCloumn) {
        note = new TcNote((Long)null, firstcloumn, secondcloumn, thirdcloumn, forthCloumn);
        WriteDataBaseAccess.shareInstance(mContext).insertData(note);
    }

    public static void storeObject(Object o) {
        if(o instanceof Event) {
            storeEvent(JSONObject.toJSONString(o));
        } else if(o instanceof AppAction) {
            storeAppAction(JSONObject.toJSONString(o));
        } else if(o instanceof Page) {
            storePage(JSONObject.toJSONString(o));
        } else if(o instanceof ExceptionInfo) {
            storeException(JSONObject.toJSONString(o));
        }

    }

    public static synchronized void deleteData() {
        WriteDataBaseAccess.shareInstance(mContext).deleteAllNote();
    }
}
