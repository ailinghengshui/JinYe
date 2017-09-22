package com.hzjytech.operation.module.frags.main;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.PtrHandler;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.hzjytech.operation.R;
import com.hzjytech.operation.adapters.home.HomeAdapter;
import com.hzjytech.operation.base.BaseFragment;
import com.hzjytech.operation.constants.Constants;
import com.hzjytech.operation.entity.Machies;
import com.hzjytech.operation.entity.User;
import com.hzjytech.operation.http.JijiaHttpSubscriber;
import com.hzjytech.operation.http.SubscriberOnNextListener;
import com.hzjytech.operation.http.api.MachinesApi;
import com.hzjytech.operation.inter.MachineStateClickListener;
import com.hzjytech.operation.inter.SearchViewClickListener;
import com.hzjytech.operation.module.home.BaseMachineActivity;
import com.hzjytech.operation.module.home.SearchActivity;
import com.hzjytech.operation.scan.activity.CaptureActivity;
import com.hzjytech.operation.utils.DensityUtil;
import com.hzjytech.operation.utils.ToastUtil;
import com.hzjytech.operation.utils.UserUtils;
import com.hzjytech.operation.widgets.GoTopImageView;
import com.hzjytech.operation.widgets.HomeUpPopWindow;
import com.hzjytech.operation.widgets.TitleBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hehongcan on 2017/4/7.
 */

public class HomeFragment extends BaseFragment {
    //扫描请求码
    private static final int REQUEST_CODE_FETCH = 100;
    @BindView(R.id.titleBar)
    TitleBar titleBar;
    @BindView(R.id.rv_home)
    RecyclerView rv_home;
    @BindView(R.id.pcfl_home)
    PtrClassicFrameLayout pcflHome;
    @BindView(R.id.iv_to_top)
    GoTopImageView iv_to_top;
    private HomeAdapter homeAdapter;
    int pageNumber ;
    String queryCondition = "";
    private PtrHandler ptrDefaultHandler = new PtrDefaultHandler() {
        @Override
        public void onRefreshBegin(PtrFrameLayout frame) {
            pageNumber = 1;
            initData();
        }
    };
    private OnLoadMoreListener onLoadMoreListener = new OnLoadMoreListener() {
        @Override
        public void loadMore() {
            pageNumber++;
            loadMoreData();

        }
    };
    private Machies machies;
    private HomeUpPopWindow popWindow;
    private ImageView plusView;
    private HomeUpPopWindow plusPopWindow;

    /**
     * 加载更多数据
     */
    private void loadMoreData() {
        User userInfo = UserUtils.getUserInfo();
        Observable<Machies> observable = MachinesApi.getMachines("operation",
                queryCondition,
                20,
                pageNumber,
                userInfo.getToken());
        JijiaHttpSubscriber subscriber = JijiaHttpSubscriber.buildSubscriber(getActivity())
                .setOnNextListener(new SubscriberOnNextListener<Machies>() {
                    @Override
                    public void onNext(Machies moreMachies) {
                        List<Machies.VendingMachines> vendingMachines = moreMachies
                                .getVendingMachines();
                        machies.getVendingMachines()
                                .addAll(vendingMachines);
                        if (moreMachies == null || vendingMachines == null || vendingMachines
                                .size() == 0) {
                            //pcflHome.loadMoreComplete(false);
                        }
                        Log.e("machines", machies.toString());
                        homeAdapter.setHomeData(machies);
                        //mAdapter.notifyDataSetChanged();
                    }
                })
                .setPtcf(pcflHome)
                .build();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }

    private RecyclerAdapterWithHF mAdapter;

    @Override
    protected int getResId() {
        return R.layout.fragment_home;
    }


    /**
     * 返回结果接收
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == 100 && resultCode == Activity.RESULT_OK) {
            String resultString = data.getStringExtra(CaptureActivity.SCAN_RESULT_KEY);
            ToastUtil.showShort(getActivity(), resultString);
        }
    }

    /**
     * 初始化界面
     */
    protected void initView() {
        initTitleBar();
        initReyclerView();
        initPtr();
        initData();
        initListeners();
    }

