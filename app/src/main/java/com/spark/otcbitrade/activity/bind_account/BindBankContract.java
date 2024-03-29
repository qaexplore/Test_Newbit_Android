package com.spark.otcbitrade.activity.bind_account;


import com.spark.library.otc.model.MessageResult;
import com.spark.otcbitrade.base.BaseContract;

/**
 * Created by Administrator on 2017/9/25.
 */

public interface BindBankContract {
    interface View extends BaseContract.BaseView {

        void doBindBankSuccess(MessageResult obj);

        void doUpdateBankSuccess(MessageResult obj);
    }

    interface Presenter extends BaseContract.BasePresenter {

        void doBindBank(String payType, String account, String bank, String branch, String tradePassword, String name);

        void doUpdateBank(Long id, String payType, String accout, String bank, String branch, String tradePassword, String name);

    }


}
