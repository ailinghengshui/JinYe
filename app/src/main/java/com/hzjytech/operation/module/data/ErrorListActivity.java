package com.hzjytech.operation.module.data;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.PtrHandler;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.hzjytech.operation.R;
import com.hzjytech.operation.adapters.data.ErrorListAdapter;
import com.hzjytech.operation.base.BaseActivity;
import com.hzjytech.operation.entity.ErrorHistory;
import com.hzjytech.operation.entity.User;

import com.hzjytech.operation.http.JijiaHttpSubscriber;
import com.hzjytech.operation.http.SubscriberOnNextListener;
import com.hzjytech.operation.http.api.MachinesApi;
import com.hzjytech.operation.utils.UserUtils;
import com.hzjytech.operation.widgets.TitleBar;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;

/**
 * Created by hehongcan on 2017/5/24.
 */
public class ErrorListActivity extends BaseActivity {
    @BindView(R.id.titleBar)
    TitleBar titleBar;
    @BindView(R.id.rv_error_list)
    RecyclerView rvErrorList;
    @BindView(R.id.pcfl_error_list)
    PtrClassicFrameLayout pcflErrorList;
    @BindView(R.id.iv_empty)
    ImageView iv_empty;
    private ErrorListAdapter adapter;
    private int pageNum = 1;
    private PtrHandler ptrDefaultHandler=new PtrHandler() {
        @Override
        public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
            return false;
        }

        @Override
        public void onRefreshBegin(PtrFrameLayout frame) {

        }
    };
    private OnLoadMoreListener onLoadMoreListener=new OnLoadMoreListener() {
        @Override
        public void loadMore() {
            pageNum++;
            initData();
        }
    };
    private List<ErrorHistory.VendingMachinesBean.VmErrorsBean> vmErrors=new ArrayList<>();

    @Override
    protected int getResId() {
        return R.layout.activity_error_list;
    }

    @Override
    protected void initView() {
        initTitle();
        initRecyclerView();
        initPtrc();
        initData();

    }
    /**
     * 初始化下拉刷新、上拉加载更多
     */
    private void initPtrc() {
        pcflErrorList.setPtrHandler(ptrDefaultHandler);//设置下拉监听
        pcflErrorList.setOnLoadMoreListener(onLoadMoreListener);//设置上拉监听
        pcflErrorList.setLoadMoreEnable(true);//设置可以加载更多
    }
    private void initData() {
        User userInfo = UserUtils.getUserInfo();
        Intent intent = getIntent();
        long startTime = intent.getLongExtra("startTime", 0);
        long endTime = intent.getLongExtra("endTime", 0);
        ArrayList<Integer> machinesId = intent.getIntegerArrayListExtra("machinesId");
        Observable<ErrorHistory> observable = MachinesApi.getErrorHistory(startTime, endTime, machinesId, 0, userInfo.getToken(), pageNum, 10);
        JijiaHttpSubscriber subscriber = JijiaHttpSubscriber.buildSubscriber(this).setOnNextListener(new SubscriberOnNextListener<ErrorHistory>() {
            @Override
            public void onNext(ErrorHistory errorHistory) {
                if(errorHistory==null||errorHistory.getVendingMachines()==null||errorHistory.getVendingMachines().size()==0||errorHistory.getVendingMachines().get(0).getVmErrors()==null||errorHistory.getVendingMachines().get(0).getVmErrors().size()==0){
                    //pcflErrorList.loadMoreComplete(false);
                    showEmptyView();
                    return;
                }
                List<ErrorHistory.VendingMachinesBean.VmErrorsBean> errors = errorHistory.getVendingMachines().get(0).getVmErrors();
                ErrorListActivity.this.vmErrors.addAll(errors);
                adapter.setData(vmErrors);
            }
        }).setPtcf(pcflErrorList).setProgressDialog(pcflErrorList.isLoadingMore() ? null : mProgressDlg).build();
        observable.subscribe(subscriber);

    }

    /**
     * 是否显示空背景
     */
   private void showEmptyView(){
    if(vmErrors.size()==0){
        iv_empty.setVisibility(View.VISIBLE);
    }else{
        iv_empty.setVisibility(View.INVISIBLE);
    }
}
    private void initRecyclerView() {
        rvErrorList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new ErrorListAdapter(this, null);
        RecyclerAdapterWithHF mAdapter = new RecyclerAdapterWithHF(adapter );
        rvErrorList.setAdapter(mAdapter);
    }

    private void initTitle() {
        titleBar.setTitleColor(Color.WHITE);
        titleBar.setTitleBold(true);
        titleBar.setTitle(R.string.error_list);
        titleBar.setLeftImageResource(R.drawable.icon_left);
        titleBar.setLeftClickListener(new View.OnClickListener() {
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
