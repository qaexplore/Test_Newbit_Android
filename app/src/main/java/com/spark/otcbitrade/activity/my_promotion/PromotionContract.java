package com.spark.otcbitrade.activity.my_promotion;

import com.spark.library.cms.model.MessageResultWebConfigVo;
import com.spark.otcbitrade.base.BaseContract;
import com.spark.otcbitrade.base.Contract;
import com.spark.otcbitrade.entity.PromotionRecord;
import com.spark.otcbitrade.entity.PromotionReward;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/10/12 0012.
 */

public interface PromotionContract {

    interface View extends BaseContract.BaseView {

        void getWebConfigSuccess(MessageResultWebConfigVo response);

    }

    interface Presenter extends BaseContract.BasePresenter {

        void getWebConfig();

    }
}
