package com.hzjytech.operation.module.home;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.PtrHandler;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.hzjytech.operation.R;
import com.hzjytech.operation.adapters.home.SearchAdapter;
import com.hzjytech.operation.base.BaseActivity;
import com.hzjytech.operation.constants.Constants;
import com.hzjytech.operation.entity.GroupInfo;
import com.hzjytech.operation.entity.GroupList;
import com.hzjytech.operation.entity.Machies;
import com.hzjytech.operation.entity.MachineList;
import com.hzjytech.operation.entity.MenuList;
import com.hzjytech.operation.entity.TaskList;
import com.hzjytech.operation.entity.TaskListInfo;
import com.hzjytech.operation.entity.User;

import com.hzjytech.operation.http.JijiaHttpSubscriber;
import com.hzjytech.operation.http.SubscriberOnNextListener;
import com.hzjytech.operation.http.api.GroupApi;
import com.hzjytech.operation.http.api.MachinesApi;
import com.hzjytech.operation.http.api.MenuApi;
import com.hzjytech.operation.http.api.TaskApi;
import com.hzjytech.operation.utils.UserUtils;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hehongcan on 2017/4/28.
 */
public class SearchActivity extends BaseActivity {
    @BindView(R.id.et_search)
    EditText searchView;
    @BindView(R.id.tv_cancel)
    TextView tv_cancel;
    @BindView(R.id.iv_search_clear)
    ImageView ivSearchClear;
    @BindView(R.id.rv_search)
    RecyclerView rvSearch;
    @BindView(R.id.pcfl_search)
    PtrClassicFrameLayout pcflSearch;
    @BindView(R.id.tv_search_head)
    TextView mTvSearchHead;
    private int pageNumber = 1;
    private User userInfo;
    private JijiaHttpSubscriber subscriber;
    private Machies machies = new Machies();
    private int type;
    private SearchAdapter searchAdapter;
    private RecyclerAdapterWithHF mAdapter;
    private JijiaHttpSubscriber searchSubscriber;
    private String type1;
    private String queryCondition;
    private PtrHandler ptrHandler = new PtrDefaultHandler() {
        @Override
        public void onRefreshBegin(PtrFrameLayout frame) {
            pageNumber = 1;
            refreshData();
        }
    };


    private OnLoadMoreListener onLoadMoreListener = new OnLoadMoreListener() {
        @Override
        public void loadMore() {
            pageNumber++;
            loadMoreData();

        }
    };
    private List<GroupInfo.SubMachinesBean> singleMachines = new ArrayList<>();
    private List<MenuList> menus = new ArrayList<>();
    private List<GroupList> groups = new ArrayList<>();
    private List<TaskListInfo> tasks = new ArrayList<>();
    private boolean isMine;
    private int status;

    @Override
    protected int getResId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView() {
        pcflSearch.setVisibility(View.GONE);
        userInfo = UserUtils.getUserInfo();
        initRecycler();
        initData();
        initSearchView();
        initPtcr();
        showSoftKeyboard();

    }

