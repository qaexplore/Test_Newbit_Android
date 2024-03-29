package com.spark.otcbitrade.model.otc;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.spark.otcbitrade.callback.ResponseCallBack;
import com.spark.otcbitrade.factory.HttpUrls;
import com.spark.otcbitrade.utils.LogUtils;
import com.spark.library.otc.api.AuthMerchantControllerApi;
import com.spark.library.otc.model.MessageResultAuthMerchantFrontVo;

/**
 * otc业务模块
 */

public class AuthMerchantControllerModel {
    private AuthMerchantControllerApi authMerchantControllerApi;

    public AuthMerchantControllerModel() {
        authMerchantControllerApi = new AuthMerchantControllerApi();
        authMerchantControllerApi.setBasePath(HttpUrls.OTC_HOST);
    }

    /**
     * 查看自己认证商家信息
     */
    public void findAuthMerchantStatus(final ResponseCallBack.SuccessListener<MessageResultAuthMerchantFrontVo> successListener, final ResponseCallBack.ErrorListener errorListener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                authMerchantControllerApi.findAuthMerchantUsingGET1(new Response.Listener<MessageResultAuthMerchantFrontVo>() {
                    @Override
                    public void onResponse(MessageResultAuthMerchantFrontVo response) {
                        LogUtils.i("response==" + response.toString());
                        if (successListener != null)
                            successListener.onResponse(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (errorListener != null)
                            errorListener.onErrorResponse(error);
                    }
                });
            }
        }).start();
    }


}
