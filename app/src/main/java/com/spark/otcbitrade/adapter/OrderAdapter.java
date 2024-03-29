package com.spark.otcbitrade.adapter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.spark.library.otc.model.OrderVo;
import com.spark.otcbitrade.MyApplication;
import com.spark.otcbitrade.R;
import com.spark.otcbitrade.utils.MathUtils;

import java.util.List;

/**
 * Created by Administrator on 2018/2/5.
 */

public class OrderAdapter extends BaseQuickAdapter<OrderVo, BaseViewHolder> {
    private Context context;

    public OrderAdapter(Context context, @Nullable List<OrderVo> data) {
        super(R.layout.item_order, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderVo item) {

        if (MyApplication.getApp().getCurrentUser() != null && item.getMemberId().equals(MyApplication.getApp().getCurrentUser().getId())) {
            helper.setText(R.id.tvType, (item.getOrderType().equals("1") ? context.getString(R.string.text_buy) : context.getString(R.string.text_sell)))
                    .setTextColor(R.id.tvType, item.getOrderType().equals("1") ? context.getResources().getColor(R.color.main_font_green) : context.getResources().getColor(R.color.main_font_red))
                    .setText(R.id.tvCount, MathUtils.subZeroAndDot(MathUtils.getRundNumber(Double.valueOf(item.getNumber().toString()), 8, null)) + item.getCoinName())
                    .setText(R.id.tvTotal, MathUtils.subZeroAndDot(item.getMoney() + "") + "CNY");
//                    .setText(R.id.tvState, (item.getOrderType().equals("1") ? context.getString(R.string.sell_side) + ": " : context.getString(R.string.buy_side) + ": "));
        } else {
            helper.setText(R.id.tvType, (item.getOrderType().equals("0") ? context.getString(R.string.text_buy) : context.getString(R.string.text_sell)))
                    .setTextColor(R.id.tvType, item.getOrderType().equals("0") ? context.getResources().getColor(R.color.main_font_green) : context.getResources().getColor(R.color.main_font_red))
                    .setText(R.id.tvCount, MathUtils.subZeroAndDot(MathUtils.getRundNumber(Double.valueOf(item.getNumber().toString()), 8, null)) + item.getCoinName())
                    .setText(R.id.tvTotal, MathUtils.subZeroAndDot(item.getMoney() + "") + "CNY");
//                    .setText(R.id.tvState, (item.getOrderType().equals("0") ? context.getString(R.string.sell_side) + ": " : context.getString(R.string.buy_side) + ": "));
        }


    }


}
