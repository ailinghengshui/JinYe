package com.hzjytech.operation.module.data;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.PtrHandler;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.hzjytech.operation.R;
import com.hzjytech.operation.adapters.data.DetailSaleTableAdapter;
import com.hzjytech.operation.base.BaseActivity;
import com.hzjytech.operation.entity.DetailSaleMachine;
import com.hzjytech.operation.entity.User;
import com.hzjytech.operation.http.JijiaHttpSubscriber;
import com.hzjytech.operation.http.SubscriberOnNextListener;
import com.hzjytech.operation.http.api.DataApi;
import com.hzjytech.operation.utils.UserUtils;
import com.hzjytech.operation.widgets.TitleBar;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONArray;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hehongcan on 2017/5/22.
 */
public class DetailSaleTableActivity extends BaseActivity {
    @BindView(R.id.titleBar)
    TitleBar titleBar;
    @BindView(R.id.rv_detail_sale_table)
    RecyclerView rvDetailSaleTable;
    @BindView(R.id.pcfl_detail_sale_table)
    PtrClassicFrameLayout pcflDetailSaleTable;
    private DetailSaleTableAdapter saleAdapter;
    private RecyclerAdapterWithHF mAdapter;
    private PtrHandler ptrDefaultHandler=new PtrHandler() {
        @Override
        public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
            return false;
        }

        @Override
        public void onRefreshBegin(PtrFrameLayout frame) {

        }
    };
    private OnLoadMoreListener onLoadMoreListener;

    @Override
    protected int getResId() {
        return R.layout.activity_detail_sale_table;
    }

    @Override
    protected void initView() {
      initTitle();
        initRecyclerView();
        initPcfl();
        initData();
    }
   //初始化数据
    private void initData() {
        User userInfo = UserUtils.getUserInfo();
        Intent intent = getIntent();
        long startTime = intent.getLongExtra("startTime", 0);
        long endTime=intent.getLongExtra("endTime",0);
        ArrayList<Integer> machinesId = intent.getIntegerArrayListExtra("machinesId");
        JSONArray jsonArray = new JSONArray();
        for (Integer integer : machinesId) {
            jsonArray.put(integer);
        }
        Observable<DetailSaleMachine> observable = DataApi.getDetailSaleMachine(userInfo.getToken(), startTime, endTime, 0, machinesId);
        JijiaHttpSubscriber subscriber = JijiaHttpSubscriber.buildSubscriber(this).setOnNextListener(new SubscriberOnNextListener<DetailSaleMachine>() {
            @Override
            public void onNext(DetailSaleMachine detailSaleMachine) {
                saleAdapter.setData(detailSaleMachine.getVendingMachines());
            }
        }).setProgressDialog(mProgressDlg).build();
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber);
    }

    private void initPcfl() {
        pcflDetailSaleTable.setPtrHandler(ptrDefaultHandler);//设置下拉监听
        pcflDetailSaleTable .setOnLoadMoreListener(onLoadMoreListener);//设置上拉监听
        pcflDetailSaleTable.setLoadMoreEnable(false);//设置可以加载更多
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvDetailSaleTable.setLayoutManager(linearLayoutManager);
        saleAdapter = new DetailSaleTableAdapter(this, null);
        mAdapter = new RecyclerAdapterWithHF(saleAdapter);
        rvDetailSaleTable.setAdapter(mAdapter);
    }

    private void initTitle() {
        titleBar.setLeftImageResource(R.drawable.icon_left);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleBar.setTitleBold(true);
        titleBar.setTitleColor(Color.WHITE);
        titleBar.setTitle(R.string.sale_table);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
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
