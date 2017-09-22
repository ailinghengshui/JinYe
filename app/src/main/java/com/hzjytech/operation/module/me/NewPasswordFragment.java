package com.hzjytech.operation.module.me;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.hzjytech.operation.R;
import com.hzjytech.operation.base.BaseFragment;
import com.hzjytech.operation.entity.User;
import com.hzjytech.operation.http.HttpResult;
import com.hzjytech.operation.http.JijiaHttpSubscriber;
import com.hzjytech.operation.http.RetrofitSingleton;
import com.hzjytech.operation.http.SubscriberOnNextListener;
import com.hzjytech.operation.http.api.AuthorizationApi;
import com.hzjytech.operation.module.common.MyApplication;
import com.hzjytech.operation.utils.MD5Util;
import com.hzjytech.operation.utils.StringUtil;
import com.hzjytech.operation.utils.TextUtil;
import com.hzjytech.operation.utils.UserUtils;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hehongcan on 2017/4/18.
 */
public class NewPasswordFragment extends BaseFragment {
    @BindView(R.id.etNewPsdPsd)
    EditText etNewPsdPsd;
    @BindView(R.id.ivNewpsdfrgClear)
    ImageView ivNewpsdfrgClear;
    @BindView(R.id.etNewPsdRePsd)
    EditText etNewPsdRePsd;
    @BindView(R.id.ivNewpsdfrgReclear)
    ImageView ivNewpsdfrgReclear;
    @BindView(R.id.btnNewPsdOK)
    Button btnNewPsdOK;

    @Override
    protected int getResId() {
        return R.layout.fragment_new_password;
    }

    @Override
    protected void initView() {
        etNewPsdPsd.addTextChangedListener(mTextWathcer);
        etNewPsdRePsd.addTextChangedListener(mReTextWathcer);
    }


    @OnClick(R.id.btnNewPsdOK)
    public void onClick() {
        if (TextUtil.checkParams(etNewPsdPsd.getText().toString(), etNewPsdRePsd.getText().toString())) {

            if (etNewPsdPsd.getText().toString().trim().length() > 5 && etNewPsdRePsd.getText().toString().trim().length() > 5) {
                if (etNewPsdPsd.getText().toString().equals(etNewPsdRePsd.getText().toString())) {
                    if (StringUtil.isPassword(etNewPsdPsd.getText().toString())) {
                        doChangePsd(etNewPsdPsd.getText().toString());
                        getActivity().finish();
                    } else {
                        showTip("字母开头，6-18位，可包含字符数字和下划线");
                    }

                } else {
                    showTip("两次密码输入不一致");
                }
            } else {
                showTip("请输入至少六位密码");
            }


        } else {
            showTip("密码不能为空");
        }

    }

    /**
     * 修改密码
     *
     * @param s 新密码
     */
    private void doChangePsd(final String s) {
        final User userInfo = UserUtils.getUserInfo();
      /*  RetrofitSingleton.getApiService().changePassword(userInfo.getAdminId(), userInfo.getPassword(), MD5Util.encrypt(s), userInfo.getToken())
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HttpResult<Object>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        RetrofitSingleton.disposeFailureRxInfo(e, MyApplication.getInstance());
                    }

                    @Override
                    public void onNext(HttpResult<Object> objectHttpResult) {
                        if (objectHttpResult.getStatusCode() == 200) {
                              //ToastUtil.showShort(getActivity(), R.string.change_password_success);
                            showTip(R.string.change_password_success);
                            userInfo.setPassword(MD5Util.encrypt(s));
                            UserUtils.saveUserInfo(userInfo);
                        }
                    }
                });*/
        Observable<HttpResult<Object>> observable = AuthorizationApi.changePassword(userInfo.getAdminId(),
                userInfo.getPassword(),
                MD5Util.encrypt(s),
                userInfo.getToken());
        JijiaHttpSubscriber subscriber = JijiaHttpSubscriber.buildSubscriber(getActivity())
                .setOnNextListener(new SubscriberOnNextListener<HttpResult<Object>>() {


                    @Override
                    public void onNext(HttpResult<Object> result) {
                          if(result.getStatusCode()==200){
                              showTip(R.string.change_password_success);
                              userInfo.setPassword(MD5Util.encrypt(s));
                              UserUtils.saveUserInfo(userInfo);
                          }
                    }

                })
                .setProgressDialog(mProgressDlg)
                .build();
        observable.subscribe(subscriber);
    }
    private TextWatcher mTextWathcer = new TextWatcher() {

        private CharSequence temp;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            temp = s;

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            if (temp.length() > 0) {
                if (!StringUtil.isNullOrEmpty(etNewPsdRePsd.getText().toString())) {
                    btnNewPsdOK.setEnabled(true);
                }
                ivNewpsdfrgClear.setVisibility(View.VISIBLE);
                ivNewpsdfrgClear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        etNewPsdPsd.setText("");
                        ivNewpsdfrgClear.setVisibility(View.GONE);
                    }
                });
            }

            if (temp.length() == 0) {
                btnNewPsdOK.setEnabled(false);
                ivNewpsdfrgClear.setVisibility(View.GONE);
            }
        }
    };

    private TextWatcher mReTextWathcer = new TextWatcher() {

        private CharSequence temp;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            temp = s;

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            if (temp.length() > 0) {
                if(!StringUtil.isNullOrEmpty(etNewPsdPsd.getText().toString())){
                    btnNewPsdOK.setEnabled(true);
                }
                ivNewpsdfrgReclear.setVisibility(View.VISIBLE);
                ivNewpsdfrgReclear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        etNewPsdRePsd.setText("");
                        ivNewpsdfrgReclear.setVisibility(View.GONE);
                    }
                });
            }

            if (temp.length() == 0) {
                btnNewPsdOK.setEnabled(false);
                ivNewpsdfrgReclear.setVisibility(View.GONE);
            }
        }
    };

}
