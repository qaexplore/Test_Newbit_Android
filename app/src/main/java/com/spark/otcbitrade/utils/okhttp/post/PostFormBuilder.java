package com.spark.otcbitrade.utils.okhttp.post;


import com.spark.otcbitrade.utils.SharedPreferenceInstance;
import com.spark.otcbitrade.utils.okhttp.OkhttpUtils;
import com.spark.otcbitrade.utils.okhttp.RequestBuilder;
import com.spark.otcbitrade.utils.okhttp.RequestCall;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/29.
 */

public class PostFormBuilder extends RequestBuilder {
    private List<FileInput> files = new ArrayList<>();

    @Override
    public PostFormBuilder url(String url) {
        this.url = url;
        return this;
    }

    @Override
    public RequestCall build() {
        int code = SharedPreferenceInstance.getInstance().getLanguageCode();
        if (code == 1) {
            addHeader("Accept-Language", "zh-CN,zh");
        } else if (code == 2) {
            addHeader("Accept-Language", "en-us,en");
        } else if (code == 3) {
            addHeader("Accept-Language", "ja-JP");
        }
//        String token = EncryUtils.getInstance().decryptString(SharedPreferenceInstance.getInstance().getToken(), MyApplication.getApp().getPackageName());
//        addHeader("access-auth-token", token);
        return new PostFormRequest(url, params, headers, files).build();
    }

    ///IdentityHashMap  与 hashMap
    @Override
    public PostFormBuilder addParams(String key, String value) {
        if (this.params == null) params = new HashMap<>();
        params.put(key, value);
        return this;
    }

    public PostFormBuilder addFile(String name, String filename, File file) {
        files.add(new FileInput(name, filename, file));
        return this;
    }

    @Override
    public RequestBuilder addParams(Map<String, String> map) {
        if (this.params == null) params = new HashMap<>();
        params.putAll(map);
        return this;
    }

    @Override
    public PostFormBuilder addHeader(String key, String value) {
        if (this.headers == null) headers = new HashMap<>();
        headers.put("User-Agent", OkhttpUtils.getUserAgent());
        headers.put(key, value);
        return this;
    }

    public static class FileInput {
        public String key;
        public String filename;
        public File file;

        public FileInput(String name, String filename, File file) {
            this.key = name;
            this.filename = filename;
            this.file = file;
        }
    }
}
