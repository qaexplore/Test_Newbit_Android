package com.spark.otcbitrade.activity.wallet_coin;

import com.spark.otcbitrade.entity.ExtractInfo;
import com.spark.library.ac.model.MemberWallet;
import com.spark.otcbitrade.base.BaseContract;

import java.util.List;


public interface RechargeContract {
    interface WalletView extends BaseContract.BaseView {
        void getAddressSuccess(MemberWallet obj);

        void getExtractInfoSuccess(List<ExtractInfo> list);
    }

    interface WalletPresenter extends BaseContract.BasePresenter {

        void getAddress(String coinName);

        void getExtractInfo(String coinName);
    }
}
