package com.hzjytech.operation.module.me;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hzjytech.operation.R;
import com.hzjytech.operation.base.BaseActivity;
import com.hzjytech.operation.utils.GlideCatchUtil;
import com.hzjytech.operation.widgets.TitleBar;
import com.hzjytech.operation.widgets.switchbutton.SwitchButton;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hehongcan on 2017/4/18.
 */
public class SettingActivity extends BaseActivity {
    @BindView(R.id.switchButton_vibrate)
    SwitchButton switchButtonVibrate;
    @BindView(R.id.switchButton_sound)
    SwitchButton switchButtonSound;
    @BindView(R.id.tl_receive_message)
    LinearLayout tl_receive_message;
    @BindView(R.id.tl_clear)
    LinearLayout tl_clear;
    @BindView(R.id.tl_about)
    LinearLayout tl_about;
    @BindView(R.id.titleBar)
    TitleBar titleBar;

    @Override
    protected int getResId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        initTitlebar();
        initTabLayout(tl_receive_message);
        initTabLayout(tl_clear);
        initTabLayout(tl_about);
    }

    private void initTitlebar() {
         titleBar.setTitleBold(true);
         titleBar.setTitle(R.string.setting);
         titleBar.setTitleColor(Color.WHITE);
        titleBar.setLeftImageResource(R.drawable.icon_left);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingActivity.this.finish();
            }
        });
    }
    private void initTabLayout(View view) {
        TextView tab_me_name = (TextView) view.findViewById(R.id.tab_me_name);
        TextView tab_me_value = (TextView) view.findViewById(R.id.tab_me_value);
        ImageView iv_me_right = (ImageView) view.findViewById(R.id.iv_me_right);
        switch (view.getId()){
            case R.id.tl_receive_message:
                tab_me_name.setText("接收通知");
                iv_me_right.setVisibility(View.INVISIBLE);
                tab_me_value.setText("已开启");
                break;
            case R.id.tl_clear:
                try {
                    tab_me_value.setText(GlideCatchUtil.getInstance().getCacheSize());
                   // tab_me_value.setText(DiskCacheUtil.getTotalCacheSize(StorageUtils.getCacheDirectory(SettingActivity.this)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                tab_me_name.setText("清理缓存数据");
                break;
            case R.id.tl_about:
                tab_me_name.setText("关于锦业运维");
                tab_me_value.setVisibility(View.INVISIBLE);
                break;
        }
    }

    @OnClick({R.id.tl_clear, R.id.tl_about})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tl_clear:
                GlideCatchUtil.getInstance().clearCacheDiskSelf();

                try {
                    TextView tv_value = (TextView) tl_clear.findViewById(R.id.tab_me_value);
                    tv_value.setText("0.0Byte");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tl_about:
                Intent intent = new Intent(this, AboutOperationActivity.class);
                startActivity(intent);
                break;
        }
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
