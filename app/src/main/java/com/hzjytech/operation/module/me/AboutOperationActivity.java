package com.hzjytech.operation.module.me;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.hzjytech.operation.R;
import com.hzjytech.operation.base.BaseActivity;
import com.hzjytech.operation.utils.AppUtil;
import com.hzjytech.operation.widgets.TitleBar;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;

/**
 * Created by hehongcan on 2017/4/20.
 */
public class AboutOperationActivity extends BaseActivity {
    @BindView(R.id.titleBar)
    TitleBar titleBar;
    @BindView(R.id.tv_about_version)
    TextView tvAboutVersion;

    @Override
    protected int getResId() {
        return R.layout.activity_about_operation;
    }

    @Override
    protected void initView() {
        titleBar.setTitle(R.string.about_operation);
        titleBar.setTitleColor(Color.WHITE);
        titleBar.setTitleBold(true);
        titleBar.setLeftImageResource(R.drawable.icon_left);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvAboutVersion.setText(getString(R.string.version)+AppUtil.getVersionName(this));

    }
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
