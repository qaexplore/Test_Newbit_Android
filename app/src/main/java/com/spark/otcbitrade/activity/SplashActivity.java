package com.spark.otcbitrade.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.gyf.barlibrary.ImmersionBar;
import com.spark.otcbitrade.MyApplication;
import com.spark.otcbitrade.R;
import com.spark.otcbitrade.activity.lock.LockActivity;
import com.spark.otcbitrade.activity.login.LoginActivity;
import com.spark.otcbitrade.activity.main.MainActivity;
import com.spark.otcbitrade.base.BaseActivity;
import com.spark.otcbitrade.serivce.ChatWebSocketService;
import com.spark.otcbitrade.utils.SharedPreferenceInstance;
import com.spark.otcbitrade.utils.okhttp.AppUtils;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

public class SplashActivity extends BaseActivity {
    @BindView(R.id.iv_pic)
    ImageView ivPic;

    private Timer timer;
    int n = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).fullScreen(true).init();
        initService();
    }

    @Override
    protected void initView() {
        //启动页变形优化
        AppUtils.scaleImage(this, ivPic, R.mipmap.splash);
    }

    /**
     * 初始化服务
     */
    private void initService() {
        startService(new Intent(activity, ChatWebSocketService.class));
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initData() {
        super.initData();
        if (!isNeedShowLockActivity()) {
            timerStart();
        }
    }


    @Override
    protected void loadData() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == LockActivity.RETURN_LOCK) timerStart();
    }

    private void timerStart() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (n == 0) {
                    timer.cancel();
                    timer = null;
                    boolean isFirst = SharedPreferenceInstance.getInstance().getIsFirstUse();
                    if (isFirst) {
                        showActivity(GuideActivity.class, null);
                    } else {
//                        boolean isLogin = MyApplication.getApp().isLogin();
//                        if (isLogin) {
                            showActivity(MainActivity.class, null);
//                        } else {
//                            showActivity(LoginActivity.class, null);
//                        }
                    }
                    finish();
                }
                n--;
            }
        }, 10, 999);
    }


    @Override
    public void onBackPressed() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        super.onBackPressed();
    }
}
