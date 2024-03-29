package com.spark.otcbitrade.activity.safe;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import com.jkb.vcedittext.VerificationCodeEditText;
import com.spark.otcbitrade.R;
import com.spark.otcbitrade.base.BaseActivity;
import com.spark.otcbitrade.utils.KeyboardUtils;
import com.spark.otcbitrade.utils.NetCodeUtils;
import com.spark.otcbitrade.utils.StringUtils;
import com.spark.otcbitrade.utils.ToastUtils;


import java.util.HashMap;

import butterknife.BindView;

/**
 * 谷歌验证
 */
public class GoogleCodeActivity extends BaseActivity implements GoogleContract.BindView {
    @BindView(R.id.etCode)
    VerificationCodeEditText etCode;
    private String code;
    private String secret;
    private GoogleContract.BindPresenter presenter;

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_google_code;
    }

    @Override
    protected void initView() {
        super.initView();
        setSetTitleAndBack(false, true);
    }

    @Override
    protected void initData() {
        super.initData();
        setTitle(getString(R.string.google_authentication_tag));
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            secret = bundle.getString("secret");
        }
    }

    @Override
    protected void setListener() {
        super.setListener();
        etCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String str = editable.toString();
                if (StringUtils.isNotEmpty(str) && str.length() == 6) {
                    code = str;
                    KeyboardUtils.hideSoftInput(GoogleCodeActivity.this);
                    HashMap<String, String> map = new HashMap<>();
                    map.put("codes", code);
                    map.put("secret", secret);
                    presenter.doBind(map);
                }
            }
        });
    }


    @Override
    public void setPresenter(GoogleContract.BindPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void doBindSuccess(String obj) {
        ToastUtils.showToast(obj);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void doBindFail(Integer code, String toastMessage) {
        NetCodeUtils.checkedErrorCode((BaseActivity) activity, code, toastMessage);
    }
}
