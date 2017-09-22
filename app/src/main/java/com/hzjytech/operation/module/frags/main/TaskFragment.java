package com.hzjytech.operation.module.frags.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import com.hzjytech.operation.adapters.task.BaseSearchListAdapter;
import com.hzjytech.operation.base.BaseFragment;
import com.hzjytech.operation.constants.BusMessage;
import com.hzjytech.operation.constants.Constants;
import com.hzjytech.operation.entity.TaskList;
import com.hzjytech.operation.entity.TaskListInfo;
import com.hzjytech.operation.entity.User;
import com.hzjytech.operation.http.JijiaHttpSubscriber;
import com.hzjytech.operation.http.RxBus;
import com.hzjytech.operation.http.SubscriberOnNextListener;
import com.hzjytech.operation.http.api.TaskApi;
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
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by hehongcan on 2017/4/7.
 */
public class TaskFragment extends BaseFragment {

    @BindView(R.id.titleBar)
    TitleBar mTitleBar;
    @BindView(R.id.bg_pop)
    View mBgPop;
    @BindView(R.id.rv_task)
    RecyclerView mRvTask;
    @BindView(R.id.pcfl_task)
    PtrClassicFrameLayout mPcflTask;
    private ArrayList<String> list;
    private TitlePopUpWindow popWindow;
    private ImageView plusView;
    private HomeUpPopWindow plusPopWindow;
    private int type = 1;
    private int releaseTaskPageNumber = 1;
    private int remainTaskPageNumber = 1;
    private int finishedTaskPageNumber = 1;
    private BaseSearchListAdapter taskAdapter;
    private RecyclerAdapterWithHF mAdapter;
    private List<TaskListInfo> data = new ArrayList<>();
    private PtrHandler ptrDefaultHandler = new PtrDefaultHandler() {
        @Override
        public void onRefreshBegin(PtrFrameLayout frame) {
            onRefresh();
        }
    };
    private OnLoadMoreListener onLoadMoreListener = new OnLoadMoreListener() {
        @Override
        public void loadMore() {
            loadMoreData();
        }
    };
    private int pageSize = 20;
    private int page = 1;
    //用于传递给搜索界面的参数
    private int searchType;
    private Observable mObservable;
    private JijiaHttpSubscriber mTaskSubscriber;
    private Subscription mSubscribe;

    /**
     * 刷新数据
     */
    private void onRefresh() {
        releaseTaskPageNumber = 1;
        remainTaskPageNumber = 1;
        finishedTaskPageNumber = 1;

        data.clear();
        initData();
    }

    /**
     * 加载更多数据
     */
    private void loadMoreData() {
        releaseTaskPageNumber++;
        remainTaskPageNumber++;
        finishedTaskPageNumber++;
        initData();
    }

    @Override
    protected int getResId() {
        return R.layout.fragment_task;
    }

