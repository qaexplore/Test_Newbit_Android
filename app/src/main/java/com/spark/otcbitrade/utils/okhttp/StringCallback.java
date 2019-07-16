package com.spark.otcbitrade.utils.okhttp;


import com.spark.otcbitrade.utils.StringFormatUtils;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by Administrator on 2017/9/29.
 */

public abstract class StringCallback extends Callback<String> {
    @Override
    public String parseNetworkResponse(Response response) throws IOException {
        return StringFormatUtils.formatHtml(response.body().string());
    }
}
