package com.spark.otcbitrade.activity.edit_login_pwd;


import com.spark.otcbitrade.base.Contract;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/9/25.
 */

public interface EditLoginPwdContract {
    interface View extends Contract.BaseView<Presenter> {

        void doLoginOutSuccess(String obj);

        void sendEditLoginPwdCodeSuccess(String obj);

        void editPwdSuccess(String obj);

        void doPostFail(Integer code, String toastMessage);
    }

    interface Presenter extends Contract.BasePresenter {

        void doLoginOut();

        void sendEditLoginPwdCode(HashMap<String, String> map);

        void editPwd(HashMap<String, String> map);
    }
}