    @Override
    protected void initView() {
        initTitle();
        initRecyclerView();
        initPtcr();
        initData();
        //do something
        mSubscribe = RxBus.getDefault()
                .toObservable()
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object event) {
                        if (event instanceof BusMessage) {
                            //do something
                            switch ((BusMessage) event) {
                                case CHANGED:
                                    if (mPcflTask != null) {
                                        mPcflTask.autoRefresh();
                                    }
                                    break;
                                case UnCHANGED:
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                });


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mSubscribe!=null){
            mSubscribe.unsubscribe();
        }

    }

    /**
     * 加载数据
     */
    private void initData() {
        User userInfo = UserUtils.getUserInfo();
        boolean isMine = false;
        int status = 1;
        //根据不同tab设置参数值
        switch (type) {
            case 0:
                isMine = true;
                status = 0;
                page = releaseTaskPageNumber;
                searchType = Constants.state_my_realse_task;
                break;
            case 1:
                isMine = false;
                status = 1;
                page = remainTaskPageNumber;
                searchType = Constants.state_remain_task;
                break;
            case 2:
                isMine = false;
                status = 2;
                page = finishedTaskPageNumber;
                searchType = Constants.state_finished_task;
                break;
        }
        mObservable = TaskApi.getTaskList(userInfo.getToken(),
                isMine,
                status,
                "",
                page,
                pageSize);
        mTaskSubscriber = JijiaHttpSubscriber.buildSubscriber(getActivity())
                .setOnNextListener(new SubscriberOnNextListener<TaskList>() {
                    @Override
                    public void onNext(TaskList taskList) {
                        data.addAll(taskList.getTasks());
                        taskAdapter.setData(data, searchType);

                    }
                })
                .setProgressDialog((mPcflTask.isRefreshing() || mPcflTask.isLoadingMore()) ? null
                        : mProgressDlg)
                .setPtcf(mPcflTask)
                .build();
        mObservable.subscribe(mTaskSubscriber);
    }

    private void initPtcr() {
        mPcflTask.setPtrHandler(ptrDefaultHandler);//设置下拉监听
        mPcflTask.setOnLoadMoreListener(onLoadMoreListener);//设置上拉监听
        mPcflTask.setLoadMoreEnable(true);//设置可以加载更多
    }

    /**
     * 0:代表我发布的任务1:我未处理的任务2:已经完成的任务
     */
    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvTask.setLayoutManager(linearLayoutManager);
        final Intent intent = new Intent(getActivity(), SearchActivity.class);
        taskAdapter = new BaseSearchListAdapter(getActivity(), null);
        mAdapter = new RecyclerAdapterWithHF(taskAdapter);

        //搜索框点击监听
        taskAdapter.setSearchViewClickListener(new SearchViewClickListener() {
            @Override
            public void searchViewClick(int type) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra("type", type);
                startActivity(intent);
            }
        });

        mRvTask.setAdapter(mAdapter);
    }

    /**
     * 初始化标题栏
     */
    private void initTitle() {
        list = new ArrayList<>();
        String myReleaseTask = getResources().getString(R.string.my_release_task);
        String remainTask = getResources().getString(R.string.task_remain_handle);
        String handledTask = getResources().getString(R.string.task_handled);
        list.add(myReleaseTask);
        list.add(remainTask);
        list.add(handledTask);
        mTitleBar.setTitle(myReleaseTask);
        mTitleBar.setTitleColor(Color.WHITE);
        mTitleBar.setTitleBold(true);
        mTitleBar.addArrow(R.drawable.arrow_up);
        if (popWindow == null) {
            popWindow = new TitlePopUpWindow(getActivity().getApplicationContext(), list, 0);
            popWindow.setClippingEnabled(false);
//            popWindow.showAsDropDown(mTitleBar);
            popWindow.setOnTitlePopWindowSelectListener(onTitlePopWindowSelectListener);
            popWindow.setOnDismissListener(onDismissListener);
            //默认选中未处理的任务
            popWindow.setPositionChecked(1);
            mTitleBar.setTitle(list.get(1));
            type = 1;
            popWindow.dismiss();

        }
        mTitleBar.setOnTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popWindow != null && popWindow.isShowing()) {
                    popWindow.dismiss();
                    return;
                } else {
                    mTitleBar.getArrow()
                            .setPivotY(mTitleBar.getArrow()
                                    .getHeight() / 2);
                    mTitleBar.getArrow()
                            .setPivotX(mTitleBar.getArrow()
                                    .getWidth() / 2);
                    mTitleBar.getArrow()
                            .setRotation(180);
                    mBgPop.setVisibility(View.VISIBLE);
                    popWindow.showAsDropDown(mTitleBar);

                }
            }
        });

        TitleBar.ImageAction imageAction = new TitleBar.ImageAction(R.drawable.icon_plus) {
            @Override
            public void performAction(View view) {
                if (plusView == null) {
                    return;
                }
                if (plusPopWindow != null && plusPopWindow.isShowing()) {
                    plusPopWindow.dismiss();
                    return;
                } else {
                    plusPopWindow = new HomeUpPopWindow(getActivity());
                    int XDx = DensityUtil.dp2px(getActivity(), -114);
                    int YDx = DensityUtil.dp2px(getActivity(), -5);
                    plusPopWindow.showAsDropDown(plusView, XDx, YDx);
                }
            }
        };
        plusView = (ImageView) mTitleBar.addAction(imageAction);

    }


    TitlePopUpWindow.OnTitlePopWindowSelectListener onTitlePopWindowSelectListener = new
            TitlePopUpWindow.OnTitlePopWindowSelectListener() {
        @Override
        public void select(int position) {
                popWindow.dismiss();
                mTitleBar.setTitle(list.get(position));
                type = position;
                mPcflTask.autoRefresh();


        }
    };
    final PopupWindow.OnDismissListener onDismissListener = new PopupWindow.OnDismissListener() {
        @Override
        public void onDismiss() {
            mTitleBar.getArrow()
                    .setPivotY(mTitleBar.getArrow()
                            .getHeight() / 2);
            mTitleBar.getArrow()
                    .setPivotX(mTitleBar.getArrow()
                            .getWidth() / 2);
            mTitleBar.getArrow()
                    .setRotation(360);
            mBgPop.setVisibility(View.INVISIBLE);
        }
    };

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
