package com.spark.otcbitrade.activity.main;

import com.android.volley.VolleyError;
import com.spark.otcbitrade.activity.main.presenter.HomePresenterImpl;
import com.spark.otcbitrade.base.Contract;
import com.spark.otcbitrade.entity.BannerEntity;
import com.spark.otcbitrade.entity.CasLoginEntity;
import com.spark.otcbitrade.entity.Favorite;
import com.spark.otcbitrade.entity.HttpErrorEntity;
import com.spark.otcbitrade.entity.Message;
import com.spark.otcbitrade.entity.Notice;
import com.spark.otcbitrade.entity.Vision;
import com.spark.otcbitrade.entity.Wallet;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/2/24.
 */

public interface MainContract {
    interface View extends Contract.BaseView<Presenter> {

        void allCurrencySuccess(Object obj);

        void allCurrencyFail(Integer code, String toastMessage);

        void homeCurrencySuccess(String obj);

        void homeCurrencyFail(Integer code, String toastMessage);

        void findSuccess(List<Favorite> obj);

        void doPostFail(Integer code, String toastMessage);

        void getNewVersionSuccess(Vision obj);

        void getRateSuccess(double obj);

        void tradeLoginSuccess(Object obj);

        void getUserInfoSuccess();

        void getRateFail(Integer code, String toastMessage);

        void checkBusinessLoginSuccess(CasLoginEntity casLoginEntity);

        void doLoginBusinessSuccess(String type);

        void dealError(HttpErrorEntity httpErrorEntity);

        void dealError(VolleyError volleyError);

    }

    interface Presenter extends Contract.BasePresenter {

        void allCurrency();

        void tradeLogin();

        void getNewVersion();

        void getUserInfo();

        void checkBusinessLogin(String type);

        void doLoginBusiness(String tgc, String type);

    }

    interface HomePresenter extends Contract.BasePresenter {

        void banners(HashMap<String, String> map);

        void getMarqueeText(HashMap<String, String> map);
    }

    interface HomeView extends Contract.BaseView<HomePresenterImpl> {

        void bannersSuccess(List<BannerEntity> obj);

        void bannersFail(Integer code, String toastMessage);

        void getMarqueeSuccess(List<Notice> messages);

        void getMarqueeFail(Integer code, String toastMessage);

    }

    interface MyPresenter extends Contract.BasePresenter {

        void myWallet();

    }

    interface MyView extends Contract.BaseView<MyPresenter> {

        void myWalletSuccess(List<Wallet> obj);

        void doPostFail(Integer code, String toastMessage);


    }


}
