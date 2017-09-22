package com.hzjytech.operation.module.task;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.PtrHandler;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.hzjytech.operation.R;
import com.hzjytech.operation.adapters.task.DetailTaskAdapter;
import com.hzjytech.operation.base.BaseActivity;
import com.hzjytech.operation.constants.BusMessage;
import com.hzjytech.operation.entity.DetailTaskInfo;
import com.hzjytech.operation.entity.PersonInfo;
import com.hzjytech.operation.entity.User;
import com.hzjytech.operation.http.JijiaHttpSubscriber;
import com.hzjytech.operation.http.RxBus;
import com.hzjytech.operation.http.SubscriberOnErrorListener;
import com.hzjytech.operation.http.SubscriberOnNextListener;
import com.hzjytech.operation.http.UpQiNiuWithCompress;
import com.hzjytech.operation.http.api.TaskApi;
import com.hzjytech.operation.inter.OnCutDownTimeClickListener;
import com.hzjytech.operation.inter.OnRemoveClickListener;
import com.hzjytech.operation.inter.OnViewItemClickListener;
import com.hzjytech.operation.utils.AppUtil;
import com.hzjytech.operation.utils.TimeUtil;
import com.hzjytech.operation.utils.UserUtils;
import com.hzjytech.operation.widgets.TitleBar;
import com.umeng.analytics.MobclickAgent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by hehongcan on 2017/6/19.
 */
public class DetailTaskActivity extends BaseActivity implements OnViewItemClickListener, OnRemoveClickListener, OnCutDownTimeClickListener {
    private static final int REQUEST_COMMENT = 222;
    @BindView(R.id.titleBar)
    TitleBar mTitleBar;
    @BindView(R.id.rv_detail_task)
    RecyclerView mRvDetailTask;
    @BindView(R.id.tv_comment)
    TextView mTvComment;
    @BindView(R.id.tv_change_state)
    TextView mTvChangeState;
    @BindView(R.id.pcfl_detail_task)
    PtrClassicFrameLayout mPcflDetailTask;
    @BindView(R.id.ll_bottom)
    LinearLayout mLlBottom;
    private DetailTaskAdapter mAdapter;
    private int mTaskId;
    private DetailTaskInfo mDetailTaskInfo;
    private boolean isChanged = false;
    private RecyclerAdapterWithHF mWrapAdapter;
    List<PersonInfo> allPersons=new ArrayList();
    private PtrHandler ptrDefaultHandler = new PtrDefaultHandler() {
        @Override
        public void onRefreshBegin(PtrFrameLayout frame) {
            initData();
        }
    };
    private User mUserInfo;
    private List<String> mSelectedNames;
    private Subscription mSubscribe;

    @Override
    protected int getResId() {
        return R.layout.activity_detail_task;
    }

    @Override
    protected void initView() {
        initTitle();
        initIntent();
        initRecyclerView();
        initPtcr();
        initData();
        initListener();
    }

    private void initIntent() {
        Intent intent = getIntent();
        mTaskId = intent.getIntExtra("taskId", -1);
    }

    private void initListener() {
        mAdapter.setOnViewItemClickListener(this);
        mAdapter.setOnRemoveClickListener(this);
        mAdapter.setOnCutDownTimeClickListener(this);
    }

    private void initPtcr() {
        mPcflDetailTask.setPtrHandler(ptrDefaultHandler);//设置下拉监听
        mPcflDetailTask.setLoadMoreEnable(false);//设置可以加载更多
    }

    /**
     * 网络请求初始化数据
     */
    private void initData() {
        mUserInfo = UserUtils.getUserInfo();
        Observable<DetailTaskInfo> observable = TaskApi.getDetailTaskInfo(mUserInfo.getToken(),
                mTaskId);
        JijiaHttpSubscriber subscriber = JijiaHttpSubscriber.buildSubscriber(this)
                .setOnNextListener(new SubscriberOnNextListener<DetailTaskInfo>() {
                    @Override
                    public void onNext(DetailTaskInfo info) {
                        mLlBottom.setVisibility(View.VISIBLE);
                        if (info == null) {
                            return;
                        }
                        mDetailTaskInfo = info;
                        setState(info.getStatus());
                        Log.e("duty",mDetailTaskInfo.getDuty().toString());
                        //任务联系人列表
                        mSelectedNames = mDetailTaskInfo.getDutyNames();
                        mAdapter.setData(info,mSelectedNames);

                    }
                })
                .setOnErrorListener(new SubscriberOnErrorListener() {
                    @Override
                    public void onError(Throwable e) {
                       mLlBottom.setVisibility(View.GONE);
                    }
                })
                .setProgressDialog(mPcflDetailTask.isRefreshing() ? null : mProgressDlg)
                .setPtcf(mPcflDetailTask)
                .build();
        observable.subscribe(subscriber);
    }

    /**
     * 根據當前任務狀態修改按钮提示
     *
     * @param status
     */
    private void setState(int status) {
        switch (status) {
            case 1:
                mTvChangeState.setText(R.string.finish_tag);
                break;
            case 2:
                mTvChangeState.setText(R.string.unfinish_tag);
                break;
        }
    }

