package com.hzjytech.operation.module.me;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hzjytech.operation.R;
import com.hzjytech.operation.base.BaseActivity;
import com.hzjytech.operation.utils.StringUtil;
import com.hzjytech.operation.utils.ToastUtil;
import com.hzjytech.operation.widgets.TitleBar;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hehongcan on 2017/4/18.
 */
public class ChangePhoneActivity extends BaseActivity {
    public static final int RESULT_CHANGED_PHONE = 112;
    @BindView(R.id.titleBar)
    TitleBar titleBar;
    @BindView(R.id.etUpdatePhone)
    EditText etUpdatePhone;
    @BindView(R.id.ivOldClear)
    ImageView ivOldClear;
    private TextView rightTextView;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
    @Override
    protected int getResId() {
        return R.layout.layout_change_phone;
    }

    @Override
    protected void initView() {
        initTitleBar();
        initEditText();


    }

    private void initTitleBar() {
       titleBar.setTitle(R.string.changephone);
        titleBar.setTitleColor(Color.WHITE);
        titleBar.setTitleBold(true);
        LinearLayout ll_container = (LinearLayout) titleBar.getChildAt(2);
        titleBar.addAction(new TitleBar.Action() {
            @Override
            public String getText() {
                return "完成";
            }

            @Override
            public int getDrawable() {
                return 0;
            }

            @Override
            public void performAction(View view) {

            }
        });
        titleBar.setLeftImageResource(R.drawable.icon_left);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED,null);
                finish();
            }
        });
        rightTextView = (TextView) ll_container.getChildAt(0);
        rightTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(StringUtil.isMobile(etUpdatePhone.getText().toString())){
                    Intent intent = new Intent();
                    String result = etUpdatePhone.getText().toString();
                    intent.putExtra("changedPhone",result);
                    setResult(RESULT_CHANGED_PHONE,intent);
                    finish();
                }else{
                    ToastUtil.showShort(ChangePhoneActivity.this,R.string.please_input_correct_phone);
                }
            }
        });

    }
    private void initEditText() {
        Intent intent = getIntent();
        phone = intent.getStringExtra("phone");
        etUpdatePhone.setText(phone);
        etUpdatePhone.setFocusable(true);
        etUpdatePhone.requestFocus();
        etUpdatePhone.setSelection(etUpdatePhone.getText().length());
        etUpdatePhone.addTextChangedListener(mTextWathcer);
        if(etUpdatePhone.getText()!=null&&!etUpdatePhone.getText().toString().equals("")){
            ivOldClear.setVisibility(View.VISIBLE);
            ivOldClear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    etUpdatePhone.setText("");
                    ivOldClear.setVisibility(View.INVISIBLE);
                    rightTextView.setTextColor(Color.GRAY);
                    rightTextView.setClickable(false);
                }
            });
        }
    }

    private TextWatcher mTextWathcer=new TextWatcher(){

        private CharSequence temp;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            temp=s;

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if(temp.length()>0){
                ivOldClear.setVisibility(View.VISIBLE);
                rightTextView.setTextColor(Color.WHITE);
                rightTextView.setClickable(true);
                ivOldClear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        etUpdatePhone.setText("");
                        ivOldClear.setVisibility(View.INVISIBLE);
                        rightTextView.setTextColor(Color.GRAY);
                        rightTextView.setClickable(false);
                    }
                });
            }
            if(temp.length()==0){
                ivOldClear.setVisibility(View.INVISIBLE);
                rightTextView.setTextColor(Color.GRAY);
                rightTextView.setClickable(false);
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
