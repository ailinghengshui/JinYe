package com.hzjytech.operation.module.data;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.hzjytech.operation.R;
import com.hzjytech.operation.adapters.SaleTableAdapter;
import com.hzjytech.operation.base.BaseActivity;
import com.hzjytech.operation.constants.Constants;
import com.hzjytech.operation.entity.GroupInfo;
import com.hzjytech.operation.inter.OnItemDelClickListener;
import com.hzjytech.operation.inter.OnLongTimePickerListener;
import com.hzjytech.operation.utils.TimeUtil;
import com.hzjytech.operation.widgets.TitleBar;
import com.umeng.analytics.MobclickAgent;

import java.text.ParseException;
import java.util.ArrayList;

import javax.security.auth.login.LoginException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 销售统计表
 * Created by hehongcan on 2017/5/17.
 */
public class ChooseTimeAndMachinesActivity extends BaseActivity {
    private static final int REQUEST_MACHINE = 100;
    private static final int REQUEST_SINGLE_MACHINE = 101;
    @BindView(R.id.titleBar)
    TitleBar titleBar;
    @BindView(R.id.rv_sale_table)
    RecyclerView rvSaleTable;
    @BindView(R.id.btn_check)
    Button btnCheck;
    private ArrayList<GroupInfo.SubMachinesBean> machineList;
    private SaleTableAdapter adapter;
    private long startTime;
    private long endTime;
    private int name;
    private static final String TAG = "ChooseTimeAndMachinesAc";

    @Override
    protected int getResId() {
        return R.layout.activity_sale_table;
    }

    @Override
    protected void initView() {
        initTitle();
        initRecyclerView();
        initListeners();
    }

