package com.spark.otcbitrade.utils;


import android.support.v4.app.Fragment;

import com.spark.otcbitrade.base.BaseActivity;
import com.spark.otcbitrade.entity.LoadExceptionEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by wonderful on 2017/5/23.
 * 网络请求错误
 */

public class NetCodeUtils {

    public static void checkedErrorCode(BaseActivity activity, Integer code, String toastMessage) {
        String toast = "";
        switch (code) {
            case GlobalConstant.LOGIN_ERROR:
                EventBus.getDefault().post(new LoadExceptionEvent());
                return;
        }
        toast = toastMessage;
        if (!StringUtils.isEmpty(toastMessage)) {
            ToastUtils.showToast(toast);
            return;
        }
    }


    public static void checkedErrorCode(Fragment fragment, Integer code, String toastMessage) {
        String toast = "";
        switch (code) {
            case GlobalConstant.LOGIN_ERROR:
                EventBus.getDefault().post(new LoadExceptionEvent());
                return;
        }
        toast = toastMessage;
        if (!StringUtils.isEmpty(toastMessage)) {
            ToastUtils.showToast(toast);
            return;
        }
    }


}
