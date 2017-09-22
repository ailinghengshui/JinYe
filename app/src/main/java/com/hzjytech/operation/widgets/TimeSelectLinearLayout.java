package com.hzjytech.operation.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.hzjytech.operation.R;
import com.hzjytech.operation.inter.OnLongTimePickerListener;
import com.hzjytech.operation.utils.TimeUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hehongcan on 2017/5/17.
 */
public class TimeSelectLinearLayout extends LinearLayout {
    @BindView(R.id.tv_more_choose)
    TextView tvMoreChoose;
    private Context context;
    @BindView(R.id.ll_check_moreTime)
    LinearLayout llCheckMoreTime;
    @BindView(R.id.tv_select_startTime)
    TextView tvSelectStartTime;
    @BindView(R.id.tv_select_endTime)
    TextView tvSelectEndTime;
    private OptionsPickerView pvOptions;
    private ArrayList<String> options1Items;
    private TimePickerView pvTime;
    private OnLongTimePickerListener onLongTimePickerListener;

    public TimeSelectLinearLayout(Context context) {
        this(context, null);
    }

    public TimeSelectLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimeSelectLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_select_linearlayout, null, false);
        addView(view);
        ButterKnife.bind(this, view);
    }

    @OnClick({R.id.ll_check_moreTime, R.id.tv_select_startTime, R.id.tv_select_endTime})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_check_moreTime:
                selectMoreTime();
                break;
            case R.id.tv_select_startTime:
                selectStartTime();
                break;
            case R.id.tv_select_endTime:
                selectEndTime();
                break;
        }
    }

    private void selectEndTime() {
        //时间选择器
        pvTime = new TimePickerView.Builder(context, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                tvSelectEndTime.setSelected(true);
                tvSelectEndTime.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
                if (onLongTimePickerListener != null) {
                    onLongTimePickerListener.getEndTime(date.getTime());
                }
            }
        })
        .build();

        pvTime.show();
    }

    private void selectStartTime() {
        //时间选择器
        pvTime = new TimePickerView.Builder(context, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                tvSelectStartTime.setSelected(true);
                tvSelectStartTime.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
                if (onLongTimePickerListener != null) {
                    onLongTimePickerListener.getStartTime(date.getTime());
                }
            }
        })
                .build();
        pvTime.show();
    }

    private void selectMoreTime() {
        options1Items = new ArrayList<>();
        options1Items.add("近三天");
        options1Items.add("近一周");
        options1Items.add("近一个月");
        options1Items.add("近三个月");
        options1Items.add("近六个月");
        //条件选择器
        pvOptions = new OptionsPickerView.Builder(context, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                tvSelectStartTime.setSelected(true);
                tvSelectEndTime.setSelected(true);
                tvSelectEndTime.setText(TimeUtil.lastDay());
                try {
                    onLongTimePickerListener.getEndTime(TimeUtil.stringToLong(TimeUtil.lastDay(),"yyyy-MM-dd HH:mm:ss"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                switch (options1) {
                    case 0:
                        tvMoreChoose.setText("近三天");
                        tvSelectStartTime.setText(TimeUtil.getThreeDaysBefore());
                        if (onLongTimePickerListener != null) {
                            try {
                                onLongTimePickerListener.getStartTime(TimeUtil.stringToLong(TimeUtil.getThreeDaysBefore(), "yyyy-MM-dd HH:mm:ss"));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case 1:
                        tvMoreChoose.setText("近一周");
                        tvSelectStartTime.setText(TimeUtil.lastWeek());
                        if (onLongTimePickerListener != null) {
                            try {
                                onLongTimePickerListener.getStartTime(TimeUtil.stringToLong(TimeUtil.lastWeek(), "yyyy-MM-dd HH:mm:ss"));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case 2:
                        tvMoreChoose.setText("近一个月");
                        tvSelectStartTime.setText(TimeUtil.lastMonth());
                        if (onLongTimePickerListener != null) {
                            try {
                                onLongTimePickerListener.getStartTime(TimeUtil.stringToLong(TimeUtil.lastMonth(), "yyyy-MM-dd HH:mm:ss"));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case 3:
                        tvMoreChoose.setText("近三个月");
                        tvSelectStartTime.setText(TimeUtil.getThreeMonthBefore());
                        if (onLongTimePickerListener != null) {
                            try {
                                onLongTimePickerListener.getStartTime(TimeUtil.stringToLong(TimeUtil.getThreeMonthBefore(), "yyyy-MM-dd HH:mm:ss"));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case 4:
                        tvMoreChoose.setText("近六个月");
                        tvSelectStartTime.setText(TimeUtil.getSixMonthBefore());
                        if (onLongTimePickerListener != null) {
                            try {
                                onLongTimePickerListener.getStartTime(TimeUtil.stringToLong(TimeUtil.getSixMonthBefore(), "yyyy-MM-dd HH:mm:ss"));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
        }).build();
        pvOptions.setPicker(options1Items);
        pvOptions.show();
    }


    public void setOnLongTimePickerListener(OnLongTimePickerListener onLongTimePickerListener) {
        this.onLongTimePickerListener = onLongTimePickerListener;
    }

}
