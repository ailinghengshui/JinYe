package com.hzjytech.operation.widgets.selectfrags;

import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.hzjytech.operation.R;
import com.hzjytech.operation.adapters.widget.selectfrags.FirstAdapter;
import com.hzjytech.operation.base.BaseFragment;
import com.hzjytech.operation.constants.Constants;
import com.hzjytech.operation.http.RxBus;
import com.hzjytech.operation.widgets.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import rx.Subscription;
import rx.functions.Action1;

import static com.hzjytech.operation.constants.Constants.EditType.EDIT_GROUP_FROM_GROUP;
import static com.hzjytech.operation.constants.Constants.EditType.EDIT_MACHINE_FROM_GROUP;
import static com.hzjytech.operation.constants.Constants.GroupRefresh.GROUPREFRESH;

/**
 * Created by hehongcan on 2017/9/22.
 */

public  class DisplayFragment<T> extends BaseFragment {
    @BindView(R.id.titleBar)
    TitleBar mTitleBar;
    @BindView(R.id.rv_first)
    RecyclerView mRvFirst;
    private SelectActivity mActivity;
    private FirstAdapter mDisplayAdapter;
    private RecyclerAdapterWithHF mAdapter;
    private ArrayList<T> mList;
    private int mGroupId;
    private String mFragsType;
    private Subscription mSubscription;

    @Override
    protected int getResId() {
        return R.layout.fragment_first;
    }

    @Override
    protected void initView() {
        initIntent();
        initTitle();
        initRecyclerView();
    }

    private void initTitle() {
        mTitleBar.setLeftImageResource(R.drawable.icon_left);
        mTitleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.finish();
            }
        });
        mTitleBar.setTitleColor(Color.WHITE);
        mTitleBar.setTitleBold(true);
        SelectActivity activity = (SelectActivity) getActivity();
        String type = activity.getFragsType();
        switch (type){
            case EDIT_GROUP_FROM_GROUP:
                mTitleBar.setTitle(R.string.contain_group);
                break;
            case EDIT_MACHINE_FROM_GROUP:
                mTitleBar.setTitle(R.string.contain_machine);
        }
        TitleBar.TextAction textAction = new TitleBar.TextAction("编辑") {
            @Override
            public void performAction(View view) {
                FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                RemoveFragment removeFragment = new RemoveFragment();
                transaction.replace(R.id.groups_content,removeFragment , null);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        };
        mTitleBar.addAction(textAction);
    }

    private void initIntent() {
        mActivity = (SelectActivity) getActivity();
        mList = mActivity.getData();
        mGroupId = mActivity.getGroupId();
        mFragsType = mActivity.getFragsType();
        mSubscription = RxBus.getDefault()
                .toObservable()
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        if (o .equals(GROUPREFRESH)) {
                            mDisplayAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    /**
     * 初始化recyclerview
     */
    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvFirst.setLayoutManager(linearLayoutManager);
        mDisplayAdapter = new FirstAdapter(mActivity, mList,mFragsType);
        mAdapter = new RecyclerAdapterWithHF(mDisplayAdapter);
        mRvFirst.setAdapter(mAdapter);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSubscription.unsubscribe();
    }
}
