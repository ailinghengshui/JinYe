package com.hzjytech.operation.module.scan;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.hzjytech.operation.R;
import com.hzjytech.operation.adapters.RecyclerViewAdapter;
import com.hzjytech.operation.adapters.scan.SignAdapter;
import com.hzjytech.operation.base.BaseActivity;
import com.hzjytech.operation.entity.CheckContent;
import com.hzjytech.operation.entity.SignInfo;
import com.hzjytech.operation.entity.StickyHeadEntity;
import com.hzjytech.operation.http.JijiaHttpSubscriber;
import com.hzjytech.operation.http.SubscriberOnErrorListener;
import com.hzjytech.operation.http.SubscriberOnNextListener;
import com.hzjytech.operation.http.api.MachinesApi;
import com.hzjytech.operation.inter.OnButtonClickListener;
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
 * Created by hehongcan on 2017/8/23.
 * 点击签到按钮，若失败提示签到失败，请重新签到，
 * 保留选择内容，签到按钮不变；若成功，
 * 提示签到成功，保留选择内容，签到按钮变为更新签到。
 * 直到重新登录咖啡机才会重置这些内容。
 */

public class SignActivity extends BaseActivity implements OnButtonClickListener {
    @BindView(R.id.titleBar)
    TitleBar mTitleBar;
    @BindView(R.id.rv_sign)
    RecyclerView mRvSign;
    @BindView(R.id.shc_sign)
    StickyHeadContainer mShcSign;
    @BindView(R.id.tv_head)
    TextView mTvHead;
    private SignAdapter mSignAdapter;
    private String mMachineType;
    private int mMachineId;
    private String mToken;
    private List<StickyHeadEntity<SignInfo>> mData;
    private JsonArray mDefaultContent;
    private JsonArray mCheckContent;
    private static SignActivity mInstance;
    public static SignActivity getInstanse(){
        if(mInstance ==null){
            mInstance =new SignActivity();
        }
        return mInstance;
    }
    @Override
    protected int getResId() {
        return R.layout.activity_sign;
    }

    @Override
    protected void initView() {
        initTitle();
        initRecycler();
        initData();
    }

    /**
     * 1020-----意式浓缩称重
     * 1021-----总杯盖数
     * 1022-----总杯托数
     */
    private void initData() {
        mData = new ArrayList<>();
        mData.add(new StickyHeadEntity<SignInfo>(new SignInfo(0,"作业内容",false),RecyclerViewAdapter.TYPE_STICKY_HEAD,"作业内容"));
        mData.add(new StickyHeadEntity<SignInfo>(new SignInfo(1020,  "意式浓缩称重", false),RecyclerViewAdapter.TYPE_DATA,""));
        mData.add(new StickyHeadEntity<SignInfo>(new SignInfo(1021,  "总杯盖数", false),RecyclerViewAdapter.TYPE_DATA,""));
        mData.add(new StickyHeadEntity<SignInfo>(new SignInfo(1022, "总杯托数", false),RecyclerViewAdapter.TYPE_DATA,""));
        mData.add(new StickyHeadEntity<SignInfo>(new SignInfo(0,"作业检查内容",false),RecyclerViewAdapter.TYPE_STICKY_HEAD,"作业检查内容"));
        Intent intent = getIntent();
        mMachineType = intent.getStringExtra("machineType");
        mMachineId = intent.getIntExtra("machineId", -1);
        mToken = UserUtils.getUserInfo()
                .getToken();
        Observable<CheckContent> observable = MachinesApi.getSignInfo(mToken, mMachineType);
        JijiaHttpSubscriber subscriber = JijiaHttpSubscriber.buildSubscriber(this)
                .setOnNextListener(new SubscriberOnNextListener<CheckContent>() {
                    @Override
                    public void onNext(CheckContent content) {
                        List<SignInfo> infos = content.getCheckContent();
                        for (SignInfo signInfo : infos) {
                            mData.add(new StickyHeadEntity<SignInfo>(new SignInfo(signInfo.getId(),
                                    signInfo.getName(),
                                    false),RecyclerViewAdapter.TYPE_CHECK_DATA,""));
                        }
                        mData.add(new StickyHeadEntity<SignInfo>(new SignInfo(0,"",false),RecyclerViewAdapter.TYPE_FOOTER,""));
                        mSignAdapter.setData(mData);
                    }
                })
                .setProgressDialog(mProgressDlg)
                .build();
        observable.subscribe(subscriber);
    }


    private void initRecycler() {
        mRvSign.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,
                false));
        mSignAdapter = new SignAdapter(this);
        // mRvSign.addItemDecoration(new SpaceItemDecoration(this));
        mShcSign.setDataCallback(new StickyHeadContainer.DataCallback() {
            @Override
            public void onDataChange(int pos) {
                mTvHead.setText(mSignAdapter.getData()
                        .get(pos).getStickyHeadName());
            }
        });
        mRvSign.addItemDecoration(new StickyItemDecoration(mShcSign,
                RecyclerViewAdapter.TYPE_STICKY_HEAD));
        mRvSign.setAdapter(mSignAdapter);
        mSignAdapter.setOnCommitClickListener(this);
    }

    private void initTitle() {
        mInstance=this;
        mTitleBar.setTitle(R.string.sign);
        mTitleBar.setTitleColor(Color.WHITE);
        mTitleBar.setTitleBold(true);
        mTitleBar.setLeftImageResource(R.drawable.icon_left);
        mTitleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition( R.anim.slide_left_in,R.anim.slide_right_out);
                moveTaskToBack(true);
            }
        });
    }

    /**
     * 点击提交按钮
     */
    @Override
    public void click() {
        handleResult();
        Observable<Object> observable = MachinesApi.addSignInfo(mToken,
                mMachineId,
                UserUtils.getUserInfo()
                        .getAdminId(),
                mDefaultContent.toString(),
                mCheckContent.toString());
        JijiaHttpSubscriber subscriber = JijiaHttpSubscriber.buildSubscriber(this)
                .setOnNextListener(new SubscriberOnNextListener() {
                    @Override
                    public void onNext(Object o) {
                        showTip(R.string.sign_success);
                        mSignAdapter.setButtonText(SignActivity.this.getString(R.string.update_sign));

                    }
                }).setOnErrorListener(new SubscriberOnErrorListener() {
                    @Override
                    public void onError(Throwable e) {
                        showTip(R.string.sign_error);
                    }
                })
                .setProgressDialog(mProgressDlg)
                .build();
        observable.subscribe(subscriber);
    }

    private void handleResult() {
        mDefaultContent = new JsonArray();
        for (StickyHeadEntity<SignInfo> signInfoStickyHeadEntity : mData) {
            SignInfo data = signInfoStickyHeadEntity.getData();
            if(data.getValue()!=null&&!data.getValue().equals("")){
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("id",data.getId());
                jsonObject.addProperty("value",data.getValue());
                mDefaultContent.add(jsonObject);
            }
        }
        mCheckContent = new JsonArray();
        for (StickyHeadEntity<SignInfo> signInfoStickyHeadEntity : mData) {
            SignInfo data = signInfoStickyHeadEntity.getData();
            if(data.getId()!=0&&(data.getValue()==null||data.getValue().equals(""))){
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("id",data.getId());
                jsonObject.addProperty("status",data.isSigned());
                mCheckContent.add(jsonObject);
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
