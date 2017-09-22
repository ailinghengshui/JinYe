package com.hzjytech.operation.module.scan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.hzjytech.operation.R;
import com.hzjytech.operation.base.BaseActivity;
import com.hzjytech.operation.entity.DensityInfo;
import com.hzjytech.operation.http.JijiaHttpSubscriber;
import com.hzjytech.operation.http.SubscriberOnErrorListener;
import com.hzjytech.operation.http.SubscriberOnNextListener;
import com.hzjytech.operation.http.api.MachinesApi;
import com.hzjytech.operation.utils.AppUtil;
import com.hzjytech.operation.utils.UserUtils;
import com.hzjytech.operation.widgets.CellLinearLayout;
import com.hzjytech.operation.widgets.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;

/**
 * Created by hehongcan on 2017/8/22.
 */

public class DensityActivity extends BaseActivity {
    @BindView(R.id.titleBar)
    TitleBar mTitleBar;
    @BindView(R.id.bt_density_confirm)
    Button mBtDensityConfirm;
    @BindView(R.id.ll_dentity_container)
    LinearLayout mLlDentityContainer;
    //去皮重量
    private CellLinearLayout mFirst_cell_tare;
    private CellLinearLayout mSecond_cell_tare;
    private CellLinearLayout mThird_cell_tare;
    //咖啡浓度
    private CellLinearLayout mFirst_cell_density;
    private CellLinearLayout mSecond_cell_density;
    private CellLinearLayout mThird_cell_density;
    //冲泡时间
    private CellLinearLayout mFirst_cell_tea_shape;
    private CellLinearLayout mSecond_cell_tea_shape;
    private CellLinearLayout mThird_cell_tea_shape;
    //研磨度
    private CellLinearLayout mFirst_cell_grinding;
    private CellLinearLayout mSecond_cell_grinding;
    private CellLinearLayout mThird_cell_grinding;
    //粉重
    private CellLinearLayout mFirst_cell_powder;
    private CellLinearLayout mSecond_cell_powder;
    private CellLinearLayout mThird_cell_powder;
    private int mMachineId;

    @Override
    protected int getResId() {
        return R.layout.activity_menu_density;
    }

    @Override
    protected void initView() {
        findView();
        initTitle();
        initData();

    }

    private void initData() {
        Intent intent = getIntent();
        mMachineId = intent.getIntExtra("machineId", -1);
    }

    private void initTitle() {
        mTitleBar.setLeftImageResource(R.drawable.icon_left);
        mTitleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitleBar.setTitle(R.string.density);
    }

    /**
     * 寻找控件，相同布局内重复使用尽量写死，不要抽取，这样butterknife就可以直接获取了
     * =
     */
    private void findView() {
        LinearLayout child0 = (LinearLayout) mLlDentityContainer.getChildAt(1);
        LinearLayout child1 = (LinearLayout) mLlDentityContainer.getChildAt(3);
        LinearLayout child2 = (LinearLayout) mLlDentityContainer.getChildAt(5);
        //去皮重量
        mFirst_cell_tare = (CellLinearLayout) child0.findViewById(R.id.cell_et_tare_deduction);
        mSecond_cell_tare = (CellLinearLayout) child1.findViewById(R.id.cell_et_tare_deduction);
        mThird_cell_tare = (CellLinearLayout) child2.findViewById(R.id.cell_et_tare_deduction);
        //咖啡浓度
        mFirst_cell_density = (CellLinearLayout) child0.findViewById(R.id.cell_et_density);
        mSecond_cell_density = (CellLinearLayout) child0.findViewById(R.id.cell_et_density);
        mThird_cell_density = (CellLinearLayout) child0.findViewById(R.id.cell_et_density);
        //冲泡时间
        mFirst_cell_tea_shape = (CellLinearLayout) child0.findViewById(R.id.cell_et_tea_shape);
        mSecond_cell_tea_shape = (CellLinearLayout) child0.findViewById(R.id.cell_et_tea_shape);
        mThird_cell_tea_shape = (CellLinearLayout) child0.findViewById(R.id.cell_et_tea_shape);
        //研磨度
        mFirst_cell_grinding = (CellLinearLayout) child0.findViewById(R.id.cell_et_grinding);
        mSecond_cell_grinding = (CellLinearLayout) child0.findViewById(R.id.cell_et_grinding);
        mThird_cell_grinding = (CellLinearLayout) child0.findViewById(R.id.cell_et_grinding);
        //粉重
        mFirst_cell_powder = (CellLinearLayout) child0.findViewById(R.id.cell_et_powder);
        mSecond_cell_powder = (CellLinearLayout) child0.findViewById(R.id.cell_et_powder);
        mThird_cell_powder = (CellLinearLayout) child0.findViewById(R.id.cell_et_powder);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.bt_density_confirm)
    public void onClick() {

        if (!isAllInputCorrect() || AppUtil.isFastClick()) {
            return;
        }
        submitReport();
    }

    /**
     * 提交报告
     */
    private void submitReport() {
        List<DensityInfo> infos = new ArrayList<>();
        infos.add(new DensityInfo(1,
                mFirst_cell_tare.getRightValue(),
                mFirst_cell_tea_shape.getRightValue(),
                mFirst_cell_density.getRightValue(),
                mFirst_cell_grinding.getRightValue(),
                mFirst_cell_powder.getRightValue()));  
        infos.add(new DensityInfo(2,
                mSecond_cell_tare.getRightValue(),
                mFirst_cell_tea_shape.getRightValue(),
                mFirst_cell_density.getRightValue(),
                mFirst_cell_grinding.getRightValue(),
                mFirst_cell_powder.getRightValue()));
        infos.add(new DensityInfo(3,
                mThird_cell_tare.getRightValue(),
                mThird_cell_tea_shape.getRightValue(),
                mThird_cell_density.getRightValue(),
                mThird_cell_grinding.getRightValue(),
                mThird_cell_powder.getRightValue()));
        Gson gson = new Gson();
        String json = gson.toJson(infos);
        Observable<Object> observable = MachinesApi.addDensityInfo(UserUtils.getUserInfo()
                .getToken(), mMachineId,json);
        JijiaHttpSubscriber subscriber = JijiaHttpSubscriber.buildSubscriber(this)
                .setOnNextListener(new SubscriberOnNextListener() {
                    @Override
                    public void onNext(Object o) {
                        showTip(R.string.commit_success);
                        finish();
                    }
                })
                .setProgressDialog(mProgressDlg)
                .build();
        observable.subscribe(subscriber);
    }

    /**
     * 是否已经全部输入
     *
     * @return
     */
    private boolean isAllInputCorrect() {
        if (mFirst_cell_density.isEmpty() && mSecond_cell_density.isEmpty() && 
                mThird_cell_density.isEmpty() && mFirst_cell_grinding.isEmpty() && 
                mSecond_cell_grinding.isEmpty() && mThird_cell_grinding.isEmpty() && 
                mFirst_cell_powder.isEmpty() && mSecond_cell_powder.isEmpty() && 
                mThird_cell_powder.isEmpty() && mFirst_cell_tare.isEmpty() && mSecond_cell_tare
                .isEmpty() && mThird_cell_tare.isEmpty() && mFirst_cell_tea_shape.isEmpty() && 
                mSecond_cell_tea_shape.isEmpty() && mThird_cell_tea_shape.isEmpty()) {
            showTip(R.string.please_input_at_least_one);
            return false;
        }
        return true;
    }
}
