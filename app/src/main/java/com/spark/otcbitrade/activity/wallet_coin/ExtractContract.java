package com.spark.otcbitrade.activity.wallet_coin;


import com.spark.otcbitrade.base.BaseContract;
import com.spark.otcbitrade.entity.ExtractInfo;

import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2017/9/25.
 */

public interface ExtractContract {
    interface ExtractView extends BaseContract.BaseView {
        void getExtractInfoSuccess(List<ExtractInfo> list);

        void walletWithdrawSuccess(String response);

        void getPhoneCodeSuccess(String obj);

        void captchSuccess(JSONObject obj);

        void codeSuccess(String obj);
    }

    interface ExtractPresenter extends BaseContract.BasePresenter {
        void getExtractInfo(String coinName);

        void walletWithdraw(String address, BigDecimal amount, String coinName, String tradePassword, String code, String phone);

        void getPhoneCode(String phone);

        void captch();

        void getPhoneCode(String phone, String check, String cid);
    }


}
