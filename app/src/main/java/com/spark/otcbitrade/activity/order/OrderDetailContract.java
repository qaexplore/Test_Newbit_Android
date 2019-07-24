package com.spark.otcbitrade.activity.order;


import com.spark.library.otc.model.MemberPayType;
import com.spark.library.otc.model.OrderDetailVo;
import com.spark.library.otc.model.OrderPaymentDto;
import com.spark.otcbitrade.base.BaseContract;
import com.spark.otcbitrade.entity.HttpErrorEntity;

import java.util.List;

/**
 * Created by Administrator on 2017/9/25.
 */

public interface OrderDetailContract {

    interface View extends BaseContract.BaseView {

        void cancelOrderUsingGETSuccess(String obj);

        void releaseOrderUsingGETSuccess(String obj);

        void paymentOrderUsingPOSTSuccess(String obj);

        void findOrderInTransitDetailUsingGETSuccess(OrderDetailVo obj);

        void findOrderAchiveDetailUsingGETSuccess(OrderDetailVo obj);

        void queryOrderPayTypeUsingGETSuccess(List<MemberPayType> obj);

        void findOrderAchiveDetailUsingGETFail(HttpErrorEntity httpErrorEntity);

        void findOrderInTransitDetailUsingGETFail(HttpErrorEntity httpErrorEntity);
    }

    interface Presenter extends BaseContract.BasePresenter {

        void cancelOrderUsingGET(String orderId);

        void releaseOrderUsingGET(String orderId, String tradePwd);

        void paymentOrderUsingPOST(OrderPaymentDto orderPaymentDto);

        void findOrderInTransitDetailUsingGET(String orderId);

        void findOrderAchiveDetailUsingGET(String orderId);

        void queryOrderPayTypeUsingGET(String orderId);
    }


}
