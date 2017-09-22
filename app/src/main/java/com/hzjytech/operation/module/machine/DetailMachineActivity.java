package com.hzjytech.operation.module.machine;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bigkoo.pickerview.OptionsPickerView;
import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.PtrHandler;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hzjytech.operation.R;
import com.hzjytech.operation.adapters.home.DetailMachineAdapter;
import com.hzjytech.operation.base.BaseActivity;
import com.hzjytech.operation.constants.Constants;
import com.hzjytech.operation.entity.AdminInfo;
import com.hzjytech.operation.entity.GroupList;
import com.hzjytech.operation.entity.MachineInfo;
import com.hzjytech.operation.entity.MenuList;
import com.hzjytech.operation.entity.RoleInfo;
import com.hzjytech.operation.entity.User;
import com.hzjytech.operation.http.JijiaHttpSubscriber;
import com.hzjytech.operation.http.SubscriberOnCompletedListener;
import com.hzjytech.operation.http.SubscriberOnNextListener;
import com.hzjytech.operation.http.api.AuthorizationApi;
import com.hzjytech.operation.http.api.GroupApi;
import com.hzjytech.operation.http.api.MachinesApi;
import com.hzjytech.operation.http.api.MenuApi;
import com.hzjytech.operation.inter.OnInfoClickListener;
import com.hzjytech.operation.inter.OnRoleClicklistener;
import com.hzjytech.operation.inter.OnSbCheckChangeListener;
import com.hzjytech.operation.utils.CommonUtil;
import com.hzjytech.operation.utils.UserUtils;
import com.hzjytech.operation.widgets.TitleBar;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by hehongcan on 2017/4/26.
 */
public class DetailMachineActivity extends BaseActivity implements OnInfoClickListener, OnSbCheckChangeListener, OnRoleClicklistener {
    private static final int REQUEST_TIME = 111;
    private static final int REQUEST_ROLE_USERS = 112;
    @BindView(R.id.titleBar)
    TitleBar titleBar;
    @BindView(R.id.rv_single_machine_detail)
    RecyclerView rvSingleMachineDetail;
    @BindView(R.id.pcfl_detail)
    PtrClassicFrameLayout pcflDetail;
    private MachineInfo machieInfo;
    private DetailMachineAdapter singleMachineAdapter;
    private PtrHandler ptrDefaultHandler = new PtrDefaultHandler() {
        @Override
        public void onRefreshBegin(PtrFrameLayout frame) {
            initData();
        }
    };


    private RecyclerAdapterWithHF mAdapter;
    private JijiaHttpSubscriber subscriber;
    private List<GroupList> groups;
    private JijiaHttpSubscriber mGroupSubscriber;
    private ArrayList<String> mGroupNameList;
    private List<MenuList> menus;
    private ArrayList<String> mMenuNameList;
    private ArrayList<String> mDrinkTypes;
    private JijiaHttpSubscriber mMenuSubscriber;
    private JijiaHttpSubscriber mRoleSubscriber;
    private List<RoleInfo> mRoles;
    private List<Integer> mSelectIds;
    private ArrayList<Integer> mRoleIdsList;
    private int mMachineId;
    private JijiaHttpSubscriber mUpLoadSubscriber;
    private boolean mBasicInfoAuth;
    private boolean mStaffInfoAuth;
    private boolean restoreAuth;

    @Override
    protected int getResId() {
        return R.layout.activity_single_detail_machine;
    }

    @Override
    protected void initView() {
        initTitle();
        initReyclerView();
        initPtr();
        getRoleListByNet();
        initListener();

    }

    private void initListener() {
        singleMachineAdapter.setOnInfoClickListener(this);
        singleMachineAdapter.setOnSbCheckChangeLisener(this);
        singleMachineAdapter.setOnRoleClickListener(this);
    }

    /**
     * 初始化上拉加载更多
     */

    private void initPtr() {
        pcflDetail.setPtrHandler(ptrDefaultHandler);//设置下拉监听
        pcflDetail.setLoadMoreEnable(false);//设置可以加载更多
    }

    /**
     * 初始化recyclerview
     */
    private void initReyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvSingleMachineDetail.setLayoutManager(linearLayoutManager);
        singleMachineAdapter = new DetailMachineAdapter(this, null);
        mAdapter = new RecyclerAdapterWithHF(singleMachineAdapter);
        rvSingleMachineDetail.setAdapter(mAdapter);

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

