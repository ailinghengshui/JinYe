package com.hzjytech.operation.module.frags.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.PtrHandler;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.hzjytech.operation.R;
import com.hzjytech.operation.adapters.group.MoreGroupAdapter;
import com.hzjytech.operation.adapters.group.MoreMachineAdapter;
import com.hzjytech.operation.adapters.menu.MoreMenuAdapter;
import com.hzjytech.operation.base.BaseFragment;
import com.hzjytech.operation.entity.GroupInfo;
import com.hzjytech.operation.entity.GroupList;
import com.hzjytech.operation.entity.MachineList;
import com.hzjytech.operation.entity.MenuList;
import com.hzjytech.operation.entity.User;
import com.hzjytech.operation.http.JijiaHttpSubscriber;
import com.hzjytech.operation.http.SubscriberOnNextListener;
import com.hzjytech.operation.http.api.GroupApi;
import com.hzjytech.operation.http.api.MachinesApi;
import com.hzjytech.operation.http.api.MenuApi;
import com.hzjytech.operation.inter.SearchViewClickListener;
import com.hzjytech.operation.module.home.SearchActivity;
import com.hzjytech.operation.utils.DensityUtil;
import com.hzjytech.operation.utils.UserUtils;
import com.hzjytech.operation.widgets.HomeUpPopWindow;
import com.hzjytech.operation.widgets.TitleBar;
import com.hzjytech.operation.widgets.TitlePopUpWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hehongcan on 2017/4/7.
 */
public class MachineFragment extends BaseFragment {
    private static final int TYPE_MACHINE = 0;
    private static final int TYPE_GROUP = 1;
    private static final int TYPE_MENU = 2;
    private int type=TYPE_MACHINE;
    private int groupPageNumber=1;
    private int menuPageNumber=1;
    private static final String TAG = "MachineFragment";
    @BindView(R.id.titleBar)
    TitleBar titleBar;
    @BindView(R.id.bg_pop)
    View bgPop;
    @BindView(R.id.rv_machine)
    RecyclerView rvMachine;
    @BindView(R.id.pcfl_machine)
    PtrClassicFrameLayout pcflMachine;
    private TitlePopUpWindow popWindow;
    private HomeUpPopWindow plusPopWindow;
    private ArrayList<String> list;
    private MoreMachineAdapter machineAdapter;
    private RecyclerAdapterWithHF mAdapter;
    private PtrHandler ptrDefaultHandler=new PtrDefaultHandler() {
        @Override
        public void onRefreshBegin(PtrFrameLayout frame) {
           refreshData();
        }
    };
    private List<GroupList> groups=new ArrayList<>();
    private MoreGroupAdapter groupAdapter;
    private MoreMenuAdapter menuAdapter;
    private List<MenuList> menus=new ArrayList<>();
    private ImageView plusView;

    //下拉刷新数据
    private void refreshData() {
        machinePageNumber=1;
        groupPageNumber=1;
        groups.clear();
        machines.clear();
        initData();
    }

    //单机管理
    private ArrayList<GroupInfo.SubMachinesBean> machines=new ArrayList<>();

    /**
     * 加载更多数据
     */
    private void loadMoreData() {
        machinePageNumber++;
        groupPageNumber++;
        initData();
    }

    private OnLoadMoreListener onLoadMoreListener=new OnLoadMoreListener() {
        @Override
        public void loadMore() {
            loadMoreData();
        }
    };
    private int machinePageNumber=1;
    private int pageSize=10;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getResId() {
        return R.layout.fragment_machie;
    }

    @Override
    protected void initView() {
        initTitle();
        initRecyclerView();
        initPtrc();
        initData();

    }


    private void initData() {
        switch (type){
            case TYPE_MACHINE:
                getMachiesData();
                break;
            case TYPE_GROUP:
                getGroupsData();
                break;
            case TYPE_MENU:
                getMenuData();
                break;
        }

    }

    /**
     * 获取菜单列表信息
     */
    private void getMenuData() {
        User userInfo = UserUtils.getUserInfo();
        Observable<List<MenuList>> observable = MenuApi.getSingleMenuList(userInfo.getToken(), "");
        JijiaHttpSubscriber MenuSubscriber = JijiaHttpSubscriber.buildSubscriber(getActivity()).setOnNextListener(new SubscriberOnNextListener<List<MenuList>>() {
            @Override
            public void onNext(List<MenuList> list) {
                menus.clear();
                menus.addAll(list);
                menuAdapter.setMenuData(menus);
            }
        }).setProgressDialog((pcflMachine.isRefreshing()||pcflMachine.isLoadingMore())?null:mProgressDlg).setPtcf(pcflMachine).build();
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(MenuSubscriber);
    }

    /**
     * 获取咖啡机组信息
     */
    private void getGroupsData() {
        User userInfo = UserUtils.getUserInfo();
        Observable<List<GroupList>> observable = GroupApi.getSingleGroupList(userInfo.getToken(), "");
        JijiaHttpSubscriber groupSubscriber = JijiaHttpSubscriber.buildSubscriber(getActivity()).setOnNextListener(new SubscriberOnNextListener<List<GroupList>>() {
            @Override
            public void onNext(List<GroupList> list) {
                groups.clear();
                groups.addAll(list);
               groupAdapter.setGroupData(groups);
            }
        }).setProgressDialog((pcflMachine.isRefreshing()||pcflMachine.isLoadingMore())?null:mProgressDlg).setPtcf(pcflMachine).build();
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(groupSubscriber);
    }

