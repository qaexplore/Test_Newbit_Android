package com.spark.otcbitrade.factory;


public class HttpUrls {

    /**
     * 主网络请求地址和cas登录地址
     */
    //压测OTC
//    public static String CUR_HOST = "bench.bitpay.com";
//    public static String WEBSOCKET_HOST_AND_PORT = "ws://192.168.2.139:28905/ws";
    //泰达OTC
    //public static String CUR_HOST = "hn772.cn";
    //public static String WEBSOCKET_HOST_AND_PORT = "ws://154.85.102.115:28905/ws";
    //Money OTC
    //public static String CUR_HOST = "money123.vip";
    //public static String WEBSOCKET_HOST_AND_PORT = "ws://47.244.187.65:28905/ws";
    //希锦OTC
//    public static String CUR_HOST = "otc.cndpay.net";
//    public static String WEBSOCKET_HOST_AND_PORT = "ws://ws.otc.cndpay.net/ws";
    //合众OTC
//    public static String CUR_HOST = "999fit.com";
//    public static String WEBSOCKET_HOST_AND_PORT = "ws://ws.bitotc.999fit.com/ws";
    //币安OTC
    public static String CUR_HOST = "otc.bittoppayment.top";
    public static String WEBSOCKET_HOST_AND_PORT = "ws://ws.otc.bittoppayment.top/ws";

    //    public static final String HOST_LOGIN = "http://cas.www." + CUR_HOST;//通用
    //    public static final String HOST_LOGIN = "http://cas.otc.cndpay.net";//希锦OTC
    public static final String HOST_LOGIN = "http://cas.otc.bittoppayment.top";//币安OTC
    public static final String UC_HOST = "http://api." + CUR_HOST + "/uc";
    public static final String AC_HOST = "http://api." + CUR_HOST + "/ac";
    public static final String OTC_HOST = "http://api." + CUR_HOST + "/otc";
    public static final String OTC_SYSTEM_HOST = "http://api." + CUR_HOST + "/otc-system";
    public static final String CMS_HOST = "http://api." + CUR_HOST + "/cms-api";

    /**
     * AC,UC,OTC模块地址后缀
     */
    public static final String TYPE_AC = "ac";
    public static final String TYPE_UC = "uc";
    public static final String TYPE_OTC = "otc";
    public static final String TYPE_OTC_SYSTEM = "otc-system";

    /**
     * 登录cas
     *
     * @return
     */
    public static String getCasLogin() {
        return HOST_LOGIN + "/cas/v1/tickets";
    }

    /**
     * 2.3 业务系统登录接口
     */
    public static String getCasTickets(String type) {
        return getBusinessUrl(type) + "/cas";
    }

    /**
     * 2.4 业务系统登录状态查询接口
     */
    public static String getCheckTicket(String type) {
        return getBusinessUrl(type) + "/check";
    }

    /**
     * 登录时需要调用的service，跟上面的2.3需要一直
     */
    public static String getService(String intType) {
        return getBusinessUrl(intType) + "/cas?client_name=CasClient";
    }

    private static String getBusinessUrl(String bussineType) {
        String url = "";
        switch (bussineType) {
            case TYPE_AC:
                url = AC_HOST;
                break;
            case TYPE_UC:
                url = UC_HOST;
                break;
            case TYPE_OTC:
                url = OTC_HOST;
                break;
            case TYPE_OTC_SYSTEM:
                url = OTC_SYSTEM_HOST;
                break;
        }
        return url;
    }

    /**
     * 获取手机验证
     */
    public static String getSendVertifyCodeUrl() {
        return HOST_LOGIN + "/cas/captcha/permission";
    }

    /**
     * 验证短信验证
     */
    public static String getVertifyCodeUrl() {
        return HOST_LOGIN + "/cas/captcha/check";
    }

    /**
     * 上传头像
     *
     * @return
     */
    public static String getAvatarUrl() {
        return UC_HOST + "/member/update/avatar";
    }

    /**
     * 绑定手机号
     *
     * @return
     */
    public static String getBindPhoneUrl() {
        return UC_HOST + "/bind/phone";
    }

    /**
     * 获取当前绑定手机的验证码
     *
     * @return
     */
    public static String getSendCodeAfterLoginUrl() {
        return UC_HOST + "/mobile/auth/code";
    }

    /**
     * 修改手机号
     *
     * @return
     */
    public static String getChangePhoneUrl() {
        return UC_HOST + "/info/change/phone";
    }


    /**
     * 推广好友
     *
     * @return
     */
    public static String getPromotionUrl() {
        return UC_HOST + "/promotion/record/page";
    }

    /**
     * 我的佣金
     *
     * @return
     */
    public static String getPromotionRewardUrl() {
        return UC_HOST + "/promotion/commission/record";
    }

    //获取APP版本更新信息
    public static String getAppVersionUsingGet() {
        return OTC_HOST + "/app/check-version?platform=1";
    }


    public static String getGeetest() {
        return UC_HOST + "/captcha/mm/gee";
    }
}
