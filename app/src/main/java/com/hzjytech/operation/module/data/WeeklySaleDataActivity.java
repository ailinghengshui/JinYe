package com.hzjytech.operation.module.data;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointF;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hzjytech.operation.R;
import com.hzjytech.operation.base.BaseActivity;
import com.hzjytech.operation.entity.User;
import com.hzjytech.operation.entity.WeeklySaleInfo;
import com.hzjytech.operation.http.JijiaHttpSubscriber;
import com.hzjytech.operation.http.SubscriberOnNextListener;
import com.hzjytech.operation.http.api.DataApi;
import com.hzjytech.operation.utils.TimeUtil;
import com.hzjytech.operation.utils.UserUtils;
import com.hzjytech.operation.widgets.TitleBar;
import com.hzjytech.operation.widgets.XYMarkerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;

/**
 * Created by hehongcan on 2017/7/12.
 */
public class WeeklySaleDataActivity extends BaseActivity  {
    @BindView(R.id.titleBar)
    TitleBar mTitleBar;
    @BindView(R.id.chart)
    BarChart mChart;
    @BindView(R.id.iv_bg)
    ImageView mIvBg;

    @Override
    protected int getResId() {
        return R.layout.activity_weekly_sale_data;
    }

    @Override
    protected void initView() {
        initTitle();
        initData();
    }

    private void initData() {
        User userInfo = UserUtils.getUserInfo();
        Intent intent = getIntent();
        long startTime = 0;
        long endTime = 0;
        try {
            startTime = TimeUtil.stringToLong(TimeUtil.lastWeek(),TimeUtil.DATE_FORMAT_LONG);
            endTime = TimeUtil.stringToLong(TimeUtil.getTimesmorning(),TimeUtil.DATE_FORMAT_LONG);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ArrayList<Integer> machinesId = intent.getIntegerArrayListExtra("machinesId");
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
                            JSONObject object = (JSONObject) jsonArray.get(0);
                            JSONArray array = (JSONArray) object.get("lastWeekVolume");
                            ArrayList<WeeklySaleInfo> list = new Gson().fromJson(array.toString(),
                                    new TypeToken<ArrayList<WeeklySaleInfo>>() {}.getType());
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

    /**
     * 设置图标数据
     *
     * @param list
     */
    private void setChartData(final ArrayList<WeeklySaleInfo> list) {
        if(list==null||list.size()==0){
            mIvBg.setVisibility(View.VISIBLE);
            return;
        }
        initChart();
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> yVals2 = new ArrayList<BarEntry>();


        for (int i = 0; i < list.size(); i++) {
            yVals1.add(new BarEntry(i,
                    (float) list.get(i).getSalesSum()));
            int val = list.get(i)
                    .getHotSalesNum();
            int val2 = list.get(i)
                    .getIceSalesNum();
            yVals2.add(new BarEntry( i,new float[]{val,val2}));
        }
        yVals1.add(new BarEntry(list.size(),0));
        yVals2.add(new BarEntry(list.size(),0));
        BarDataSet set1, set2;

        if (mChart.getData() != null && mChart.getData()
                .getDataSetCount() > 0) {

            set1 = (BarDataSet) mChart.getData()
                    .getDataSetByIndex(0);
            set2 = (BarDataSet) mChart.getData()
                    .getDataSetByIndex(1);

            set1.setValues(yVals1);
            set2.setValues(yVals2);
            mChart.getData()
                    .notifyDataChanged();
            mChart.notifyDataSetChanged();

        } else {
            // create 4 DataSets
            set1 = new BarDataSet(yVals1, "销售总额");
            set1.setColor(getResources().getColor(R.color.colorPrimary));
            set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            set1.setDrawValues(false);
            set2 = new BarDataSet(yVals2, "");
            set2.setColors(getColors());
            set2.setDrawValues(false);
            set2.setStackLabels(new String[]{"热饮销售量","冷饮销售量"});
            set2.setAxisDependency(YAxis.AxisDependency.RIGHT);
            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);
            dataSets.add(set2);
            BarData data = new BarData(dataSets);
          // data.setValueFormatter(new LargeValueFormatter());
            YAxis leftAxis = mChart.getAxisLeft();
            leftAxis.setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    //DecimalFormat df = new DecimalFormat("0.00");
                    return  (value) + "元";
                }
            });
            leftAxis.setDrawGridLines(false);
            leftAxis.setSpaceTop(35f);
            leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
            YAxis axisRight = mChart.getAxisRight();
            axisRight.setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    return (int) value + "单";
                }
            });
            axisRight.setDrawGridLines(true);
            axisRight.setSpaceTop(35f);
            axisRight.setAxisMinimum(0f);
            axisRight.setGranularityEnabled(true);
            axisRight.setGranularity(1f);
            XAxis xAxis = mChart.getXAxis();
            xAxis.setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    Log.e("value", value + "");
                    try {
                        return list.get((int) value)
                                .getVmName();
                    } catch (Exception e) {
                        return String.valueOf((int) value);
                    }


                }
            });
            //xAxis.setGranularityEnabled(true);    //粒度
            //xAxis.setGranularity(1f);
            mChart.setData(data);
        }
        float groupSpace = 0.08f;
        float barSpace = 0.06f; // x4 DataSet
        float barWidth = 0.4f; // x4
        // specify the width each bar should have
        mChart.getBarData()
                .setBarWidth(barWidth);

        // restrict the x-axis range
        mChart.getXAxis()
                .setAxisMinimum(0);

        // barData.getGroupWith(...) is a helper that calculates the width each group needs based
        // on the provided parameters
        //  mChart.getXAxis().setAxisMaximum(0 + mChart.getBarData().getGroupWidth(groupSpace,
        // barSpace) * list.size());
        mChart.groupBars(0, groupSpace, barSpace);
        XYMarkerView mv = new XYMarkerView(this);
        mv.setChartView(mChart); // For bounds control
       // mChart.setMarker(mv); // Set the marker to the chart
        mChart.setFitBars(true);
        mChart.invalidate();

    }

    /**
     * 初始化图标有关参数
     */
    private void initChart() {
        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);
        Description description = new Description();
        description.setText("");
        mChart.setDescription(description);
        mChart.setDrawBarShadow(false);
        mChart.setDrawGridBackground(false);
        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setFormSize(8f);
        l.setFormToTextSpace(4f);
        l.setXEntrySpace(6f);
        XAxis xAxis = mChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setCenterAxisLabels(true);
        YAxis axisLeft = mChart.getAxisLeft();
        axisLeft.setGranularity(1f);
        mChart.getAxisRight()
                .setEnabled(true);
        mChart.animateXY(3000, 3000);
    }

    private void initTitle() {
        mTitleBar.setTitleColor(Color.WHITE);
        mTitleBar.setTitleBold(true);
        mTitleBar.setTitle(R.string.weekly_sale_data);
        mTitleBar.setLeftImageResource(R.drawable.icon_left);
        mTitleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private int[] getColors() {

        int stacksize = 2;

        // have as many colors as stack-values per entry
        int[] colors = new int[stacksize];
        colors[0] = ContextCompat.getColor(this, R.color.color_red);
        colors[1] = ContextCompat.getColor(this, R.color.material_orange);
        return colors;
    }
}