    private void initRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,
                false);
        mRvDetailTask.setLayoutManager(manager);
        mAdapter = new DetailTaskAdapter(this, null);
        mWrapAdapter = new RecyclerAdapterWithHF(mAdapter);
        mRvDetailTask.setAdapter(mWrapAdapter);
    }

    private void initTitle() {
        mTitleBar.setTitle(R.string.task_detail);
        mTitleBar.setLeftImageResource(R.drawable.icon_left);
        mTitleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isChanged) {
                    RxBus.getDefault()
                            .send(BusMessage.CHANGED);
                }

                finish();
            }
        });
        //先设置底部ll不可见，如果数据可用则设置可见
        mLlBottom.setVisibility(View.GONE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void onBackPressed() {
        if (isChanged) {
            RxBus.getDefault()
                    .send(BusMessage.CHANGED);
        }
        finish();
        super.onBackPressed();
    }

    @OnClick({R.id.tv_comment, R.id.tv_change_state})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_comment:
                Intent intent = new Intent(this, CommentActivity.class);
                startActivityForResult(intent, REQUEST_COMMENT);
                break;
            case R.id.tv_change_state:
                if(AppUtil.isFastClick()){
                    return;
                }
                changeStatus();
                //修改数据，通知taskFragment刷新
                isChanged = true;
                break;
        }
    }

    /**
     * 修改任务状态
     */
    private void changeStatus() {
        String token = UserUtils.getUserInfo()
                .getToken();
        int status = mDetailTaskInfo.getStatus();
        int changedStatus = 0;
        if (status == 1) {
            changedStatus = 2;
        } else if (status == 2) {
            changedStatus = 1;
        }
        Observable<Boolean> observable = TaskApi.changeTaskStatus(token, changedStatus, mTaskId);
        JijiaHttpSubscriber subscriber = JijiaHttpSubscriber.buildSubscriber(this)
                .setOnNextListener(new SubscriberOnNextListener<Boolean>() {
                    @Override
                    public void onNext(Boolean b) {
                        if (b) {
                            // showTip(R.string.change_state_success);
                            if(mRvDetailTask!=null){
                                mRvDetailTask.scrollToPosition(0);
                            }

                            mPcflDetailTask.autoRefresh();
                        } else {
                            showTip(R.string.change_state_failed);
                        }
                    }
                })
                .build();
        observable.subscribe(subscriber);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_COMMENT && resultCode == CommentActivity.RESULT_OK &&
                data != null) {
            //评论的字符串内容
            String mCommentString = data.getStringExtra("str");
            //评论携带的图片
            ArrayList<String> mUrls = data.getStringArrayListExtra("url");
            commitCommnet(mCommentString, mUrls);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 添加评论
     *
     * @param mCommentString
     * @param urls
     */
    private void commitCommnet(final String mCommentString, ArrayList<String> urls) {

        if (urls == null || urls.size() == 0) {
            commit(mCommentString, null);
            return;
        }
        UpQiNiuWithCompress upQiNiuWithCompress = new UpQiNiuWithCompress(this);
        upQiNiuWithCompress.upLoadPic(urls);
        mSubscribe = RxBus.getDefault()
                .toObservable()
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object event) {
                        if (event instanceof ArrayList) {
                            ArrayList<String> mUrls = (ArrayList<String>) event;
                            commit(mCommentString, mUrls);

                        }
                    }
                });

    }

    /**
     * 提交评论
     * @param mCommentString
     * @param mUrls
     */
    int index=1;
    private void commit(String mCommentString, ArrayList<String> mUrls) {
        if(mSubscribe!=null){
            mSubscribe.unsubscribe();
        }
        Log.e("commitTimes",index+++"");
        String token = UserUtils.getUserInfo()
                .getToken();
        Observable<Boolean> observable = TaskApi.addComment(token, mCommentString, mUrls, mTaskId);
        JijiaHttpSubscriber subscriber = JijiaHttpSubscriber.buildSubscriber(this)
                .setOnNextListener(new SubscriberOnNextListener<Boolean>() {
                    @Override
                    public void onNext(Boolean b) {
                        // mProgressDlg.hide();
                        if (b) {
                            // showTip(R.string.add_comment_success);
                            //重新加载数据
                            if(mRvDetailTask!=null){
                                mRvDetailTask.scrollToPosition(0);
                            }
                            if(mPcflDetailTask!=null){
                                mPcflDetailTask.autoRefresh();
                            }

                            //initData();
                        } else {
                            showTip(R.string.add_comment_failed);
                        }
                    }
                })
                .setOnErrorListener(new SubscriberOnErrorListener() {
                    @Override
                    public void onError(Throwable e) {
                        //   mProgressDlg.hide();
                    }
                })
                .build();
        observable.subscribe(subscriber);
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

    /**
     * 点击添加负责人，根据机器id，获取联系人列表
     */
    @Override
    public void click() {
        if(allPersons.size()>0){
            selectPerson();
        }else{
            getPersonByMachineId();
        }

    }

    /**
     *打开选择联系人的弹窗
     * 1、任务已经有的负责人，增加，移除（可能无负责人）
     * 2、变化后的负责人列表 增加，移除
     */
    private void selectPerson() {

        List<String> allNameList = new ArrayList<>();
        for (PersonInfo personInfo : allPersons) {
            allNameList.add(personInfo.getName());
        }
        final List<String> unSelectedNames=new ArrayList<>();
        unSelectedNames.addAll(allNameList);
        unSelectedNames.removeAll(mSelectedNames);
        if(unSelectedNames.size()==0){
            return;
        }
        //条件选择器
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this,
                new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        addPerson(unSelectedNames.get(options1),true);

                    }
                }).build();
        pvOptions.setPicker(unSelectedNames);
        pvOptions.show();
    }

    /**
     *根据机器获取联系人列表
     */
    private void getPersonByMachineId() {
        int machineId=mDetailTaskInfo.getVendingMachineDO().getId();
        Observable<List<PersonInfo>> bObservable = TaskApi.getPersonList(mUserInfo.getToken(), machineId);
        JijiaHttpSubscriber subscriber = JijiaHttpSubscriber.buildSubscriber(this)
                .setOnNextListener(new SubscriberOnNextListener<List<PersonInfo>>() {
                    @Override
                    public void onNext(List<PersonInfo> list) {
                        allPersons.clear();
                        allPersons.addAll(list);
                        selectPerson();


                    }
                })
                .setProgressDialog(mProgressDlg)
                .build();
        bObservable.subscribe(subscriber);
    }

    /**
     * 点击移除联系人
     * @param position
     */
    @Override
    public void onRemoveClick(int position) {
        addPerson(mSelectedNames.get(position),false);
    }

    /**
     * 网络请求添加删除联系人
     * @param s 联系人名字
     * @param b true:添加 false :移除
      */
    private void addPerson(final String s, final boolean b) {
        List<DetailTaskInfo.Duty> duties = mDetailTaskInfo.getDuty();
        if(duties.size()==1&&!b){
            showTip(R.string.cannot_remove);
            return;
        }
        int id=-1;
        if(b){
            for (PersonInfo allPerson : allPersons) {
                if(s.equals(allPerson.getName())){
                    id=allPerson.getId();
                }
            }

        }else{
            for (DetailTaskInfo.Duty duty : duties) {
                if(duty.getDutyName().equals(s)){
                    id= Integer.parseInt(duty.getDutyId());
                }
            }
        }

        if (id ==-1) {
            showTip(R.string.change_person_failed);
            return;
        }
            Observable<Boolean> observable = TaskApi.changeTaskDutyNames(mUserInfo.getToken(),
                    mTaskId,
                    id,
                    b);
        JijiaHttpSubscriber subscriber = JijiaHttpSubscriber.buildSubscriber(this)
                .setOnNextListener(new SubscriberOnNextListener<Boolean>(){
                    @Override
                    public void onNext(Boolean o) {
                        if(o){
                            if(b){
                                mSelectedNames.add(s);
                                mAdapter.notifyDataSetChanged();
                            }else{
                                mSelectedNames.remove(s);
                                mAdapter.setDutyNames(mSelectedNames);
                            }
                            showTip(R.string.change_person_success);
                        }else{
                            showTip(R.string.change_person_failed);
                        }
                        mPcflDetailTask.autoRefresh();
                    }
                })
                .build();
        observable.subscribe(subscriber);
    }

    /**
     * 点击修改截止时间
     */
    @Override
    public void changeCutDownTimeClick() {
        TimePickerView tpTime = new TimePickerView.Builder(this,
                new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        if (isTimeCorrect(TimeUtil.dateToLong(date))) {
                            String s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
                            changeCutDownTime(s);
                        }
                    }
                }).build();

        tpTime.show();
    }
    /**
     * 网络请求修改截止时间
     * @param s
     */
    private void changeCutDownTime(final String s) {
        Observable<Boolean> observable = TaskApi.changeCutOffTime(mUserInfo.getToken(),
                mTaskId,
                s);
        JijiaHttpSubscriber subscriber = JijiaHttpSubscriber.buildSubscriber(this)
                .setOnNextListener(new SubscriberOnNextListener<Boolean>() {
                    @Override
                    public void onNext(Boolean b) {
                          if(b){
                              showTip(R.string.change_time_success);
                              if(mRvDetailTask!=null){
                                  mRvDetailTask.scrollToPosition(0);
                              }
                              mPcflDetailTask.autoRefresh();
                          }else{
                              showTip(R.string.change_time_failed);
                          }
                    }
                })
                .setProgressDialog(mProgressDlg)
                .build();
        observable.subscribe(subscriber);
    }

    //判断输入时间是否合法
    public boolean isTimeCorrect(long time) {
        if (time < System.currentTimeMillis()) {
            return false;
        }
        return true;
    }
}
