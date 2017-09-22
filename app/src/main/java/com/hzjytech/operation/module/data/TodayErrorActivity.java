package com.hzjytech.operation.module.data;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.PtrHandler;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.hzjytech.operation.R;
import com.hzjytech.operation.adapters.data.ErrorListAdapter;
import com.hzjytech.operation.base.BaseActivity;
import com.hzjytech.operation.entity.CurrentDataInfo;
import com.hzjytech.operation.entity.ErrorHistory;
import com.hzjytech.operation.entity.User;

import com.hzjytech.operation.http.JijiaHttpSubscriber;
import com.hzjytech.operation.http.SubscriberOnNextListener;
import com.hzjytech.operation.http.api.DataApi;
import com.hzjytech.operation.utils.TimeUtil;
import com.hzjytech.operation.utils.UserUtils;
import com.hzjytech.operation.widgets.TitleBar;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hehongcan on 2017/7/3.
 */
public class TodayErrorActivity extends BaseActivity {
    @BindView(R.id.titleBar)
    TitleBar mTitleBar;
    @BindView(R.id.rv_today_error)
    RecyclerView mRvTodayError;
    @BindView(R.id.pcfl_today_error)
    PtrClassicFrameLayout mPcflTodayError;
    @BindView(R.id.iv_empty)
    ImageView mIvEmpty;
    private ErrorListAdapter mErrorListAdapter;
    private PtrHandler ptrDefaultHandler = new PtrHandler() {
        @Override
        public boolean checkCanDoRefresh(
                PtrFrameLayout frame, View content, View header) {
            return false;
        }

        @Override
        public void onRefreshBegin(PtrFrameLayout frame) {

        }
    };

    /**
     * 刷新数据
     */
    private void refreshData() {

    }

    private OnLoadMoreListener onLoadMoreListener = new OnLoadMoreListener() {
        @Override
        public void loadMore() {
            loadMoreData();
        }
    };

    /**
     * 加载更多数据
     */
    private void loadMoreData() {
        mPcflTodayError.loadMoreComplete(false);
    }

    @Override
    protected int getResId() {
        return R.layout.activity_today_error;
    }

    @Override
    protected void initView() {
        initTitle();
        initRecycler();
        initPtcr();
        initData();
    }


    private void initTitle() {
        mTitleBar.setTitle(R.string.today_error);
        mTitleBar.setLeftImageResource(R.drawable.icon_left);
        mTitleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void initRecycler() {
        mRvTodayError.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,
                false));
        mErrorListAdapter = new ErrorListAdapter(this, null);
        RecyclerAdapterWithHF mAdapter = new RecyclerAdapterWithHF(mErrorListAdapter);
        mRvTodayError.setAdapter(mAdapter);
    }


    private void initPtcr() {
        mPcflTodayError.setPtrHandler(ptrDefaultHandler);//设置下拉监听
        mPcflTodayError.setOnLoadMoreListener(onLoadMoreListener);//设置上拉监听
        mPcflTodayError.setLoadMoreEnable(true);//设置可以加载更多
    }

    private void initData() {
        User userInfo = UserUtils.getUserInfo();
        Observable<CurrentDataInfo> observable = DataApi.getCurrentData(userInfo.getToken(),
                TimeUtil.getTimesmorning(),
                TimeUtil.getTimeCurrent());
        JijiaHttpSubscriber subscriber = JijiaHttpSubscriber.buildSubscriber(this)
                .setOnNextListener(new SubscriberOnNextListener<CurrentDataInfo>() {
                    @Override
                    public void onNext(CurrentDataInfo data) {
                        parseResult(data);
                    }
                })
                .setProgressDialog(mProgressDlg)
                .build();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 解析数据
     *
     * @param data
     */
    private void parseResult(CurrentDataInfo data) {
        if(data==null||data.getErrors()==null||data.getErrors().size()==0){
            mIvEmpty.setVisibility(View.VISIBLE);
            return;
        }
        mIvEmpty.setVisibility(View.INVISIBLE);
        List<CurrentDataInfo.ErrorsBean> errors = data.getErrors();
        ArrayList<ErrorHistory.VendingMachinesBean.VmErrorsBean> list = new ArrayList<>();
        for (CurrentDataInfo.ErrorsBean error : errors) {
            list.add(new ErrorHistory.VendingMachinesBean.VmErrorsBean(-1,
                    error.getCode(),
                    error.getContent(),
                    error.getCreatedAt(),
                    true,
                    error.getRecoverAt(),
                    -1,    error.getMachineName(),
                    error.getNote(),
                    error.getAddress()));
        }
        mErrorListAdapter.setData(list);

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
