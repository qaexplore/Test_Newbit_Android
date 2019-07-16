package com.spark.otcbitrade.activity.wallet_coin;


import com.spark.otcbitrade.base.BaseContract;
import com.spark.otcbitrade.entity.Address;

import java.util.List;

/**
 * Created by Administrator on 2017/9/25.
 */

public interface AddressContract {
    interface View extends BaseContract.BaseView {
        void findWalletWithdrawAddressSuccess(List<Address> obj);

        void delWalletWithdrawAddressUsingGETSuccess(String obj);
    }

    interface Presenter extends BaseContract.BasePresenter {

        void findWalletWithdrawAddress(String coinName);

        void delWalletWithdrawAddressUsingGET(String id);
    }


}
