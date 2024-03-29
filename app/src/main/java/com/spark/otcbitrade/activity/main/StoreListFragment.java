package com.spark.otcbitrade.activity.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.spark.otcbitrade.R;
import com.spark.otcbitrade.activity.store.StorePublishActivity;
import com.spark.otcbitrade.base.BaseLazyFragment;
import com.spark.otcbitrade.event.CheckLoginSuccessEvent;
import com.spark.otcbitrade.utils.StringUtils;
import com.spark.otcbitrade.utils.ToastUtils;
import com.spark.library.otc.model.MessageResult;
import com.spark.library.otc.model.MessageResultAuthMerchantFrontVo;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 商家
 */

public class StoreListFragment extends BaseLazyFragment implements StoreListContract.View {
    public static final String TAG = StoreListFragment.class.getSimpleName();
    @BindView(R.id.tvCoin)
    TextView tvCoin;
    @BindView(R.id.tvPrice)
    TextView tvPrice;
    @BindView(R.id.tvCoinName)
    TextView tvCoinName;
    @BindView(R.id.llBuy)
    RelativeLayout llBuy;

    private String coinName;
    private StoreListPresenterImpl presenter;
    private String priceStr;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 1) {

        }
    }

    public static StoreListFragment getInstance(String coinName) {
        StoreListFragment storeListFragment = new StoreListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("coinName", coinName);
        storeListFragment.setArguments(bundle);
        return storeListFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_store_list;
    }

    @Override
    protected void initView() {
        super.initView();
        setShowBackBtn(true);
    }

    @Override
    protected void initData() {
        presenter = new StoreListPresenterImpl(this);
        Bundle bundle = getArguments();
        if (bundle != null) {
            coinName = bundle.getString("coinName");
            tvCoin.setText(coinName);
            tvCoinName.setText("出" + coinName);
        }
    }

    @Override
    protected void loadData() {
        super.loadData();
        if (StringUtils.isNotEmpty(coinName)) {
            presenter.priceFind(coinName, "CNY");
        }
    }

    @Override
    public void priceFindSuccess(MessageResult obj) {
        if (obj != null && obj.getData() != null) {
            priceStr = obj.getData().toString();
            tvPrice.setText(priceStr + " CNY");
        }
    }

    @OnClick({R.id.llBuy})
    @Override
    protected void setOnClickListener(View v) {
        super.setOnClickListener(v);
        switch (v.getId()) {
            case R.id.llBuy:
                presenter.findAuthMerchantStatus();
                break;
        }
    }

    /**
     * check uc、ac、acp成功后，通知刷新界面
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCheckLoginSuccessEvent(CheckLoginSuccessEvent response) {
        if (StringUtils.isNotEmpty(coinName)) {
            presenter.priceFind(coinName, "CNY");
        }
    }

    @Override
    public void findAuthMerchantStatusSuccess(MessageResultAuthMerchantFrontVo obj) {
        //认证商家状态 0：未认证 1：认证-待审核 2：认证-审核成功 3：认证-审核失败 5：退保-待审核 6：退保-审核失败 7:退保-审核成功
        if (obj != null && obj.getData() != null) {
            int certifiedBusinessStatus = obj.getData().getCertifiedBusinessStatus();
            if (certifiedBusinessStatus == 2) {
                Bundle bundle = new Bundle();
                bundle.putString("coinName", coinName);
                bundle.putString("priceStr", priceStr);
                showActivity(StorePublishActivity.class, bundle, 1);
            } else {
                ToastUtils.showToast("请前往pc端进行商家认证后才能发布广告");
            }
        } else if (obj.getCode() == 30548) {
            ToastUtils.showToast("请前往pc端进行商家认证后才能发布广告");
        } else {
            ToastUtils.showToast("认证信息获取失败");
        }
    }
}
