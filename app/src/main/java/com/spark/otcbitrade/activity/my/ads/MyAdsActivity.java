package com.spark.otcbitrade.activity.my.ads;

import android.content.Intent;
import android.widget.FrameLayout;

import com.spark.otcbitrade.R;
import com.spark.otcbitrade.base.BaseTransFragmentActivity;

import butterknife.BindView;

/**
 * 我的广告
 */
public class MyAdsActivity extends BaseTransFragmentActivity {

    @BindView(R.id.flContainer)
    FrameLayout flContainer;

    private MyAdsFragment orderFragment;

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_my_order2;
    }

    @Override
    protected void initView() {
        reCoveryView();
    }

    /**
     * 避免出现activity被销毁，fragment重新创建，造成界面重叠
     */
    private void reCoveryView() {
        if (fragments.size() == 0) {
            recoverFragment();
        }
        selecte(0);
    }


    private void selecte(int page) {
        showFragment(fragments.get(page));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }


    @Override
    protected void initFragments() {
        if (orderFragment == null) fragments.add(orderFragment = new MyAdsFragment());
    }

    @Override
    protected void recoverFragment() {
        orderFragment = (MyAdsFragment) getSupportFragmentManager().findFragmentByTag(MyAdsFragment.TAG);
        if (orderFragment == null) fragments.add(orderFragment = new MyAdsFragment());
        else fragments.add(orderFragment);
    }

    @Override
    public int getContainerId() {
        return R.id.flContainer;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            selecte(0);
        }
    }

}
