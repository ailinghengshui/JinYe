package com.hzjytech.operation.module.group;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
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
import com.hzjytech.operation.adapters.group.GroupAdapter;
import com.hzjytech.operation.base.BaseActivity;
import com.hzjytech.operation.constants.Constants;
import com.hzjytech.operation.entity.GroupInfo;
import com.hzjytech.operation.entity.GroupList;
import com.hzjytech.operation.entity.User;
import com.hzjytech.operation.http.JijiaHttpSubscriber;
import com.hzjytech.operation.http.RxBus;
import com.hzjytech.operation.http.SubscriberOnNextListener;
import com.hzjytech.operation.http.api.MachinesApi;
import com.hzjytech.operation.inter.OnInfoClickListener;
import com.hzjytech.operation.utils.CommonUtil;
import com.hzjytech.operation.utils.UserUtils;
import com.hzjytech.operation.widgets.TitleBar;
import com.hzjytech.operation.widgets.selectfrags.SelectActivity;
import com.umeng.analytics.MobclickAgent;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static com.hzjytech.operation.constants.Constants.EditType.EDIT_GROUP_FROM_GROUP;
import static com.hzjytech.operation.constants.Constants.EditType.EDIT_MACHINE_FROM_GROUP;
import static com.hzjytech.operation.constants.Constants.GroupClick.ADSSETTING;
import static com.hzjytech.operation.constants.Constants.GroupClick.GROUPADDMORE;
import static com.hzjytech.operation.constants.Constants.GroupClick.GROUPSETTING;
import static com.hzjytech.operation.constants.Constants.GroupClick.MACHINEADDMORE;
import static com.hzjytech.operation.constants.Constants.GroupClick.MACHINESETTING;
import static com.hzjytech.operation.constants.Constants.GroupClick.MONNEYDISCONUT;
import static com.hzjytech.operation.constants.Constants.GroupClick.MONNEYOFF;
import static com.hzjytech.operation.constants.Constants.GroupClick.PROMOTIONTEXT;
import static com.hzjytech.operation.constants.Constants.GroupRefresh.GROUPREFRESH;

/**
 * Created by hehongcan on 2017/5/2.
 */
public class DetailGroupActivity extends BaseActivity implements OnInfoClickListener {
    private static final int REQUEST_PROMOTION_TEXT = 111;
    private static final int REQUEST_MONEY_OFF_OR_DISCOUNT = 112;
    private static final int REQUEST_GROUP_EDIT = 113;
    private static final int REQUEST_MACHINE_EDIT = 114;
    @BindView(R.id.titleBar)
    TitleBar titleBar;
    @BindView(R.id.rv_group)
    RecyclerView rvGroup;
    @BindView(R.id.pcfl_group)
    PtrClassicFrameLayout pcflGroup;
    private GroupAdapter groupAdapter;
    private RecyclerAdapterWithHF mAdapter;
    private GroupInfo groupInfo;
    private PtrHandler ptrDefaultHandler=new PtrDefaultHandler() {
        @Override
        public void onRefreshBegin(PtrFrameLayout frame) {
            initData();
        }
    };
    private OnLoadMoreListener onLoadMoreListener=new OnLoadMoreListener() {
        @Override
        public void loadMore() {
            initData();
        }
    };
    private int groupId=-1;
    private JijiaHttpSubscriber mSubscriber;
    private ArrayList<GroupList> mGroupLists;
    private Subscription mSubscription;

    @Override
    protected int getResId() {
        return R.layout.activity_group;
    }

    @Override
    protected void initView() {
        initTitle();
        initRecyclerView();
        initPtrc();
        initData();
        initListeners();
    }

    /**
     * 初始化各种监听
     */
    private void initListeners() {
        groupAdapter.setOnEditInfoClickListener(this);
        mSubscription = RxBus.getDefault()
                .toObservable()
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        if (o .equals(GROUPREFRESH)) {
                            groupAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    /**
     * 初始化下拉刷新、上拉加载更多
     */
    private void initPtrc() {
        pcflGroup.setPtrHandler(ptrDefaultHandler);//设置下拉监听
        pcflGroup.setOnLoadMoreListener(onLoadMoreListener);//设置上拉监听
        pcflGroup.setLoadMoreEnable(false);//设置可以加载更多
    }

    /**
     * 初始化recyclerview
     */
    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvGroup.setLayoutManager(linearLayoutManager);
        groupAdapter = new GroupAdapter(this, null);
        mAdapter = new RecyclerAdapterWithHF(groupAdapter);
        rvGroup.setAdapter(mAdapter);
        mGroupLists = new ArrayList<>();

    }

    /**
     * 初始化数据
     */
    private void initData() {
        User userInfo = UserUtils.getUserInfo();
        Intent intent = getIntent();
        groupId = intent.getIntExtra("groupId", -1);
        Observable<GroupInfo> observable = MachinesApi.getGroupInfo(userInfo.getToken(), groupId);
        mSubscriber = JijiaHttpSubscriber.buildSubscriber(this).setOnNextListener(new SubscriberOnNextListener<GroupInfo>() {
            @Override
            public void onNext(GroupInfo groupInfo) {
              titleBar.setTitle(groupInfo.getBasicInfo().getName());
                DetailGroupActivity.this.groupInfo=groupInfo;
                groupAdapter.setGroupData(groupInfo);
                resolveResult();
            }
        }).setProgressDialog(pcflGroup.isRefreshing()||pcflGroup.isLoadingMore() ? null : mProgressDlg).setPtcf(pcflGroup).build();
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                mSubscriber);
    }

