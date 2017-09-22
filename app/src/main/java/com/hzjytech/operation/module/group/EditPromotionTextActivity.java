package com.hzjytech.operation.module.group;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.hzjytech.operation.R;
import com.hzjytech.operation.base.BaseActivity;
import com.hzjytech.operation.http.JijiaHttpSubscriber;
import com.hzjytech.operation.http.SubscriberOnNextListener;
import com.hzjytech.operation.http.api.GroupApi;
import com.hzjytech.operation.utils.CommonUtil;
import com.hzjytech.operation.utils.UserUtils;
import com.hzjytech.operation.widgets.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;

/**
 * Created by hehongcan on 2017/9/18.
 */

public class EditPromotionTextActivity extends BaseActivity {
    @BindView(R.id.titleBar)
    TitleBar mTitleBar;
    @BindView(R.id.et_promotion_text)
    EditText mEtPromotionText;
    private JijiaHttpSubscriber mSubscriber;

    @Override
    protected int getResId() {
        return R.layout.acitivty_edit_promotion_text;
    }

    @Override
    protected void initView() {
    initTitle();
    }

    private void initTitle() {
        mTitleBar.setLeftText(R.string.cancel);
        mTitleBar.setLeftTextColor(Color.WHITE);
        mTitleBar.setTitle(R.string.edit_promotion_text);
        mTitleBar.addAction(new TitleBar.TextAction(getResources().getString(R.string.finish)) {
            @Override
            public void performAction(View view) {
                overAndCommit();
            }
        });
        mTitleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 提交促销信息
     */
    private void overAndCommit() {
        final Intent intent = getIntent();
        int groupId = intent.getIntExtra("groupId", -1);
        Observable<Object> observable = GroupApi.commitPromotionText(UserUtils.getUserInfo()
                        .getToken(),
                groupId,
                mEtPromotionText.getText()
                        .toString());
        mSubscriber = JijiaHttpSubscriber.buildSubscriber(this)
                .setOnNextListener(new SubscriberOnNextListener() {
                    @Override
                    public void onNext(Object o) {
                         showTip(R.string.commit_success);
                         EditPromotionTextActivity.this.setResult(RESULT_OK, new Intent());
                        EditPromotionTextActivity.this.finish();
                    }
                })
                .setProgressDialog(mProgressDlg)
                .build();
        observable.subscribe(mSubscriber);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CommonUtil.unSubscribeSubs(mSubscriber);
    }
}
