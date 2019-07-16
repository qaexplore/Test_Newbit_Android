package com.spark.otcbitrade.activity.main;


import com.spark.library.otc.model.Coin;
import com.spark.otcbitrade.base.BaseContract;
import com.spark.otcbitrade.entity.Country;
import com.spark.library.otc.model.MessageResultAuthMerchantFrontVo;

import java.util.List;


public interface C2CContract {
    interface C2CView extends BaseContract.BaseView {

        void listOtcTradeCoinSuccess(List<Coin> obj);

        void countrySuccess(List<Country> obj);

        void findAuthMerchantStatusSuccess(MessageResultAuthMerchantFrontVo response);
    }

    interface C2CPresenter extends BaseContract.BasePresenter {

        void listOtcTradeCoin();

        void country();

        void findAuthMerchantStatus();
    }
}