    private void initData() {
        String token = UserUtils.getUserInfo()
                .getToken();
        Intent intent = getIntent();
        mMachineId = intent.getIntExtra("machineId", -1);
        if (subscriber == null || subscriber.isUnsubscribed()) {
            Observable<MachineInfo> observable = MachinesApi.getSingleMachieDetail(token,
                    mMachineId);
            subscriber = JijiaHttpSubscriber.buildSubscriber(this)
                    .setOnNextListener(new SubscriberOnNextListener<MachineInfo>() {
                        @Override
                        public void onNext(MachineInfo machineInfo) {
                            titleBar.setTitle(machineInfo.getBasicInfo()
                                    .getName());
                            DetailMachineActivity.this.machieInfo = machineInfo;
                            resolveAuth();
                            initFileds();
                            singleMachineAdapter.setData(machineInfo,mRoleIdsList,mRoles,mMachineId);
                        }
                    })
                    .setPtcf(pcflDetail)
                    .setProgressDialog(pcflDetail.isRefreshing() ? null : mProgressDlg)
                    .build();
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(subscriber);
        }
    }

    /**
     * 处理权限问题 1.0保存权限 1.1基础信息权限 1.2添加角色权限
     */
    private void resolveAuth() {
        List<String> authList = machieInfo.getAuth();
        restoreAuth=true;
        mBasicInfoAuth = true;
        mStaffInfoAuth = true;
        if(!authList.contains("1.0")){
            restoreAuth=false;
        }
        if(!authList.contains("1.1")){
            mBasicInfoAuth=false;
        }
        if(!authList.contains("1.2")){
            mStaffInfoAuth=false;
        }
        singleMachineAdapter.setAuths(mBasicInfoAuth,mStaffInfoAuth);
    }

