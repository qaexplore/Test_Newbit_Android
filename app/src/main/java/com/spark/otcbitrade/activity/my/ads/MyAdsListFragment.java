package com.spark.otcbitrade.activity.my.ads;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.VolleyError;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.spark.otcbitrade.R;
import com.spark.otcbitrade.activity.ads.AdsContract;
import com.spark.otcbitrade.activity.ads.AdsDialog;
import com.spark.otcbitrade.activity.ads.AdsPresenterImpl;
import com.spark.otcbitrade.activity.releaseAd.PubAdsActivity;
import com.spark.otcbitrade.activity.store.StorePublishActivity;
import com.spark.otcbitrade.adapter.AdsAdapter;
import com.spark.otcbitrade.base.BaseLazyFragment;
import com.spark.otcbitrade.dialog.PasswordDialog;
import com.spark.otcbitrade.entity.Ads;
import com.spark.otcbitrade.entity.FilterBean;
import com.spark.otcbitrade.entity.HttpErrorEntity;
import com.spark.otcbitrade.ui.FilterPopView;
import com.spark.otcbitrade.utils.GlobalConstant;
import com.spark.otcbitrade.utils.LogUtils;
import com.spark.otcbitrade.utils.StringUtils;
import com.spark.otcbitrade.utils.ToastUtils;
import com.spark.library.otc.model.QueryCondition;
import com.spark.library.otc.model.QueryParamAdvertiseVo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Administrator on 2018/2/28.
 */

public class MyAdsListFragment extends BaseLazyFragment implements AdsContract.View, FilterPopView.FilterCallBack {
    public static final int RETURN_ADS = 0;
    @BindView(R.id.rvContent)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.llHead)
    LinearLayout llHead;
    @BindView(R.id.ivFilter)
    ImageView ivFilter;

    private AdsPresenterImpl presenter;
    private List<Ads> objList = new ArrayList<>();
    private AdsAdapter adapter;
    private AdsDialog adsDialog;
    private Ads ads;
    private int pageNo = 1;
    private FilterPopView filterPopView;
    private ArrayList<FilterBean> firList;
    private ArrayList<FilterBean> secList;
    private ArrayList<FilterBean> thdList;
    private String unit = "";
    private String advertiseType = "";
    private String status = "";
    private int position = 1;//1已下架 2上架中
    private boolean isFirst = true;
    private int tradeType = 0;/* 广告商家类型 0 普通 1 商家 */

    public static MyAdsListFragment getInstance(int position) {
        MyAdsListFragment myAdsListFragment = new MyAdsListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        myAdsListFragment.setArguments(bundle);
        return myAdsListFragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == RETURN_ADS) {
            adapter.setEnableLoadMore(false);
            pageNo = 1;
            getList(false);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_c2c_list;
    }

    @Override
    protected void initData() {
        super.initData();
        adsDialog = new AdsDialog(activity);
        presenter = new AdsPresenterImpl(this);
        initRvAds();
        Bundle bundle = getArguments();
        if (bundle != null) {
            position = bundle.getInt("position");
        }
    }

    @Override
    protected void setListener() {
        super.setListener();
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ads = (Ads) adapter.getItem(position);
                if (ads != null) {
                    adsDialog.setAds(ads);
                    adsDialog.show();
                }
            }
        });

        adsDialog.getTvDelete().setOnClickListener(new View.OnClickListener() { // 删除
            @Override
            public void onClick(View view) {
                doOption(2);
                adsDialog.dismiss();
            }
        });

        adsDialog.getTvEdit().setOnClickListener(new View.OnClickListener() { // 修改
            @Override
            public void onClick(View view) {
                if (tradeType == 0) {/* 广告商家类型 0 普通 1 商家 */
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("ads", ads);
                    showActivity(PubAdsActivity.class, bundle, RETURN_ADS);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("ads", ads);
                    showActivity(StorePublishActivity.class, bundle, RETURN_ADS);
                }
                adsDialog.dismiss();
            }
        });

        adsDialog.getTvReleaseAgain().setOnClickListener(new View.OnClickListener() { // 下架
            @Override
            public void onClick(View view) {
                doOption(0);
                adsDialog.dismiss();
            }
        });

        adsDialog.getTvShelOff().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doOption(1);
                adsDialog.dismiss();

            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.setEnableLoadMore(false);
                pageNo = 1;
                getList(false);
            }
        });
        if (position == 1) {//已下架
            adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    refreshLayout.setEnabled(false);
                    pageNo = pageNo + 1;
                    getList(false);

                }
            }, recyclerView);
        }
    }

    /**
     * 获取列表
     */
    private void getList(boolean isShow) {
        if (isShow)
            showLoading();

        if (position == 2) {//上架中
            presenter.findMyAdvertise(tradeType);
        } else {//已下架
            QueryParamAdvertiseVo queryParam = new QueryParamAdvertiseVo();
            queryParam.setPageIndex(pageNo);
            queryParam.setPageSize(GlobalConstant.PageSize);

            List<QueryCondition> queryConditionList = new ArrayList<>();
            if (StringUtils.isNotEmpty(advertiseType)) {
                queryConditionList.add(getQueryCondition("advertiseType", advertiseType, "and", "="));
            }

            queryConditionList.add(getQueryCondition("tradeType", tradeType, "and", "="));

            queryParam.setQueryList(queryConditionList);

            queryParam.setSortFields("createTime_d");

            presenter.findMyAdvertiseAchive(queryParam);
        }

    }

    private QueryCondition getQueryCondition(String key, Object value, String join, String oper) {
        QueryCondition queryCondition = new QueryCondition();
        queryCondition.setKey(key);
        queryCondition.setValue(value);
        queryCondition.setJoin(join);
        queryCondition.setOper(oper);
        return queryCondition;
    }

    @Override
    protected void loadData() {
        getList(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isFirst) {
            getList(false);
        }
    }

    private void initRvAds() {
        LinearLayoutManager manager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(manager);
        adapter = new AdsAdapter(R.layout.item_ads, objList, activity);
        adapter.bindToRecyclerView(recyclerView);
    }

