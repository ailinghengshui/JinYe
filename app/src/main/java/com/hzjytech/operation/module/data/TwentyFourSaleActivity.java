package com.hzjytech.operation.module.data;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.hzjytech.operation.R;
import com.hzjytech.operation.base.BaseActivity;
import com.hzjytech.operation.entity.User;
import com.hzjytech.operation.http.JijiaHttpSubscriber;
import com.hzjytech.operation.http.SubscriberOnNextListener;
import com.hzjytech.operation.http.api.DataApi;
import com.hzjytech.operation.utils.UserUtils;
import com.hzjytech.operation.widgets.TitleBar;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hehongcan on 2017/5/23.
 */
public class TwentyFourSaleActivity extends BaseActivity {
    @BindView(R.id.titleBar)
    TitleBar titleBar;
    @BindView(R.id.chart)
    LineChart mChart;
    @BindView(R.id.iv_bg)
    ImageView mIvBg;

    @Override
    protected int getResId() {
        return R.layout.activity_twentyfour_sale;
    }

    @Override
    protected void initView() {
        initTitle();
        initChart();
        initData();
    }

    private void initChart() {
        int grey = ContextCompat.getColor(this, R.color.heavy_grey);
        mChart.getDescription()
                .setEnabled(false);

        // enable touch gestures
        mChart.setTouchEnabled(true);

        mChart.setDragDecelerationFrictionCoef(0.9f);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setDrawGridBackground(false);
        mChart.setHighlightPerDragEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(true);

        // set an alternative background color
        // mChart.setBackgroundColor(Color.LTGRAY);

        // add data

        mChart.animateX(2500);

        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.SQUARE);
        l.setTextSize(11f);
        l.setTextColor(grey);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        //        l.setYOffset(11f);
        XAxis xAxis = mChart.getXAxis();
        xAxis.setTextSize(11f);
        xAxis.setTextColor(grey);
        xAxis.setDrawGridLines(true);
        xAxis.setDrawAxisLine(false);
        xAxis.setGranularityEnabled(true);
        xAxis.setGranularity(1f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTextColor(grey);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularityEnabled(true);
        mChart.getAxisRight()
                .setEnabled(false);
    }

    private void initData() {
        User userInfo = UserUtils.getUserInfo();
        Intent intent = getIntent();
        long startTime = intent.getLongExtra("startTime", 0);
        long endTime = intent.getLongExtra("endTime", 0);
        ArrayList<Integer> machinesId = intent.getIntegerArrayListExtra("machinesId");
        Observable<Object> observable = DataApi.getDailyData(userInfo.getToken(),
                startTime,
                endTime,
                0,
                machinesId);
        JijiaHttpSubscriber subscriber = JijiaHttpSubscriber.buildSubscriber(this)
                .setOnNextListener(new SubscriberOnNextListener<Object>() {
                    @Override
                    public void onNext(Object o) {
                        if (o == null || ((ArrayList) o).size() < 4) {
                            mIvBg.setVisibility(View.VISIBLE);
                            return;
                        }
                        ArrayList<Object> arr = (ArrayList) (o);
                        List<Double> list = (List<Double>) arr.get(3);
                        setChartData(list);

                    }
                })
                .setProgressDialog(mProgressDlg)
                .build();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 设置图表数据
     *
     * @param list
     */
    private void setChartData(final List<Double> list) {
        if(list==null||list.size()==0){
            mIvBg.setVisibility(View.VISIBLE);
            return;
        }
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        for (int i = 0; i < list.size(); i++) {
            Double aDouble = list.get(i);
            float v = aDouble.floatValue();
            yVals1.add(new Entry(i, v));
        }

        LineDataSet set1, set2;

        if (mChart.getData() != null && mChart.getData()
                .getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData()
                    .getDataSetByIndex(0);
            set1.setValues(yVals1);
            mChart.getData()
                    .notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(yVals1, "每小时杯数");
            set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            set1.setColor(ContextCompat.getColor(this, R.color.colorPrimary));
            set1.setLineWidth(2f);
            set1.setDrawCircles(false);
            set1.setDrawValues(false);
            LineData data = new LineData(set1);
          /*  data.setValueTextColor(Color.WHITE);
            data.setValueTextSize(9f);*/
         /*   mChart.getXAxis().setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    return TimeUtil.getSimpleTimeString();
                }
            });*/
            mChart.getAxisLeft()
                    .setValueFormatter(new IAxisValueFormatter() {
                        @Override
                        public String getFormattedValue(float value, AxisBase axis) {
                            return ((int) value) + "杯";
                        }
                    });
            // set data
            mChart.setData(data);
        }
    }


    private void initTitle() {
        titleBar.setTitleColor(Color.WHITE);
        titleBar.setTitleBold(true);
        titleBar.setTitle(R.string.twentyfour_sale);
        titleBar.setLeftImageResource(R.drawable.icon_left);
        titleBar.setLeftClickListener(new View.OnClickListener() {
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
