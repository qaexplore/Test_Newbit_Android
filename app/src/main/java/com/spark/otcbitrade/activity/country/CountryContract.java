package com.spark.otcbitrade.activity.country;


import com.spark.otcbitrade.base.BaseContract;
import com.spark.otcbitrade.entity.Country;

import java.util.List;

/**
 * Created by Administrator on 2017/9/25.
 */

public interface CountryContract {

    interface View extends BaseContract.BaseView {

        void countrySuccess(List<Country> obj);
    }

    interface Presenter extends BaseContract.BasePresenter {

        void country();
    }


}
