package com.spark.otcbitrade.activity.bind_email;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.spark.otcbitrade.R;
import com.spark.otcbitrade.base.BaseActivity;
import com.spark.otcbitrade.utils.NetCodeUtils;
import com.spark.otcbitrade.utils.StringUtils;
import com.spark.otcbitrade.utils.ToastUtils;
import com.spark.otcbitrade.widget.TimeCount;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import config.Injection;

/**
 * 绑定邮箱
 */
public class BindEmailActivity extends BaseActivity implements BindEmailContract.View {
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etCode)
    EditText etCode;
    @BindView(R.id.tvGetCode)
    TextView tvGetCode;
    @BindView(R.id.etPwd)
    EditText etPwd;
    @BindView(R.id.tvBind)
    TextView tvBind;
    private TimeCount timeCount;
    private BindEmailContract.Presenter presenter;


    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_bind_email;
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
        setTitle(getString(R.string.binding_email));
        new BindEmailPresenter(Injection.provideTasksRepository(getApplicationContext()), this);
    }

    @OnClick({R.id.tvGetCode, R.id.tvBind})
    @Override
    protected void setOnClickListener(View v) {
        super.setOnClickListener(v);
        switch (v.getId()) {
            case R.id.tvGetCode:
                sendCode();
                break;
            case R.id.tvBind:
                bindEmail();
                break;
        }
    }

    /**
     * 发送验证码
     */
    private void sendCode() {
        String email = etEmail.getText().toString();
        if (StringUtils.isEmpty(email) || !StringUtils.isEmail(email)) {
            ToastUtils.showToast(getString(R.string.email_diff));
        } else {
            HashMap<String, String> map = new HashMap<>();
            map.put("email", email);
            presenter.sendEmailCode(map);
        }
    }

    /**
     * 绑定邮箱
     */
    private void bindEmail() {
        String password = etPwd.getText().toString();
        String email = etEmail.getText().toString();
        String code = etCode.getText().toString();
        if (StringUtils.isEmpty(password, email, code)) {
            ToastUtils.showToast(R.string.incomplete_information);
        } else {
            HashMap<String, String> map = new HashMap<>();
            map.put("email", email);
            map.put("code", code);
            map.put("password", password);
            presenter.bindEmail(map);
        }
    }

    @Override
    public void setPresenter(BindEmailContract.Presenter presenter) {
        this.presenter = presenter;
    }


    @Override
    public void bindEmailSuccess(String obj) {
        ToastUtils.showToast(obj);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void doPostFail(Integer code, String toastMessage) {
        NetCodeUtils.checkedErrorCode(this, code, toastMessage);
    }

    @Override
    public void sendEmailCodeSuccess(String obj) {
        ToastUtils.showToast(obj);
        timeCount.start();
    }

}
