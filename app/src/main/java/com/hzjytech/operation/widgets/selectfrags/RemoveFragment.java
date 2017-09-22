package com.hzjytech.operation.widgets.selectfrags;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.PtrHandler;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.hzjytech.operation.R;
import com.hzjytech.operation.adapters.widget.selectfrags.RemoveAdapter;
import com.hzjytech.operation.base.BaseFragment;
import com.hzjytech.operation.constants.Constants;
import com.hzjytech.operation.entity.GroupList;
import com.hzjytech.operation.http.JijiaHttpSubscriber;
import com.hzjytech.operation.http.RxBus;
import com.hzjytech.operation.http.SubscriberOnNextListener;
import com.hzjytech.operation.http.api.GroupApi;
import com.hzjytech.operation.inter.OnInfoClickListener;
import com.hzjytech.operation.utils.CommonUtil;
import com.hzjytech.operation.utils.UserUtils;
import com.hzjytech.operation.widgets.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

import com.hzjytech.operation.entity.GroupInfo.SubMachinesBean;

import static com.hzjytech.operation.constants.Constants.EditType.EDIT_GROUP_FROM_GROUP;
import static com.hzjytech.operation.constants.Constants.EditType.EDIT_MACHINE_FROM_GROUP;
import static com.hzjytech.operation.constants.Constants.GroupRefresh.GROUPREFRESH;

/**
 * Created by hehongcan on 2017/9/22.
 */

public  class RemoveFragment<T> extends BaseFragment implements OnInfoClickListener {
    @BindView(R.id.titleBar)
    TitleBar mTitleBar;
    @BindView(R.id.rv_edit_group)
    RecyclerView mRvRemoveGroup;
    @BindView(R.id.pcfl_more_group)
    PtrClassicFrameLayout mPcflMoreGroup;
    private FragmentActivity mContext;
    private RemoveAdapter mRemoveAdapter;
    private RecyclerAdapterWithHF mAdapter;
    private PtrHandler mPtrHandler = new PtrHandler() {
        @Override
        public boolean checkCanDoRefresh(
                PtrFrameLayout frame, View content, View header) {
            return false;
        }

        @Override
        public void onRefreshBegin(PtrFrameLayout frame) {

        }
    };
    private JijiaHttpSubscriber mSubscriber;
    private Subscription mSubscription;
    private JijiaHttpSubscriber mInitSubscriber;
    private String mS;
    private SelectActivity mActivity;
    private ArrayList<T> mList;
    private int mGroupId;
    private String mFragsType;

    @Override
    protected int getResId() {
        return R.layout.fragment_edit_groups;
    }
    @Override
    protected void initView() {
        initIntent();
        initTitle();
        initRecyclerView();
        initPtrc();
    }

    private void initIntent() {
        mActivity = (SelectActivity) getActivity();
        mList = mActivity.getData();
        mGroupId = mActivity.getGroupId();
        mFragsType = mActivity.getFragsType();
    }



