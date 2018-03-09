//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.tamic.statInterface.statsdk.core;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.tamic.statInterface.statsdk.constants.StaticsConfig;
import com.tamic.statInterface.statsdk.core.TcObserverPresenter.ScheduleListener;
import com.tamic.statInterface.statsdk.core.TcStatInterface.UploadPolicy;
import com.tamic.statInterface.statsdk.db.helper.DataConstruct;
import com.tamic.statInterface.statsdk.db.helper.StaticsAgent;
import com.tamic.statInterface.statsdk.model.DataBlock;
import com.tamic.statInterface.statsdk.service.Platform;
import com.tamic.statInterface.statsdk.util.JsonUtil;
import com.tamic.statInterface.statsdk.util.NetworkUtil;
import com.tamic.statInterface.statsdk.util.StatLog;

import java.io.InputStream;
import java.util.HashMap;

import cz.msebera.android.httpclient.util.EncodingUtils;

public class TcStaticsManagerImpl implements TcStaticsManager, ScheduleListener {
    private Context mContext;
    private static TcStaticsManager sInstance;
    private static TcObserverPresenter paObserverPresenter;
    private StaticsListener eventInterface;
    private TcStatiPollMgr statiPollMgr;
    HashMap<String, String> pageIdMaps = new HashMap();
    private static final String LOG_TAG = TcStatiPollMgr.class.getSimpleName();

    public TcStaticsManagerImpl(Context mContext) {
        this.mContext = mContext;
    }

    public boolean onInit(int appId, String channel, String fileName) {
        paObserverPresenter = new TcObserverPresenter(this);
        StaticsAgent.init(this.mContext);
        TcCrashHandler.getInstance().init(this.mContext);
        this.pageIdMaps = this.getStatIdMaps(fileName);
        this.statiPollMgr = new TcStatiPollMgr(this);
        return this.initHeader(appId, channel);
    }

    public void onSend() {
        Platform.get().execute(new Runnable() {
            public void run() {
                DataBlock dataBlock = StaticsAgent.getDataBlock();
                if(!dataBlock.getApp_action().isEmpty() || !dataBlock.getEvent().isEmpty() || !dataBlock.getPage().isEmpty()) {
                    StatLog.d("TamicStat::TaNetEngine", "TcStatfacr >> report is Start");
                    TcUpLoadManager.getInstance(TcStaticsManagerImpl.this.mContext).report(JsonUtil.toJSONString(dataBlock));
                }
            }
        });
    }

    public void onStore() {
        DataConstruct.storeEvents();
        DataConstruct.storePage();
    }

    public void onRelease() {
        if(paObserverPresenter != null) {
            paObserverPresenter.destroy();
        }

        this.stopSchedule();
    }

    public void onRecordAppStart() {
        this.onSend();
        DataConstruct.storeAppAction("1");
    }

    public void onRrecordPageEnd() {
        DataConstruct.storeEvents();
        DataConstruct.storePage();
        if(paObserverPresenter != null) {
            paObserverPresenter.onStop(this.mContext);
        }

        this.stopSchedule();
    }

    public void onRecordPageStart(Context context) {
        if(context != null) {
            this.startSchedule();
            String pageId = this.checkValidId(context.getClass().getSimpleName());
            if(pageId == null) {
                pageId = context.getClass().getSimpleName();
            }

            this.onInitPage(new String[]{pageId, null});
            if(paObserverPresenter != null) {
                paObserverPresenter.init(this.mContext);
            }

            if(paObserverPresenter != null) {
                paObserverPresenter.onStart(this.mContext);
            }

        }
    }

    public void onRrecordAppEnd() {
        DataConstruct.storeAppAction("2");
        this.onSend();
        this.onRelease();
    }

    public void onInitPage(String... strings) {
        DataConstruct.initPage(this.mContext, this.eventInterface, strings[0], strings[1]);
    }

    public void onPageParameter(String... strings) {
        DataConstruct.initPageParameter(strings[0], strings[1]);
    }

    public void onInitEvent(String eventName) {
        DataConstruct.initEvent(this.eventInterface, eventName);
    }

    public void onEventParameter(String... strings) {
        DataConstruct.onEvent(strings[0], strings[1]);
    }

    public void onEvent(String eventName, HashMap<String, String> parameters) {
        DataConstruct.initEvent(this.eventInterface, eventName, parameters);
    }

    private boolean initHeader(int appId, String channel) {
        return !TcHeadrHandle.isInit()?TcHeadrHandle.initHeader(this.mContext, appId, channel):false;
    }

    void onScheduleTimeOut() {
        StatLog.d(LOG_TAG, "onScheduleTimeOut  is sendData");
        this.onSend();
    }

    public void startSchedule() {
        if(StaticsConfig.DEBUG && TcStatInterface.uploadPolicy == UploadPolicy.UPLOAD_POLICY_DEVELOPMENT) {
            this.statiPollMgr.start(5000L);
            StatLog.d(LOG_TAG, "Schedule is start");
        } else if(NetworkUtil.isWifi(this.mContext)) {
            this.statiPollMgr.start((long)(TcStatInterface.getIntervalRealtime() * 60 * 1000));
        } else {
            this.statiPollMgr.start(1800000L);
        }

    }

    private String checkValidId(String name) {
        return TextUtils.isEmpty(name)?null:(name.length() <= 0?null:this.getPageId(name));
    }

    private String getPageId(String clazz) {
        return this.mContext == null?null:(String)this.pageIdMaps.get(clazz);
    }

    public void stopSchedule() {
        StatLog.d(LOG_TAG, "stopSchedule()");
        this.statiPollMgr.stop();
    }

    public void onStart() {
        StatLog.d(LOG_TAG, "startSchedule");
        this.startSchedule();
    }

    public void onStop() {
        this.stopSchedule();
    }

    public void onReStart() {
        this.stopSchedule();
        this.startSchedule();
    }

    public HashMap<String, String> getStatIdMaps(String jsonName) {
        HashMap<String, String> map = null;
        if(this.getFromAsset(jsonName) != null) {
            map = (HashMap)JSON.parseObject(this.getFromAsset("stat_id.json"), HashMap.class);
        }

        return map;
    }

    public String getFromAsset(String fileName) {
        String result = "";

        try {
            InputStream in = this.mContext.getResources().getAssets().open(fileName);
            int length = in.available();
            byte[] buffer = new byte[length];
            in.read(buffer);
            result = EncodingUtils.getString(buffer, "UTF-8");
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return result;
    }
}