    private void initTitle() {
        Intent intent = getIntent();
        name = intent.getIntExtra("name", -1);
        titleBar.setTitleBold(true);
        titleBar.setTitleColor(Color.WHITE);
        switch (name){
            case Constants.order_sale_table:
                titleBar.setTitle(R.string.sale_table);
                break;
            case Constants.order_daily:
                titleBar.setTitle(R.string.daily_sale);
                break;
            case Constants.orser_daily_money:
                titleBar.setTitle(R.string.daily_sale_amout);
                break;
            case Constants.order_tweenty_four_hours:
                titleBar.setTitle(R.string.twentyfour_sale);
                break;
            case Constants.feed_overview:
                titleBar.setTitle(R.string.feed_overview);
                break;
            case Constants.material_waste:
                titleBar.setTitle(R.string.waste_material);
                break;
            case Constants.error_count:
                titleBar.setTitle(R.string.error_list);
                break;
            case Constants.order_source:
                titleBar.setTitle(R.string.daily_order_source);
                break;
            case Constants.order_week_sale:
                titleBar.setTitle(R.string.order_week_sale);
                break;
            case Constants.order_repeat_buy:
                titleBar.setTitle(R.string.repeat_buy);
                break;
            case Constants.sugar_ratio:
                titleBar.setTitle(R.string.sugar_ratio);
                break;
            case Constants.drink_buy_raio:
                titleBar.setTitle(R.string.drink_ratio);
                break;
            case Constants.machine_comment:
                titleBar.setTitle(R.string.machine_comment);
        }

        titleBar.setLeftImageResource(R.drawable.icon_left);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initListeners() {
        adapter.setOnSelectMoreMachineClickListener(new SaleTableAdapter.OnSelectMoreMachineClickListener() {
            @Override
            public void onClick() {
                if(name==Constants.error_count){
                    Intent intent = new Intent(ChooseTimeAndMachinesActivity.this, MachineSelectActivity.class);
                    intent.putExtra("name",name);
                    startActivityForResult(intent, REQUEST_SINGLE_MACHINE);
                }else{
                    Intent intent = new Intent(ChooseTimeAndMachinesActivity.this, MachineSelectActivity.class);
                    intent.putExtra("name",name);
                    startActivityForResult(intent, REQUEST_MACHINE);
                }

            }
        });
        adapter.setOnCLearAllMachineClickListener(new SaleTableAdapter.OnClearAllMachineClickListener() {
            @Override
            public void onClear() {
                adapter.setData(null);
                machineList = null;
                isCheckEnable();
            }
        });
        adapter.setOnLongTimePickerListener(new OnLongTimePickerListener() {
            @Override
            public void getStartTime(long startTime) {
                ChooseTimeAndMachinesActivity.this.startTime = startTime;
                isCheckEnable();
            }

            @Override
            public void getEndTime(long endTime) {
                ChooseTimeAndMachinesActivity.this.endTime = endTime;
                isCheckEnable();
            }
        });
        adapter.setOnItemDelClickListener(new OnItemDelClickListener(){
            @Override
            public void onDelClick(int position){
                machineList .remove(position-2);
                adapter.notifyItemRemoved(position);
                adapter.notifyItemRangeChanged(position, machineList.size()+2);
                isCheckEnable();
            }

        });
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvSaleTable.setLayoutManager(linearLayoutManager);
        adapter = new SaleTableAdapter(this, null);
        rvSaleTable.setAdapter(adapter);
        if(name==Constants.error_count){
            adapter.setCanOnlyChoooseOne(true);
        }
        if(name==Constants.order_week_sale){
            adapter.setTimeVisiable(false);
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_MACHINE && resultCode == MachineSelectActivity.SELECTED_MACHIES && data != null) {
            if(machineList==null){
                machineList = (ArrayList<GroupInfo.SubMachinesBean>) data.getSerializableExtra("list");
            }else {
                ArrayList<Integer> keyList = new ArrayList<>();
                for (GroupInfo.SubMachinesBean subMachinesBean : machineList) {
                    keyList.add(subMachinesBean.getId());
                }
                ArrayList<GroupInfo.SubMachinesBean> list = (ArrayList<GroupInfo
                        .SubMachinesBean>) data.getSerializableExtra(
                        "list");
                for (GroupInfo.SubMachinesBean subMachinesBean : list) {
                    if(!keyList.contains(subMachinesBean.getId())){
                        machineList.add(subMachinesBean);
                    }
                }
            }

            adapter.setData(machineList);
            isCheckEnable();

        }else if(requestCode == REQUEST_SINGLE_MACHINE && resultCode == MachineSelectActivity.SELECTED_MACHIES && data != null){
            machineList = (ArrayList<GroupInfo.SubMachinesBean>) data.getSerializableExtra("list");
            adapter.setData(machineList);
            isCheckEnable();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //判断输入时间是否合法
    public boolean isTimeCorrect(long startTime1, long endTime1) {
        if (startTime1 == 0 || endTime1 == 0 || startTime1 > endTime1 || endTime1 > System.currentTimeMillis()) {
            return false;
        }
        return true;
    }

    //是否可以点击
    public void isCheckEnable() {
          if(name==Constants.order_week_sale&&machineList != null && machineList.size() > 0){
              btnCheck.setEnabled(true);
          }else {
              if (isTimeCorrect(startTime, endTime) && machineList != null && machineList.size() > 0) {
                  btnCheck.setEnabled(true);
              } else {
                  btnCheck.setEnabled(false);
              }

          }

    }



    @OnClick(R.id.btn_check)
    public void onClick() {
        Intent intent = new Intent();
        intent.putExtra("startTime",startTime);
        intent.putExtra("endTime",endTime);
        ArrayList<Integer> ids = new ArrayList<>();
        for (GroupInfo.SubMachinesBean subMachinesBean : machineList) {
            ids.add(subMachinesBean.getId());
        }
        intent.putIntegerArrayListExtra("machinesId",ids);
        switch (name){
            case Constants.order_sale_table:
                intent.setClass(this, DetailSaleTableActivity.class);
                break;
            case Constants.order_daily:
               intent.setClass(this,DailyOrdersActivity.class);
                break;
            case Constants.orser_daily_money:
                intent.setClass(this, DailySaleAmountActivity.class);
                break;
            case Constants.order_tweenty_four_hours:
                intent.setClass(this, TwentyFourSaleActivity.class);
                break;
            case Constants.feed_overview:
                intent.setClass(this,FeedOverViewActivity.class);
                break;
            case Constants.material_waste:
                intent.setClass(this,WasteMaterialActivity.class);
                break;
            case Constants.error_count:
                intent.setClass(this,ErrorListActivity.class);
                break;
            case Constants.order_source:
                intent.setClass(this,DailyOrderSourceActivity.class);
                break;
            case Constants.order_week_sale:
                intent.setClass(this,WeeklySaleDataActivity.class);
                break;
            case Constants.order_repeat_buy:
                intent.setClass(this,UserRepeatActivity.class);
                break;
            case Constants.sugar_ratio:
                intent.setClass(this,SugarRatioActivity.class);
                break;
            case Constants.drink_buy_raio:
                intent.setClass(this,DrinkBuyRatioActivity.class);
                break;
            case Constants.machine_comment:
                intent.setClass(this,UserCommentActivity.class);
        }
        startActivity(intent);

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
