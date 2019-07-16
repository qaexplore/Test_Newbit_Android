package com.spark.otcbitrade.activity.feed;


import com.spark.otcbitrade.data.DataSource;
import com.spark.otcbitrade.factory.UrlFactory;
import com.spark.otcbitrade.utils.GlobalConstant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.spark.otcbitrade.utils.GlobalConstant.JSON_ERROR;

/**
 * Created by Administrator on 2017/9/25.
 */

public class FeedBackPresenter implements FeedBackContract.Presenter {
    private final DataSource dataRepository;
    private final FeedBackContract.View view;

    public FeedBackPresenter(DataSource dataRepository, FeedBackContract.View view) {
        this.dataRepository = dataRepository;
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void doFeedBack(HashMap<String, String> params) {
        view.displayLoadingPopup();
        dataRepository.doStringPost(UrlFactory.getRemarkUrl(), params, new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                String response = (String) obj;
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.optInt("code") == GlobalConstant.SUCCESS_CODE) {
                        view.doFeedBackSuccess(object.getString("message"));
                    } else {
                        view.doFeedBackFail(object.getInt("code"), object.optString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    view.doFeedBackFail(JSON_ERROR, null);
                }
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.doFeedBackFail(code, toastMessage);
            }
        });
    }

}
