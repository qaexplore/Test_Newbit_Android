package com.spark.otcbitrade.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.spark.otcbitrade.R;
import com.spark.otcbitrade.entity.PayWay;

import java.util.List;

/**
 * Created by Administrator on 2018/3/1.
 */
public class SelectPayWayAdapter extends BaseQuickAdapter<PayWay, BaseViewHolder> {
    public SelectPayWayAdapter(int layoutResId, @Nullable List<PayWay> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PayWay item) {
        helper.setText(R.id.tvCoinName, item.getName());
        helper.setVisible(R.id.ivSellect, item.isSelect());

    }
}
