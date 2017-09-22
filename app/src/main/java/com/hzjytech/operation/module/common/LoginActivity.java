package com.hzjytech.operation.module.common;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hzjytech.operation.R;
import com.hzjytech.operation.base.BaseActivity;
import com.hzjytech.operation.entity.LoginInfo;
import com.hzjytech.operation.entity.User;

import com.hzjytech.operation.http.JijiaHttpSubscriber;
import com.hzjytech.operation.http.NetConstants;
import com.hzjytech.operation.http.SubscriberOnNextListener;
import com.hzjytech.operation.http.api.AuthorizationApi;
import com.hzjytech.operation.utils.AppUtil;
import com.hzjytech.operation.utils.MD5Util;
import com.hzjytech.operation.utils.StringUtil;
import com.hzjytech.operation.utils.TextUtil;
import com.hzjytech.operation.utils.ToastUtil;
import com.hzjytech.operation.utils.UserUtils;
import com.hzjytech.operation.widgets.dialogs.TipDingDingDialog;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by hehongcan on 2017/4/6.
 */
public class LoginActivity extends BaseActivity {
    @BindView(R.id.metLoginTel)
    EditText metLoginTel;
    @BindView(R.id.metLoginPsd)
    EditText metLoginPsd;
    @BindView(R.id.btnLoginFgpsd)
    TextView btnLoginFgpsd;
    @BindView(R.id.ivOldpsdfrgClear1)
    ImageView ivOldpsdfrgClear1;
    @BindView(R.id.ivOldpsdfrgClear2)
    ImageView ivOldpsdfrgClear2;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    private String token;


    @Override
    protected int getResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        metLoginTel.addTextChangedListener(mTextWathcer1);
        metLoginPsd.addTextChangedListener(mTextWathcer2);
        String s = metLoginPsd.getText()
                .toString();
        if (UserUtils.getMobile() == null) {
            return;
        }
        metLoginTel.setText(UserUtils.getMobile());
        if (UserUtils.getMobile() != null && !UserUtils.getMobile().equals("")) {
            metLoginTel.setSelection(metLoginTel.getText().length());
            ivOldpsdfrgClear1.setVisibility(View.VISIBLE);
            ivOldpsdfrgClear1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    metLoginTel.setText("");
                    ivOldpsdfrgClear1.setVisibility(View.GONE);
                }
            });
        }
    }

    @OnClick({R.id.btnLogin,R.id.btnLoginFgpsd})
    public void onClick(View v) {
        if (AppUtil.isFastClick())
            return;
      switch (v.getId()){
          case R.id.btnLogin:
              if (TextUtil.checkParams(metLoginTel.getText().toString().trim(), metLoginPsd.getText().toString().trim())) {
                  loginButtonOnClick();
              } else {
                  ToastUtil.showShort(LoginActivity.this, "请输入账号或密码");
              }
              break;
          case R.id.btnLoginFgpsd:
              TipDingDingDialog tipDingDingDialog = new TipDingDingDialog();
              tipDingDingDialog.show(getSupportFragmentManager(),"dingding");
      }



    }
    private void loginButtonOnClick(){
        String tel = metLoginTel.getText().toString().trim();
        String password = metLoginPsd.getText().toString().trim();
        Observable<User> observable = AuthorizationApi.login(tel, MD5Util.encrypt(password), NetConstants.IS_ONLINE)
                .flatMap(new Func1<LoginInfo, Observable<User>>() {
            @Override
            public Observable<User> call(LoginInfo loginInfo) {
                token=loginInfo.getToken();
                return AuthorizationApi.getPersonalData(loginInfo.getToken());
            }
        });
        long l = System.currentTimeMillis();
        JijiaHttpSubscriber subscriber = JijiaHttpSubscriber.buildSubscriber(this).setOnNextListener(new SubscriberOnNextListener<User>() {
            @Override
            public void onNext(User user) {
                user.setToken(token);
                JPushInterface.setAlias(LoginActivity.this,user.getAdminId()+"",null);
                UserUtils.saveLocalMobileAndPwd(metLoginTel.getText().toString(),"");
                UserUtils.saveUserInfo(user);

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        }).setProgressDialog(mProgressDlg).build();
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * edittext的内容观察者
     */
    private TextWatcher mTextWathcer1 = new TextWatcher() {

        private CharSequence temp;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            temp = s;
            if (temp.length() > 0) {
                ivOldpsdfrgClear1.setVisibility(View.VISIBLE);
                ivOldpsdfrgClear1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        metLoginTel.setText("");
                        btnLogin.setEnabled(false);
                        ivOldpsdfrgClear1.setVisibility(View.GONE);
                    }
                });
                if(!StringUtil.isNullOrEmpty(metLoginPsd.getText().toString())){
                    btnLogin.setEnabled(true);
                }
            }

            if (temp.length() == 0) {
                btnLogin.setEnabled(false);
                ivOldpsdfrgClear1.setVisibility(View.GONE);
            }
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            if (temp.length() > 0) {
                ivOldpsdfrgClear1.setVisibility(View.VISIBLE);
                ivOldpsdfrgClear1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        metLoginTel.setText("");
                        btnLogin.setEnabled(false);
                        ivOldpsdfrgClear1.setVisibility(View.GONE);
                    }
                });
                if (!StringUtil.isNullOrEmpty(metLoginPsd.getText().toString())) {
                    btnLogin.setEnabled(true);
                }
            }

            if (temp.length() == 0) {
                btnLogin.setEnabled(false);
                ivOldpsdfrgClear1.setVisibility(View.GONE);
            }
        }
    };

    private TextWatcher mTextWathcer2 = new TextWatcher() {

        private CharSequence temp;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            temp = s;
            if (temp.length() > 0) {
                ivOldpsdfrgClear2.setVisibility(View.VISIBLE);
                ivOldpsdfrgClear2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        metLoginPsd.setText("");
                        btnLogin.setEnabled(false);
                        ivOldpsdfrgClear2.setVisibility(View.GONE);
                    }
                });
                if(!StringUtil.isNullOrEmpty(metLoginTel.getText().toString())){
                    btnLogin.setEnabled(true);
                }
            }

            if (temp.length() == 0) {
                btnLogin.setEnabled(false);
                ivOldpsdfrgClear2.setVisibility(View.GONE);
            }

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            if (temp.length() > 0) {
                ivOldpsdfrgClear2.setVisibility(View.VISIBLE);
                ivOldpsdfrgClear2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        metLoginPsd.setText("");
                        btnLogin.setEnabled(false);
                        ivOldpsdfrgClear2.setVisibility(View.GONE);
                    }
                });
            }

            if (temp.length() == 0) {
                ivOldpsdfrgClear2.setVisibility(View.GONE);
            }
        }
    };

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
