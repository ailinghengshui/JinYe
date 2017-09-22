package com.hzjytech.operation.module.data;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.icu.text.DecimalFormat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hzjytech.operation.R;
import com.hzjytech.operation.base.BaseFragment;
import com.hzjytech.operation.constants.Constants;
import com.hzjytech.operation.entity.CurrentDataInfo;
import com.hzjytech.operation.entity.User;

import com.hzjytech.operation.http.JijiaHttpSubscriber;
import com.hzjytech.operation.http.SubscriberOnNextListener;
import com.hzjytech.operation.http.api.DataApi;
import com.hzjytech.operation.utils.TimeUtil;
import com.hzjytech.operation.utils.UserUtils;
import com.hzjytech.operation.widgets.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hehongcan on 2017/4/7.
 */
public class DataFragment extends BaseFragment {
    @BindView(R.id.titleBar)
    TitleBar titleBar;
    @BindView(R.id.tv_data_daily_totle)
    TextView tvDataDailyTotle;
    @BindView(R.id.tv_data_daily_order)
    TextView tvDataDailyOrder;
    @BindView(R.id.tv_data_daily_cups)
    TextView tvDataDailyCups;
    @BindView(R.id.tv_data_daily_machine_count)
    TextView tvDataDailyMachineCount;
    @BindView(R.id.tv_error_count)
    TextView mTvErrorCount;
    private CurrentDataInfo data;
    private java.text.DecimalFormat df;

    @Override
    protected int getResId() {
        return R.layout.fragment_data;
    }

    @Override
    protected void initView() {
        initTitle();
        initData();
    }

    @Override
    public void onResume() {
        initData();
        super.onResume();
    }

    /**
     * 初始化标题栏
     */
    private void initTitle() {
        titleBar.setTitle(R.string.data_statistics);
        titleBar.setTitleBold(true);
        titleBar.setTitleColor(Color.WHITE);
    }

    /**
     * 初始化数据界面数据
     */
    private void initData() {
        User userInfo = UserUtils.getUserInfo();
        Observable<CurrentDataInfo> observable = DataApi.getCurrentData(userInfo.getToken(),
                TimeUtil.getTimesmorning(),
                TimeUtil.getTimeCurrent());
        JijiaHttpSubscriber subscriber = JijiaHttpSubscriber.buildSubscriber(getActivity())
                .setOnNextListener(new SubscriberOnNextListener<CurrentDataInfo>() {
                    @Override
                    public void onNext(CurrentDataInfo data) {
                        parseResult(data);
                    }
                })
                .setProgressDialog(mProgressDlg)
                .build();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 解析数据
     *
     * @param data
     */
    private void parseResult(CurrentDataInfo data) {
        this.data = data;
        java.text.DecimalFormat df = new java.text.DecimalFormat("0.00");
        tvDataDailyTotle.setText("¥" + df.format(data.getVolume()));
        tvDataDailyOrder.setText("订单总数" + "\n" + data.getOrderNum() + "单");
        tvDataDailyCups.setText("销售总杯数" + "\n" + data.getGoodNum() + "杯");
        tvDataDailyMachineCount.setText("咖啡机台数" + "\n" + data.getMachineNum() + "台");
        mTvErrorCount.setText( data.getErrorTime() + "次");
    }

    /**
     * 进入时间和机器选择界面，1代表销售统计表 2代表每日订单数
     *
     * @param view
     */
    @OnClick({R.id.ll_data_daily_sale_list, R.id.ll_data_sale_table, R.id.ll_daily_order, R.id
            .ll_daily_sale_count, R.id.ll_repeat_cost, R.id.ll_feed_overview, R.id
            .ll_material_waste, R.id.ll_today_error, R.id.ll_beakdown_counts,R.id.ll_data_daily_shut_down, R.id.ll_daily_order_source, R.id.ll_data_24hours, R
            .id.ll_week_data, R.id.ll_sugar_contain,R.id.ll_drink_buy_ratio,R.id.ll_user_comment})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.ll_data_daily_sale_list:
                Intent intent = new Intent(getActivity(), SaleListActivity.class);
                intent.putExtra("currentData", data);
                startActivity(intent);
                break;
            case R.id.ll_data_sale_table:
                Intent intent1 = new Intent(getActivity(), ChooseTimeAndMachinesActivity.class);
                intent1.putExtra("name", Constants.order_sale_table);
                startActivity(intent1);
                break;
            case R.id.ll_daily_order:
                Intent intent2 = new Intent(getActivity(), ChooseTimeAndMachinesActivity.class);
                intent2.putExtra("name", Constants.order_daily);
                startActivity(intent2);
                break;
            case R.id.ll_daily_sale_count:
                Intent intent3 = new Intent(getActivity(), ChooseTimeAndMachinesActivity.class);
                intent3.putExtra("name", Constants.orser_daily_money);
                startActivity(intent3);
                break;
            case R.id.ll_data_24hours:
                Intent intent4 = new Intent(getActivity(), ChooseTimeAndMachinesActivity.class);
                intent4.putExtra("name", Constants.order_tweenty_four_hours);
                startActivity(intent4);
                break;
            case R.id.ll_feed_overview:
                Intent intent5 = new Intent(getActivity(), ChooseTimeAndMachinesActivity.class);
                intent5.putExtra("name", Constants.feed_overview);
                startActivity(intent5);
                break;
            case R.id.ll_material_waste:
                Intent intent6 = new Intent(getActivity(), ChooseTimeAndMachinesActivity.class);
                intent6.putExtra("name", Constants.material_waste);
                startActivity(intent6);
                break;
            case R.id.ll_today_error:
                Intent intent7 = new Intent(getActivity(), TodayErrorActivity.class);
                startActivity(intent7);
                break;
            case R.id.ll_beakdown_counts:
                Intent intent8 = new Intent(getActivity(), ChooseTimeAndMachinesActivity.class);
                intent8.putExtra("name", Constants.error_count);
                startActivity(intent8);
                break;
            case R.id.ll_data_daily_shut_down:
                Intent intent9 = new Intent(getActivity(), TodayErrorActivity.class);
                startActivity(intent9);
                break;
            case R.id.ll_daily_order_source:
                Intent intent10 = new Intent(getActivity(), ChooseTimeAndMachinesActivity.class);
                intent10.putExtra("name", Constants.order_source);
                startActivity(intent10);
                break;
            case R.id.ll_repeat_cost:
                Intent intent11 = new Intent(getActivity(), ChooseTimeAndMachinesActivity.class);
                intent11.putExtra("name", Constants.order_repeat_buy);
                startActivity(intent11);
                break;
            case R.id.ll_week_data:
                Intent intent12 = new Intent(getActivity(), ChooseTimeAndMachinesActivity.class);
                intent12.putExtra("name", Constants.order_week_sale);
                startActivity(intent12);
                break;
            case R.id.ll_sugar_contain:
                Intent intent13 = new Intent(getActivity(), ChooseTimeAndMachinesActivity.class);
                intent13.putExtra("name", Constants.sugar_ratio);
                startActivity(intent13);
                break;
            case R.id.ll_drink_buy_ratio:
                Intent intent14 = new Intent(getActivity(), ChooseTimeAndMachinesActivity.class);
                intent14.putExtra("name", Constants.drink_buy_raio);
                startActivity(intent14);
                break;
            case R.id.ll_user_comment:
                Intent intent15 = new Intent(getActivity(), ChooseTimeAndMachinesActivity.class);
                intent15.putExtra("name", Constants.machine_comment);
                startActivity(intent15);
                break;
        }
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
