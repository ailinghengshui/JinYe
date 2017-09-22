package com.hzjytech.operation.module.group;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.google.gson.JsonObject;
import com.hzjytech.operation.R;
import com.hzjytech.operation.base.BaseActivity;
import com.hzjytech.operation.entity.GroupInfo;
import com.hzjytech.operation.http.JijiaHttpSubscriber;
import com.hzjytech.operation.http.SubscriberOnNextListener;
import com.hzjytech.operation.http.api.GroupApi;
import com.hzjytech.operation.utils.TimeUtil;
import com.hzjytech.operation.utils.UserUtils;
import com.hzjytech.operation.widgets.TitleBar;
import com.hzjytech.operation.widgets.switchbutton.SwitchButton;

import java.text.ParseException;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;

import static com.hzjytech.operation.constants.Constants.GroupClick.MONNEYOFF;

/**
 * Created by hehongcan on 2017/9/18.
 */

public class MoneyOffActivity extends BaseActivity {
    @BindView(R.id.titleBar)
    TitleBar mTitleBar;
    @BindView(R.id.sb_start_off_money)
    SwitchButton mSbStartOffMoney;
    @BindView(R.id.et_count_m)
    EditText mEtCountM;
    @BindView(R.id.et_count_d)
    EditText mEtCountD;
    @BindView(R.id.tv_moneyoff_startTime)
    TextView mTvStartTime;
    @BindView(R.id.tv_moneyoff_endTime)
    TextView mTvEndTime;
    @BindView(R.id.tv_is_start_discount)
    TextView mTvIsStartDiscount;
    @BindView(R.id.ll_money_off)
    LinearLayout mLlMoneyOff;
    @BindView(R.id.et_discount)
    EditText mEtDiscount;
    @BindView(R.id.ll_discount)
    LinearLayout mLlDiscount;
    private GroupInfo mGroupInfo;
    private int mGroupId;
    private boolean mIsStartMoneyOff;
    private String mStartTime = "";
    private String mEndTime = "";
    private double mCountM;
    private double mCountD;
    private int mMoneyType;
    private boolean mIsStartMoneyDiscount;
    private double mCount;

    @Override
    protected int getResId() {
        return R.layout.activity_money_off;
    }

    @Override
    protected void initView() {
        initData();
        initTitle();
    }