    private void initPtrc() {
        mPcflMoreGroup.setPtrHandler(mPtrHandler);
        mSubscription = RxBus.getDefault()
                .toObservable()
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        if (o .equals(GROUPREFRESH)) {
                            Log.e("list",mList.toString());
                            mRemoveAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvRemoveGroup.setLayoutManager(linearLayoutManager);
        mRemoveAdapter = new RemoveAdapter(mContext,mList,mFragsType);
        mAdapter = new RecyclerAdapterWithHF(mRemoveAdapter);
        mRvRemoveGroup.setAdapter(mAdapter);
        mRemoveAdapter.setOnItemClickListener(this);
    }

    /**
     * 初始化标题栏
     */
    private void initTitle() {
        mContext = getActivity();
        mTitleBar.setLeftText("取消");
        mTitleBar.setLeftTextColor(Color.WHITE);
        mTitleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        mTitleBar.setTitleColor(Color.WHITE);
        mTitleBar.setTitleBold(true);
        if(mFragsType.equals(EDIT_GROUP_FROM_GROUP)){
            mTitleBar.setTitle(R.string.contain_group);
        }else if(mFragsType.equals(EDIT_MACHINE_FROM_GROUP)){
            mTitleBar.setTitle(R.string.contain_machine);
        }
        TitleBar.TextAction textAction = new TitleBar.TextAction("移除") {
            @Override
            public void performAction(View view) {
                mTitleBar.setLeftText("");
                mTitleBar.setLeftImageResource(R.drawable.icon_left);
                commitEditInfo();
            }
        };
        mTitleBar.addAction(textAction);

    }

    /**
     * 真实联网修改数据
     */
    private void commitEditInfo() {
        if(mFragsType.equals(EDIT_GROUP_FROM_GROUP)){
            removeGroups();
        }else if(mFragsType.equals(EDIT_MACHINE_FROM_GROUP)){
            removeMachines();
        }

    }

    /**
     * 删除机器
     */
    private void removeMachines() {
        mS = "";
        if(mList==null||mList.size()<1){
            return;
        }
        ArrayList<SubMachinesBean> subMachins= (ArrayList<SubMachinesBean>) mList;
        final ArrayList<Object> cacheList = new ArrayList<>();
        for (SubMachinesBean subMachine : subMachins) {
            if(subMachine.isCheck()){
                mS += subMachine.getId()+",";
                cacheList.add(subMachine);
            }
        }
        if(mS.equals("")){
            return;
        }
        String substring = mS.substring(0, mS.length() - 1);
        Log.e("removesubstring",substring);
        Observable<Object> observable = GroupApi.detachMachine(UserUtils.getUserInfo()
                .getToken(),mGroupId, substring);
        mSubscriber = JijiaHttpSubscriber.buildSubscriber(mContext)
                .setOnNextListener(new SubscriberOnNextListener() {
                    @Override
                    public void onNext(Object o) {
                        mContext.setResult(Activity.RESULT_OK,new Intent());
                        for (Object o1 : cacheList) {
                            mList.remove(o1);
                        }
                        mRemoveAdapter.notifyDataSetChanged();
                        RxBus.getDefault().send(GROUPREFRESH);
                        showTip(R.string.delete_success);
                    }
                })
                .setProgressDialog(mProgressDlg)
                .build();
        observable.subscribe(mSubscriber);
    }

    /**
     * 删除分组
     */
    private void removeGroups() {
        mS = "";
        if(mList==null||mList.size()<1){
            return;
        }
        ArrayList<GroupList> subGroups= (ArrayList<GroupList>) mList;
        final ArrayList<Object> cacheList = new ArrayList<>();
        for (GroupList subGroup : subGroups) {
            if(subGroup.isCheck()){
                mS += subGroup.getId()+",";
                cacheList.add(subGroup);
            }
        }
        if(mS.equals("")){
            return;
        }
        String substring = mS.substring(0, mS.length() - 1);
        Log.e("removesubstring",substring);
        Observable<Object> observable = GroupApi.detachGroup(UserUtils.getUserInfo()
                .getToken(),mGroupId, substring);
        mSubscriber = JijiaHttpSubscriber.buildSubscriber(mContext)
                .setOnNextListener(new SubscriberOnNextListener() {
                    @Override
                    public void onNext(Object o) {
                        mContext.setResult(Activity.RESULT_OK,new Intent());
                        for (Object o1 : cacheList) {
                            mList.remove(o1);
                        }
                        mRemoveAdapter.notifyDataSetChanged();
                        showTip(R.string.delete_success);
                    }
                })
                .setProgressDialog(mProgressDlg)
                .build();
        observable.subscribe(mSubscriber);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        CommonUtil.unSubscribeSubs(mSubscriber,mSubscription,mInitSubscriber);
    }


    @Override
    public void click(int type) {
        FragmentManager fragmentManager = mContext.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.groups_content, new AddFragment(), null);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
