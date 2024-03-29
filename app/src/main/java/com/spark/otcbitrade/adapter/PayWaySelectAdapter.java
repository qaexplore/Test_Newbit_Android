package com.spark.otcbitrade.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.spark.otcbitrade.MyApplication;
import com.spark.otcbitrade.R;
import com.spark.otcbitrade.entity.PayWaySetting;
import com.spark.otcbitrade.entity.User;
import com.spark.otcbitrade.utils.GlobalConstant;
import com.spark.otcbitrade.utils.StringUtils;

import java.util.List;

/**
 * 选择收款方式
 */
public class PayWaySelectAdapter extends BaseQuickAdapter<PayWaySetting, BaseViewHolder> {

    public PayWaySelectAdapter(@Nullable List<PayWaySetting> data) {
        super(R.layout.adapter_pay_way_select, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PayWaySetting item) {
        switch (item.getPayType()) {
            case GlobalConstant.alipay:
                helper.setImageResource(R.id.ivType, R.mipmap.icon_pay);
                helper.setText(R.id.tvBankName, "支付宝");
                break;
            case GlobalConstant.wechat:
                helper.setImageResource(R.id.ivType, R.mipmap.icon_wechat);
                helper.setText(R.id.tvBankName, "微信");
                break;
            case GlobalConstant.card:
                helper.setImageResource(R.id.ivType, R.mipmap.icon_unionpay);
                helper.setText(R.id.tvBankName, item.getBank());
                break;
            case GlobalConstant.PAYPAL:
                helper.setImageResource(R.id.ivType, R.mipmap.icon_paypal);
                helper.setText(R.id.tvBankName, "PayPal");
                break;
            case GlobalConstant.other:
                helper.setImageResource(R.id.ivType, R.mipmap.other);
                helper.setText(R.id.tvBankName, "其他");
                break;
            default:
                helper.setImageResource(R.id.ivType, R.mipmap.other);
                helper.setText(R.id.tvBankName, "其他");
                break;
        }
        if (item.getIsSelected() == 1) {
            helper.setVisible(R.id.ivStatus, true);
        } else {
            helper.setVisible(R.id.ivStatus, false);
        }
//        String name = "";
//        User user = MyApplication.getApp().getCurrentUser();
//        if (user != null && StringUtils.isNotEmpty(user.getRealName())) {
//            name = user.getRealName();
//        }
        helper.setText(R.id.tvName, item.getRealName()).setText(R.id.tvBankNum, item.getPayAddress());

    }


}
