package com.spark.otcbitrade.utils.okhttp;


import com.spark.otcbitrade.utils.LogUtils;

import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by Administrator on 2017/9/29.
 */

public class RequestCall {
    private OkHttpRequest okHttpRequest;
    private Request request;
    private Call call;

    public RequestCall(OkHttpRequest okHttpRequest) {
        this.okHttpRequest = okHttpRequest;
    }

    public void execute(Callback callback) {
        generateCall(callback);
        if (callback != null) {
            callback.onBefore(request);
        }
        OkhttpUtils.getInstance().execute(this, callback);
    }

    private Call generateCall(Callback callback) {
        request = generateRequest(callback);
        LogUtils.i("request headers==" + request.headers().toString());
//        if (readTimeOut > 0 || writeTimeOut > 0 || connTimeOut > 0) {
//            cloneClient();
//            readTimeOut = readTimeOut > 0 ? readTimeOut : OkHttpUtils.DEFAULT_MILLISECONDS;
//            writeTimeOut = writeTimeOut > 0 ? writeTimeOut : OkHttpUtils.DEFAULT_MILLISECONDS;
//            connTimeOut = connTimeOut > 0 ? connTimeOut : OkHttpUtils.DEFAULT_MILLISECONDS;
//
//            clone.setReadTimeout(readTimeOut, TimeUnit.MILLISECONDS);
//            clone.setWriteTimeout(writeTimeOut, TimeUnit.MILLISECONDS);
//            clone.setConnectTimeout(connTimeOut, TimeUnit.MILLISECONDS);
//
//            call = clone.newCall(request);
//        } else {
        call = OkhttpUtils.getInstance().getOkHttpClient().newCall(request);
//        }
        return call;
    }


    private Request generateRequest(Callback callback) {
        return okHttpRequest.generateRequest(callback);
    }

    public Call getCall() {
        return call;
    }
}


