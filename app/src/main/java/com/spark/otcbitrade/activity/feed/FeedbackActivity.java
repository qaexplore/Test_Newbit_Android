package com.spark.otcbitrade.activity.feed;

import android.content.Context;
import android.content.Intent;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.spark.otcbitrade.R;
import com.spark.otcbitrade.base.BaseActivity;
import com.spark.otcbitrade.utils.NetCodeUtils;
import com.spark.otcbitrade.utils.StringUtils;
import com.spark.otcbitrade.utils.ToastUtils;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;
import config.Injection;

/**
 * 意见反馈
 */
public class FeedbackActivity extends BaseActivity implements FeedBackContract.View {
    @BindView(R.id.etRemark)
    EditText etRemark;
    @BindView(R.id.tvSubmit)
    TextView tvSubmit;
    @BindView(R.id.llFeed)
    LinearLayout llFeed;
    @BindView(R.id.etAccount)
    EditText etAccount;
    @BindView(R.id.tvTag)
    TextView tvTag;
    private FeedBackContract.Presenter presenter;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, FeedbackActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_feed;
    }

    @Override
    protected void initView() {
        setSetTitleAndBack(false, true);
        etAccount.setVisibility(View.VISIBLE);
        llFeed.setVisibility(View.VISIBLE);
        tvTag.setVisibility(View.GONE);
    }


    @Override
    protected void initData() {
        super.initData();
        setTitle(getString(R.string.feed_back));
        new FeedBackPresenter(Injection.provideTasksRepository(getApplicationContext()), this);
    }

    @OnClick(R.id.tvSubmit)
    @Override
    protected void setOnClickListener(View v) {
        super.setOnClickListener(v);
        String remark = etRemark.getText().toString();
        if (StringUtils.isEmpty(remark)) {
            ToastUtils.showToast(getString(R.string.incomplete_information));
        } else {
            HashMap<String, String> map = new HashMap<>();
            map.put("doFeedBack", remark);
            presenter.doFeedBack(map);
        }
    }

    @Override
    protected void setListener() {
        super.setListener();
        etRemark.setFilters(new InputFilter[]{inputFilter, new InputFilter.LengthFilter(300)});
    }

    private InputFilter inputFilter = new InputFilter() {
        Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            Matcher emojiMatcher = emoji.matcher(source);
            if (emojiMatcher.find()) {
                ToastUtils.showToast(getString(R.string.no_input_emoji));
                return "";
            }
            return null;
        }
    };

    @Override
    public void setPresenter(FeedBackContract.Presenter presenter) {
        this.presenter = presenter;
    }


    @Override
    public void doFeedBackSuccess(String obj) {
        ToastUtils.showToast(obj);
        finish();
    }

    @Override
    public void doFeedBackFail(Integer code, String toastMessage) {
        NetCodeUtils.checkedErrorCode(this, code, toastMessage);
    }

}
