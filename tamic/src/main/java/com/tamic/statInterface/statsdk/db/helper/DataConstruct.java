//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.tamic.statInterface.statsdk.db.helper;

import android.content.Context;
import android.text.TextUtils;
import com.tamic.statInterface.statsdk.core.StaticsListener;
import com.tamic.statInterface.statsdk.model.AppAction;
import com.tamic.statInterface.statsdk.model.Event;
import com.tamic.statInterface.statsdk.model.KeyValueBean;
import com.tamic.statInterface.statsdk.model.Page;
import com.tamic.statInterface.statsdk.sp.SharedPreferencesHelper;
import com.tamic.statInterface.statsdk.util.DateUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class DataConstruct {
    private static Event event = null;
    private static CopyOnWriteArrayList<KeyValueBean> parameter = new CopyOnWriteArrayList();
    private static ConcurrentSkipListMap<String, Event> events = new ConcurrentSkipListMap();
    private static Page page = null;
    private static ArrayList<KeyValueBean> pageParameter = new ArrayList();
    private static AppAction appAction = null;
    private static StaticsListener staticsListener;
    private static String pageId;
    private static String referPageId;
    private static String REFERPAGE_ID = "referPage_Id";

    private DataConstruct() {
    }

    public static void initEvent(StaticsListener eventInterface, String event_name) {
        initEvent(eventInterface, event_name, (Map)null);
    }

    public static synchronized void initEvent(StaticsListener eventInterface, String eventName, Map<String, String> parameters) {
        if(eventInterface != null && !TextUtils.isEmpty(eventName)) {
            Event event = new Event();
            event.setPage_id(pageId);
            event.setReferer_page_id(referPageId);
            event.setEvent_name(eventName);
            event.setAction_time(DateUtil.getDateString(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss"));
            if(parameters != null && !parameters.isEmpty() && parameters.size() > 0) {
                CopyOnWriteArrayList<KeyValueBean> parameter = new CopyOnWriteArrayList();
                Iterator keys = parameters.keySet().iterator();

                while(keys.hasNext()) {
                    String key = (String)keys.next();
                    if(parameters.get(key) != null) {
                        parameter.add(new KeyValueBean(key, (String)parameters.get(key)));
                    }
                }

                if(!parameter.isEmpty()) {
                    event.setParameter(parameter);
                }
            }

            storeEvent(event);
        } else {
            throw new RuntimeException("you must set eventName!");
        }
    }

    public static void onEvent(String businessName, String businessValue) {
        if(!TextUtils.isEmpty(businessName) && !TextUtils.isEmpty(businessValue)) {
            parameter.add(new KeyValueBean(businessName, businessValue));
            if(event != null && !event.getEvent_name().isEmpty()) {
                event.setParameter(parameter);
                events.put(event.getEvent_name(), event);
                parameter.clear();
            } else {
                throw new RuntimeException("you must call initEvent before onEvent!");
            }
        }
    }

    public static void initPage(Context context, StaticsListener eventInterface, String page_Id, String referPage_Id) {
        staticsListener = eventInterface;
        pageId = page_Id;
        if(TextUtils.isEmpty(referPage_Id)) {
            referPageId = getReferPageId(context);
        } else {
            referPageId = referPage_Id;
        }

        recardPageId(context, page_Id);
        page = new Page();
        page.setPage_start_time(DateUtil.getDateString(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss"));
        page.setReferer_page_id(referPageId);
        page.setPage_id(pageId);
        pageParameter.clear();
    }

    public static void initPageParameter(String name, String value) {
        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(value)) {
            if(page != null) {
                pageParameter.add(new KeyValueBean(name, value));
                page.setParameter(pageParameter);
            }
        }
    }

    public static void storePage() {
        if(page == null) {
            throw new RuntimeException("you must init before storePage");
        } else {
            page.setPage_end_time(DateUtil.getDateString(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss"));
            StaticsAgent.storeObject(page);
        }
    }

    public static void storeAppAction(String type) {
        appAction = new AppAction();
        appAction.setAction_time(DateUtil.getDateString(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss"));
        appAction.setApp_action_type(type);
        StaticsAgent.storeObject(appAction);
        appAction = null;
    }

    private static synchronized void storeEvent(Event event) {
        if(event != null) {
            StaticsAgent.storeObject(event);
        }
    }

    public static synchronized void storeEvents() {
        if(events != null && events.size() != 0) {
            if(events.size() > 0) {
                Iterator keys = events.keySet().iterator();

                while(keys.hasNext()) {
                    String key = (String)keys.next();
                    StaticsAgent.storeObject(events.get(key));
                }
            }

            event = null;
            events.clear();
        }
    }

    public static void deleteData() {
        StaticsAgent.deleteData();
    }

    private static void recardPageId(Context context, String page_Id) {
        SharedPreferencesHelper.getInstance(context).putString(REFERPAGE_ID, page_Id);
    }

    private static String getReferPageId(Context context) {
        return SharedPreferencesHelper.getInstance(context).getString(REFERPAGE_ID);
    }
}
