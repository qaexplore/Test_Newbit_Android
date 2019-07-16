package com.spark.otcbitrade.activity.edit_login_pwd;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.spark.otcbitrade.MyApplication;
import com.spark.otcbitrade.R;
import com.spark.otcbitrade.activity.login.LoginActivity;
import com.spark.otcbitrade.base.BaseActivity;
import com.spark.otcbitrade.utils.NetCodeUtils;
import com.spark.otcbitrade.utils.SharedPreferenceInstance;
import com.spark.otcbitrade.utils.StringUtils;
import com.spark.otcbitrade.utils.ToastUtils;
import com.spark.otcbitrade.widget.TimeCount;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import config.Injection;

/**
 * 修改登录密码
 */
public class EditLoginPwdActivity extends BaseActivity implements EditLoginPwdContract.View {
    @BindView(R.id.etOldPwd)
    EditText etOldPwd;
    @BindView(R.id.etNewPwd)
    EditText etNewPwd;
    @BindView(R.id.etRepeatPwd)
    EditText etRepeatPwd;
    @BindView(R.id.tvConfirm)
    TextView tvConfirm;
    @BindView(R.id.etCode)
    EditText etCode;
    @BindView(R.id.tvGetCode)
    TextView tvGetCode;
    @BindView(R.id.tvNumber)
    TextView tvNumber;
    private EditLoginPwdContract.Presenter presenter;
    private String phone;
    private TimeCount timeCount;


    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_edit_login_pwd;
    }

    @Override
    protected void initView() {
        super.initView();
        setSetTitleAndBack(false, true);
    }

    @Override
    protected void initData() {
        super.initData();
        timeCount = new TimeCount(60000, 1000, tvGetCode);
        setTitle(getString(R.string.change_login_pwd));
        new EditLoginPwdPresenter(Injection.provideTasksRepository(getApplicationContext()), this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            phone = bundle.getString("phone");
            if (StringUtils.isMobile(phone)) {
                tvNumber.setText(getString(R.string.change_login_pwd_bind_phone_tag) + phone.substring(0, 3) + "****" + phone.substring(7));
            }else {
                tvNumber.setText(getString(R.string.change_login_pwd_bind_phone_tag) + phone + "****" + phone);
            }
        }
    }

    @OnClick({R.id.tvGetCode, R.id.tvConfirm})
    @Override
    protected void setOnClickListener(View v) {
        super.setOnClickListener(v);
        switch (v.getId()) {
            case R.id.tvConfirm:
                editPwd();
                break;
            case R.id.tvGetCode:
                getCode();
                break;
        }
    }

    /**
     * 获取验证码
     */
    private void getCode() {
        HashMap<String, String> map = new HashMap<>();
        map.put("type", "1");
        presenter.sendEditLoginPwdCode(map);
    }

    /**
     * 修改登录密码
     */
    private void editPwd() {
        String oldPassword = etOldPwd.getText().toString();
        String newPassword = etNewPwd.getText().toString();
        String repeatePwd = etRepeatPwd.getText().toString();
        String code = etCode.getText().toString();
        if (StringUtils.isEmpty(oldPassword, newPassword, repeatePwd, code)) {
            ToastUtils.showToast(getString(R.string.incomplete_information));
        } else if (!newPassword.equals(repeatePwd)) {
            ToastUtils.showToast(getString(R.string.pwd_diff));
        } else {
            HashMap<String, String> map = new HashMap<>();
            map.put("oldPassword", oldPassword);
            map.put("newPassword", newPassword);
            map.put("code", code);
            presenter.editPwd(map);
        }
    }

    @Override
    public void setPresenter(EditLoginPwdContract.Presenter presenter) {
        this.presenter = presenter;
    }


    @Override
    public void doLoginOutSuccess(String obj) {
       // ToastUtils.showToast(obj);
        MyApplication.getApp().deleteCurrentUser();
        SharedPreferenceInstance.getInstance().saveIsNeedShowLock(false);
        SharedPreferenceInstance.getInstance().saveLockPwd("");
        setResult(RESULT_OK);
        showActivity(LoginActivity.class, null, LoginActivity.RETURN_LOGIN);
    }

    @Override
    public void sendEditLoginPwdCodeSuccess(String obj) {
        ToastUtils.showToast(obj);
        timeCount.start();
        tvGetCode.setEnabled(false);
    }

    @Override
    public void editPwdSuccess(String obj) {
        ToastUtils.showToast(getString(R.string.change_login_pwd_success_tag));
        MyApplication.getApp().deleteCurrentUser();
        presenter.doLoginOut();
    }

    @Override
    public void doPostFail(Integer code, String toastMessage) {
        NetCodeUtils.checkedErrorCode(this, code, toastMessage);
    }
}
