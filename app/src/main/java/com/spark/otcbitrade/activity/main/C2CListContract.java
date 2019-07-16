package com.spark.otcbitrade.activity.main;


import com.spark.library.otc.model.PageAdvertiseShowVo;
import com.spark.library.otc.model.QueryParamAdvertiseShowVo;
import com.spark.library.otcSys.model.MessageResult;
import com.spark.library.otcSys.model.TradeDto;
import com.spark.otcbitrade.base.BaseContract;


public interface C2CListContract {
    interface C2CListPresenter extends BaseContract.BasePresenter {

        void findPageForCoin(QueryParamAdvertiseShowVo queryParam);

        void createOrder(TradeDto tradeDto);
    }

    interface C2CListView extends BaseContract.BaseView {

        void findPageForCoinSuccess(PageAdvertiseShowVo obj);

        void createOrderSuccess(MessageResult response);
    }
}
