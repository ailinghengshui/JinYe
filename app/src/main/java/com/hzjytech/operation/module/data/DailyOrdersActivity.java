package com.hzjytech.operation.module.data;

import android.content.Intent;
import android.graphics.Color;
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
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hzjytech.operation.R;
import com.hzjytech.operation.base.BaseActivity;
import com.hzjytech.operation.entity.User;
import com.hzjytech.operation.entity.VolumeBean;
import com.hzjytech.operation.http.JijiaHttpSubscriber;
import com.hzjytech.operation.http.SubscriberOnNextListener;
import com.hzjytech.operation.http.api.DataApi;
import com.hzjytech.operation.utils.TimeUtil;
import com.hzjytech.operation.utils.UserUtils;
import com.hzjytech.operation.widgets.TitleBar;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hehongcan on 2017/5/22.
 */
public class DailyOrdersActivity extends BaseActivity {
    private static final String TAG = "DailyOrdersActivity";
    @BindView(R.id.titleBar)
    TitleBar titleBar;
    @BindView(R.id.chart)
    LineChart mChart;
    @BindView(R.id.iv_bg)
    ImageView mIvBg;
    private ArrayList<VolumeBean> volumeList;

    @Override
    protected int getResId() {
        return R.layout.activity_daily_order;
    }

    @Override
    protected void initView() {
        initTitle();
        initChart();
        initData();

    }


    private void initTitle() {
        titleBar.setTitleColor(Color.WHITE);
        titleBar.setTitleBold(true);
        titleBar.setTitle(R.string.daily_order);
        titleBar.setLeftImageResource(R.drawable.icon_left);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initData() {
        volumeList = new ArrayList<>();
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
                        String s = new Gson().toJson(o);
                        try {
                            JSONArray jsonArray = new JSONArray(s);
                            JSONObject object = (JSONObject) jsonArray.get(0);
                            JSONArray array = object.getJSONArray("volume");
                            ArrayList<VolumeBean> list = new Gson().fromJson(array.toString(),
                                    new TypeToken<ArrayList<VolumeBean>>() {}.getType());
                            setChartData(list);
                        } catch (JSONException e) {


                        }

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
     * @param list 数据集合
     */
    private void setChartData(final ArrayList<VolumeBean> list) {
        if (list == null || list.size() == 0) {
            mIvBg.setVisibility(View.VISIBLE);
            return;
        }
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        for (int i = 0; i < list.size(); i++) {
            float val = list.get(i)
                    .getOrderNum();
            yVals1.add(new Entry(i, val));
        }

        ArrayList<Entry> yVals2 = new ArrayList<Entry>();

        for (int i = 0; i < list.size(); i++) {
            float val = list.get(i)
                    .getCupNum();
            yVals2.add(new Entry(i, val));
        }


        LineDataSet set1, set2;

        if (mChart.getData() != null && mChart.getData()
                .getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData()
                    .getDataSetByIndex(0);
            set2 = (LineDataSet) mChart.getData()
                    .getDataSetByIndex(1);
            set1.setValues(yVals1);
            set2.setValues(yVals2);
            mChart.getData()
                    .notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(yVals1, "每日订单数");
            set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            set1.setColor(ContextCompat.getColor(this, R.color.colorPrimary));
            set1.setLineWidth(2f);
            set1.setDrawCircles(false);
            set1.setDrawValues(false);
            //set1.setFillFormatter(new MyFillFormatter(0f));
            //set1.setDrawHorizontalHighlightIndicator(false);
            //set1.setVisible(false);
            //set1.setCircleHoleColor(Color.WHITE);
            // create a dataset and give it a type
            set2 = new LineDataSet(yVals2, "每日销售杯数");
            set2.setAxisDependency(YAxis.AxisDependency.LEFT);
            set2.setColor(ContextCompat.getColor(this, R.color.color_red));
            set2.setLineWidth(2f);
            set2.setDrawCircles(false);
            set2.setDrawValues(false);
            set2.setValueFormatter(new IValueFormatter() {
                @Override
                public String getFormattedValue(
                        float value,
                        Entry entry,
                        int dataSetIndex,
                        ViewPortHandler viewPortHandler) {
                    return (int) value + "单";
                }
            });
            //set2.setFillFormatter(new MyFillFormatter(900f));
            // create a data object with the datasets
            LineData data = new LineData(set1, set2);
            data.setValueTextColor(Color.WHITE);
            data.setValueTextSize(9f);
            mChart.getXAxis()
                    .setValueFormatter(new IAxisValueFormatter() {
                        @Override
                        public String getFormattedValue(float value, AxisBase axis) {
                            return TimeUtil.getShort4TimeFromShort2(list.get((int) value)
                                    .getTime());
                        }
                    });
            mChart.getAxisLeft()
                    .setValueFormatter(new IAxisValueFormatter() {
                        @Override
                        public String getFormattedValue(float value, AxisBase axis) {
                            return ((int) value) + "杯";
                        }
                    });
            mChart.getAxisRight()
                    .setValueFormatter(new IAxisValueFormatter() {
                        @Override
                        public String getFormattedValue(float value, AxisBase axis) {
                            return ((int) value) + "单";
                        }
                    });
            // set data
            mChart.setData(data);
        }

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
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTextColor(grey);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularityEnabled(true);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setTextColor(grey);
        rightAxis.setAxisMinimum(0);
        rightAxis.setDrawGridLines(false);
        rightAxis.setGranularityEnabled(true);
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
