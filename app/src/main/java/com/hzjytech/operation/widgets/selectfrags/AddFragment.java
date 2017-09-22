package com.hzjytech.operation.widgets.selectfrags;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.hzjytech.operation.R;
import com.hzjytech.operation.adapters.group.AddAdapter;
import com.hzjytech.operation.base.BaseFragment;
import com.hzjytech.operation.entity.AddGroupInfo;
import com.hzjytech.operation.entity.AddMachineInfo;
import com.hzjytech.operation.entity.GroupInfo;
import com.hzjytech.operation.entity.GroupList;
import com.hzjytech.operation.http.JijiaHttpSubscriber;
import com.hzjytech.operation.http.RxBus;
import com.hzjytech.operation.http.SubscriberOnNextListener;
import com.hzjytech.operation.http.api.GroupApi;
import com.hzjytech.operation.utils.UserUtils;
import com.hzjytech.operation.widgets.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Observable;

import static com.hzjytech.operation.constants.Constants.EditType.EDIT_GROUP_FROM_GROUP;
import static com.hzjytech.operation.constants.Constants.EditType.EDIT_MACHINE_FROM_GROUP;
import static com.hzjytech.operation.constants.Constants.GroupClick.GROUPSETTING;
import static com.hzjytech.operation.constants.Constants.GroupRefresh.GROUPREFRESH;

/**
 * Created by hehongcan on 2017/9/22.
 */

public class AddFragment<T> extends BaseFragment{
    @BindView(R.id.titleBar)
    TitleBar mTitleBar;
    @BindView(R.id.rv_add_group)
    RecyclerView mRvAddGroup;
    private AddAdapter mAdapter;
    private String mString;
    private SelectActivity mActivity;
    private ArrayList<T> mList;
    private int mGroupId;
    private String mFragsType;
    private int mVmId;
    private int mEditOrAdd;
    private ArrayList<T>mCacheList;

    @Override
    protected int getResId() {
        return R.layout.fragment_add_groups;
    }

    @Override
    protected void initView() {
        initIntent();
        initTitle();
        initRecyclerView();
        initData();
    }

    private void initIntent() {
        mActivity = (SelectActivity) getActivity();
        mList = mActivity.getData();
        mGroupId = mActivity.getGroupId();
        mFragsType = mActivity.getFragsType();
        mVmId = mActivity.getVmId();
        mEditOrAdd = mActivity.getEditOrAdd();
        mCacheList=new ArrayList<>();

    }


    private void initData() {
        if(mFragsType.equals(EDIT_GROUP_FROM_GROUP)){
            mTitleBar.setTitle(R.string.add_group);
            initGroupData();
        }else if(mFragsType.equals(EDIT_MACHINE_FROM_GROUP)){
            mTitleBar.setTitle(R.string.add_machine);
            initMachineData();
        }

    }

    private void initMachineData() {
        Observable<List<AddMachineInfo>> observable = GroupApi.getAddMachines(UserUtils.getUserInfo()
                .getToken(), mGroupId, mVmId);
        JijiaHttpSubscriber subscriber = JijiaHttpSubscriber.buildSubscriber(mActivity)
                .setOnNextListener(new SubscriberOnNextListener<List<AddMachineInfo>>() {
                    @Override
                    public void onNext(List<AddMachineInfo>machineList) {
                        resolveMachinesResult(machineList);
                    }
                })
                .setProgressDialog(mProgressDlg)
                .build();
        observable.subscribe(subscriber);
    }

    /**
     * 处理可添加机器返回值
     * @param machineList
     */
    private void resolveMachinesResult(List<AddMachineInfo> machineList) {
        mCacheList.clear();
        if(machineList==null){
            return;
        }
        for (AddMachineInfo addMachineInfo : machineList) {
            for (AddMachineInfo.SubMachinesBean subMachinesBean : addMachineInfo.getSubMachines()) {
                mCacheList.add((T) new GroupInfo.SubMachinesBean(subMachinesBean.getMachineId(),subMachinesBean.getMachineName(),subMachinesBean.getAddress()));
            }
        }
        mAdapter.setData(mCacheList);
    }

    /**
     * 处理可添加分组返回值
     * @param groupList
     */
    private void resolveGroupsResult(List<AddGroupInfo> groupList) {
        mCacheList.clear();
        if(groupList==null){
            return;
        }
        for (AddGroupInfo info : groupList) {
            mList.add((T) new GroupList(info.getId(),info.getName(),info.isHaveSub()));
            if(info.getSubGroups()!=null&&info.getSubGroups().size()>0){
                for (AddGroupInfo.SubGroupsBean childInfo : info.getSubGroups()) {
                    mCacheList.add((T) new GroupList(childInfo.getId(),childInfo.getName(),false));
                }
            }
        }
        mAdapter.setData(mCacheList);
    }

