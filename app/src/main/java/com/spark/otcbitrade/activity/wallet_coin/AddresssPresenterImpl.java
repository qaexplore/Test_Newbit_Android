package com.spark.otcbitrade.activity.wallet_coin;

import com.android.volley.VolleyError;
import com.spark.otcbitrade.callback.ResponseCallBack;
import com.spark.otcbitrade.entity.Address;
import com.spark.otcbitrade.entity.HttpErrorEntity;
import com.spark.otcbitrade.model.ac.AssetControllerModel;

import java.util.List;

/**
 * Created by Administrator on 2019/3/2 0002.
 */

public class AddresssPresenterImpl implements AddressContract.Presenter {
    private AddressContract.View view;
    private AssetControllerModel assetControllerModel;

    public AddresssPresenterImpl(AddressContract.View view) {
        this.view = view;
        assetControllerModel = new AssetControllerModel();
    }

    @Override
    public void showLoading() {
        if (view != null)
            view.showLoading();
    }

    @Override
    public void hideLoading() {
        if (view != null)
            view.hideLoading();
    }

    @Override
    public void destory() {
        view = null;
    }

    @Override
    public void findWalletWithdrawAddress(String coinName) {
        assetControllerModel.findWalletWithdrawAddress(coinName,
                new ResponseCallBack.SuccessListener<List<Address>>() {
                    @Override
                    public void onResponse(List<Address> response) {
                        hideLoading();
                        if (view != null)
                            view.findWalletWithdrawAddressSuccess(response);
                    }
                }, new ResponseCallBack.ErrorListener() {
                    @Override
                    public void onErrorResponse(HttpErrorEntity httpErrorEntity) {
                        hideLoading();
                        if (view != null)
                            view.dealError(httpErrorEntity);
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        hideLoading();
                        if (view != null)
                            view.dealError(volleyError);
                    }
                });
    }

    @Override
    public void delWalletWithdrawAddressUsingGET(String id) {
        assetControllerModel.delWalletWithdrawAddressUsingGET(id,
                new ResponseCallBack.SuccessListener<String>() {
                    @Override
                    public void onResponse(String response) {
                        hideLoading();
                        if (view != null)
                            view.delWalletWithdrawAddressUsingGETSuccess(response);
                    }
                }, new ResponseCallBack.ErrorListener() {
                    @Override
                    public void onErrorResponse(HttpErrorEntity httpErrorEntity) {
                        hideLoading();
                        if (view != null)
                            view.dealError(httpErrorEntity);
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        hideLoading();
                        if (view != null)
                            view.dealError(volleyError);
                    }
                });
    }






}