    /**
     * 初始化各类监听
     */
    private void initListeners() {
        homeAdapter.setSearchViewClickListener(new SearchViewClickListener() {
            @Override
            public void searchViewClick(int type) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra("type", type);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
            }
        });
        homeAdapter.setMachineStateChangeLister(new MachineStateClickListener() {
            @Override
            public void state(int state) {
                Intent intent = new Intent(getActivity(), BaseMachineActivity.class);
                switch (state) {
                    case Constants.state_error:
                        intent.putExtra("state", Constants.state_error);
                        break;
                    case Constants.state_lack:
                        intent.putExtra("state", Constants.state_lack);
                        break;
                    case Constants.state_offline:
                        intent.putExtra("state", Constants.state_offline);
                        break;
                    case Constants.state_unoperation:
                        intent.putExtra("state", Constants.state_unoperation);
                        break;
                    case Constants.state_lock:
                        intent.putExtra("state", Constants.state_lock);
                        break;
                    default:
                        break;
                }
                startActivity(intent);
            }
        });
    }

    /**
     * 初始化下拉刷新和上拉加载
     */
    private void initPtr() {
        pcflHome.setPtrHandler(ptrDefaultHandler);//设置下拉监听
        pcflHome.setOnLoadMoreListener(onLoadMoreListener);//设置上拉监听
        pcflHome.setLoadMoreEnable(true);//设置可以加载更多
    }

    /**
     * 初始化recyclerview
     */
    private void initReyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_home.setLayoutManager(linearLayoutManager);
        homeAdapter = new HomeAdapter(getActivity(), null, Constants.state_opteration);
        mAdapter = new RecyclerAdapterWithHF(homeAdapter);
        rv_home.setAdapter(mAdapter);
        iv_to_top.setRecyclerView(rv_home);
    }

    /**
     * 网络获取数据，设置界面
     */
    private void initData() {
        pageNumber=1;
        User userInfo = UserUtils.getUserInfo();
        Observable<Machies> observable = MachinesApi.getMachines("operation",
                queryCondition,
                10,
                pageNumber,
                userInfo.getToken());
        JijiaHttpSubscriber subscriber = JijiaHttpSubscriber.buildSubscriber(getActivity())
                .setOnNextListener(new SubscriberOnNextListener<Machies>() {
                    @Override
                    public void onNext(Machies headMachies) {
                        machies = headMachies;
                        homeAdapter.setHomeData(machies);
                        //mAdapter.notifyDataSetChanged();
                    }
                })
                .setProgressDialog(pcflHome.isRefreshing() ? null : mProgressDlg)
                .setPtcf(pcflHome)
                .build();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 初始化标题栏
     */
    private void initTitleBar() {
        titleBar.setTitle(R.string.home);
        titleBar.setTitleColor(Color.WHITE);
        titleBar.setTitleBold(true);
        plusView = (ImageView) titleBar.addAction(new TitleBar.ImageAction(R.drawable.icon_plus) {
            @Override
            public void performAction(View view) {
                if (plusPopWindow != null && plusPopWindow.isShowing()) {
                    plusPopWindow.dismiss();
                    return;
                } else {
                    if (plusView == null) {
                        return;
                    }
                    plusPopWindow = new HomeUpPopWindow(getActivity());
                    int XDx = DensityUtil.dp2px(getActivity(), -114);
                    int YDx = DensityUtil.dp2px(getActivity(), -5);
                    plusPopWindow.showAsDropDown(plusView, XDx, YDx);
                }
            }
        });
    }


    /**
     * 扫一扫
     */
    private void scan() {
        Intent intent = new Intent(getActivity(), CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE_FETCH);
    }

    /**
     * 新建任务
     */
    private void newTask() {

    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
