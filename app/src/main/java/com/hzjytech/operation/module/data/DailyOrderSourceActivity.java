package com.hzjytech.operation.module.data;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hzjytech.operation.R;
import com.hzjytech.operation.base.BaseActivity;
import com.hzjytech.operation.entity.DailyOrderSourceInfo;
import com.hzjytech.operation.entity.User;
import com.hzjytech.operation.http.JijiaHttpSubscriber;
import com.hzjytech.operation.http.SubscriberOnNextListener;
import com.hzjytech.operation.http.api.DataApi;
import com.hzjytech.operation.utils.MyAxisValueFormatter;
import com.hzjytech.operation.utils.MyAxisXValueFormatter;
import com.hzjytech.operation.utils.MyValueFormatter;
import com.hzjytech.operation.utils.TimeUtil;
import com.hzjytech.operation.utils.UserUtils;
import com.hzjytech.operation.widgets.TitleBar;
import com.hzjytech.operation.widgets.XYMarkerView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;

/**
 * Created by hehongcan on 2017/7/12.
 */
public class DailyOrderSourceActivity extends BaseActivity {
    @BindView(R.id.titleBar)
    TitleBar mTitleBar;
    @BindView(R.id.chart)
    BarChart mChart;
    @BindView(R.id.iv_bg)
    ImageView mIvBg;

    @Override
    protected int getResId() {
        return R.layout.activity_daily_order_source;
    }

    @Override
    protected void initView() {
        initTitle();
        initData();
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
                        String s = o.toString();
                        try {
                            JSONArray jsonArray = new JSONArray(s);
                            JSONArray array = (JSONArray) jsonArray.get(2);
                            ArrayList<DailyOrderSourceInfo> list = new Gson().fromJson(array
                                            .toString(),
                                    new TypeToken<ArrayList<DailyOrderSourceInfo>>() {}.getType());
                            setChartData(list);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.e("s", s);

                    }
                })
                .setProgressDialog(mProgressDlg)
                .build();
        observable.subscribe(subscriber);
    }

    /**
     * 设置图表数据
     *
     * @param list
     */
    private void setChartData(ArrayList<DailyOrderSourceInfo> list) {
        if(list==null||list.size()==0){
            mIvBg.setVisibility(View.VISIBLE);
            return;
        }
        initChart();
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        ArrayList<String> xValues = new ArrayList<String>();
        for (int i = 0; i < list.size(); i++) {
            xValues.add(TimeUtil.getShort4TimeFromShort2(list.get(i)
                    .getTime()));
        }

        for (int i = 0; i < list.size(); i++) {
            float mult = list.size();
            float val1 = list.get(i)
                    .getDailyOrigin()
                    .get(0)
                    .getNum();
            float val2 = list.get(i)
                    .getDailyOrigin()
                    .get(1)
                    .getNum();
            float val3 = list.get(i)
                    .getDailyOrigin()
                    .get(2)
                    .getNum();

            yVals1.add(new BarEntry(i, new float[]{val1, val2, val3}));
        }

        BarDataSet set1;

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
            set1.setStackLabels(new String[]{"咖啡机端", "手机端", "活动订单"});
            set1.setDrawValues(false);
            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);
            BarData data = new BarData(dataSets);
            data.setValueFormatter(new MyValueFormatter());
            data.setValueTextColor(Color.WHITE);
            XAxis xAxis = mChart.getXAxis();
            xAxis.setValueFormatter(new MyAxisXValueFormatter(xValues));
            xAxis.setGranularityEnabled(true);    //粒度
            xAxis.setGranularity(1f);
            //xAxis.setLabelCount(list.size()/2,false);//设置x轴label个数
            mChart.setData(data);
        }
        XYMarkerView mv = new XYMarkerView(this);
        mv.setChartView(mChart); // For bounds control
        mChart.setMarker(mv); // Set the marker to the chart
        mChart.setFitBars(true);
        mChart.invalidate();
    }

    private void initChart() {
        mChart.getDescription()
                .setEnabled(false);
        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart.setMaxVisibleValueCount(40);

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        mChart.setDrawGridBackground(false);
        mChart.setDrawBarShadow(false);

        mChart.setDrawValueAboveBar(false);
        mChart.setHighlightFullBarEnabled(false);
        mChart.animateXY(3000, 3000);

        // change the position of the y-labels
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setValueFormatter(new MyAxisValueFormatter());
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        leftAxis.setGranularity(1);
        leftAxis.setGranularityEnabled(true);
        mChart.getAxisRight()
                .setEnabled(false);

        XAxis xLabels = mChart.getXAxis();
        xLabels.setPosition(XAxis.XAxisPosition.BOTTOM);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setFormSize(8f);
        l.setFormToTextSpace(4f);
        l.setXEntrySpace(6f);

    }

    private void initTitle() {
        mTitleBar.setTitleColor(Color.WHITE);
        mTitleBar.setTitleBold(true);
        mTitleBar.setTitle(R.string.daily_order_orgin);
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

    private int[] getColors() {

        int stacksize = 3;

        // have as many colors as stack-values per entry
        int[] colors = new int[stacksize];
        colors[0] = ContextCompat.getColor(this, R.color.colorPrimary);
        colors[1] = ContextCompat.getColor(this, R.color.normal_green);
        colors[2] = ContextCompat.getColor(this, R.color.orange);
        return colors;
    }
}
