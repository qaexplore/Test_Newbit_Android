package com.spark.otcbitrade.activity.buy_or_sell;

import com.android.volley.VolleyError;
import com.spark.otcbitrade.entity.PayWaySetting;
import com.spark.otcbitrade.model.otc.PayControllerModel;
import com.spark.library.otc.model.AuthMerchantFrontVo;
import com.spark.library.otc.model.OrderInTransitDto;
import com.spark.otcbitrade.callback.ResponseCallBack;
import com.spark.otcbitrade.entity.HttpErrorEntity;
import com.spark.otcbitrade.model.otc.AdvertiseScanControllerModel;
import com.spark.otcbitrade.model.otc.TradeControllerModel;

import java.util.List;

/**
 * Created by Administrator on 2018/2/28.
 */

public class C2CBuyOrSellPresenterImpl implements C2CBuyOrSellContract.Presenter {
    private C2CBuyOrSellContract.View view;
    private TradeControllerModel tradeControllerModel;
    private AdvertiseScanControllerModel advertiseScanControllerModel;
    private PayControllerModel payControllerModel;

    public C2CBuyOrSellPresenterImpl(C2CBuyOrSellContract.View view) {
        this.view = view;
        this.tradeControllerModel = new TradeControllerModel();
        this.advertiseScanControllerModel = new AdvertiseScanControllerModel();
        this.payControllerModel = new PayControllerModel();
    }

    @Override
    public void showLoading() {
        if (view != null)
            view.showLoading();
    }

    @Override
    public void hideLoading() {
        if (view != null)
            view.hideLoading();
    }

    @Override
    public void destory() {
        view = null;
    }

    @Override
    public void createOrder(OrderInTransitDto orderInTransitDto) {
        tradeControllerModel.createOrder(orderInTransitDto, new ResponseCallBack.SuccessListener<String>() {
            @Override
            public void onResponse(String response) {
                if (view != null) {
                    view.createOrderSuccess(response);
                }
            }
        }, new ResponseCallBack.ErrorListener() {
            @Override
            public void onErrorResponse(HttpErrorEntity httpErrorEntity) {
                if (view != null)
                    view.dealError(httpErrorEntity);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (view != null)
                    view.dealError(volleyError);
            }
        });
    }

    @Override
    public void getAvgTime(Long memberId) {
        advertiseScanControllerModel.findAuthMerchant(memberId, new ResponseCallBack.SuccessListener<AuthMerchantFrontVo>() {
            @Override
            public void onResponse(AuthMerchantFrontVo response) {
                if (view != null) {
                    view.getAvgTimeSuccess(response);
                }
            }
        }, new ResponseCallBack.ErrorListener() {
            @Override
            public void onErrorResponse(HttpErrorEntity httpErrorEntity) {
                if (view != null)
                    view.dealError(httpErrorEntity);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (view != null)
                    view.dealError(volleyError);
            }
        });
    }

    @Override
    public void queryPayWayList() {
        showLoading();
        payControllerModel.queryListUsingGET(new ResponseCallBack.SuccessListener<List<PayWaySetting>>() {
            @Override
            public void onResponse(List<PayWaySetting> response) {
                hideLoading();
                if (view != null) {
                    view.queryPayWayListSuccess(response);
                }
            }
        }, new ResponseCallBack.ErrorListener() {
            @Override
            public void onErrorResponse(HttpErrorEntity httpErrorEntity) {
                hideLoading();
                if (view != null) {
                    view.dealError(httpErrorEntity);
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                hideLoading();
                if (view != null) {
                    view.dealError(volleyError);
                }
            }
        });
    }

}
