package com.spark.otcbitrade.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.spark.library.cms.model.ArticleType;
import com.spark.otcbitrade.MyApplication;
import com.spark.otcbitrade.R;
import com.spark.otcbitrade.entity.PayWaySetting;
import com.spark.otcbitrade.entity.User;
import com.spark.otcbitrade.utils.GlobalConstant;
import com.spark.otcbitrade.utils.StringUtils;

import java.util.List;

/**
 * 帮助中心
 */
public class HelpAdapter extends BaseQuickAdapter<ArticleType, BaseViewHolder> {

    public HelpAdapter(@Nullable List<ArticleType> data) {
        super(R.layout.adapter_my_help, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ArticleType item) {
        helper.setText(R.id.tvName, item.getName());

    }


}
