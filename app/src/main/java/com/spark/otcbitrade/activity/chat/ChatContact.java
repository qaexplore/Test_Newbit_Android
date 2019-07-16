package com.spark.otcbitrade.activity.chat;

import com.spark.otcbitrade.base.BaseContract;
import com.spark.otcbitrade.base.Contract;
import com.spark.otcbitrade.entity.ChatEntity;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/4/16 0016.
 */

public interface ChatContact {
    interface View extends BaseContract.BaseView {

        void getHistoryMessageSuccess(List<ChatEntity> entityList);

        void getHistoryMessageFail(Integer code, String toastMessage);
    }

    interface Presenter extends BaseContract.BasePresenter {

        void getHistoryMessage(String orderId, HashMap<String, String> params);

    }
}
