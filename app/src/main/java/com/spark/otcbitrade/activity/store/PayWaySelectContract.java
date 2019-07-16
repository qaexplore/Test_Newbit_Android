package com.spark.otcbitrade.activity.store;



import com.spark.otcbitrade.base.BaseContract;
import com.spark.otcbitrade.entity.PayWaySetting;

import java.util.List;

/**
 * Created by Administrator on 2017/9/25.
 */

public interface PayWaySelectContract {
    interface View extends BaseContract.BaseView {

        void queryPayWayListSuccess(List<PayWaySetting> response);

    }

    interface Presenter extends BaseContract.BasePresenter {

        void queryPayWayList();

    }


}
