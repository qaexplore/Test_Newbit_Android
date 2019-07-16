package com.spark.otcbitrade.activity.wallet;

import com.spark.library.ac.model.MessageResult;
import com.spark.otcbitrade.base.BaseContract;
import com.spark.otcbitrade.entity.Wallet;

import java.util.List;

/**
 * 钱包
 */

public interface BaseWalletContract {
    interface WalletView extends BaseContract.BaseView {
        void getWalletSuccess(List<Wallet> list);

        void findSupportAssetUsingGETSuccess(List<Wallet> list);
    }

    interface WalletPresenter extends BaseContract.BasePresenter {

        void getWallet(String type);

        void findSupportAssetUsingGET();

    }
}