    private void initGroupData() {
        Observable<List<AddGroupInfo>> observable = GroupApi.getAddGroups(UserUtils.getUserInfo()
                .getToken(), mGroupId, mVmId);
        JijiaHttpSubscriber subscriber = JijiaHttpSubscriber.buildSubscriber(mActivity)
                .setOnNextListener(new SubscriberOnNextListener<List<AddGroupInfo>>() {
                    @Override
                    public void onNext(List<AddGroupInfo> groupList) {
                        resolveGroupsResult(groupList);
                    }
                })
                .setProgressDialog(mProgressDlg)
                .build();
        observable.subscribe(subscriber);
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvAddGroup.setLayoutManager(linearLayoutManager);
        mAdapter = new AddAdapter(mActivity, null,mFragsType);
        mRvAddGroup.setAdapter(mAdapter);
    }

    private void initTitle() {
        mTitleBar.setLeftTextColor(Color.WHITE);
        mTitleBar.setLeftText(R.string.cancel);
        mTitleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mEditOrAdd==GROUPSETTING){
                    mActivity.getSupportFragmentManager().popBackStack();
                }else{
                    mActivity.finish();
                }

            }
        });
        mTitleBar.setTitleColor(Color.WHITE);
        mTitleBar.setTitleBold(true);
        TitleBar.TextAction textAction = new TitleBar.TextAction("添加") {
            @Override
            public void performAction(View view) {
                mTitleBar.setLeftText("");
                mTitleBar.setLeftImageResource(R.drawable.icon_left);
                commitAdd();
            }
        };
        mTitleBar.addAction(textAction);
    }

    /**
     * 点击确定添加分组
     */
    private void commitAdd() {
        if(mFragsType.equals(EDIT_GROUP_FROM_GROUP)){
            addGroups();
        }else if(mFragsType.equals(EDIT_MACHINE_FROM_GROUP)){
            addMachines();
        }

    }

    private void addMachines() {
        mString = "";

        if (mCacheList == null || mCacheList.size() < 1) {
            return;
        }
        ArrayList<GroupInfo.SubMachinesBean> machines= (ArrayList<GroupInfo.SubMachinesBean>) mCacheList;
        final ArrayList<T> selectList = new ArrayList<>();
        for (GroupInfo.SubMachinesBean machine : machines) {
            if(machine.isCheck()){
                mString += machine.getId()+",";
                machine.setCheck(false);
                selectList.add((T) machine);
            }
        }
        if(mString.equals("")){
            return;
        }
        String substring = mString.substring(0, mString.length() - 1);
        Log.e("addsubstring",substring);
        Observable<Object> observable = GroupApi.addMachine(UserUtils.getUserInfo()
                .getToken(), mGroupId, substring);
        JijiaHttpSubscriber subscriber = JijiaHttpSubscriber.buildSubscriber(mActivity)
                .setOnNextListener(new SubscriberOnNextListener() {
                    @Override
                    public void onNext(Object o) {
                        showTip(R.string.add_success);
                        mList.addAll(selectList);
                        for (T t : selectList) {
                            mCacheList.remove(t);
                        }
                        mAdapter.notifyDataSetChanged();
                        RxBus.getDefault().send(GROUPREFRESH);
                    }
                })
                .setProgressDialog(mProgressDlg)
                .build();
        observable.subscribe(subscriber);
    }

    private void addGroups() {
        mString = "";

        if (mCacheList == null || mCacheList.size() < 1) {
            return;
        }
        ArrayList<GroupList> subGroups= (ArrayList<GroupList>) mCacheList;
        final ArrayList<T> selectList = new ArrayList<>();
        for (GroupList subGroup : subGroups) {
            if(subGroup.isCheck()){
                mString += subGroup.getId()+",";
                subGroup.setCheck(false);
                selectList.add((T) subGroup);
            }
        }
        if(mString.equals("")){
            return;
        }
        String substring = mString.substring(0, mString.length() - 1);
        Log.e("addsubstring",substring);
        Observable<Object> observable = GroupApi.addGroup(UserUtils.getUserInfo()
                .getToken(), mGroupId, substring);
        JijiaHttpSubscriber subscriber = JijiaHttpSubscriber.buildSubscriber(mActivity)
                .setOnNextListener(new SubscriberOnNextListener() {
                    @Override
                    public void onNext(Object o) {
                        showTip(R.string.add_success);
                        mList.addAll(selectList);
                        for (T t : selectList) {
                            mCacheList.remove(t);
                        }
                        mAdapter.notifyDataSetChanged();
                        RxBus.getDefault().send(GROUPREFRESH);
                    }
                })
                .setProgressDialog(mProgressDlg)
                .build();
        observable.subscribe(subscriber);
    }

    @Override
    public void onDestroyView () {
        super.onDestroyView();
    }
}
