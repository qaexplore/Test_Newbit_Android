package com.spark.otcbitrade.activity.main;

import com.spark.otcbitrade.base.BaseContract;
import com.spark.otcbitrade.entity.Ads;
import com.spark.otcbitrade.entity.PayWaySetting;
import com.spark.library.otc.model.AdvertiseDto;
import com.spark.library.otc.model.AuthMerchantApplyMarginType;
import com.spark.library.otc.model.MessageResult;
import com.spark.library.otc.model.MessageResultAuthMerchantFrontVo;

import java.util.List;

/**
 * Created by Administrator on 2017/9/25.
 */

public interface StoreListContract {

    interface View extends BaseContract.BaseView {

        void priceFindSuccess(MessageResult obj);

        void findAuthMerchantStatusSuccess(MessageResultAuthMerchantFrontVo response);
    }

    interface Presenter extends BaseContract.BasePresenter {

        void priceFind(String coinName, String currency);

        void findAuthMerchantStatus();
    }

}
