package com.hzjytech.operation.module.machine;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.hzjytech.operation.R;
import com.hzjytech.operation.base.BaseActivity;
import com.hzjytech.operation.widgets.TitleBar;
import com.hzjytech.operation.widgets.switchbutton.SwitchButton;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hehongcan on 2017/9/5.
 */

public class OperationTimeSetActivity extends BaseActivity {
    public static final String MORNING = "00:00";
    public static final String DAWN = "23:59";
    @BindView(R.id.titleBar)
    TitleBar mTitleBar;
    @BindView(R.id.sb_is_hole_day)
    SwitchButton mSbIsHoleDay;
    @BindView(R.id.tv_startTime)
    TextView mTvStartTime;
    @BindView(R.id.tv_endTime)
    TextView mTvEndTime;
    private String mEndTime;
    private String mStartTime;

    @Override
    protected int getResId() {
        return R.layout.activity_operation_time_set;
    }

    @Override
    protected void initView() {
        initTitle();
        initTimeView();
    }

    private void initTimeView() {
        Intent intent = getIntent();
        String openingTime = intent.getStringExtra("openingTime");
        String closingTime = intent.getStringExtra("closingTime");
        if(openingTime!=null&&!openingTime.equals("")){
            mStartTime=openingTime;
            mTvStartTime.setText(openingTime);
        }
        if(closingTime!=null&&!closingTime.equals("")){
            mEndTime=closingTime;
            mTvEndTime.setText(closingTime);
        }
        mSbIsHoleDay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mTvStartTime.setText(MORNING);
                    mStartTime=MORNING;
                    mTvEndTime.setText(DAWN);
                    mEndTime=DAWN;
                }

            }
        });
        mTvStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectStartTime();
            }
        });
        mTvEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectEndTime();
            }
        });
    }

    private void selectEndTime() {
        TimePickerView.Builder builder = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {


            @Override
            public void onTimeSelect(Date date, View v) {
                mEndTime = new SimpleDateFormat("HH:mm").format(date);
                mTvEndTime.setText(mEndTime);
            }
        });
        builder.setLabel("","","","时","分","");
        builder.setType(TimePickerView.Type.HOURS_MINS);
        TimePickerView timePickerView = builder.build();
        timePickerView.show();
    }

    private void selectStartTime() {
        TimePickerView.Builder builder = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date, View v) {
                mStartTime = new SimpleDateFormat("HH:mm").format(date);
                mTvStartTime.setText(mStartTime);
            }
        });
        builder.setLabel("","","","时","分","");
        builder.setType(TimePickerView.Type.HOURS_MINS);
        TimePickerView timePickerView = builder.build();
        timePickerView.show();
    }

    private void initTitle() {
        final Intent intent = new Intent();
        mTitleBar.setTitle(R.string.set_operation_time);
        mTitleBar.setLeftText(R.string.cancel);
        mTitleBar.setLeftTextColor(Color.WHITE);
        mTitleBar.addAction(new TitleBar.TextAction("保存") {
            @Override
            public void performAction(View view) {
                intent.putExtra("startTime", mStartTime);
                intent.putExtra("endTime", mEndTime);
                setResult(RESULT_OK, intent);
                finish();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


}
