package com.hzjytech.operation.module.scan;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hzjytech.operation.R;
import com.hzjytech.operation.adapters.RecyclerViewAdapter;
import com.hzjytech.operation.adapters.scan.DosingAdapter;
import com.hzjytech.operation.base.BaseActivity;
import com.hzjytech.operation.entity.MaterialInfo;
import com.hzjytech.operation.entity.MaterialInfo.InventoriesBean;
import com.hzjytech.operation.entity.StickyHeadEntity;
import com.hzjytech.operation.http.JijiaHttpSubscriber;
import com.hzjytech.operation.http.SubscriberOnNextListener;
import com.hzjytech.operation.http.api.MachinesApi;
import com.hzjytech.operation.inter.OnTwoButtonClickListener;
import com.hzjytech.operation.utils.UserUtils;
import com.hzjytech.operation.widgets.TitleBar;
import com.hzjytech.operation.widgets.stickyitemdecoration.StickyHeadContainer;
import com.hzjytech.operation.widgets.stickyitemdecoration.StickyItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
/**
 * Created by hehongcan on 2017/8/24.
 */

public class DosingActivity extends BaseActivity implements OnTwoButtonClickListener {
    public static final String ADD_MATERIAL = "addMaterial";
    public static final String CALIBRATION_MATERIAL = "calibrationMaterial";
    @BindView(R.id.titleBar)
    TitleBar mTitleBar;
    @BindView(R.id.rv_dosing)
    RecyclerView mRvDosing;
    @BindView(R.id.tv_head)
    TextView mTvHead;
    @BindView(R.id.shc_dosing)
    StickyHeadContainer mShcDosing;
    private DosingAdapter mDosingAdapter;
    private int mMachineId;
    private String mToken;
    private ArrayList<InventoriesBean> mInventories;

    @Override
    protected int getResId() {
        return R.layout.activity_dosing;
    }

    @Override
    protected void initView() {
       initTitle();
        initRecycler();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        mMachineId = intent.getIntExtra("machineId", -1);
        mToken = UserUtils.getUserInfo()
                .getToken();
        Observable<MaterialInfo> observable = MachinesApi.getInventoriest(mToken, mMachineId);
        JijiaHttpSubscriber subscriber = JijiaHttpSubscriber.buildSubscriber(this)
                .setOnNextListener(new SubscriberOnNextListener<MaterialInfo>() {
                    @Override
                    public void onNext(MaterialInfo info) {
                        if(info==null||info.getInventories()==null){
                            return;
                        }
                        mInventories = (ArrayList<InventoriesBean>) info.getInventories();
                        handleResult(info.getInventories());
                    }
                })
                .setProgressDialog(mProgressDlg)
                .build();
        observable.subscribe(subscriber);
    }

    /**
     * 处理结果生成粘连控件所需list
     * @param inventories
     */
    private void handleResult(List<InventoriesBean> inventories) {
        ArrayList<StickyHeadEntity<InventoriesBean>> list = new ArrayList<>();
        list.add(new StickyHeadEntity<InventoriesBean>(new InventoriesBean("料盒库存"),RecyclerViewAdapter.TYPE_STICKY_HEAD,"料盒库存"));
        for (InventoriesBean inventory : inventories) {
            if(!inventory.getMaterial_name().equals("杯子")&&!inventory.getMaterial_name().equals("水")){
                list.add(new StickyHeadEntity<InventoriesBean>(inventory,RecyclerViewAdapter.TYPE_DATA,""));
            }
        }
        list.add(new StickyHeadEntity<InventoriesBean>(new InventoriesBean("非料盒库存"),RecyclerViewAdapter.TYPE_STICKY_HEAD,"非料盒库存"));
        for (InventoriesBean inventory : inventories) {
            if(inventory.getMaterial_name().equals("杯子")||inventory.getMaterial_name().equals("水")){
                list.add(new StickyHeadEntity<InventoriesBean>(inventory,RecyclerViewAdapter.TYPE_DATA,""));
            }
        }
        list.add(new StickyHeadEntity<InventoriesBean>(null,RecyclerViewAdapter.TYPE_FOOTER,""));
        mDosingAdapter.setData(list);
    }

    private void initRecycler() {
        mRvDosing.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,
                false));
        mDosingAdapter = new DosingAdapter(this);
        // mRvSign.addItemDecoration(new SpaceItemDecoration(this));
        mShcDosing.setDataCallback(new StickyHeadContainer.DataCallback() {
            @Override
            public void onDataChange(int pos) {
                mTvHead.setText(mDosingAdapter.getData()
                        .get(pos).getStickyHeadName());
            }
        });
        mRvDosing.addItemDecoration(new StickyItemDecoration(mShcDosing,
                RecyclerViewAdapter.TYPE_STICKY_HEAD));
        mRvDosing.setAdapter(mDosingAdapter);
        mDosingAdapter.setOnTwoButtonClickListener(this);
    }

    private void initTitle() {
        mTitleBar.setTitle(R.string.dosing);
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

    @Override
    public void leftClick() {
        Intent intent = new Intent(this,AddDosingActivity.class);
        intent.putParcelableArrayListExtra("list",mInventories);
        intent.putExtra("type",ADD_MATERIAL);
        startActivity(intent);
    }

    @Override
    public void rightClick() {
        Intent intent = new Intent(this,AddDosingActivity.class);
        intent.putParcelableArrayListExtra("list",mInventories);
        intent.putExtra("type",CALIBRATION_MATERIAL);
        startActivity(intent);
    }
}
