package com.spark.otcbitrade.activity.aboutus;


import com.spark.library.cms.model.MessageResultWebConfigVo;
import com.spark.otcbitrade.base.BaseContract;
import com.spark.otcbitrade.base.Contract;
import com.spark.otcbitrade.entity.Vision;

/**
 * Created by Administrator on 2017/9/25.
 */

public interface AboutUsContract {

    interface View extends BaseContract.BaseView {

        void getWebConfigSuccess(MessageResultWebConfigVo response);

        void checkVersionSuccess(String obj);

    }

    interface Presenter extends BaseContract.BasePresenter {

        void getWebConfig();

        void checkVersion();

    }
}
