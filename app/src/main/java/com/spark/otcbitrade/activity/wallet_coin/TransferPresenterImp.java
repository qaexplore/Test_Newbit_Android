package com.spark.otcbitrade.activity.wallet_coin;

import com.android.volley.VolleyError;
import com.spark.library.ac.model.AssetTransferDto;
import com.spark.otcbitrade.callback.ResponseCallBack;
import com.spark.otcbitrade.entity.HttpErrorEntity;
import com.spark.otcbitrade.entity.Wallet;
import com.spark.otcbitrade.model.ac.AssetControllerModel;

import java.math.BigDecimal;

/**
 * 资金划转
 */

public class TransferPresenterImp implements TransferContract.TransferPresenter {
    private TransferContract.TransferView transferView;
    private AssetControllerModel assetControllerModel;

    public TransferPresenterImp(TransferContract.TransferView transferView) {
        this.transferView = transferView;
        assetControllerModel = new AssetControllerModel();
    }


    @Override
    public void doWithDraw(BigDecimal amount, String coinName, AssetTransferDto.FromEnum from, AssetTransferDto.ToEnum to, String tradePassword) {
        showLoading();
        assetControllerModel.walletWithdraw(amount, coinName, from, to, tradePassword,
                new ResponseCallBack.SuccessListener<String>() {
                    @Override
                    public void onResponse(String response) {
                        hideLoading();
                        if (assetControllerModel != null)
                            transferView.doWithDrawSuccess(response);
                    }
                }, new ResponseCallBack.ErrorListener() {
                    @Override
                    public void onErrorResponse(HttpErrorEntity httpErrorEntity) {
                        hideLoading();
                        if (assetControllerModel != null)
                            transferView.dealError(httpErrorEntity);
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        hideLoading();
                        if (assetControllerModel != null)
                            transferView.dealError(volleyError);
                    }
                });
    }

    @Override
    public void findWalletByCoinName(String busiType, String coinName) {
        showLoading();
        assetControllerModel.findWalletByCoinNameUsingGET(busiType, coinName,
                new ResponseCallBack.SuccessListener<Wallet>() {
                    @Override
                    public void onResponse(Wallet response) {
                        hideLoading();
                        if (assetControllerModel != null)
                            transferView.findWalletByCoinNameSuccess(response);
                    }
                }, new ResponseCallBack.ErrorListener() {
                    @Override
                    public void onErrorResponse(HttpErrorEntity httpErrorEntity) {
                        hideLoading();
                        if (assetControllerModel != null)
                            transferView.dealError(httpErrorEntity);
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        hideLoading();
                        if (assetControllerModel != null)
                            transferView.dealError(volleyError);
                    }
                });
    }

    @Override
    public void showLoading() {
        if (transferView != null)
            transferView.showLoading();
    }

    @Override
    public void hideLoading() {
        if (transferView != null)
            transferView.hideLoading();
    }

    @Override
    public void destory() {
        transferView = null;
    }
}
