package com.hzjytech.operation.module.me;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.hzjytech.operation.R;
import com.hzjytech.operation.base.BaseFragment;
import com.hzjytech.operation.utils.MD5Util;
import com.hzjytech.operation.utils.MyToastUtil;
import com.hzjytech.operation.utils.StringUtil;
import com.hzjytech.operation.utils.UserUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hehongcan on 2017/4/18.
 */
public class ChangePasswordFragment extends BaseFragment {
    @BindView(R.id.etOldPsdPsd)
    EditText etOldPsdPsd;
    @BindView(R.id.ivOldpsdfrgClear)
    ImageView ivOldpsdfrgClear;
    @BindView(R.id.btnOldPsdNext)
    Button btnOldPsdNext;

    @Override
    protected int getResId() {
        return R.layout.fragment_old_password;
    }

    @Override
    protected void initView() {
        etOldPsdPsd.addTextChangedListener(mTextWathcer);

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnNextFragmentClickListener){
            onNextFragmentClickListener= (OnNextFragmentClickListener) context;
        }else{
            throw new ClassCastException(context+" must implements OnNextFragmentClickListener");
        }
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
                ivOldpsdfrgClear.setVisibility(View.VISIBLE);
                btnOldPsdNext.setEnabled(true);


                ivOldpsdfrgClear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        etOldPsdPsd.setText("");
                        btnOldPsdNext.setEnabled(false);
                        ivOldpsdfrgClear.setVisibility(View.GONE);
                    }
                });
            }

            if (temp.length() == 0) {
                btnOldPsdNext.setEnabled(false);
                ivOldpsdfrgClear.setVisibility(View.GONE);
            }
        }
    };


    @OnClick(R.id.btnOldPsdNext)
    public void onClick() {
        String password = UserUtils.getUserInfo().getPassword();
        if(etOldPsdPsd.getText()==null||StringUtil.isNullOrEmpty(etOldPsdPsd.getText().toString().trim())||!(MD5Util.encrypt(etOldPsdPsd.getText().toString()).trim().equals(password))){
            //showTip(R.string.old_password_error);
            MyToastUtil.show(getActivity(),"原密码错误");
           // ToastUtil.showShort(getActivity(),"mimacuowu");
        }else{
            onNextFragmentClickListener.onNextClick();
        }
    }
    private  OnNextFragmentClickListener onNextFragmentClickListener;
    public interface OnNextFragmentClickListener{
        void onNextClick();
    }
}
