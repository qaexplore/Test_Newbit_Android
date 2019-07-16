package com.spark.otcbitrade.activity.mychart;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.spark.otcbitrade.R;

/**
 * Created by wuzongjie on 2018/10/23
 */
public class LoadDialog extends Dialog{

    public LoadDialog(@NonNull Context context) {
        super(context, R.style.customDialog);
        initView();
        initSettings();
    }

    private void initView() {
        setContentView(R.layout.pop_loading);
    }

    private void initSettings() {
        setCanceledOnTouchOutside(false);
    }

}

