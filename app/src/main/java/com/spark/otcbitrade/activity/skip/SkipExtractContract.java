package com.spark.otcbitrade.activity.skip;


import com.spark.library.ac.model.MemberWallet;
import com.spark.otcbitrade.base.BaseContract;

/**
 * Created by Administrator on 2017/9/25.
 */

public interface SkipExtractContract {
    interface View extends BaseContract.BaseView {

        void getAddressSuccess(MemberWallet obj);
    }

    interface Presenter extends BaseContract.BasePresenter {

        void getAddress(String coinName);
    }


}
