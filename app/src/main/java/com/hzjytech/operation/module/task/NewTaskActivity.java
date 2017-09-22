package com.hzjytech.operation.module.task;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.hzjytech.operation.R;
import com.hzjytech.operation.adapters.PicAdapter;
import com.hzjytech.operation.adapters.task.PersonAdapter;
import com.hzjytech.operation.base.BaseActivity;
import com.hzjytech.operation.constants.BusMessage;
import com.hzjytech.operation.constants.Constants;
import com.hzjytech.operation.entity.GroupInfo;
import com.hzjytech.operation.entity.MachineInfo;
import com.hzjytech.operation.entity.PersonInfo;
import com.hzjytech.operation.http.JijiaHttpResultZip;
import com.hzjytech.operation.http.JijiaHttpSubscriber;
import com.hzjytech.operation.http.JijiaRZField;
import com.hzjytech.operation.http.RxBus;
import com.hzjytech.operation.http.SubscriberOnCompletedListener;
import com.hzjytech.operation.http.SubscriberOnErrorListener;
import com.hzjytech.operation.http.SubscriberOnNextListener;
import com.hzjytech.operation.http.UpQiNiuWithCompress;
import com.hzjytech.operation.http.api.MachinesApi;
import com.hzjytech.operation.http.api.TaskApi;
import com.hzjytech.operation.inter.OnRemoveClickListener;
import com.hzjytech.operation.module.data.MachineSelectActivity;
import com.hzjytech.operation.utils.AppUtil;
import com.hzjytech.operation.utils.TimeUtil;
import com.hzjytech.operation.utils.UserUtils;
import com.hzjytech.operation.widgets.LinearLayout.LinearListView;
import com.hzjytech.operation.widgets.TitleBar;
import com.umeng.analytics.MobclickAgent;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.sql.Time;
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
import rx.functions.Func2;

/**
 * Created by hehongcan on 2017/6/14.
 */
public class NewTaskActivity extends BaseActivity implements OnRemoveClickListener {
    private static final int REQUEST_MACHINE = 123;
    private static final int REQUEST_COMMIT = 124;
    @BindView(R.id.titleBar)
    TitleBar mTitleBar;
    @BindView(R.id.tv_task_type)
    TextView mTvTaskType;
    @BindView(R.id.tv_task_code)
    TextView mTvTaskCode;
    @BindView(R.id.tv_task_group)
    TextView mTvTaskGroup;
    @BindView(R.id.tv_task_menu)
    TextView mTvTaskMenu;
    @BindView(R.id.tv_task_version)
    TextView mTvTaskVersion;
    @BindView(R.id.tv_task_location)
    TextView mTvTaskLocation;
    @BindView(R.id.ll_machine_info)
    LinearLayout mLlMachineInfo;
    @BindView(R.id.ll_person_container)
    LinearListView mLlPersonContainer;
    @BindView(R.id.tv_click_add_comment)
    TextView mTvClickAddComment;
    @BindView(R.id.rv_new_comment_pic)
    RecyclerView mRvNewCommentPic;
    @BindView(R.id.tv_add_machine)
    TextView mTvAddMachine;
    @BindView(R.id.ll_more_person)
    LinearLayout mLlMorePerson;
    @BindView(R.id.ll_click_add_comment)
    LinearLayout mLlClickAddComment;
    @BindView(R.id.tv_cut_down_time)
    TextView mTvCutDownTime;
    private ArrayList<Object> typeList;
    private OptionsPickerView pvOptions;
    private int machineId;
    private List<PersonInfo> mPersonList;
    private ArrayList<Object> nameList;
    private List<PersonInfo> mSelectPersonList = new ArrayList<>();
    private PersonAdapter mAdapter;
    private PicAdapter mPicAdapter;
    private ArrayList<String> mUrls;
    private int mImageSize;
    private DisplayMetrics mDm;
    //1、维修任务2、加料任务 3、其他任务
    private String mCommentString = null;
    private int type = 1;
    private static final String TAG = "NewTaskActivity";
    private ArrayList<String> upimg_key_list = new ArrayList<>();
    private ArrayList<Integer> mDutyIds;
    private String cutDownTime;
    private Subscription mSubscribe;


