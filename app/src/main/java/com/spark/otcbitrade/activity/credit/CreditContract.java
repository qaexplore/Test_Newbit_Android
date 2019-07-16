package com.spark.otcbitrade.activity.credit;


import com.spark.otcbitrade.base.BaseContract;
import com.spark.otcbitrade.entity.Credit;

/**
 * Created by Administrator on 2017/9/25.
 */

public interface CreditContract {
    interface View extends BaseContract.BaseView {

        void uploadBase64PicSuccess(String obj);

        void doCreditSuccess(String obj);

        void getCreditInfoSuccess(Credit.DataBean obj);

    }

    interface Presenter extends BaseContract.BasePresenter {

        void uploadBase64Pic(String base64);

        void credit(Long certifiedType, String idCardNumber, String identityCardImgFront, String identityCardImgInHand, String identityCardImgReverse, String realName);

        void getCreditInfo();
    }
}
