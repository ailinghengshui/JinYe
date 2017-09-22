package com.hzjytech.operation.module.scan;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hzjytech.operation.R;
import com.hzjytech.operation.adapters.RecyclerViewAdapter;
import com.hzjytech.operation.adapters.scan.AddDosingAdapter;
import com.hzjytech.operation.adapters.scan.DosingAdapter;
import com.hzjytech.operation.base.BaseActivity;
import com.hzjytech.operation.entity.MaterialInfo;
import com.hzjytech.operation.entity.StickyHeadEntity;
import com.hzjytech.operation.inter.OnButtonClickListener;
import com.hzjytech.operation.widgets.TitleBar;
import com.hzjytech.operation.widgets.stickyitemdecoration.StickyHeadContainer;
import com.hzjytech.operation.widgets.stickyitemdecoration.StickyItemDecoration;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hehongcan on 2017/8/25.
 */

public class AddDosingActivity extends BaseActivity implements OnButtonClickListener {
    @BindView(R.id.titleBar)
    TitleBar mTitleBar;
    @BindView(R.id.rv_add_dosing)
    RecyclerView mRvAddDosing;
    @BindView(R.id.tv_head)
    TextView mTvHead;
    @BindView(R.id.shc_add_dosing)
    StickyHeadContainer mShcAddDosing;
    private AddDosingAdapter mAddDosingAdapter;
    private String mType;
    private ArrayList<MaterialInfo.InventoriesBean> mList;

    @Override
    protected int getResId() {
        return R.layout.activity_add_dosing;
    }

    @Override
    protected void initView() {
        initIntent();
        initTitle();
        initRecycler();
        initData();
    }

    private void initData() {
        //将值默认设置为0
        for (MaterialInfo.InventoriesBean inventoriesBean : mList) {
            inventoriesBean.setRemain(0);
        }
        ArrayList<StickyHeadEntity<MaterialInfo.InventoriesBean>> list = new ArrayList<>();
        list.add(new StickyHeadEntity<MaterialInfo.InventoriesBean>(new MaterialInfo.InventoriesBean("料盒库存"),RecyclerViewAdapter.TYPE_STICKY_HEAD,"料盒库存"));
        for (MaterialInfo.InventoriesBean inventory : mList) {
            if(!inventory.getMaterial_name().equals("杯子")&&!inventory.getMaterial_name().equals("水")){
                list.add(new StickyHeadEntity<MaterialInfo.InventoriesBean>(inventory,RecyclerViewAdapter.TYPE_DATA,""));
            }
        }
        list.add(new StickyHeadEntity<MaterialInfo.InventoriesBean>(new MaterialInfo.InventoriesBean("非料盒库存"),RecyclerViewAdapter.TYPE_STICKY_HEAD,"非料盒库存"));
        for (MaterialInfo.InventoriesBean inventory : mList) {
            if(inventory.getMaterial_name().equals("杯子")||inventory.getMaterial_name().equals("水")){
                list.add(new StickyHeadEntity<MaterialInfo.InventoriesBean>(inventory,RecyclerViewAdapter.TYPE_DATA,""));
            }
        }
        list.add(new StickyHeadEntity<MaterialInfo.InventoriesBean>(null,RecyclerViewAdapter.TYPE_FOOTER,""));
        mAddDosingAdapter.setData(list);
    }

    private void initIntent() {
        Intent intent = getIntent();
        mType = intent.getStringExtra("type");
        mList = intent.getParcelableArrayListExtra("list");
    }

    private void initRecycler() {
        mRvAddDosing.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,
                false));
        mAddDosingAdapter = new AddDosingAdapter(this);
        // mRvSign.addItemDecoration(new SpaceItemDecoration(this));
        mShcAddDosing.setDataCallback(new StickyHeadContainer.DataCallback() {
            @Override
            public void onDataChange(int pos) {
                mTvHead.setText(mAddDosingAdapter.getData()
                        .get(pos).getStickyHeadName());
            }
        });
        mRvAddDosing.addItemDecoration(new StickyItemDecoration(mShcAddDosing,RecyclerViewAdapter.TYPE_STICKY_HEAD));
        mRvAddDosing.setAdapter(mAddDosingAdapter);
        mAddDosingAdapter.setOnButtonClickListener(this);
    }

    private void initTitle() {
        switch (mType){
            case DosingActivity.ADD_MATERIAL:
                mTitleBar.setTitle(R.string.add_materail);
                break;
            case DosingActivity.CALIBRATION_MATERIAL:
                mTitleBar.setTitle(R.string.calibration_materail);
                break;

        }
        mTitleBar.setTitleColor(Color.WHITE);
        mTitleBar.setTitleBold(true);
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

    /**
     * 点击调教，根据type不同执行不同的操作
     */
    @Override
    public void click() {

    }
}
