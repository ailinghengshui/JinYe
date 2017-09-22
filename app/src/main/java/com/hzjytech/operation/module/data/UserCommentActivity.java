package com.hzjytech.operation.module.data;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.hzjytech.operation.R;
import com.hzjytech.operation.base.BaseActivity;
import com.hzjytech.operation.entity.DrinkInfo;
import com.hzjytech.operation.entity.User;
import com.hzjytech.operation.entity.UserCommentInfo;
import com.hzjytech.operation.http.JijiaHttpSubscriber;
import com.hzjytech.operation.http.SubscriberOnCompletedListener;
import com.hzjytech.operation.http.SubscriberOnNextListener;
import com.hzjytech.operation.http.api.DataApi;
import com.hzjytech.operation.utils.MyAxisXValueFormatter;
import com.hzjytech.operation.utils.MyValueFormatter;
import com.hzjytech.operation.utils.TimeUtil;
import com.hzjytech.operation.utils.UserUtils;
import com.hzjytech.operation.widgets.TitleBar;
import com.hzjytech.operation.widgets.XYMarkerView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.functions.Func1;

import com.hzjytech.operation.entity.UserCommentInfo.UserEvaluationRateBean;

/**
 * Created by hehongcan on 2017/8/4.
 */

public class UserCommentActivity extends BaseActivity {
    @BindView(R.id.titleBar)
    TitleBar mTitleBar;
    @BindView(R.id.chart)
    BarChart mChart;
    @BindView(R.id.iv_bg)
    ImageView mIvBg;

    @Override
    protected int getResId() {
        return R.layout.activity_user_comment;
    }

    @Override
    protected void initView() {
        initTitle();
        initChart();
        initData();
    }

    private void initTitle() {
        mTitleBar.setTitleColor(Color.WHITE);
        mTitleBar.setTitleBold(true);
        mTitleBar.setTitle(R.string.machine_comment);
        mTitleBar.setLeftImageResource(R.drawable.icon_left);
        mTitleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
    private void initChart() {
        mChart.getDescription()
                .setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        mChart.setDrawBarShadow(false);
        mChart.setDrawGridBackground(false);
        mChart.setDrawValueAboveBar(false);
        //x轴
        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);
        xAxis.setGranularityEnabled(true);
        xAxis.setGranularity(1f);

        //右轴
        mChart.getAxisRight()
                .setEnabled(false);
        //左轴
        YAxis axisLeft = mChart.getAxisLeft();
        axisLeft.setYOffset(0f);
        axisLeft.setDrawGridLines(true);
        axisLeft.setAxisMinimum(0f);
        axisLeft.setAxisMaximum(100f);
        axisLeft.setGranularityEnabled(true);
        axisLeft.setGranularity(1f);
        // add a nice and smooth animation
        mChart.animateY(2500);
        //设置legend
        mChart.getLegend()
                .setEnabled(true);
        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setDrawInside(false);
        l.setFormSize(8f);
        l.setFormToTextSpace(4f);
        l.setXEntrySpace(6f);
        XYMarkerView mv = new XYMarkerView(this);
        mv.setChartView(mChart); // For bounds control
        //mChart.setMarker(mv); // Set the marker to the chart
    }
    private void initData() {
        User userInfo = UserUtils.getUserInfo();
        Intent intent = getIntent();
        ArrayList<Integer> machinesId = intent.getIntegerArrayListExtra("machinesId");
        long startTime = intent.getLongExtra("startTime", 0);
        long endTime = intent.getLongExtra("endTime", 0);
        Observable<UserCommentInfo.UserEvaluationRateBean> observable = DataApi.getUserEvaluation(userInfo.getToken(),
                startTime,
                endTime,
                0,
                machinesId).flatMap(new Func1<UserCommentInfo, Observable<UserEvaluationRateBean>>() {
            @Override
            public Observable<UserEvaluationRateBean> call(UserCommentInfo info) {
                return Observable.from(info.getUserEvaluationRate());
            }
        }).filter(new Func1<UserEvaluationRateBean, Boolean>() {
            @Override
            public Boolean call(UserEvaluationRateBean bean) {
                if(bean.getKeep_trying()==0&&bean.getNot_very_satisfied()==0&&bean.getVery_satisfied()==0){
                    return false;
                }
                return true;
            }
        });
        final List<UserCommentInfo.UserEvaluationRateBean> list=new ArrayList<>();
        JijiaHttpSubscriber subscriber = JijiaHttpSubscriber.buildSubscriber(this)
                .setOnNextListener(new SubscriberOnNextListener<UserEvaluationRateBean>() {
                    @Override
                    public void onNext(UserEvaluationRateBean bean) {
                         list.add(bean);

                    }
                }).setOnCompletedListener(new SubscriberOnCompletedListener() {
                    @Override
                    public void onCompleted() {
                        setChartData(list);
                    }
                })
                .setProgressDialog(mProgressDlg)
                .build();
        observable.subscribe(subscriber);
    }