    private void initData() {
        Intent intent = getIntent();
        mGroupInfo = (GroupInfo) intent.getSerializableExtra("groupInfo");
        mGroupId = intent.getIntExtra("groupId", -1);
        mMoneyType = intent.getIntExtra("moneyType", -1);
        if (mMoneyType == MONNEYOFF) {
            initMoneyOffView();
        } else {
            initMoneyDiscountView();
        }
        mTvStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker(true);

            }
        });
        mTvEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker(false);
            }
        });

    }

    /**
     * 折扣界面
     */
    private void initMoneyDiscountView() {
        mLlDiscount.setVisibility(View.VISIBLE);
        mLlMoneyOff.setVisibility(View.GONE);
        mTvIsStartDiscount.setText(R.string.is_open_money_discount);
        if(mGroupInfo.getDiscount()!=0){
            mSbStartOffMoney.setChecked(true);
            mIsStartMoneyDiscount=true;
            mEtDiscount.setText(mGroupInfo.getDiscount()+"");
            mCount=mGroupInfo.getDiscount();

        }
        GroupInfo.BasicInfoBean info = mGroupInfo.getBasicInfo();
        if (info.getDiscountStartTime()!= null && !info.getDiscountStartTime()
                .equals("")) {
            mTvStartTime.setText(TimeUtil.getLong2FromLong(info.getDiscountStartTime()));
            mStartTime = info.getDiscountStartTime();
        }
        if (info.getDiscountEndTime() != null && !info.getDiscountEndTime()
                .equals("")) {
            mTvEndTime.setText(TimeUtil.getLong2FromLong(info.getDiscountEndTime()));
            mEndTime = info.getDiscountEndTime();
        }
        mSbStartOffMoney.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mIsStartMoneyDiscount = isChecked;
            }
        });
        mEtDiscount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    mCount= Double.valueOf(s.toString());
                } catch (NumberFormatException e) {
                    mCount = 0;
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 满减界面
     */
    private void initMoneyOffView() {
        mLlMoneyOff.setVisibility(View.VISIBLE);
        mLlDiscount.setVisibility(View.GONE);
        mTvIsStartDiscount.setText(R.string.is_open_money_off);
        if (mGroupInfo.getDiscountM() != 0) {
            mSbStartOffMoney.setChecked(true);
            mIsStartMoneyOff = true;
            mEtCountM.setText(mGroupInfo.getDiscountM() + "");
            mEtCountD.setText(mGroupInfo.getDiscountJ() + "");
            mCountM = mGroupInfo.getDiscountM();
            mCountD = mGroupInfo.getDiscountJ();
        }
        GroupInfo.BasicInfoBean info = mGroupInfo.getBasicInfo();
        if (info.getMoneyOfStartTime() != null && !info.getMoneyOfStartTime()
                .equals("")) {
            mTvStartTime.setText(TimeUtil.getLong2FromLong(info.getMoneyOfStartTime()));
            mStartTime = info.getMoneyOfStartTime();
        }
        if (info.getMoneyOfEndTime() != null && !info.getMoneyOfEndTime()
                .equals("")) {
            mTvEndTime.setText(TimeUtil.getLong2FromLong(info.getMoneyOfEndTime()));
            mEndTime = info.getMoneyOfEndTime();
        }

        mSbStartOffMoney.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mIsStartMoneyOff = isChecked;
            }
        });

        mEtCountM.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    mCountM = Double.valueOf(s.toString());
                } catch (NumberFormatException e) {
                    mCountM = 0;
                    e.printStackTrace();
                }

            }
        });
        mEtCountD.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    mCountD = Double.valueOf(s.toString());
                } catch (NumberFormatException e) {
                    mCountD = 0;
                    e.printStackTrace();
                }

            }
        });
        mEtCountD.setSelection(mEtCountD.getText()
                .length());
        mEtCountM.setSelection(mEtCountM.getText()
                .length());
    }

    /**
     * 显示时间选择器
     *
     * @param b
     */
    private void showTimePicker(final boolean b) {
        TimePickerView.Builder builder = new TimePickerView.Builder(MoneyOffActivity.this,
                new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        long l = TimeUtil.dateToLong(date);
                        String time = TimeUtil.getLongTime(l);
                        if (b) {
                            mStartTime = time;
                            mTvStartTime.setText(mStartTime);
                        } else {
                            mEndTime = time;
                            mTvEndTime.setText(mEndTime);
                        }

                    }
                });
        TimePickerView pickerView = builder.build();
        pickerView.show();
    }

    private void initTitle() {
        mTitleBar.setTitle(R.string.set_operation_time);
        mTitleBar.setLeftText(R.string.cancel);
        mTitleBar.setLeftTextColor(Color.WHITE);
        mTitleBar.addAction(new TitleBar.TextAction("完成") {
            @Override
            public void performAction(View view) {
                if(mMoneyType==MONNEYOFF){
                    commitOffTime();
                }else{
                    commitDiscountTime();
                }

            }
        });
        mTitleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

    /**
     * 提交折扣
     */
    private void commitOffTime() {
        if (!checkParams() && mIsStartMoneyOff) {
            return;
        }
        GroupInfo.BasicInfoBean basicInfo = mGroupInfo.getBasicInfo();
        JsonObject TimeSet = new JsonObject();
        if (mIsStartMoneyOff) {
            TimeSet.addProperty("moneyOfStartTime", mStartTime);
            TimeSet.addProperty("moneyOfEndTime", mEndTime);
        } else {
            TimeSet.addProperty("moneyOfStartTime", "");
            TimeSet.addProperty("moneyOfEndTime", "");
        }
        TimeSet.addProperty("discountStartTime",
                basicInfo.getDiscountStartTime() == null ? "" : basicInfo.getDiscountStartTime());
        TimeSet.addProperty("discountEndTime",
                basicInfo.getDiscountEndTime() == null ? "" : basicInfo.getDiscountStartTime());

        Observable<Object> observable = GroupApi.commitGroupModify(UserUtils.getUserInfo()
                        .getToken(),
                mGroupId,
                basicInfo.getName(),
                mGroupInfo.getDiscount(),
                mIsStartMoneyOff ? mCountM : 0,
                mIsStartMoneyOff ? mCountD : 0,
                TimeSet.toString());
        JijiaHttpSubscriber subscriber = JijiaHttpSubscriber.buildSubscriber(this)
                .setOnNextListener(new SubscriberOnNextListener() {
                    @Override
                    public void onNext(Object o) {
                        showTip(R.string.commit_success);
                        final Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                })
                .setProgressDialog(mProgressDlg)
                .build();
        observable.subscribe(subscriber);
    }

    /**
     * 提交满减
     */
    private void commitDiscountTime() {
        if (!checkParams() && mIsStartMoneyDiscount) {
            return;
        }
        GroupInfo.BasicInfoBean basicInfo = mGroupInfo.getBasicInfo();
        JsonObject TimeSet = new JsonObject();
        if (mIsStartMoneyDiscount) {
            TimeSet.addProperty("discountStartTime", mStartTime);
            TimeSet.addProperty("discountEndTime", mEndTime);
        } else {
            TimeSet.addProperty("discountStartTime", "");
            TimeSet.addProperty("discountEndTime", "");
        }
        TimeSet.addProperty("moneyOfEndTime",
                basicInfo.getMoneyOfStartTime() == null ? "" : basicInfo.getMoneyOfStartTime());
        TimeSet.addProperty("moneyOfStartTime",
                basicInfo.getMoneyOfEndTime() == null ? "" : basicInfo.getMoneyOfEndTime());

        Observable<Object> observable = GroupApi.commitGroupModify(UserUtils.getUserInfo()
                        .getToken(),
                mGroupId,
                basicInfo.getName(),
                mIsStartMoneyDiscount?mCount:0,
                mGroupInfo.getDiscountM(),
                mGroupInfo.getDiscountJ(),
                TimeSet.toString());
        JijiaHttpSubscriber subscriber = JijiaHttpSubscriber.buildSubscriber(this)
                .setOnNextListener(new SubscriberOnNextListener() {
                    @Override
                    public void onNext(Object o) {
                        showTip(R.string.commit_success);
                        final Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                })
                .setProgressDialog(mProgressDlg)
                .build();
        observable.subscribe(subscriber);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    /**
     * 校验提交参数
     *
     * @return
     */
    private boolean checkParams() {
        if(mMoneyType==MONNEYOFF){
            if (mCountD == 0 || mCountM == 0) {
                showTip(R.string.please_input_countd);
                return false;
            }
            if (mStartTime.equals("") || mEndTime.equals("")) {
                showTip(R.string.time_not_empty);
                return false;
            }
        }else{
            if(mCount==0){
                showTip(R.string.please_input_discount);
                return false;
            }
        }

        try {
            long start = TimeUtil.stringToLong(mStartTime, TimeUtil.DATE_FORMAT_LONG);
            long end = TimeUtil.stringToLong(mEndTime, TimeUtil.DATE_FORMAT_LONG);
            long current = System.currentTimeMillis();
            if (end < current) {
                showTip(R.string.finish_time_early);
                return false;
            }
            if (start > end) {
                showTip(R.string.end_early_start);
                return false;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }
}
