package com.hzjytech.operation.module.data;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.MPPointF;
import com.google.gson.Gson;
import com.hzjytech.operation.R;
import com.hzjytech.operation.base.BaseActivity;
import com.hzjytech.operation.entity.SugarInfo;
import com.hzjytech.operation.entity.User;
import com.hzjytech.operation.http.JijiaHttpSubscriber;
import com.hzjytech.operation.http.SubscriberOnNextListener;
import com.hzjytech.operation.http.api.DataApi;
import com.hzjytech.operation.utils.MyMath;
import com.hzjytech.operation.utils.UserUtils;
import com.hzjytech.operation.widgets.TitleBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;

/**
 * Created by hehongcan on 2017/7/17.
 */
public class SugarRatioActivity extends BaseActivity {
    @BindView(R.id.titleBar)
    TitleBar mTitleBar;
    @BindView(R.id.chart1)
    PieChart mChart;
    @BindView(R.id.iv_bg)
    ImageView mIvBg;

    @Override
    protected int getResId() {
        return R.layout.activity_sugar_ratio;
    }

    @Override
    protected void initView() {
        initTitle();
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
                            JSONObject object = (JSONObject) jsonArray.get(3);
                            SugarInfo info = new Gson().fromJson(object.toString(),
                                    SugarInfo.class);
                            setChartData(info);
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
     * 设置
     *
     * @param info
     */
    private void setChartData(SugarInfo info) {
        if(info==null){
            mIvBg.setVisibility(View.VISIBLE);
            return;
        }
        List<SugarInfo.SugarBean.DetailBean> detail = info.getSugar()
                .getDetail();
        float totle = info.getSugarFree();
        for (SugarInfo.SugarBean.DetailBean detailBean : detail) {
            totle += detailBean.getNum();
        }
        if (totle==0 ) {
          mIvBg.setVisibility(View.VISIBLE);
            return;
        }
        initChart();
        //1、2、3份糖


        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();


        // NOTE: The order of the entries when being added to the entries array determines their
        // position around the center of
        // the chart.
        //筛选颜色
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(ContextCompat.getColor(this, R.color.normal_green));
        colors.add(ContextCompat.getColor(this, R.color.colorPrimary));
        colors.add(ContextCompat.getColor(this, R.color.color_red));
        colors.add(ContextCompat.getColor(this, R.color.orange));
        ArrayList<Integer> usedColors = new ArrayList<Integer>();
        entries.add(new PieEntry((info.getSugarFree() / totle), "不加糖"));
        for (int i = 0; i < detail.size(); i++) {
            entries.add(new PieEntry((detail.get(i)
                    .getNum() / totle),
                    detail.get(i)
                            .getSugarContent()));
        }
        for (int i = 0; i < entries.size(); i++) {
            if (entries.get(i)
                    .getValue() > 0.0001) {
                usedColors.add(colors.get(i));
            }
        }
        Iterator<PieEntry> iterator = entries.iterator();
        while (iterator.hasNext()) {
            PieEntry entry = iterator.next();
            if (Float.valueOf(MyMath.getIntOrDouble(entry.getValue())) < 0.0001) {
                iterator.remove();
            }
        }
        PieDataSet dataSet = new PieDataSet(entries, "");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);
        // add a lot of colors


        dataSet.setColors(usedColors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        // data.setValueTypeface(mTfLight);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
    }

    private void initChart() {
        mChart.setUsePercentValues(true);
        mChart.getDescription()
                .setEnabled(false);
        mChart.setExtraOffsets(5, 10, 5, 5);

        mChart.setDragDecelerationFrictionCoef(0.95f);

        // mChart.setCenterTextTypeface(mTfLight);
        // mChart.setCenterText(generateCenterSpannableText());

        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(Color.WHITE);

        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);

        mChart.setHoleRadius(58f);
        mChart.setTransparentCircleRadius(61f);

        mChart.setDrawCenterText(true);

        mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(true);

        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener

        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);


        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // entry label styling
        mChart.setEntryLabelColor(Color.WHITE);
        // mChart.setEntryLabelTypeface(mTfRegular);
        mChart.setEntryLabelTextSize(12f);
    }

    private void initTitle() {
        mTitleBar.setTitleColor(Color.WHITE);
        mTitleBar.setTitleBold(true);
        mTitleBar.setTitle(R.string.sugar_ratio);
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
}
