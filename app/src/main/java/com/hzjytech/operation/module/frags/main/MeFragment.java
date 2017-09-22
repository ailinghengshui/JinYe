package com.hzjytech.operation.module.frags.main;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hzjytech.operation.R;
import com.hzjytech.operation.base.BaseFragment;
import com.hzjytech.operation.entity.User;
import com.hzjytech.operation.http.HttpResult;
import com.hzjytech.operation.http.JijiaHttpSubscriber;
import com.hzjytech.operation.http.SubscriberOnNextListener;
import com.hzjytech.operation.http.api.AuthorizationApi;
import com.hzjytech.operation.module.common.LoginActivity;
import com.hzjytech.operation.module.me.ChangePasswordActivity;
import com.hzjytech.operation.module.me.ChangePhoneActivity;
import com.hzjytech.operation.module.me.SettingActivity;
import com.hzjytech.operation.utils.DensityUtil;
import com.hzjytech.operation.utils.UserUtils;
import com.hzjytech.operation.widgets.HomeUpPopWindow;
import com.hzjytech.operation.widgets.TitleBar;
import com.hzjytech.operation.widgets.dialogs.ITwoButtonClick;
import com.hzjytech.operation.widgets.dialogs.TitleButtonsDialog;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;

/**
 * Created by hehongcan on 2017/4/7.
 */
public class MeFragment extends BaseFragment {
    private static final int REQUEST_CHANGEPHONE = 111;
    @BindView(R.id.titleBar)
    TitleBar titleBar;
    @BindView(R.id.btnMeExit)
    Button btnMeExit;
    @BindView(R.id.ll_me_name)
    LinearLayout ll_me_name;
    @BindView(R.id.ll_me_role)
    LinearLayout ll_me_role;
    @BindView(R.id.ll_me_email)
    LinearLayout ll_me_email;
    @BindView(R.id.ll_me_phone)
    LinearLayout ll_me_phone;
    @BindView(R.id.ll_me_password)
    LinearLayout ll_me_password;
    @BindView(R.id.ll_me_setting)
    LinearLayout ll_me_setting;
    private User userInfo;
    private HomeUpPopWindow popWindow;
    private View plusView;
    private HomeUpPopWindow plusPopWindow;


