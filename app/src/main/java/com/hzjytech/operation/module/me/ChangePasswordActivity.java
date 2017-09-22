package com.hzjytech.operation.module.me;

import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RelativeLayout;

import com.hzjytech.operation.R;
import com.hzjytech.operation.base.BaseActivity;
import com.hzjytech.operation.widgets.TitleBar;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;

/**
 * Created by hehongcan on 2017/4/18.
 */
public class ChangePasswordActivity extends BaseActivity implements ChangePasswordFragment.OnNextFragmentClickListener {
    @BindView(R.id.titleBar)
    TitleBar titleBar;
    @BindView(R.id.rl_change_password_container)
    RelativeLayout change_password_container;
    private FragmentTransaction fragmentTransaction;
    private ChangePasswordFragment changePasswordFragment;
    private NewPasswordFragment newPsdFragment;

    @Override
    protected int getResId() {
        return R.layout.activity_change_password;
    }

    @Override
    protected void initView() {
        initTitle();
    }

    private void initTitle() {
        titleBar.setLeftImageResource(R.drawable.icon_left);
        titleBar.setTitle(R.string.change_password);
        titleBar.setTitleColor(Color.WHITE);
        titleBar.setTitleBold(true);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                if (fragmentManager.getBackStackEntryCount() > 0) {
                    fragmentManager.popBackStack();
                }else{
                    finish();
                }
            }
        });
        initFragments();
    }

    private void initFragments() {
        fragmentTransaction =getSupportFragmentManager().beginTransaction();
        changePasswordFragment =new ChangePasswordFragment();
        fragmentTransaction.add(R.id.rl_change_password_container, changePasswordFragment,"registerfragment");
        fragmentTransaction.commit();
    }

    @Override
    public void onNextClick() {
        fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.hide(changePasswordFragment);
        fragmentTransaction.addToBackStack("changePasswordFragment");
        newPsdFragment =new NewPasswordFragment();
        fragmentTransaction.addToBackStack("changePasswordFragment");
        fragmentTransaction.add(R.id.rl_change_password_container, newPsdFragment,"NewPsdFragment");
        fragmentTransaction.commit();
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