    private void setChartData(final List<UserEvaluationRateBean> list) {
        if(list==null||list.size()==0){
            mIvBg.setVisibility(View.VISIBLE);
            return;
        }
        BarDataSet set1;
        ArrayList<String> xValues = new ArrayList<String>();
        for (int i = 0; i < list.size(); i++) {
            xValues.add(list.get(i).getMachineCode());
        }

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        for (int i = 0; i < list.size(); i++) {
            int val0 = list.get(i).getVery_satisfied();
            int val1 = list.get(i).getKeep_trying();
            int val2 = list.get(i).getNot_very_satisfied();
            float totle=val0+val1+val2;
            yVals1.add(new BarEntry(i, new float[]{val0*100/totle, val1*100/totle, val2*100/totle}));
        }
        if (mChart.getData() != null && mChart.getData()
                .getDataSetCount() > 0) {
            set1 = (BarDataSet) mChart.getData()
                    .getDataSetByIndex(0);
            set1.setValues(yVals1);
            mChart.getData()
                    .notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "");
            set1.setDrawIcons(false);
            set1.setColors(getColors());
            set1.setStackLabels(new String[]{"非常满意", "继续努力", "不太满意"});
            set1.setDrawValues(true);
            mChart.setDrawValueAboveBar(false);
            mChart.setMaxVisibleValueCount(20);
            set1.setValueFormatter(new CustomFormatter(list));
            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);
            BarData data = new BarData(dataSets);
            data.setValueTextColor(Color.WHITE);
            XAxis xAxis = mChart.getXAxis();
            xAxis.setLabelCount(4,false);//设置x轴label个数
            xAxis.setValueFormatter(new MyAxisXValueFormatter(xValues));
            xAxis.setGranularityEnabled(true);    //粒度
            xAxis.setGranularity(1f);
            YAxis axisLeft = mChart.getAxisLeft();
            axisLeft.setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    return (int)value+"%";
                }
            });
            mChart.setData(data);
        }
        XYMarkerView mv = new XYMarkerView(this);
      //  mv.setChartView(mChart); // For bounds control
       // mChart.setMarker(mv); // Set the marker to the chart
        mChart.setFitBars(true);
        mChart.invalidate();
    }
    private int[] getColors() {

        int stacksize = 3;

        // have as many colors as stack-values per entry
        int[] colors = new int[stacksize];
        colors[0] = ContextCompat.getColor(this, R.color.colorPrimary);
        colors[1] = ContextCompat.getColor(this, R.color.normal_green);
        colors[2] = ContextCompat.getColor(this, R.color.orange);
        return colors;
    }
    private class CustomFormatter implements IValueFormatter, IAxisValueFormatter
    {

        private final List<UserEvaluationRateBean> list;
        private DecimalFormat mFormat;

        public CustomFormatter(List<UserEvaluationRateBean> list) {
            this.list=list;
            mFormat = new DecimalFormat("#.0");
        }

        // data
        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            UserEvaluationRateBean bean = list.get((int) entry.getX());
            int val0 = bean.getVery_satisfied();
            int val1 = bean.getNot_very_satisfied();
            int val2 = bean.getKeep_trying();
            int val = val0 + val1 + val2;
            float v = val * value / 100f;
            return (int)v+"单\n"+mFormat.format(value)+"%";
            //return "1"+"\n"+"%";
        }

        // YAxis
        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return mFormat.format(Math.abs(value)) + "m";
        }
    }

}