    @Override
    protected int getResId() {
        return R.layout.activity_new_task;
    }

    @Override
    protected void initView() {
        initTitle();
        initListView();
        initPicRey();
    }

    /**
     * 初始化评论图片recyclerview
     */
    private void initPicRey() {
        mDm = getResources().getDisplayMetrics();
        mImageSize = (int) ((mDm.widthPixels - 62 * mDm.density) / 3);
        mPicAdapter = new PicAdapter(this, mImageSize, null);
        GridLayoutManager layoutManager = new GridLayoutManager(this,
                3,
                GridLayoutManager.VERTICAL,
                false);
        mRvNewCommentPic.setLayoutManager(layoutManager);
        mRvNewCommentPic.setAdapter(mPicAdapter);
    }

    public void addNewButtonAndRefresh() {
        int size = mPicAdapter.getItemCount();
        int rows = 1;
        if (size > 6) {
            rows = 3;
        } else if (size > 3) {
            rows = 2;
        }
        mRvNewCommentPic.getLayoutParams().height = Math.round(mImageSize * rows + rows * 10 *
                mDm.density);
        mPicAdapter.notifyDataSetChanged();
    }

    /**
     * 初始化负责人列表
     */
    private void initListView() {
        mLlPersonContainer.setVisibility(View.VISIBLE);
        mLlPersonContainer.setOrientation(LinearLayout.VERTICAL);
        mAdapter = new PersonAdapter(this, mSelectPersonList);
        mLlPersonContainer.setAdapter(mAdapter);
    }

