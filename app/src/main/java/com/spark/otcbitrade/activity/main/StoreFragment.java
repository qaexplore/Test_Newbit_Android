package com.spark.otcbitrade.activity.main;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.spark.otcbitrade.MyApplication;
import com.spark.otcbitrade.R;
import com.spark.otcbitrade.activity.login.LoginActivity;
import com.spark.otcbitrade.activity.main.presenter.C2CPresenterImpl;
import com.spark.otcbitrade.activity.releaseAd.PubAdsActivity;
import com.spark.otcbitrade.adapter.PagerAdapter;
import com.spark.otcbitrade.base.BaseFragment;
import com.spark.otcbitrade.base.BaseNestingTransFragment;
import com.spark.otcbitrade.entity.Country;
import com.spark.otcbitrade.entity.FilterBean;
import com.spark.otcbitrade.event.CheckLoginSuccessEvent;
import com.spark.otcbitrade.ui.FilterPopView;
import com.spark.otcbitrade.utils.GlobalConstant;
import com.spark.otcbitrade.utils.ToastUtils;
import com.spark.library.otc.model.Coin;
import com.spark.library.otc.model.MessageResultAuthMerchantFrontVo;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 商家
 */

public class StoreFragment extends BaseNestingTransFragment implements C2CContract.C2CView {
    public static final String TAG = StoreFragment.class.getSimpleName();
    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.viewPager)
    ViewPager vp;
    @BindView(R.id.tvBuy)
    TextView tvBuy;
    @BindView(R.id.tvSell)
    TextView tvSell;
    @BindView(R.id.ivReleseAd)
    ImageView ivReleseAd;
    @BindView(R.id.ivFilter)
    ImageView ivFilter;
    @BindView(R.id.llBuyAndSell)
    LinearLayout llBuyAndSell;

    private C2CPresenterImpl presenter;
    private List<Coin> coinInfos = new ArrayList<>();
    private PagerAdapter adapter;
    private ArrayList<String> tabs = new ArrayList<>();

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        if (!isSetTitle) {
            immersionBar.setTitleBar(getActivity(), llTitle);
            isSetTitle = true;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_c2c;
    }

    @Override
    protected void initView() {
        setTitle(getResources().getString(R.string.exchange));
    }

    @Override
    protected void initData() {
        super.initData();
        presenter = new C2CPresenterImpl(this);
    }

    @Override
    protected void loadData() {
        super.loadData();
        if (coinInfos.size() <= 0) {
            presenter.listOtcTradeCoin();
        }
    }

    @Override
    public void listOtcTradeCoinSuccess(List<Coin> obj) {
        if (obj == null || obj.size() == 0) return;
        coinInfos.clear();
        coinInfos.addAll(obj);
        tabs.clear();
        fragments.clear();
        for (Coin coinInfo : coinInfos) {
            tabs.add(coinInfo.getCoinName());

            fragments.add(StoreListFragment.getInstance(coinInfo.getCoinName()));
        }
        if (fragments.size() > 4) tab.setTabMode(TabLayout.MODE_SCROLLABLE);
        else tab.setTabMode(TabLayout.MODE_FIXED);
        if (adapter == null) {
            vp.setAdapter(adapter = new PagerAdapter(getChildFragmentManager(), fragments, tabs));
            tab.setupWithViewPager(vp);
            vp.setOffscreenPageLimit(fragments.size() - 1);
        } else adapter.notifyDataSetChanged();
        isNeedLoad = false;
    }

    public static String getTAG() {
        return TAG;
    }

    @Override
    protected void addFragments() {
    }

    @Override
    protected void recoveryFragments() {
    }

    @Override
    protected String getmTag() {
        return TAG;
    }

    @Override
    public void countrySuccess(List<Country> obj) {
    }

    /**
     * check uc、ac、acp成功后，通知刷新界面
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCheckLoginSuccessEvent(CheckLoginSuccessEvent response) {
        if (coinInfos.size() <= 0) {
            presenter.listOtcTradeCoin();
        }
    }

    @Override
    public void findAuthMerchantStatusSuccess(MessageResultAuthMerchantFrontVo obj) {
    }
}
