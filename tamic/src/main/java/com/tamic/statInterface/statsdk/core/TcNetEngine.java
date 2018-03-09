//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.tamic.statInterface.statsdk.core;

import android.content.Context;
import android.os.Looper;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.tamic.statInterface.statsdk.constants.NetConfig;
import com.tamic.statInterface.statsdk.constants.StaticsConfig;
import com.tamic.statInterface.statsdk.http.TcHttpClient;
import com.tamic.statInterface.statsdk.util.JsonUtil;
import com.tamic.statInterface.statsdk.util.StatLog;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HeaderElement;
import cz.msebera.android.httpclient.ParseException;

public class TcNetEngine {
    private Context context;
    private TcHttpClient mHttpClient;
    private String mKey;
    protected int mRetrytimes;
    public static final String TAG = "TamicStat::TaNetEngine";
    protected boolean mCanContinue;
    private String mHostUrl;
    private TcNetEngine.PaJsonHttpResponseHandler mTaskHandler;
    private IUpLoadlistener mUpLoadlistener;
    private HashMap<String, String> headers;
    private RequestParams requestParams;
    Header[] reqHeaders;
    Header header;

    public TcNetEngine(Context context, IUpLoadlistener upLoadlistener) {
        this(context, (TcHttpClient)null, upLoadlistener);
    }

    public TcNetEngine(Context context, TcHttpClient httpClient, IUpLoadlistener upLoadlistener) {
        this.mRetrytimes = 3;
        this.mHostUrl = NetConfig.ONLINE_URL;
        this.context = context;
        this.mHttpClient = httpClient;
        this.mCanContinue = true;
        this.mTaskHandler = new TcNetEngine.PaJsonHttpResponseHandler(true);
        this.mUpLoadlistener = upLoadlistener;
        this.init();
    }

    private void init() {
        if(StaticsConfig.DEBUG) {
            this.mHostUrl = "http://www.baidu.com";
        }

        this.headers = new HashMap();
        this.requestParams = new RequestParams();
    }

    public TcHttpClient getHttpClient() {
        return this.mHttpClient;
    }

    public void setHttpClient(TcHttpClient mHttpClient) {
        this.mHttpClient = mHttpClient;
    }

    public String start(String... strings) {
        String str = JsonUtil.toJSONString(TcHeadrHandle.getHeader(this.context));
        StatLog.d("TamicStat::TaNetEngine", "head:" + str);
        if(this.headers.size() >= 0) {
            this.headers.clear();
        }

        this.headers.put("data_head", URLEncoder.encode(str));
        this.requestParams.remove("data_body");
        this.requestParams.put("data_body", strings[0]);
        StatLog.d("TamicStat::TaNetEngine", "body:" + strings[0]);
        if(this.headers != null && this.headers.size() > 0) {
            this.reqHeaders = new Header[this.headers.size()];
            Set<String> keys = this.headers.keySet();
            int index = 0;

            for(Iterator var5 = keys.iterator(); var5.hasNext(); this.reqHeaders[index++] = this.header) {
                final String mykey = (String)var5.next();
                this.header = new Header() {
                    public String getName() {
                        return mykey;
                    }

                    public String getValue() {
                        return (String)TcNetEngine.this.headers.get(mykey);
                    }

                    public HeaderElement[] getElements() throws ParseException {
                        return new HeaderElement[0];
                    }
                };
            }
        }

        TcHttpClient.post(this.context, this.mHostUrl, this.reqHeaders, this.requestParams, "application/json", this.mTaskHandler);
        return null;
    }

    void cancel() {
        TcHttpClient.cancle(this.mKey, true);
    }

    private class PaJsonHttpResponseHandler extends AsyncHttpResponseHandler {
        public PaJsonHttpResponseHandler() {
        }

        public PaJsonHttpResponseHandler(Looper looper) {
            super(looper);
        }

        public PaJsonHttpResponseHandler(boolean usePoolThread) {
            super(usePoolThread);
        }

        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            if(TcNetEngine.this.mUpLoadlistener != null) {
                TcNetEngine.this.mUpLoadlistener.onSucess();
            }

            Header[] var4 = headers;
            int var5 = headers.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                Header tmp = var4[var6];
                StatLog.d("TamicStat::TaNetEngine", tmp.getName() + ":" + tmp.getValue());
            }

            StatLog.d("TamicStat::TaNetEngine", "response code: " + statusCode);
            if(statusCode == 200) {
                StatLog.d("TamicStat::TaNetEngine", "onSuccess");
                TcNetEngine.this.mCanContinue = false;
            } else if(statusCode == 206) {
                TcNetEngine.this.mCanContinue = true;
            }

        }

        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            if(TcNetEngine.this.mUpLoadlistener != null) {
                TcNetEngine.this.mUpLoadlistener.onFailure();
            }

            TcNetEngine.this.cancel();
        }
    }
}