    private void initTitle() {
        mTitleBar.setLeftTextColor(Color.WHITE);
        mTitleBar.setLeftText(R.string.cancel);
        mTitleBar.setTitle(R.string.new_task);
        mTitleBar.setTitleColor(Color.WHITE);
        mTitleBar.addAction(new TitleBar.TextAction(getResources().getString(R.string.restore)) {
            @Override
            public void performAction(View view) {
                if (AppUtil.isFastClick())
                    return;
                commit();
            }
        });
        mTitleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 提交任务
     */
    private void commit() {
        upLoadPic();

    }

    /**
     * 图片上传完毕后上传新建任务所有信息
     */
    private void commitAllComment() {
        if(mSubscribe!=null){
            mSubscribe.unsubscribe();
        }
        String token = UserUtils.getUserInfo()
                .getToken();
        if (mSelectPersonList != null && mSelectPersonList.size() != 0) {
            mDutyIds = new ArrayList<>();
            for (PersonInfo personInfo : mSelectPersonList) {
                mDutyIds.add(personInfo.getId());
            }
        }

        Observable<Boolean> observable = TaskApi.creatNewTask(token,
                machineId,
                mDutyIds,
                mCommentString,
                upimg_key_list,
                type,
                cutDownTime);
        JijiaHttpSubscriber subscriber = JijiaHttpSubscriber.buildSubscriber(this)
                .setOnNextListener(new SubscriberOnNextListener<Boolean>() {
                    @Override
                    public void onNext(Boolean success) {
                        //mProgressDlg.dismiss();
                        if (success) {
                            showTip(R.string.new_task_success);
                            RxBus.getDefault()
                                    .send(BusMessage.CHANGED);
                            finish();
                        } else {
                            // mProgressDlg.dismiss();
                        }
                    }
                })
                .setOnCompletedListener(new SubscriberOnCompletedListener() {
                    @Override
                    public void onCompleted() {
                        mProgressDlg.dismiss();
                    }
                })
                .build();
        observable.subscribe(subscriber);

    }

    public void upLoadPic() {
        final int[] index = {1};
        // mProgressDlg.show();
        if (machineId == 0) {
            showTip(R.string.please_check_machine);
            mProgressDlg.dismiss();
            return;
        }
        if (mUrls == null || mUrls.size() == 0) {
            commitAllComment();
            return;
        }
        UpQiNiuWithCompress upQiNiuWithCompress = new UpQiNiuWithCompress(this);
        upQiNiuWithCompress.upLoadPic(mUrls);
        mSubscribe = RxBus.getDefault()
                .toObservable()
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object event) {
                        if (event instanceof ArrayList) {
                            upimg_key_list.addAll((ArrayList<String>) event);
                            commitAllComment();
                        }
                    }
                });

    }


    @OnClick({R.id.ll_more_task_type, R.id.ll_select_machines, R.id.ll_more_person, R.id
            .ll_click_add_comment, R.id.ll_cut_down_time})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_more_task_type:
                selectType();
                break;
            case R.id.ll_select_machines:
                selectSingleMachine();
                break;
            case R.id.ll_more_person:
                selectPerson();
                break;
            case R.id.ll_click_add_comment:
                startComment();
                break;
            case R.id.ll_cut_down_time:
                changeCutDownTime();
                break;
        }
    }

    /**
     * 选择截止时间
     */
    private void changeCutDownTime() {
        TimePickerView tpTime = new TimePickerView.Builder(this,
                new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        if (isTimeCorrect(TimeUtil.dateToLong(date))) {
                            String s = new SimpleDateFormat("yyyy/MM/dd HH:mm").format(date);
                            mTvCutDownTime.setText(s);
                            cutDownTime = DateTime.parse(s, DateTimeFormat.forPattern("yyyy/MM/dd HH:mm")).toString("yyyy-MM-dd HH:mm:ss");
                        }
                    }
                }).build();

        tpTime.show();
    }

    //判断输入时间是否合法
    public boolean isTimeCorrect(long time) {
        if (time < System.currentTimeMillis()) {
            return false;
        }
        return true;
    }

    /**
     * 选择负责人
     */
    private void selectPerson() {
        if (mPersonList == null || mPersonList.size() == 0) {
            showTip(R.string.please_select_machine);
            return;
        }
        nameList = new ArrayList<>();
        for (PersonInfo personInfo : mPersonList) {
            nameList.add(personInfo.getName());
        }
        //条件选择器
        pvOptions = new OptionsPickerView.Builder(this,
                new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        mSelectPersonList.add(mPersonList.get(options1));
                        mPersonList.remove(options1);
                        nameList.remove(options1);
                        addPerson(mSelectPersonList);
                    }
                }).build();
        pvOptions.setPicker(nameList);
        pvOptions.show();
    }

    /**
     * 添加选择的负责人到布局中
     *
     * @param selectPersonList
     */
    private void addPerson(List<PersonInfo> selectPersonList) {
        if (selectPersonList != null && selectPersonList.size() > 0) {
            mAdapter.setData(selectPersonList);
            mAdapter.setOnRemoveClickListener(this);
        } else {
            mLlPersonContainer.setVisibility(View.GONE);
        }
    }


    /**
     * 选择一台咖啡机
     */
    private void selectSingleMachine() {
        Intent intent = new Intent(this, MachineSelectActivity.class);
        intent.putExtra("name", Constants.task);
        startActivityForResult(intent, REQUEST_MACHINE);
    }

    /**
     * 打开选择器，选择相应的任务类型
     */
    private void selectType() {
        typeList = new ArrayList<>();
        typeList.add(getResources().getString(R.string.fix_task));
        typeList.add(getResources().getString(R.string.feed_task));
        typeList.add(getResources().getString(R.string.other_task));
        //条件选择器
        pvOptions = new OptionsPickerView.Builder(this,
                new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        switch (options1) {
                            case 0:
                                mTvTaskType.setText(getResources().getString(R.string.fix_task));
                                type = 1;
                                break;
                            case 1:
                                mTvTaskType.setText(getResources().getString(R.string.feed_task));
                                type = 2;
                                break;
                            case 2:
                                mTvTaskType.setText(getResources().getString(R.string.other_task));
                                type = 3;
                                break;
                            default:
                                break;
                        }
                    }
                }).build();
        pvOptions.setPicker(typeList);
        pvOptions.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_MACHINE && resultCode == MachineSelectActivity
                .SELECTED_MACHIES && data != null) {
            mTvAddMachine.setText(R.string.change_machine);
            ArrayList<GroupInfo.SubMachinesBean> machineList = (ArrayList<GroupInfo
                    .SubMachinesBean>) data.getSerializableExtra(
                    "list");
            machineId = machineList.get(0)
                    .getId();
            queryMachineInfo(machineId);
        } else if (requestCode == REQUEST_COMMIT && resultCode == CommentActivity.RESULT_OK &&
                data != null) {
            //评论的字符串内容
            mCommentString = data.getStringExtra("str");
            //评论携带的图片
            mUrls = data.getStringArrayListExtra("url");
            setCommentsLayout(mCommentString, mUrls);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 设置评论区域内容
     *
     * @param commentString
     * @param urls
     */
    private void setCommentsLayout(String commentString, ArrayList<String> urls) {
        mTvClickAddComment.setText(commentString);
        mPicAdapter.setData(urls);
        addNewButtonAndRefresh();
    }

    /**
     * 根据machineId获取咖啡机的详细资料
     * 同时根据机器id获取负责人列表
     *
     * @param machineId
     */
    private void queryMachineInfo(int machineId) {
        String token = UserUtils.getUserInfo()
                .getToken();
        Observable<MachineInfo> aObservable = MachinesApi.getSingleMachieDetail(token, machineId);
        Observable<List<PersonInfo>> bObservable = TaskApi.getPersonList(token, machineId);
        Observable<ResultZip> zipObservable = Observable.zip(aObservable,
                bObservable,
                new Func2<MachineInfo, List<PersonInfo>, ResultZip>() {


                    @Override
                    public ResultZip call(
                            MachineInfo machineInfo, List<PersonInfo> personInfos) {
                        return new ResultZip(machineInfo, personInfos);
                    }
                });
        JijiaHttpSubscriber subscriber = JijiaHttpSubscriber.buildSubscriber(this)
                .setOnNextListener(new SubscriberOnNextListener<ResultZip>() {
                    @Override
                    public void onNext(ResultZip zip) {
                        if (zip != null) {
                            MachineInfo machineInfo = zip.mMachineInfo;
                            MachineInfo.BasicInfoBean basicInfo = machineInfo.getBasicInfo();
                            setMachineInfo(basicInfo);
                            mPersonList = zip.mPersonList;
                        }

                    }
                })
                .setProgressDialog(mProgressDlg)
                .build();
        zipObservable.subscribe(subscriber);

    }

    /**
     * 根据获取的咖啡机详情，设置界面
     *
     * @param info
     */
    public void setMachineInfo(MachineInfo.BasicInfoBean info) {
        if (info != null) {
            mLlMachineInfo.setVisibility(View.VISIBLE);
            mTvTaskCode.setText("咖啡机编号：" + info.getName());
            mTvTaskGroup.setText("促销分组：" + (info.getGroupName()==null?"":info.getGroupName()));
            mTvTaskMenu.setText("菜单：" + (info.getMenuName()==null?"":info.getMenuName()));
            mTvTaskVersion.setText("当前版本：" + info.getVersion());
            mTvTaskLocation.setText(info.getAddress());
        } else {
            mLlMachineInfo.setVisibility(View.GONE);
        }
    }

    /**
     * 点击移除负责人
     *
     * @param position
     */
    @Override
    public void onRemoveClick(int position) {
        PersonInfo removePerson = mSelectPersonList.remove(position);
        mPersonList.add(removePerson);
        mAdapter.setData(mSelectPersonList);
        mAdapter.notifyDataSetInvalidated();
    }

    /**
     * 打开评论界面
     */
    private void startComment() {
        Intent intent = new Intent(this, CommentActivity.class);
        startActivityForResult(intent, REQUEST_COMMIT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private class ResultZip extends JijiaHttpResultZip {
        @JijiaRZField
        MachineInfo mMachineInfo;
        @JijiaRZField
        List<PersonInfo> mPersonList;

        public ResultZip(MachineInfo mMachineInfo, List<PersonInfo> mPersonList) {
            this.mMachineInfo = mMachineInfo;
            this.mPersonList = mPersonList;
        }
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
