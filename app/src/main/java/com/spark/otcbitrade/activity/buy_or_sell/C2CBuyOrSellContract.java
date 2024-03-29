package com.spark.otcbitrade.activity.buy_or_sell;


import com.spark.otcbitrade.entity.PayWaySetting;
import com.spark.library.otc.model.AuthMerchantFrontVo;
import com.spark.library.otc.model.OrderInTransitDto;
import com.spark.otcbitrade.base.BaseContract;

import java.util.List;

/**
 * Created by Administrator on 2017/9/25.
 */

public interface C2CBuyOrSellContract {
//    interface View extends Contract.BaseView<Presenter> {
//
//        void c2cInfoSuccess(C2CExchangeInfo obj);
//
//        void c2cBuyOrSellSuccess(String data,String message);
//
//        void queryRateSuccess(String obj);
//
//        void doPostFail(Integer code, String toastMessage);
//    }
//
//    interface Presenter extends Contract.BasePresenter {
//        void c2cInfo(HashMap<String, String> params);
//
//        void c2cBuy(HashMap<String, String> params);
//
//        void c2cSell(HashMap<String, String> params);
//
//        void queryRate(HashMap<String, String> params);
//    }

    interface View extends BaseContract.BaseView {

        void createOrderSuccess(String obj);

        void getAvgTimeSuccess(AuthMerchantFrontVo obj);

        void queryPayWayListSuccess(List<PayWaySetting> response);
    }

    interface Presenter extends BaseContract.BasePresenter {

        void createOrder(OrderInTransitDto orderInTransitDto);

        void getAvgTime(Long memberId);

        void queryPayWayList();
    }


}