    /**
     * 上传数据并刷新数据
     */
    private void upLoadData() {
        //清空未使用的角色位置
        defineUnselectRoles();
        /**
         * 处理上传数据
         */
        Gson gson = new Gson();
        MachineInfo.BasicInfoBean basicInfo = machieInfo.getBasicInfo();
        String basicJson = gson.toJson(basicInfo);
        List<MachineInfo.StaffInfoBean> staffInfo = machieInfo.getStaffInfo();
        String staffJson = gson.toJson(staffInfo);
        List<MachineInfo.AlertBean> alertInfo = machieInfo.getAlert();
        String alertJson = gson.toJson(alertInfo);
        MachineInfo.OtherInfoBean otherInfo = machieInfo.getOtherInfo();
        String otherJson = gson.toJson(otherInfo);
        MachineInfo.CostInfoBean costInfo = machieInfo.getCostInfo();
        String s4 = gson.toJson(costInfo);
        JsonObject json = gson.fromJson(s4, JsonObject.class);
        JsonObject cosumeJson = new JsonObject();
        JsonElement groundCost = json.get("groundCost");
        cosumeJson.addProperty("groundCost",groundCost.toString());
        JsonElement operationCost = json.get("operationCost");
        cosumeJson.addProperty("operationCost",operationCost.toString());
        JsonElement fourGCost = json.get("fourGCost");
        cosumeJson.addProperty("fourGCost",fourGCost.toString());
        JsonElement deprecitionYear = json.get("deprecitionYear");
        cosumeJson.addProperty("deprecitionYear",deprecitionYear.toString());
        JsonElement machineCost = json.get("machineCost");
        cosumeJson.addProperty("machineCost",machineCost.toString());
        String token=UserUtils.getUserInfo().getToken();
        Observable<Object> upLoadObservable = MachinesApi.uploadMachineInfo(token,
                basicJson,
                staffJson,
                alertJson,
                otherJson,
                mMachineId,
                cosumeJson.toString());
        mUpLoadSubscriber = JijiaHttpSubscriber.buildSubscriber(this)
                .setOnNextListener(new SubscriberOnNextListener() {
                    @Override
                    public void onNext(Object o) {

                    }
                }).setOnCompletedListener(new SubscriberOnCompletedListener() {
                    @Override
                    public void onCompleted() {
                        pcflDetail.autoRefresh();
                    }
                })
                .setProgressDialog(mProgressDlg)
                .build();
        upLoadObservable.subscribe(mUpLoadSubscriber);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CommonUtil.unSubscribeSubs(subscriber, mGroupSubscriber,mMenuSubscriber,mRoleSubscriber,mUpLoadSubscriber);

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
     * 添加职位
     */
    private void addRole() {
            selectRole();
    }

    /**
     * 网络获取角色列表
     */
    private void getRoleListByNet() {
        Observable<List<RoleInfo>> observable = AuthorizationApi.getRoleList(UserUtils.getUserInfo()
                .getToken());
        mRoleSubscriber = JijiaHttpSubscriber.buildSubscriber(this)
                .setOnNextListener(new SubscriberOnNextListener<List<RoleInfo>>() {
                    @Override
                    public void onNext(List<RoleInfo> roles) {
                        mRoles=roles;
                        initData();
                    }
                })
                .setProgressDialog(mProgressDlg)
                .build();
        observable.subscribe(mRoleSubscriber);
    }

    /**
     * 添加角色，从总角色中剔除已有角色
     */
    private void selectRole() {
        ArrayList<Integer> notExistRoles = new ArrayList<>();
        final ArrayList<String> notExistRolesName = new ArrayList<>();
        for (RoleInfo role : mRoles) {
           notExistRoles.add(role.getId());
        }
        notExistRoles.removeAll(mRoleIdsList);
        for (Integer notExistRole : notExistRoles) {
            for (RoleInfo role : mRoles) {
                if(notExistRole==role.getId()){
                    notExistRolesName.add(role.getName());
                }
            }
        }
        //条件选择器
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this,
                new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        for (RoleInfo role : mRoles) {
                            if(role.getName().equals(notExistRolesName.get(options1))){
                                mRoleIdsList.add(role.getId());
                                singleMachineAdapter.notifyDataSetChanged();
                                click(role.getId(),null,role.getName());
                            }
                        }

                    }
                }).build();
        pvOptions.setPicker(notExistRolesName);
        pvOptions.show();

    }

    /**
     * 修改饮品种类 0：冷饮 1：热饮
     */
    private void changeDrinkType() {
        if(mDrinkTypes==null){
            mDrinkTypes = new ArrayList<>();
        }
            mDrinkTypes.clear();
            mDrinkTypes.add("冷饮");
            mDrinkTypes.add("热饮");

        //条件选择器
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this,
                new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        machieInfo.getBasicInfo().setDefaultSet(options1);
                        upLoadData();
                    }
                }).build();
        pvOptions.setPicker(mDrinkTypes);
        pvOptions.show();
    }

    /**
     * 选择更换菜单
     */
    private void changeMenu() {
        if (mMenuNameList == null) {
            mMenuNameList = new ArrayList<>();
        } else {
            mMenuNameList.clear();
        }
        for (MenuList menu : menus) {
            mMenuNameList.add(menu.getName());
        }
        //条件选择器
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this,
                new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        int menuId = menus.get(options1)
                                .getId();
                        machieInfo.getBasicInfo().setMenuId(menuId);
                        machieInfo.getBasicInfo().setMenuName(menus.get(options1).getName());
                        upLoadData();
                    }
                }).build();
        pvOptions.setPicker(mMenuNameList);
        pvOptions.show();
    }

    /**
     * 网络获取菜单列表
     */
    private void getMenuListByNet() {
        User userInfo = UserUtils.getUserInfo();
        Observable<List<MenuList>> observable = MenuApi.getSingleMenuList(userInfo.getToken(), "");
        mMenuSubscriber = JijiaHttpSubscriber.buildSubscriber(this).setOnNextListener(new SubscriberOnNextListener<List<MenuList>>() {
            @Override
            public void onNext(List<MenuList> list) {
                menus=list;
                changeMenu();

            }
        }).setProgressDialog(mProgressDlg).build();
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                mMenuSubscriber);
    }

    /**
     * 网络获取促销分组列表
     */
    private void getGroupListByNet() {
        User userInfo = UserUtils.getUserInfo();
        Observable<List<GroupList>> observable = GroupApi.getSingleGroupList(userInfo.getToken(),
                "");
        mGroupSubscriber = JijiaHttpSubscriber.buildSubscriber(this)
                .setOnNextListener(new SubscriberOnNextListener<List<GroupList>>() {
                    @Override
                    public void onNext(List<GroupList> list) {
                        groups = list;
                        changeGroup();
                    }
                })
                .setProgressDialog(mProgressDlg)
                .build();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mGroupSubscriber);
    }

    /**
     * 选择更换促销分组
     */
    private void changeGroup() {
        if (mGroupNameList == null) {
            mGroupNameList = new ArrayList<>();
        } else {
            mGroupNameList.clear();
        }
        for (GroupList group : groups) {
            mGroupNameList.add(group.getName());
        }
        //条件选择器
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this,
                new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        int groupId = groups.get(options1)
                                .getId();
                        String groupName = groups.get(options1)
                                .getName();
                        machieInfo.getBasicInfo().setGroupId(groupId);
                        machieInfo.getBasicInfo().setGroupName(groupName);
                        upLoadData();
                    }
                }).build();
        pvOptions.setPicker(mGroupNameList);
        pvOptions.show();
    }
    /**
     * 初始化可能会发生变化的变量
     */
    private void initFileds() {
            defineUnselectRoles();
    }

    /**
     * 根据网络返回确定未被选择的角色
     */
    private void defineUnselectRoles() {
        mSelectIds = new ArrayList<>();
        List<MachineInfo.StaffInfoBean> staffInfo = machieInfo.getStaffInfo();
        for (MachineInfo.StaffInfoBean staffInfoBean : staffInfo) {
            if(!mSelectIds.contains(staffInfoBean.getRoleId())){
                mSelectIds.add(staffInfoBean.getRoleId());
            }
        }
        mRoleIdsList = new ArrayList<>();
        for (Integer selectId : mSelectIds) {
            mRoleIdsList.add(selectId);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==REQUEST_TIME&&resultCode==RESULT_OK&&data!=null){
            String openingTime = data.getStringExtra("startTime");
            String closingTime = data.getStringExtra("endTime");
            machieInfo.getBasicInfo().setOpeningTime(openingTime);
            machieInfo.getBasicInfo().setClosingTime(closingTime);
            upLoadData();
        }else if(requestCode==REQUEST_ROLE_USERS&&resultCode==RESULT_OK&&data!=null){
            final ArrayList<MachineInfo.StaffInfoBean> staff = data.getParcelableArrayListExtra("staff");
            final int roleId = data.getIntExtra("roleId", -1);
            Observable<MachineInfo.StaffInfoBean> observable = Observable.from(machieInfo
                    .getStaffInfo())
                    .filter(new Func1<MachineInfo.StaffInfoBean, Boolean>() {
                        @Override
                        public Boolean call(MachineInfo.StaffInfoBean staffInfoBean) {
                            return staffInfoBean.getRoleId()!=roleId;
                        }
                    });
            final ArrayList<MachineInfo.StaffInfoBean> others = new ArrayList<>();
            JijiaHttpSubscriber subscriber = JijiaHttpSubscriber.buildSubscriber(this)
                    .setOnNextListener(new SubscriberOnNextListener<MachineInfo.StaffInfoBean>() {


                        @Override
                        public void onNext(MachineInfo.StaffInfoBean bean) {
                           others.add(bean);
                        }
                    })
                    .setOnCompletedListener(new SubscriberOnCompletedListener() {
                        @Override
                        public void onCompleted() {
                            machieInfo.getStaffInfo().clear();
                            machieInfo.getStaffInfo().addAll(others);
                            machieInfo.getStaffInfo().addAll(staff);
                            upLoadData();
                        }
                    })
                    .setProgressDialog(mProgressDlg)
                    .build();
            observable.subscribe(subscriber);
        }else if(resultCode==RESULT_CANCELED){
            if(mBasicInfoAuth){
                upLoadData();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public void click(int type) {
        switch (type) {
            case Constants.InfoClick.GROUP:
                if (groups == null) {
                    getGroupListByNet();
                } else {
                    changeGroup();
                }
                break;
            case Constants.InfoClick.MENU:
                if(menus==null){
                    getMenuListByNet();
                }else{
                    changeMenu();
                }
                break;
            case Constants.InfoClick.TIMESET:
                Intent intent = new Intent(this, OperationTimeSetActivity.class);
                intent.putExtra("openingTime",machieInfo.getBasicInfo().getOpeningTime());
                intent.putExtra("closingTime",machieInfo.getBasicInfo().getClosingTime());
                startActivityForResult(intent,REQUEST_TIME);
                break;
            case Constants.InfoClick.DRINKTYPE:
                changeDrinkType();
                break;
            case Constants.InfoClick.ROLE:
                addRole();
                break;
        }
    }
    @Override
    public void check(int which, boolean status) {
        switch (which){
            case Constants.InfoSb.STATUS:
              machieInfo.getBasicInfo().setOperationStatus(status);
                break;
            case Constants.InfoSb.CLOSE:
                machieInfo.getBasicInfo().setClose(status);
                break;
            case Constants.InfoSb.LOCK:
                machieInfo.getBasicInfo().setLocked(status);
                break;
            case Constants.InfoSb.COFFEEME:
                machieInfo.getBasicInfo().setSupportCoffeeMe(status);
                break;
        }
      upLoadData();
    }

    @Override
    public void click(int roleId, List<AdminInfo> admins, String roleName) {
        Intent intent = new Intent(this,SelectUsersByRoleActivity.class);
        intent.putExtra("roleId",roleId);
        intent.putExtra("machineId",mMachineId);
        intent.putExtra("roleName",roleName);
        intent.putExtra("staffAuth",mStaffInfoAuth);
        //已选择的负责人不希望被重复选则
        ArrayList<AdminInfo> selectAdmins = new ArrayList<>();
        for (MachineInfo.StaffInfoBean staffInfoBean : machieInfo.getStaffInfo()) {
            selectAdmins.add(new AdminInfo(staffInfoBean.getUserId(),staffInfoBean.getUserName()));
        }
        intent.putParcelableArrayListExtra("selectAdmins",selectAdmins);
        intent.putParcelableArrayListExtra("admins", (ArrayList<AdminInfo>) admins);
       // intent.putIntegerArrayListExtra("userIds", (ArrayList<Integer>) selectUserIds);
        startActivityForResult(intent,REQUEST_ROLE_USERS);
    }
}