    /**
     * 初始化recyclerview
     */
    private void initRecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvSearch.setLayoutManager(linearLayoutManager);
        searchAdapter = new SearchAdapter(this, machies);
        mAdapter = new RecyclerAdapterWithHF(searchAdapter);
        rvSearch.setAdapter(mAdapter);
    }

    /**
     * 设置搜索条hint
     */
    private void initData() {
        Intent intent = getIntent();
        type = intent.getIntExtra("type", -1);
        if (type == Constants.state_single_machine) {
            searchView.setHint("搜索咖啡机");
        } else if (type == Constants.state_single_group) {
            searchView.setHint("搜索分组");
        } else if (type == Constants.state_single_menu) {
            searchView.setHint("搜索菜单");
        } else if (type == Constants.state_my_realse_task) {
            searchView.setHint(R.string.search_my_release_task);
        } else if (type == Constants.state_remain_task) {
            searchView.setHint(R.string.search_task_remain_handle);
        } else if (type == Constants.state_finished_task) {
            searchView.setHint(R.string.search_task_handled);
        } else {
            initMachines();
        }


    }

    private void initPtcr() {
        pcflSearch.setPtrHandler(ptrHandler);//设置下拉监听
        pcflSearch.setOnLoadMoreListener(onLoadMoreListener);//设置上拉监听
        pcflSearch.setLoadMoreEnable(true);//设置可以加载更多
    }

    private void initSearchView() {
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
            }
        });
        searchView.addTextChangedListener(mWatcher);
        searchView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event
                        .getAction() == KeyEvent.ACTION_DOWN) {
                    pageNumber = 1;
                    if (machies != null && machies.getVendingMachines() != null) {
                        machies.getVendingMachines()
                                .clear();
                    }
                    if (singleMachines != null) {
                        singleMachines.clear();
                    }
                    if (groups != null) {
                        groups.clear();
                    }
                    if (menus != null) {
                        menus.clear();
                    }
                    if (tasks != null) {
                        tasks.clear();
                    }
                    pcflSearch.setLoadMoreEnable(true);
                    searchText(searchView.getText());
                    hideSoftKeyboard();
                    return true;

                }
                return false;
            }
        });
    }

    /**
     * 刷新数据
     */
    private void refreshData() {
        if (machies.getVendingMachines() != null) {
            machies.getVendingMachines()
                    .clear();
        }
        singleMachines.clear();
        groups.clear();
        menus.clear();
        tasks.clear();
        searchText(searchView.getText());
    }

    /**
     * 加载更多
     */
    private void loadMoreData() {
        // initData();
        searchText(searchView.getText());
    }


    /**
     * 根据输入内容搜索
     *
     * @param text
     */
    private void searchText(Editable text) {

        if (text == null || text.toString() == null) {
            return;
        }
        pcflSearch.setVisibility(View.VISIBLE);
        switch (type) {
            case Constants.state_single_machine:
                searchSingleMachines(text);
                break;
            case Constants.state_single_group:
                searchSingleGroups(text);
                break;
            case Constants.state_single_menu:
                searchSingleMenus(text);
                break;
            case Constants.state_my_realse_task:
                searchTask(text);
                break;
            case Constants.state_remain_task:
                searchTask(text);
                break;
            case Constants.state_finished_task:
                searchTask(text);
                break;
            default:
                searchMachines(text);
                break;

        }

    }

    /**
     * 搜索任务
     *
     * @param text
     */
    private void searchTask(Editable text) {
        User userInfo = UserUtils.getUserInfo();
        switch (type) {
            case Constants.state_my_realse_task:
                isMine = true;
                status = 0;
                break;
            case Constants.state_remain_task:
                isMine = false;
                status = 1;

                break;
            case Constants.state_finished_task:
                isMine = false;
                status = 2;
                break;
        }
        Observable<TaskList> observable = TaskApi.getTaskList(userInfo.getToken(),
                isMine,
                status,
                text.toString(),
                pageNumber,
                20);
        JijiaHttpSubscriber subscriber = JijiaHttpSubscriber.buildSubscriber(this)
                .setOnNextListener(new SubscriberOnNextListener<TaskList>() {
                    @Override
                    public void onNext(TaskList taskList) {
                        ArrayList<TaskListInfo> list = taskList.getTasks();
                        if (list == null || list.size() == 0) {
                            mTvSearchHead.setText("没有结果");
                            if(SearchActivity.this.tasks !=null&& SearchActivity.this.tasks.size()>0){
                                pcflSearch.loadMoreComplete(false);
                            }else{
                                pcflSearch.loadMoreComplete(true);
                            }
                            return;
                        }
                        mTvSearchHead.setText("我的搜索");
                        SearchActivity.this.tasks.clear();
                        SearchActivity.this.tasks.addAll(list);
                        searchAdapter.setTaskSearchData(SearchActivity.this.tasks);
                    }
                })
                .setProgressDialog((pcflSearch.isRefreshing() || pcflSearch.isLoadingMore()) ?
                        null : mProgressDlg)
                .setPtcf(pcflSearch)
                .build();
        observable.subscribe(subscriber);
    }

    private void searchSingleMenus(Editable text) {
        User userInfo = UserUtils.getUserInfo();
        Observable<List<MenuList>> observable = MenuApi.getSingleMenuList(userInfo.getToken(),
                text.toString());
        JijiaHttpSubscriber MenuSubscriber = JijiaHttpSubscriber.buildSubscriber(this)
                .setOnNextListener(new SubscriberOnNextListener<List<MenuList>>() {
                    @Override
                    public void onNext(List<MenuList> list) {
                        if (list == null || list.size() == 0) {
                            mTvSearchHead.setText("没有结果");
                            if(menus!=null&&menus.size()>0){
                                pcflSearch.loadMoreComplete(false);
                            }else{
                                pcflSearch.loadMoreComplete(true);
                            }
                            return;
                        }
                        mTvSearchHead.setText("我的搜索");
                        menus.clear();
                        menus.addAll(list);
                        searchAdapter.setMenusSearchData(menus);
                    }
                })
                .setProgressDialog((pcflSearch.isRefreshing() || pcflSearch.isLoadingMore()) ?
                        null : mProgressDlg)
                .setPtcf(pcflSearch)
                .build();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(MenuSubscriber);
    }

    private void searchSingleGroups(Editable text) {
        User userInfo = UserUtils.getUserInfo();
        Observable<List<GroupList>> observable = GroupApi.getSingleGroupList(userInfo.getToken(),
                text.toString());
        JijiaHttpSubscriber groupSubscriber = JijiaHttpSubscriber.buildSubscriber(this)
                .setOnNextListener(new SubscriberOnNextListener<List<GroupList>>() {
                    @Override
                    public void onNext(List<GroupList> list) {
                        if (list == null || list.size() == 0) {

                            mTvSearchHead.setText("没有结果");
                            if(groups!=null&&groups.size()>0){
                                pcflSearch.loadMoreComplete(false);
                            }else{
                                pcflSearch.loadMoreComplete(true);
                            }
                            return;
                        }
                        mTvSearchHead.setText("我的搜索");
                        groups.clear();
                        groups.addAll(list);
                        searchAdapter.setGroupsSearchData(groups);
                    }
                })
                .setProgressDialog((pcflSearch.isRefreshing() || pcflSearch.isLoadingMore()) ?
                        null : mProgressDlg)
                .setPtcf(pcflSearch)
                .build();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(groupSubscriber);
    }

    private void searchSingleMachines(Editable text) {
        User userInfo = UserUtils.getUserInfo();
        Observable<MachineList> observable = MachinesApi.getSingleMachineList(userInfo.getToken(),
                text.toString(),
                pageNumber,
                10);
        JijiaHttpSubscriber machineSubscriber = JijiaHttpSubscriber.buildSubscriber(this)
                .setOnNextListener(new SubscriberOnNextListener<MachineList>() {
                    @Override
                    public void onNext(MachineList machineList) {
                        if (machineList == null || machineList.getMachineList() == null ||
                                machineList.getMachineList()
                                        .size() == 0) {

                            mTvSearchHead.setText("没有结果");
                            if(singleMachines!=null&&singleMachines.size()>0){
                                pcflSearch.loadMoreComplete(false);
                            }else{
                                pcflSearch.loadMoreComplete(true);
                            }
                            return;
                        }
                        mTvSearchHead.setText("我的搜索");
                        List<GroupInfo.SubMachinesBean> machinesBeen = machineList.getMachineList();
                        singleMachines.addAll(machinesBeen);
                        searchAdapter.setSingleMachinesSearchData(singleMachines);
                    }
                })
                .setProgressDialog((pcflSearch.isRefreshing() || pcflSearch.isLoadingMore()) ?
                        null : mProgressDlg)
                .setPtcf(pcflSearch)
                .build();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(machineSubscriber);
    }

    private void searchMachines(Editable text) {
        if (searchSubscriber == null || searchSubscriber.isUnsubscribed()) {
            Observable<Machies> observable = MachinesApi.getMachines(queryCondition,
                    text.toString(),
                    10,
                    pageNumber,
                    userInfo.getToken());
            searchSubscriber = JijiaHttpSubscriber.buildSubscriber(this)
                    .setOnNextListener(new SubscriberOnNextListener<Machies>() {
                        @Override
                        public void onNext(Machies resultMachies) {
                            if (resultMachies == null || resultMachies.getVendingMachines() ==
                                    null || resultMachies.getVendingMachines()
                                    .size() == 0) {
                                mTvSearchHead.setText("没有结果");
                                if(machies.getVendingMachines()!=null&&machies.getVendingMachines().size()>0){
                                    pcflSearch.loadMoreComplete(false);
                                }else{
                                    pcflSearch.loadMoreComplete(true);
                                }

                            } else {
                                mTvSearchHead.setText("我的搜索");
                                if (machies.getVendingMachines() == null) {
                                    machies.setVendingMachines(resultMachies.getVendingMachines());
                                } else {
                                    machies.getVendingMachines()
                                            .addAll(resultMachies.getVendingMachines());
                                }
                            }
                            searchAdapter.setSearchData(machies);
                        }
                    })
                    .setPtcf(pcflSearch)
                    .setProgressDialog((pcflSearch.isLoadingMore() || pcflSearch.isRefreshing())
                            ? null : mProgressDlg)
                    .build();
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(searchSubscriber);
        }
    }


    /**
     * 初始化各种状况的机器
     */
    private void initMachines() {
        switch (type) {
            case Constants.state_opteration:
                queryCondition = "operation";
                break;
            case Constants.state_error:
                queryCondition = "sick";
                break;
            case Constants.state_lack:
                queryCondition = "lack";
                break;
            case Constants.state_lock:
                queryCondition = "locked";
                break;
            case Constants.state_offline:
                queryCondition = "offline";
                break;
            case Constants.state_unoperation:
                queryCondition = "offOperation";
                break;
        }
        if (subscriber == null || subscriber.isUnsubscribed()) {
            Observable<Machies> observable = MachinesApi.getMachines(queryCondition,
                    "",
                    10,
                    pageNumber,
                    userInfo.getToken());
            subscriber = JijiaHttpSubscriber.buildSubscriber(this)
                    .setOnNextListener(new SubscriberOnNextListener<Machies>() {
                        @Override
                        public void onNext(Machies machies) {
                            switch (type) {
                                case Constants.state_opteration:
                                    searchView.setHint("搜索-" + machies.getOperation() + "台咖啡机运营中");
                                    break;
                                case Constants.state_error:
                                    searchView.setHint("搜索-" + machies.getSick() + "台咖啡机错误");
                                    break;
                                case Constants.state_lack:
                                    searchView.setHint("搜索-" + machies.getLack() + "台咖啡机余料不足");
                                    break;
                                case Constants.state_lock:
                                    searchView.setHint("搜索-" + machies.getLocked() + "台咖啡机已锁定");
                                    break;
                                case Constants.state_offline:
                                    searchView.setHint("搜索-" + machies.getOffline() + "台咖啡机离线状态");
                                    break;
                                case Constants.state_unoperation:
                                    searchView.setHint("搜索-" + machies.getOffOperation() +
                                            "台咖啡机未运营");
                                    break;
                            }
                            // SearchActivity.this.machies=machies;
                            //searchAdapter.setSearchData(SearchActivity.this.machies);
                        }
                    })
                    .setProgressDialog(mProgressDlg)
                    .build();
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(subscriber);
        }
    }

    /**
     * 初始化单台机器管理列表
     */
    private void initSingleMachine() {

    }

    private TextWatcher mWatcher = new TextWatcher() {

        private CharSequence temp;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            temp = s;
            if (temp.length() > 0) {
                ivSearchClear.setVisibility(View.VISIBLE);
                ivSearchClear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        searchView.setText("");
                        ivSearchClear.setVisibility(View.GONE);
                    }
                });
            }

            if (temp.length() == 0) {
                ivSearchClear.setVisibility(View.GONE);
            }

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            if (temp.length() > 0) {
                ivSearchClear.setVisibility(View.VISIBLE);
                ivSearchClear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        searchView.setText("");
                        ivSearchClear.setVisibility(View.GONE);
                    }
                });
            }

            if (temp.length() == 0) {
                ivSearchClear.setVisibility(View.GONE);
            }
        }
    };

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            //如果点击除EditText以外的其他VIew，键盘回收
            if (v instanceof EditText) {
                View nextFocus = findViewFocus((ViewGroup) searchView.getParent(), event);
                if (nextFocus != null && nextFocus instanceof EditText) {
                    return super.dispatchTouchEvent(event);
                }
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    //AndroidUtils.hideSoftKeyboard(this,contentView);
                    searchView.requestFocus();
                    v.clearFocus();
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    private View findViewFocus(ViewGroup viewGroup, MotionEvent event) {
        View view = null;
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            view = viewGroup.getChildAt(i);
            Rect outRect = new Rect();
            view.getGlobalVisibleRect(outRect);
            if (outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                if (view instanceof ViewGroup) {
                    return findViewFocus((ViewGroup) view, event);
                } else {
                    return view;
                }
            }
        }
        return null;
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
