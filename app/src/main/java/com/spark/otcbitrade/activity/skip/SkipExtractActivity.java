package com.spark.otcbitrade.activity.skip;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.spark.otcbitrade.activity.login.LoginActivity;
import com.spark.library.ac.model.MemberWallet;
import com.spark.otcbitrade.MyApplication;
import com.spark.otcbitrade.R;
import com.spark.otcbitrade.base.BaseActivity;
import com.spark.otcbitrade.dialog.SkipExtractTipDialog;
import com.spark.otcbitrade.utils.StringUtils;
import com.spark.otcbitrade.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 其它app跳转进来的提币页面
 */

public class SkipExtractActivity extends BaseActivity implements SkipExtractContract.View {
    @BindView(R.id.tvPay)
    TextView tvPay;
    @BindView(R.id.tvCoinName)
    TextView tvCoinName;
    @BindView(R.id.tvAmount)
    TextView tvAmount;

    private String coinName;
    private String amount;
    private MemberWallet memberWallet;

    private SkipExtractPresnetImpl presnet;

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_skip_extract;
    }

    @Override
    protected void initView() {
        super.initView();
        setSetTitleAndBack(false, false);
    }

    @Override
    protected void initData() {
        super.initData();
        setTitle(getString(R.string.activity_skip_extract_title));

        presnet = new SkipExtractPresnetImpl(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            coinName = bundle.getString("coinName");
            amount = bundle.getString("amount");

            if (StringUtils.isNotEmpty(coinName)) {
                tvCoinName.setText(coinName);
            }

            if (StringUtils.isNotEmpty(amount)) {
                tvAmount.setText(amount);
            }
        }
    }

    @Override
    protected void loadData() {
        super.loadData();
        getCoin();
    }

    private void getCoin() {
        if (MyApplication.getApp().isLogin()) {
            if (StringUtils.isNotEmpty(coinName)) {
                presnet.getAddress(coinName);
            }
        } else {
            ToastUtils.showToast(getString(R.string.text_login_first));
            showActivity(LoginActivity.class, null);
        }
    }

    @OnClick({R.id.tvPay})
    @Override
    protected void setOnClickListener(View v) {
        super.setOnClickListener(v);
        switch (v.getId()) {
            case R.id.tvPay:
                showTipDialog();
                break;
        }
    }

    private void showTipDialog() {
        final SkipExtractTipDialog extractTipDialog = new SkipExtractTipDialog(this);
        extractTipDialog.onPositiveClickLisenter(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                extractTipDialog.dismiss();
                if (memberWallet != null && StringUtils.isNotEmpty(memberWallet.getAddress())) {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("address", memberWallet.getAddress());
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    ToastUtils.showToast("未获取到提币地址,请重试");
                    getCoin();
                }
            }
        });
        extractTipDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presnet.destory();
    }

    @Override
    public void getAddressSuccess(MemberWallet obj) {
        if (obj == null) return;
        memberWallet = obj;
    }
}