    /**
     * 获取咖啡机列表信息
     */
    private void getMachiesData() {
        User userInfo = UserUtils.getUserInfo();
        Observable<MachineList> observable = MachinesApi.getSingleMachineList(userInfo.getToken(), "", machinePageNumber, pageSize);
        JijiaHttpSubscriber machineSubscriber = JijiaHttpSubscriber.buildSubscriber(getActivity()).setOnNextListener(new SubscriberOnNextListener<MachineList>() {
            @Override
            public void onNext(MachineList machineList) {
                List<GroupInfo.SubMachinesBean> machinesBeen = machineList.getMachineList();
                machines.addAll(machinesBeen);
                machineAdapter.setMachinesData(machines);
            }
        }).setProgressDialog((pcflMachine.isRefreshing()||pcflMachine.isLoadingMore())?null:mProgressDlg).setPtcf(pcflMachine).build();
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(machineSubscriber);
    }

    private void initPtrc() {
        pcflMachine.setPtrHandler(ptrDefaultHandler);//设置下拉监听
        pcflMachine.setOnLoadMoreListener(onLoadMoreListener);//设置上拉监听
        pcflMachine.setLoadMoreEnable(true);//设置可以加载更多
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvMachine.setLayoutManager(linearLayoutManager);
        final Intent intent = new Intent(getActivity(), SearchActivity.class);
        switch (type){
            case TYPE_MACHINE:
                machineAdapter = new MoreMachineAdapter(getActivity(), null);
                machineAdapter.setHasHead(true);
                machineAdapter.setSearchViewClickListener(new SearchViewClickListener() {
                    @Override
                    public void searchViewClick(int type) {
                        intent.putExtra("type",type);
                        startActivity(intent);
                    }
                });
                mAdapter = new RecyclerAdapterWithHF(machineAdapter);
                break;
            case TYPE_GROUP:
                groupAdapter = new MoreGroupAdapter(getActivity(),null);
                groupAdapter.setHasHead(true);
                groupAdapter.setSearchViewClickListener(new SearchViewClickListener() {
                    @Override
                    public void searchViewClick(int type) {
                        intent.putExtra("type",type);
                        startActivity(intent);
                    }
                });
                mAdapter = new RecyclerAdapterWithHF(groupAdapter);
                break;
            case TYPE_MENU:
                menuAdapter = new MoreMenuAdapter(getActivity(),null);
                menuAdapter.setHasHead(true);

                menuAdapter.setSearchViewClickListener(new SearchViewClickListener() {
                    @Override
                    public void searchViewClick(int type) {
                        intent.putExtra("type",type);
                        startActivity(intent);
                    }
                });
                mAdapter = new RecyclerAdapterWithHF(menuAdapter);
                break;
        }
        rvMachine.setAdapter(mAdapter);
    }

    /**
     * 初始化标题栏
     */
    private void initTitle() {
        list = new ArrayList<>();
        list.add("单机管理");
        list.add("促销分组");
        list.add("菜单");
        titleBar.setTitle("单台管理");
        titleBar.setTitleColor(Color.WHITE);
        titleBar.setTitleBold(true);
        titleBar.addArrow(R.drawable.arrow_up);
        titleBar.setOnTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popWindow != null && popWindow.isShowing()) {
                    popWindow.dismiss();
                    return;
                } else {
                    if (popWindow == null) {
                        popWindow = new TitlePopUpWindow(getContext().getApplicationContext(), list, 0);
                        popWindow.setClippingEnabled(false);
                        popWindow.showAsDropDown(titleBar);
                        popWindow.setOnTitlePopWindowSelectListener(onTitlePopWindowSelectListener);
                        popWindow.setOnDismissListener(onDismissListener);
                        titleBar.getArrow().setRotation(180);
                        bgPop.setVisibility(View.VISIBLE);
                    } else {
                        titleBar.getArrow().setPivotY(titleBar.getArrow().getHeight() / 2);
                        titleBar.getArrow().setPivotX(titleBar.getArrow().getWidth() / 2);
                        titleBar.getArrow().setRotation(180);
                        bgPop.setVisibility(View.VISIBLE);
                        popWindow.showAsDropDown(titleBar);
                    }

                }
            }
        });
        TitleBar.ImageAction imageAction = new TitleBar.ImageAction(R.drawable.icon_plus) {
            @Override
            public void performAction(View view) {
                if(plusView==null){
                    return;
                }
                if (plusPopWindow != null && plusPopWindow.isShowing()) {
                    plusPopWindow.dismiss();
                    return;
                } else {
                    plusPopWindow = new HomeUpPopWindow(getActivity());
                    int XDx = DensityUtil.dp2px(getActivity(), -114);
                    int YDx = DensityUtil.dp2px(getActivity(), -5);
                    plusPopWindow.showAsDropDown(plusView,XDx,YDx);
                }
            }
        };
        plusView = (ImageView) titleBar.addAction(imageAction);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
    final TitlePopUpWindow.OnTitlePopWindowSelectListener onTitlePopWindowSelectListener = new TitlePopUpWindow.OnTitlePopWindowSelectListener() {
        @Override
        public void select(int position) {
            popWindow.dismiss();
            titleBar.setTitle(list.get(position));
            type=position;
            machinePageNumber=1;
            groupPageNumber=1;
            menuPageNumber=1;
            groups.clear();
            machines.clear();
            initRecyclerView();
            initPtrc();
            initData();
        }
    };
    final PopupWindow.OnDismissListener onDismissListener = new PopupWindow.OnDismissListener() {
        @Override
        public void onDismiss() {
            titleBar.getArrow().setPivotY(titleBar.getArrow().getHeight() / 2);
            titleBar.getArrow().setPivotX(titleBar.getArrow().getWidth() / 2);
            titleBar.getArrow().setRotation(360);
            bgPop.setVisibility(View.INVISIBLE);
        }
    };
}
