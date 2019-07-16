package com.spark.otcbitrade.activity.my_promotion;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.spark.library.cms.model.MessageResultWebConfigVo;
import com.spark.otcbitrade.activity.aboutus.AboutUsContract;
import com.spark.otcbitrade.callback.ResponseCallBack;
import com.spark.otcbitrade.data.DataSource;
import com.spark.otcbitrade.entity.HttpErrorEntity;
import com.spark.otcbitrade.entity.PromotionRecord;
import com.spark.otcbitrade.entity.PromotionReward;
import com.spark.otcbitrade.factory.UrlFactory;
import com.spark.otcbitrade.model.cms.WebConfigControllerModel;
import com.spark.otcbitrade.utils.GlobalConstant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import static com.spark.otcbitrade.utils.GlobalConstant.JSON_ERROR;

/**
 * Created by Administrator on 2018/5/8 0008.
 */

public class PromotionPresenter implements PromotionContract.Presenter {

    private PromotionContract.View view;
    private WebConfigControllerModel webConfigControllerModel;

    public PromotionPresenter(PromotionContract.View view) {
        this.view = view;
        webConfigControllerModel = new WebConfigControllerModel();
    }

    @Override
    public void showLoading() {
        if (view != null) {
            view.showLoading();
        }
    }

    @Override
    public void hideLoading() {
        if (view != null) {
            view.hideLoading();
        }
    }

    @Override
    public void destory() {
        view = null;
    }

    @Override
    public void getWebConfig() {
        showLoading();
        webConfigControllerModel.webConfigQuery(new ResponseCallBack.SuccessListener<MessageResultWebConfigVo>() {
            @Override
            public void onResponse(MessageResultWebConfigVo response) {
                hideLoading();
                if (view != null) {
                    view.getWebConfigSuccess(response);
                }
            }
        }, new ResponseCallBack.ErrorListener() {
            @Override
            public void onErrorResponse(HttpErrorEntity httpErrorEntity) {
                hideLoading();
                if (view != null) {
                    view.dealError(httpErrorEntity);
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                hideLoading();
                if (view != null) {
                    view.dealError(volleyError);
                }
            }
        });
    }

}