    @Override
    protected int getResId() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initView() {
        initTitleBar();
        initMeTab(ll_me_name);
        initMeTab(ll_me_role);
        initMeTab(ll_me_phone);
        initMeTab(ll_me_email);
        initMeTab(ll_me_password);
        initMeTab(ll_me_setting);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data!=null){
            if(requestCode==REQUEST_CHANGEPHONE&&resultCode== ChangePhoneActivity.RESULT_CHANGED_PHONE){
                String changedPhone = data.getStringExtra("changedPhone");
                userInfo.setPhone(changedPhone);
                UserUtils.saveUserInfo(userInfo);
                initMeTab(ll_me_phone);
                changedPhone(changedPhone);
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 修改电话号码
     */
    private void changedPhone(String changedPhone) {
      /*  RetrofitSingleton.getApiService().changePhone(userInfo.getAdminId(),userInfo.getToken(),changedPhone)
                .subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<HttpResult<Object>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                RetrofitSingleton.disposeFailureRxInfo(e, MyApplication.getInstance());
            }

            @Override
            public void onNext(HttpResult<Object> objectHttpResult) {
               if(objectHttpResult.getStatusCode()==200){
                   showTip(R.string.change_phone_success);
               }
            }
        });*/
        Observable<HttpResult<Object>> observable = AuthorizationApi.changePhone(userInfo.getAdminId(),
                userInfo.getToken(),
                changedPhone);
        JijiaHttpSubscriber subscriber = JijiaHttpSubscriber.buildSubscriber(getActivity())
                .setOnNextListener(new SubscriberOnNextListener<HttpResult<Object>>() {


                    @Override
                    public void onNext(HttpResult<Object> result) {
                       if(result.getStatusCode()==200){
                           showTip(R.string.change_phone_success);
                       }
                    }
                })
                .setProgressDialog(mProgressDlg)
                .build();
        observable.subscribe(subscriber);
    }

    /**
     * 初始化标题栏
     */
    private void initTitleBar() {
        titleBar.setTitle(R.string.me);
        titleBar.setTitleColor(Color.WHITE);
        titleBar.setTitleBold(true);
        plusView = titleBar.addAction(new TitleBar.ImageAction(R.drawable.icon_plus) {
            @Override
            public void performAction(View view) {
                if (plusPopWindow != null &&   plusPopWindow.isShowing()) {
                    plusPopWindow.dismiss();
                    return;
                } else {
                    if(plusView==null){
                        return;
                    }
                    plusPopWindow = new HomeUpPopWindow(getActivity());
                    int XDx = DensityUtil.dp2px(getActivity(), -114);
                    int YDx = DensityUtil.dp2px(getActivity(), -5);
                    plusPopWindow.showAsDropDown(plusView,XDx,YDx);
                }
            }
        });
    }

    /**
     * 初始化Me tab
     */
    private void initMeTab(View view) {
        TextView tab_me_name = (TextView) view.findViewById(R.id.tab_me_name);
        TextView tab_me_value = (TextView) view.findViewById(R.id.tab_me_value);
        ImageView iv_me_right = (ImageView) view.findViewById(R.id.iv_me_right);
        userInfo = UserUtils.getUserInfo();
        switch (view.getId()) {
            case R.id.ll_me_name:
                tab_me_name.setText(R.string.name);
                tab_me_value.setText(userInfo.getAdminName());
                iv_me_right.setVisibility(View.INVISIBLE);
                break;
            case R.id.ll_me_role:
                tab_me_name.setText(R.string.role);
                tab_me_value.setText(userInfo.getRoleName());
                iv_me_right.setVisibility(View.INVISIBLE);
                break;
            case R.id.ll_me_phone:
                tab_me_name.setText(R.string.phone);
                tab_me_value.setText(userInfo.getPhone());
                break;
            case R.id.ll_me_email:
                tab_me_name.setText(R.string.email);
                tab_me_value.setText(userInfo.getEmail());
                iv_me_right.setVisibility(View.INVISIBLE);
                break;
            case R.id.ll_me_password:
                tab_me_name.setText(R.string.password);
                tab_me_value.setText(R.string.change);
                break;
            case R.id.ll_me_setting:
                tab_me_name.setText(R.string.setting);
                tab_me_value.setVisibility(View.INVISIBLE);
                break;
        }
    }




    @OnClick({R.id.ll_me_name, R.id.ll_me_phone,R.id.ll_me_password,R.id.ll_me_setting,R.id.btnMeExit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_me_phone:
                Intent intent = new Intent(getActivity(), ChangePhoneActivity.class);
                intent.putExtra("phone",UserUtils.getUserInfo().getPhone());
                startActivityForResult(intent,REQUEST_CHANGEPHONE);
                break;
            case R.id.ll_me_password:
                Intent intent2 = new Intent(getActivity(), ChangePasswordActivity.class);
                startActivity(intent2);
                break;
            case R.id.ll_me_setting:
                Intent intent3 = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent3);
                break;
            case R.id.btnMeExit:
                TitleButtonsDialog descCenterDialog= TitleButtonsDialog.newInstance("确定退出当前账号?","取消","确定");
                descCenterDialog.setOnTwoClickListener(new ITwoButtonClick() {
                                                           @Override
                                                           public void onLeftButtonClick() {

                                                           }

                                                           @Override
                                                           public void onRightButtonClick() {
                                                               UserUtils.saveUserInfo(null);
                                                               getActivity().finish();
                                                               Intent intent4 = new Intent(getActivity(), LoginActivity.class);
                                                               startActivity(intent4);
                                                           }
                                                       });
                descCenterDialog.show(getFragmentManager(),"desc");

        }
    }
}