//    @Override
//    public void allAdsSuccess(List<Ads> obj) {
//        adapter.setEnableLoadMore(true);
//        adapter.loadMoreComplete();
//        if (refreshLayout != null) {
//            refreshLayout.setEnabled(true);
//            refreshLayout.setRefreshing(false);
//        }
//        if (obj == null) return;
//        if (pageNo == 1) {
//            objList.clear();
//            if (obj.size() == 0) {
//                adapter.setEmptyView(R.layout.empty_no_message);
//                adapter.notifyDataSetChanged();
//            }
//        } else if (obj.size() == 0) {
//            adapter.loadMoreEnd();
//        }
//        objList.addAll(obj);
//        adapter.notifyDataSetChanged();
//        adapter.disableLoadMoreIfNotFullPage();
//    }
//
//    @Override
//    public void allAdsFail(Integer code, String toastMessage) {
//        adapter.setEnableLoadMore(true);
//        refreshLayout.setEnabled(true);
//        refreshLayout.setRefreshing(false);
//        NetCodeUtils.checkedErrorCode((BaseActivity) activity, code, toastMessage);
//    }
//
//    @Override
//    public void doOptionSucess(String obj) {
//        ToastUtils.showToast(obj);
//        pageNo = 1;
//        getList(true);
//    }
//
//    @Override
//    public void allSuccess(List<CoinInfo> obj) {
//        if (obj == null || obj.size() == 0) return;
//        firList = new ArrayList<>();
//        for (CoinInfo coinInfo : obj) {
//            FilterBean filterBean = new FilterBean();
//            filterBean.setName(coinInfo.getUnit());
//            filterBean.setStrUpload(coinInfo.getUnit());
//            firList.add(filterBean);
//        }
//        secList = new ArrayList<>();
//        secList.add(new FilterBean(getString(R.string.all), "", true));
//        secList.add(new FilterBean(getString(R.string.text_buy), "0", false));
//        secList.add(new FilterBean(getString(R.string.text_sell), "1", false));
//
//        thdList = new ArrayList<>();
//        thdList.add(new FilterBean(getString(R.string.all), "", true));
//        thdList.add(new FilterBean(getString(R.string.grounding), "0", false));
//        thdList.add(new FilterBean(getString(R.string.shelved), "1", false));
//
//        filterPopView = new FilterPopView(activity, this, FilterPopView.CALLFROM_ADS,
//                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        filterPopView.setFirstList(firList);
//        filterPopView.setSecList(secList);
//        filterPopView.setThdList(thdList);
//        showPopWind();
//    }

    private void showPopWind() {
        filterPopView.setFocusable(true);
        filterPopView.setOutsideTouchable(true);
        filterPopView.showAsDropDown(llTitle);
        filterPopView.update();
    }

    /**
     * 操作
     *
     * @param type 0-上架，1-下架，2-删除
     */
    public void doOption(int type) {
        if (ads != null) {
            if (type == 0) {
                showPasswordDialog();
            } else if (type == 1)
                presenter.offShelvesAdvertise(ads.getId());
            else
                presenter.deleteAdvertise(ads.getId());
        }
    }

    private void showPasswordDialog() {
        final PasswordDialog passwordDialog = new PasswordDialog(getmActivity());
        passwordDialog.show();
        passwordDialog.setClicklistener(new PasswordDialog.ClickListenerInterface() {
            @Override
            public void doConfirm(String password) {
                passwordDialog.dismiss();
                presenter.onShelvesAdvertise(ads.getId(), password);
            }

            @Override
            public void doCancel() {
                passwordDialog.dismiss();
            }
        });
    }

    @Override
    public void callBack(HashMap<String, String> map) {
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                LogUtils.i("key= " + entry.getKey() + " and value= " + entry.getValue());
            }
            adapter.setEnableLoadMore(false);
            pageNo = 1;
            unit = map.get("unit");
            advertiseType = map.get("advertiseType");
            status = map.get("status");
            getList(false);
        }
    }


    @OnClick(R.id.ivFilter)
    public void onViewClicked() {
        if (filterPopView == null) {
//            presenter.all();
        } else {
            showPopWind();
        }
    }

    @Override
    public void findMyAdvertiseSuccess(List<Ads> obj) {
        hideAll();
        if (obj == null) return;
        if (pageNo == 1) {
            objList.clear();
            if (obj.size() == 0) {
                adapter.setEmptyView(R.layout.empty_no_message);
                adapter.notifyDataSetChanged();
            }
        } else if (obj.size() == 0) {
            adapter.loadMoreEnd();
        }
        objList.addAll(obj);
        adapter.notifyDataSetChanged();
        adapter.disableLoadMoreIfNotFullPage();
    }

    @Override
    public void findMyAdvertiseAchiveSuccess(List<Ads> list) {
        isFirst = false;
        refreshLayout.setEnabled(true);
        refreshLayout.setRefreshing(false);
        adapter.setEnableLoadMore(true);
        adapter.loadMoreComplete();
        if (list == null) return;
        if (pageNo == 1) {
            objList.clear();
            if (list.size() == 0) {
                adapter.loadMoreEnd();
                adapter.setEmptyView(R.layout.empty_no_message);
                adapter.notifyDataSetChanged();
            } else {
                objList.addAll(list);
            }
        } else {
            if (list.size() != 0) {
                objList.addAll(list);
            } else {
                adapter.loadMoreEnd();
            }
        }
        adapter.notifyDataSetChanged();
        adapter.disableLoadMoreIfNotFullPage();
    }

    @Override
    public void onShelvesAdvertiseSuccess(String obj) {
        hideAll();
        if (StringUtils.isNotEmpty(obj)) {
            ToastUtils.showToast(obj);
        }
        adapter.setEnableLoadMore(false);
        pageNo = 1;
        getList(true);
    }

    @Override
    public void offShelvesAdvertiseSuccess(String obj) {
        hideAll();
        if (StringUtils.isNotEmpty(obj)) {
            ToastUtils.showToast(obj);
        }
        adapter.setEnableLoadMore(false);
        pageNo = 1;
        getList(true);
    }

    @Override
    public void deleteAdvertiseSuccess(String obj) {
        hideAll();
        if (StringUtils.isNotEmpty(obj)) {
            ToastUtils.showToast(obj);
        }
        adapter.setEnableLoadMore(false);
        pageNo = 1;
        getList(true);
    }

    @Override
    public void dealError(HttpErrorEntity httpErrorEntity) {
        super.dealError(httpErrorEntity);
        hideAll();
    }

    @Override
    public void dealError(VolleyError volleyError) {
        super.dealError(volleyError);
        hideAll();
    }

    private void hideAll() {
        hideLoading();
        if (adapter != null) {
            adapter.setEnableLoadMore(true);
            adapter.loadMoreComplete();
        }

        if (refreshLayout != null) {
            refreshLayout.setEnabled(true);
            refreshLayout.setRefreshing(false);
        }
    }

    /* 广告商家类型 0 普通 1 商家 */
    public void setTradeType(int p) {
        tradeType = p;
        adapter.setEnableLoadMore(false);
        pageNo = 1;
        getList(true);
    }

    public void setSelectResult(String country, String payMode, String lowQuota, String highQuota) {
//        this.country = country;
//        this.payMode = payMode;
//        this.lowQuota = lowQuota;
//        this.highQuota = highQuota;
        adapter.setEnableLoadMore(false);
        pageNo = 1;
        getList(true);
    }

}
