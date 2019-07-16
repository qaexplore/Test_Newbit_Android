package com.spark.otcbitrade.activity.my.help;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.spark.library.cms.model.ArticleType;
import com.spark.library.cms.model.WebArticleVo;
import com.spark.library.otc.model.MessageResult;
import com.spark.otcbitrade.MyApplication;
import com.spark.otcbitrade.R;
import com.spark.otcbitrade.activity.bind_account.BindAccountActivity;
import com.spark.otcbitrade.activity.message.WebViewActivity;
import com.spark.otcbitrade.activity.my_account.MyAccountContract;
import com.spark.otcbitrade.activity.my_account.MyAccountPresenterImpl;
import com.spark.otcbitrade.adapter.HelpAdapter;
import com.spark.otcbitrade.adapter.PayWayAdapter;
import com.spark.otcbitrade.base.BaseActivity;
import com.spark.otcbitrade.dialog.DeleteDialog;
import com.spark.otcbitrade.entity.PayWaySetting;
import com.spark.otcbitrade.utils.StringUtils;
import com.spark.otcbitrade.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.spark.otcbitrade.utils.GlobalConstant.SUCCESS_CODE;


/**
 * 帮助中心
 */
public class HelpActivity extends BaseActivity implements HelpContract.View {
    @BindView(R.id.recyView)
    RecyclerView recyclerView;
    @BindView(R.id.ivAdd)
    ImageView ivAdd;

    private HelpPresenterImpl presenter;
    private List<ArticleType> payWaySettings;
    private HelpAdapter adapter;
    private DeleteDialog deleteDialog;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.destory();
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_my_account;
    }

    @Override
    protected void initView() {
        super.initView();
        setSetTitleAndBack(false, true);
        tvTitle.setText(R.string.str_help);
    }

    @Override
    protected void initData() {
        super.initData();
        presenter = new HelpPresenterImpl(this);
        initRv();
    }

    @Override
    protected void loadData() {
        super.loadData();
        refreshData();
    }

    private void refreshData() {
        if (MyApplication.getApp().isLogin()) {
            presenter.queryList(0);
        }
    }

    private void initRv() {
        payWaySettings = new ArrayList<>();
        adapter = new HelpAdapter(payWaySettings);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void queryListSuccess(List<ArticleType> response) {
        if (response != null) {
            for (ArticleType articleType : response) {
                if (articleType.getName().equals(getString(R.string.str_help))) {
                    presenter.queryList2(articleType.getId().intValue());
                }
            }
        }
    }

    @Override
    public void queryListSuccess2(List<ArticleType> response) {
        if (response != null) {
            payWaySettings.clear();
            payWaySettings.addAll(response);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void setListener() {
        super.setListener();
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ArticleType articleType = (ArticleType) adapter.getItem(position);
                presenter.webArticleQuery(articleType.getId());
            }
        });
    }

    @Override
    public void webArticleQuerySuccess(WebArticleVo response) {
        if (response != null) {
            Bundle bundle = new Bundle();
            bundle.putString("title", response.getTitle());
            bundle.putString("content", response.getContent());
            showActivity(WebViewActivity.class, bundle);
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("title", getString(R.string.detail_title));
            bundle.putString("content", "");
            showActivity(WebViewActivity.class, bundle);
        }
    }


}
