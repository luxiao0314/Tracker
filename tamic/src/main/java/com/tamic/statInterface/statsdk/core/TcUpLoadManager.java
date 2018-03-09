//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.tamic.statInterface.statsdk.core;

import android.content.Context;
import android.text.TextUtils;
import com.tamic.statInterface.statsdk.db.helper.StaticsAgent;
import com.tamic.statInterface.statsdk.http.TcHttpClient;
import com.tamic.statInterface.statsdk.service.Platform;
import com.tamic.statInterface.statsdk.util.JsonUtil;
import com.tamic.statInterface.statsdk.util.NetworkUtil;
import com.tamic.statInterface.statsdk.util.StatLog;
import java.util.concurrent.atomic.AtomicReference;

public class TcUpLoadManager implements IUpLoadlistener {
    private Context mContext;
    private TcHttpClient mHttpClient;
    private static TcUpLoadManager sInstance;
    private Boolean isRunning = Boolean.valueOf(false);
    private AtomicReference<TcNetEngine> atomic;
    private TcNetEngine netEngine;
    private static final String TAG = TcNetEngine.class.getSimpleName();

    public static synchronized TcUpLoadManager getInstance(Context aContext) {
        if(sInstance == null) {
            sInstance = new TcUpLoadManager(aContext);
        }

        return sInstance;
    }

    private TcUpLoadManager(Context aContext) {
        this.mContext = aContext;
        this.init();
    }

    private void init() {
        this.mHttpClient = this.getHttpclient();
        this.atomic = new AtomicReference();
        this.netEngine = new TcNetEngine(this.mContext, this);
    }

    public void report(String jsonString) {
        if(NetworkUtil.isNetworkAvailable(this.mContext)) {
            if(!TextUtils.isEmpty(jsonString)) {
                this.atomic.set(this.netEngine);
                ((TcNetEngine)this.atomic.getAndSet(this.netEngine)).start(new String[]{jsonString});
            }
        }
    }

    public void cancle() {
        if(this.atomic.get() != null) {
            ((TcNetEngine)this.atomic.get()).cancel();
        }

    }

    public TcHttpClient getHttpclient() {
        if(this.mHttpClient == null) {
            this.mHttpClient = new TcHttpClient();
            TcHttpClient var10000 = this.mHttpClient;
            TcHttpClient.setTimeOut('썐');
        }

        return this.mHttpClient;
    }

    public void onStart() {
        this.isRunning = Boolean.valueOf(true);
    }

    public void onUpLoad() {
        this.isRunning = Boolean.valueOf(true);
    }

    public void onSucess() {
        this.isRunning = Boolean.valueOf(false);
        StatLog.d(TAG, "DELETE  ：StaticsAgent.deleteTable()");
        Platform.get().execute(new Runnable() {
            public void run() {
                StaticsAgent.deleteData();
                StatLog.d(TcUpLoadManager.TAG, "delete after :>>>>>>" + JsonUtil.toJSONString(StaticsAgent.getDataBlock()));
            }
        });
    }

    public void onFailure() {
        this.isRunning = Boolean.valueOf(false);
    }

    public void onCancell() {
        this.isRunning = Boolean.valueOf(false);
    }
}
