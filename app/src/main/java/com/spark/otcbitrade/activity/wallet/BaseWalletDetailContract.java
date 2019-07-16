package com.spark.otcbitrade.activity.wallet;

import com.spark.otcbitrade.base.BaseContract;
import com.spark.otcbitrade.entity.AssetRecord;
import com.spark.otcbitrade.entity.Wallet;

import java.util.HashMap;
import java.util.List;


public interface BaseWalletDetailContract {
    interface WalletView extends BaseContract.BaseView {

        void getRecordListSuccess(List<AssetRecord> list);

    }

    interface WalletPresenter extends BaseContract.BasePresenter {

        void getRecordList(boolean isShow, Integer type, HashMap<String, String> params, String busiType, String coinName);

    }
}
