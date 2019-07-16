package com.spark.otcbitrade.activity.releaseAd;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.spark.otcbitrade.R;
import com.spark.otcbitrade.adapter.PagerAdapter;
import com.spark.otcbitrade.base.BaseActivity;
import com.spark.otcbitrade.base.BaseFragment;
import com.spark.otcbitrade.entity.Ads;
import com.spark.otcbitrade.ui.CustomViewPager;
import com.spark.otcbitrade.ui.NoScrollViewPager;
import com.spark.otcbitrade.utils.GlobalConstant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 发布广告
 */
public class PubAdsActivity extends BaseActivity {
    public static final int REQUEST_COUNTRY = 0;
    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.viewPager)
    NoScrollViewPager viewPager;
    @BindView(R.id.llRoot)
    LinearLayout llRoot;
    private List<BaseFragment> fragments = new ArrayList<>();
    private Ads ads;

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_pub_ads;
    }

    @Override
    protected void initView() {
        setSetTitleAndBack(false, true);
        tvTitle.setText(R.string.text_titless);
        Bundle bundle = getIntent().getExtras();
        viewPager.setScroll(true);
        if (bundle != null) {
            ads = (Ads) bundle.getSerializable("ads");
            if (ads != null) {
                tvTitle.setText(getString(R.string.text_ad_change));
                tab.setVisibility(View.GONE);
                viewPager.setScroll(false);
                viewPager.setCurrentItem(1);
            }
        }
    }

    @Override
    protected void loadData() {
        super.loadData();
        setFragments();
    }

    private void setFragments() {
        addFragments();
        List<String> list = new ArrayList<>();
        list.add(getString(R.string.text_ad_title_buy));
        list.add(getString(R.string.text_ad_title_sell));
        viewPager.setOffscreenPageLimit(fragments.size() - 1);
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(), fragments, list));
        tab.setupWithViewPager(viewPager);
        if (ads != null) {
            if (ads.getAdvertiseType() == GlobalConstant.INT_BUY) viewPager.setCurrentItem(0);
            else viewPager.setCurrentItem(1);
        }
    }

    private void addFragments() {
        fragments.add(PubAdsFragment.getInstance(GlobalConstant.INT_BUY, ads));
        fragments.add(PubAdsFragment.getInstance(GlobalConstant.INT_SELL, ads));
    }

}
