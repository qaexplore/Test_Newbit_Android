package com.spark.otcbitrade.activity.main.presenter;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.spark.library.cms.model.MessageResultWebConfigVo;
import com.spark.library.cms.model.WebOtherContent;
import com.spark.otcbitrade.activity.main.MainContract;
import com.spark.otcbitrade.callback.ResponseCallBack;
import com.spark.otcbitrade.entity.BannerEntity;
import com.spark.otcbitrade.entity.HttpErrorEntity;
import com.spark.otcbitrade.entity.Message;
import com.spark.otcbitrade.entity.Notice;
import com.spark.otcbitrade.model.cms.WebConfigControllerModel;
import com.spark.otcbitrade.model.cms.WebOtherContentControllerModel;
import com.spark.otcbitrade.utils.GlobalConstant;

import java.util.HashMap;
import java.util.List;

import static com.spark.otcbitrade.utils.GlobalConstant.JSON_ERROR;

/**
 * Created by Administrator on 2018/2/24.
 */

public class HomePresenterImpl implements MainContract.HomePresenter {
    private MainContract.HomeView view;
    private WebConfigControllerModel webConfigControllerModel;
    private WebOtherContentControllerModel webOtherContentControllerModel;

    public HomePresenterImpl(MainContract.HomeView view) {
        this.view = view;
        this.webConfigControllerModel = new WebConfigControllerModel();
        this.webOtherContentControllerModel = new WebOtherContentControllerModel();
        this.view.setPresenter(this);
    }


    @Override
    public void banners(HashMap<String, String> map) {
        view.displayLoadingPopup();
        webConfigControllerModel.webConfigQuery(new ResponseCallBack.SuccessListener<MessageResultWebConfigVo>() {
            @Override
            public void onResponse(MessageResultWebConfigVo response) {
                view.hideLoadingPopup();
                if (response.getCode() == GlobalConstant.SUCCESS_CODE) {
                    List<BannerEntity> objs = new Gson().fromJson(response.getData().getAppCarousel(), new TypeToken<List<BannerEntity>>() {
                    }.getType());
                    view.bannersSuccess(objs);
                } else {
                    view.bannersFail(response.getCode(), response.getMessage());
                }
            }
        }, new ResponseCallBack.ErrorListener() {
            @Override
            public void onErrorResponse(HttpErrorEntity httpErrorEntity) {
                view.hideLoadingPopup();
                if (view != null)
                    view.bannersFail(httpErrorEntity.getCode(), httpErrorEntity.getMessage());
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                view.hideLoadingPopup();
//                if (view != null)
//                    view.dealError(volleyError);
            }
        });
    }

    @Override
    public void getMarqueeText(HashMap<String, String> map) {
        view.displayLoadingPopup();
        webOtherContentControllerModel.webOtherContentQuery(map, new ResponseCallBack.SuccessListener<List<WebOtherContent>>() {
            @Override
            public void onResponse(List<WebOtherContent> response) {
                view.hideLoadingPopup();
                Gson gson = new Gson();
                List<Notice> messages = new Gson().fromJson(gson.toJson(response), new TypeToken<List<Notice>>() {
                }.getType());
                view.getMarqueeSuccess(messages);
            }
        }, new ResponseCallBack.ErrorListener() {
            @Override
            public void onErrorResponse(HttpErrorEntity httpErrorEntity) {
                view.hideLoadingPopup();
                if (view != null)
                    view.bannersFail(httpErrorEntity.getCode(), httpErrorEntity.getMessage());
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                view.hideLoadingPopup();
//                if (view != null)
//                    view.dealError(volleyError);
            }
        });
    }
}
