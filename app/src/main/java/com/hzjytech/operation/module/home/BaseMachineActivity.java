package com.hzjytech.operation.module.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.PtrHandler;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.hzjytech.operation.R;
import com.hzjytech.operation.adapters.home.HomeAdapter;
import com.hzjytech.operation.base.BaseActivity;
import com.hzjytech.operation.constants.Constants;
import com.hzjytech.operation.entity.Machies;
import com.hzjytech.operation.entity.User;
import com.hzjytech.operation.http.JijiaHttpSubscriber;
import com.hzjytech.operation.http.SubscriberOnNextListener;
import com.hzjytech.operation.http.api.MachinesApi;
import com.hzjytech.operation.inter.SearchViewClickListener;
import com.hzjytech.operation.utils.UserUtils;
import com.hzjytech.operation.widgets.GoTopImageView;
import com.hzjytech.operation.widgets.HomeUpPopWindow;
import com.hzjytech.operation.widgets.TitleBar;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hehongcan on 2017/5/2.
 */
public class BaseMachineActivity extends BaseActivity {
    @BindView(R.id.titleBar)
    TitleBar titleBar;
    @BindView(R.id.rv_base_machines)
    RecyclerView rvBaseMachines;
    @BindView(R.id.pcfl_base_machines)
    PtrClassicFrameLayout pcflBaseMachines;
    @BindView(R.id.iv_to_top)
    GoTopImageView iv_to_top;
    private String type = "operation";
    private HomeUpPopWindow popWindow;
    private int state;
    private PtrHandler ptrDefaultHandler=new PtrDefaultHandler() {
        @Override
        public void onRefreshBegin(PtrFrameLayout frame) {
            pageNumber=1;
            initData();
        }
    };
    private OnLoadMoreListener onLoadMoreListener=new OnLoadMoreListener() {
        @Override
        public void loadMore() {
            pageNumber++;
            loadMoreData();
        }
    };


    private String queryCondition="";
    private int pageNumber=1;
    private Machies machies;
    private HomeAdapter homeAdapter;
    private RecyclerAdapterWithHF mAdapter;
    private HomeUpPopWindow plusPopWindow;
    private View plusView;

    @Override
    protected int getResId() {
        return R.layout.activity_base_machine;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        state = intent.getIntExtra("state", -1);
        initTitleBar();
        initReyclerView();
        initPtr();
        initData();
        initListeners();
    }

    /**
     * 初始化各种监听
     */
    private void initListeners() {
       homeAdapter.setSearchViewClickListener(new SearchViewClickListener() {
           @Override
           public void searchViewClick(int type) {
               Intent intent = new Intent(BaseMachineActivity.this, SearchActivity.class);
               intent.putExtra("type",type);
               startActivity(intent);
               overridePendingTransition(R.anim.zoomin,R.anim.zoomout);
           }
       });
    }

    /**
     * 初始化标题栏
     */
    private void initTitleBar() {
        titleBar.setLeftImageResource(R.drawable.icon_left);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
      /*  plusView = titleBar.addAction(new TitleBar.ImageAction(R.drawable.icon_plus) {
            @Override
            public void performAction(View view) {
                if (plusPopWindow != null && plusPopWindow.isShowing()) {
                    plusPopWindow.dismiss();
                    return;
                } else {
                    if(plusView==null){
                        return;
                    }
                    plusPopWindow = new HomeUpPopWindow(BaseMachineActivity.this);
                    int XDx = DensityUtil.dp2px(BaseMachineActivity.this, -114);
                    int YDx = DensityUtil.dp2px(BaseMachineActivity.this, -5);
                    plusPopWindow.showAsDropDown(plusView, XDx, YDx);
                }
            }
        });*/
        titleBar.setTitleBold(true);
        titleBar.setTitleColor(Color.WHITE);
        switch (state) {
            case Constants.state_error:
                type = "sick";
                titleBar.setTitle("咖啡机错误");
                break;
            case Constants.state_lack:
                type = "lack";
                titleBar.setTitle("余料不足");
                break;
            case Constants.state_offline:
                type = "offline";
                titleBar.setTitle("离线状态");
                break;
            case Constants.state_lock:
                type = "locked";
                titleBar.setTitle("已锁定");
                break;
            case Constants.state_unoperation:
                type = "offOperation";
                titleBar.setTitle("未运营");
                break;
        }
    }
    /**
     * 初始化下拉刷新和上拉加载
     */
    private void initPtr() {
        pcflBaseMachines.setPtrHandler(ptrDefaultHandler);//设置下拉监听
        pcflBaseMachines.setOnLoadMoreListener(onLoadMoreListener);//设置上拉监听
        pcflBaseMachines.setLoadMoreEnable(true);//设置可以加载更多
    }

    /**
     * 初始化recyclerview
     */
    private void initReyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvBaseMachines.setLayoutManager(linearLayoutManager);
        homeAdapter = new HomeAdapter(this,null,state);
        mAdapter = new RecyclerAdapterWithHF(homeAdapter);
         rvBaseMachines.setAdapter(mAdapter);
        iv_to_top.setRecyclerView(rvBaseMachines);
    }

    /**
     * 网络获取数据，设置界面
     */
    private void initData() {
        User userInfo = UserUtils.getUserInfo();
        Observable<Machies> observable = MachinesApi.getMachines(type, queryCondition, 10, pageNumber, userInfo.getToken());
        JijiaHttpSubscriber subscriber = JijiaHttpSubscriber.buildSubscriber(this).setOnNextListener(new SubscriberOnNextListener<Machies>() {
            @Override
            public void onNext(Machies headMachies) {
                machies=headMachies;
                homeAdapter.setHomeData(machies);
            }
        }).setProgressDialog(pcflBaseMachines.isRefreshing()?null:mProgressDlg).setPtcf(pcflBaseMachines).build();
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber);
    }

    /**
     * 加载更多
     */
    private void loadMoreData() {
        User userInfo = UserUtils.getUserInfo();
        Observable<Machies> observable = MachinesApi.getMachines(type, queryCondition, 10, pageNumber, userInfo.getToken());
        JijiaHttpSubscriber subscriber = JijiaHttpSubscriber.buildSubscriber(this).setOnNextListener(new SubscriberOnNextListener<Machies>() {
            @Override
            public void onNext(Machies moreMachies) {
                if(moreMachies==null||moreMachies.getVendingMachines()==null||moreMachies.getVendingMachines().size()==0){
                    pcflBaseMachines.loadMoreComplete(false);
                }
                machies.getVendingMachines().addAll(moreMachies.getVendingMachines());
                homeAdapter.setHomeData(machies);
            }
        }).setPtcf(pcflBaseMachines).build();
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber);
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
