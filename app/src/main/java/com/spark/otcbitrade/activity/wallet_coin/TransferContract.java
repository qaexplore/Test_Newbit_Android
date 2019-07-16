package com.spark.otcbitrade.activity.wallet_coin;


import com.spark.library.ac.model.AssetTransferDto;
import com.spark.otcbitrade.base.BaseContract;
import com.spark.otcbitrade.entity.Wallet;

import java.math.BigDecimal;

/**
 * 资金划转
 */

public interface TransferContract {
    interface TransferView extends BaseContract.BaseView {
        void doWithDrawSuccess(String response);

        void findWalletByCoinNameSuccess(Wallet obj);
    }

    interface TransferPresenter extends BaseContract.BasePresenter {
        void doWithDraw(BigDecimal amount, String coinName, AssetTransferDto.FromEnum from, AssetTransferDto.ToEnum to, String tradePassword);

        void findWalletByCoinName(String busiType, String coinName);
    }


}
