package com.spark.otcbitrade.activity.store;

import com.spark.otcbitrade.base.BaseContract;
import com.spark.otcbitrade.entity.Ads;
import com.spark.otcbitrade.entity.PayWaySetting;
import com.spark.library.otc.model.AdvertiseDto;
import com.spark.library.otc.model.AuthMerchantApplyMarginType;
import com.spark.library.otc.model.MessageResult;

import java.util.List;

/**
 * Created by Administrator on 2017/9/25.
 */

public interface StorePublishContract {

    interface View extends BaseContract.BaseView {

        void createAdvertiseSuccess(String obj);

        void listMerchantAdvertiseCoinSuccess(List<AuthMerchantApplyMarginType> obj);

        void queryPayWayListSuccess(List<PayWaySetting> obj);

        void updateAdvertiseSuccess(String obj);
    }

    interface Presenter extends BaseContract.BasePresenter {
        void createAdvertise(AdvertiseDto advertiseDto);

        void listMerchantAdvertiseCoin();

        void queryPayWayList();

        void updateAdvertise(AdvertiseDto advertiseDto, Long advertiseId);
    }


}