    private void resolveResult() {
        mGroupLists.clear();
        List<GroupInfo.SubGroupBean> subGroups = groupInfo.getSubGroups();
        for (GroupInfo.SubGroupBean subGroup : subGroups) {
            mGroupLists.add(new GroupList(subGroup.getId(),subGroup.getName(),false));
        }
    }

    /**
     * 初始化标题栏
     */
    private void initTitle() {
        titleBar.setLeftImageResource(R.drawable.icon_left);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleBar.setTitleColor(Color.WHITE);
        titleBar.setTitleBold(true);

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

    /**
     * PROMOTIONTEXT,MONNEYOFF,MONNEYDISCONUT,ADSSETTING,GROUPSETTING,MACHINESETTING
     * @param type
     */
    @Override
    public void click(int type) {
        switch (type){
            case PROMOTIONTEXT:
                editPrromotionText();
                break;
            case MONNEYOFF:
                editMonneyOff();
                break;
            case MONNEYDISCONUT:
                editMonneyDiscount();
                break;
            case ADSSETTING:
                editPhotos();
                break;
            case GROUPSETTING:
                editGroups();
                break;
            case GROUPADDMORE:
                addMoreGroup();
                break;
            case MACHINESETTING:
                editMachines();
                break;
            case MACHINEADDMORE:
                addMachines();
                break;
            default:
                break;
        }
    }



    /**
     * 编辑促销信息
     */
    private void editPrromotionText() {
        Intent intent = new Intent(this, EditPromotionTextActivity.class);
        intent.putExtra("groupId",groupId);
        startActivityForResult(intent,REQUEST_PROMOTION_TEXT);
    }
    /**
     * 满减设置
     */
    private void editMonneyOff() {
        Intent intent = new Intent(this, MoneyOffActivity.class);
        intent.putExtra("groupInfo",groupInfo);
        intent.putExtra("groupId",groupId);
        intent.putExtra("moneyType",MONNEYOFF);
        startActivityForResult(intent, REQUEST_MONEY_OFF_OR_DISCOUNT);
    }

    /**
     * 折扣设置
     */
    private void editMonneyDiscount() {
        Intent intent = new Intent(this, MoneyOffActivity.class);
        intent.putExtra("groupInfo",groupInfo);
        intent.putExtra("groupId",groupId);
        intent.putExtra("moneyType",MONNEYDISCONUT);
        startActivityForResult(intent, REQUEST_MONEY_OFF_OR_DISCOUNT);
    }

    /**
     * 修改广告图片
     */
    private void editPhotos() {

    }

    /**
     * 编辑包含机器
     */
    private void editMachines() {
        Intent intent = new Intent(DetailGroupActivity.this, SelectActivity.class);
        intent.putExtra("fragType",EDIT_MACHINE_FROM_GROUP);
        intent.putExtra("editOrAdd",GROUPSETTING);
        intent.putExtra("groupId",groupId);
        intent.putExtra("vmId",groupInfo.getBasicInfo().getVmTypeId());
        intent.putParcelableArrayListExtra("data",
                (ArrayList<GroupInfo.SubMachinesBean>) groupInfo.getSubMachines());
        startActivityForResult(intent,REQUEST_MACHINE_EDIT);
        startActivity(intent);
    }

    /**
     * 添加咖啡机
     */
    private void addMachines() {
        Intent intent = new Intent(DetailGroupActivity.this, SelectActivity.class);
        intent.putExtra("fragType",EDIT_MACHINE_FROM_GROUP);
        intent.putExtra("editOrAdd",GROUPADDMORE);
        intent.putExtra("groupId",groupId);
        intent.putExtra("vmId",groupInfo.getBasicInfo().getVmTypeId());
        intent.putParcelableArrayListExtra("data",
                (ArrayList<GroupInfo.SubMachinesBean>) groupInfo.getSubMachines());
        startActivityForResult(intent,REQUEST_MACHINE_EDIT);
    }
    /**
     * 编辑包含分组
     */
    private void editGroups() {
        Intent intent = new Intent(DetailGroupActivity.this, SelectActivity.class);
        intent.putExtra("fragType",EDIT_GROUP_FROM_GROUP);
        intent.putExtra("editOrAdd",GROUPSETTING);
        intent.putExtra("groupId",groupId);
        intent.putExtra("vmId",groupInfo.getBasicInfo().getVmTypeId());
        intent.putParcelableArrayListExtra("data",mGroupLists);
        startActivityForResult(intent,REQUEST_GROUP_EDIT);
    }

    /**
     * 包含分组为空，直接跳转添加分组界面
     */
    private void addMoreGroup() {
        Intent intent = new Intent(DetailGroupActivity.this, SelectActivity.class);
        intent.putExtra("fragType",EDIT_GROUP_FROM_GROUP);
        intent.putExtra("groupId",groupId);
        intent.putExtra("editOrAdd",GROUPADDMORE);
        intent.putExtra("vmId",groupInfo.getBasicInfo().getVmTypeId());
        startActivityForResult(intent,REQUEST_GROUP_EDIT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK&&data!=null){
            pcflGroup.autoRefresh();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CommonUtil.unSubscribeSubs(mSubscriber,mSubscription);
    }
}
