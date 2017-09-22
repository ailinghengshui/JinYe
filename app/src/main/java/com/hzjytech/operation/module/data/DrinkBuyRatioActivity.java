package com.hzjytech.operation.module.data;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hzjytech.operation.R;
import com.hzjytech.operation.base.BaseActivity;
import com.hzjytech.operation.entity.DrinkInfo;
import com.hzjytech.operation.entity.User;
import com.hzjytech.operation.http.JijiaHttpSubscriber;
import com.hzjytech.operation.http.SubscriberOnNextListener;
import com.hzjytech.operation.http.api.DataApi;
import com.hzjytech.operation.utils.UserUtils;
import com.hzjytech.operation.widgets.TitleBar;
import com.hzjytech.operation.widgets.XYMarkerView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;

/**
 * Created by hehongcan on 2017/7/17.
 */
public class DrinkBuyRatioActivity extends BaseActivity {
    @BindView(R.id.titleBar)
    TitleBar mTitleBar;
    @BindView(R.id.chart)
    BarChart mChart;
    @BindView(R.id.iv_bg)
    ImageView mIvBg;

    @Override
    protected int getResId() {
        return R.layout.activity_drink_buy_ratio;
    }

    @Override
    protected void initView() {
        initTitle();
        initChart();
        initData();
    }

    private void initData() {
        User userInfo = UserUtils.getUserInfo();
        Intent intent = getIntent();
        ArrayList<Integer> machinesId = intent.getIntegerArrayListExtra("machinesId");
        long startTime = intent.getLongExtra("startTime", 0);
        long endTime = intent.getLongExtra("endTime", 0);
        Observable<Object> observable = DataApi.getWeeklySaleData(userInfo.getToken(),
                startTime,
                endTime,
                0,
                machinesId);
        JijiaHttpSubscriber subscriber = JijiaHttpSubscriber.buildSubscriber(this)
                .setOnNextListener(new SubscriberOnNextListener<Object>() {
                    @Override
                    public void onNext(Object o) {
                        String s = new Gson().toJson(o);
                        Log.e("s", s);
                        try {
                            JSONArray jsonArray = new JSONArray(s);
                            JSONArray array = (JSONArray) jsonArray.get(1);
                            ArrayList<DrinkInfo> list = new Gson().fromJson(array.toString(),
                                    new TypeToken<ArrayList<DrinkInfo>>() {}.getType());
                            setChartData(list);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                })
                .setProgressDialog(mProgressDlg)
                .build();
        observable.subscribe(subscriber);
    }

    private void setChartData(final ArrayList<DrinkInfo> list) {
        if(list==null||list.size()==0){
            mIvBg.setVisibility(View.VISIBLE);
            return;
        }
        int totle = 0;
        for (DrinkInfo drinkInfo : list) {
            totle += drinkInfo.getNum();
        }
        if(totle==0){
            mIvBg.setVisibility(View.VISIBLE);
            return;
        }
        for (DrinkInfo drinkInfo : list) {
            drinkInfo.setRatio(100 * (drinkInfo.getNum() / (float) totle));
        }
        Collections.sort(list, new Comparator<DrinkInfo>() {
            @Override
            public int compare(DrinkInfo o1, DrinkInfo o2) {
                return new Float(o2.getRatio()).compareTo(new Float(o1.getRatio()));
            }
        });
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        for (int i = 0; i < list.size(); i++) {
            yVals1.add(new BarEntry(i,
                    list.get(i)
                            .getRatio()));
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
            set1 = new BarDataSet(yVals1, "购买比例");
            set1.setColors(MATERIAL_COLORS);
            set1.setDrawValues(true);
            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);
            BarData data = new BarData(dataSets);
            data.setValueFormatter(new IValueFormatter() {
                @Override
                public String getFormattedValue(
                        float value,
                        Entry entry,
                        int dataSetIndex,
                        ViewPortHandler viewPortHandler) {
                    return String.format("%.2f", value) + "%";
                }
            });
            data.setDrawValues(false);
            //处理y轴数据
            mChart.getAxisLeft()
                    .setValueFormatter(new IAxisValueFormatter() {
                        @Override
                        public String getFormattedValue(float value, AxisBase axis) {
                            return (int) value + "%";
                        }
                    });
            //处理x轴数据
            mChart.getXAxis()
                    .setValueFormatter(new IAxisValueFormatter() {
                        @Override
                        public String getFormattedValue(float value, AxisBase axis) {
                            return list.get((int) value)
                                    .getName();
                        }
                    });
            mChart.setData(data);
            mChart.setFitBars(true);
        }

        mChart.invalidate();
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

    private void initTitle() {
        mTitleBar.setTitleColor(Color.WHITE);
        mTitleBar.setTitleBold(true);
        mTitleBar.setTitle(R.string.drink_ratio);
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

    public static final int[] MATERIAL_COLORS = {rgb("#00acee"), rgb("#0070ee"), rgb("#676de9"),
            rgb(
            "#3ed7f8"), rgb("#e7c165"), rgb("#e87142"), rgb("#0cc5d6")};

    /**
     * Converts the given hex-color-string to rgb.
     *
     * @param hex
     * @return
     */
    public static int rgb(String hex) {
        int color = (int) Long.parseLong(hex.replace("#", ""), 16);
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = (color >> 0) & 0xFF;
        return Color.rgb(r, g, b);
    }
}
