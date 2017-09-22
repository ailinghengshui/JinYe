package com.hzjytech.operation.module.data;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.internal.LinkedTreeMap;
import com.hzjytech.operation.R;
import com.hzjytech.operation.adapters.data.FeedOverViewAdapter;
import com.hzjytech.operation.base.BaseActivity;
import com.hzjytech.operation.entity.AddMaterialInfo;
import com.hzjytech.operation.entity.User;
import com.hzjytech.operation.http.JijiaHttpSubscriber;
import com.hzjytech.operation.http.SubscriberOnNextListener;
import com.hzjytech.operation.http.api.DataApi;
import com.hzjytech.operation.utils.UserUtils;
import com.hzjytech.operation.widgets.TitleBar;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;

/**
 * Created by hehongcan on 2017/5/24.
 */
public class FeedOverViewActivity extends BaseActivity {
    @BindView(R.id.titleBar)
    TitleBar titleBar;
    @BindView(R.id.rv_feed_overview)
    RecyclerView rvFeedOverview;
    private FeedOverViewAdapter adapter;

    @Override
    protected int getResId() {
        return R.layout.activity_feed_overview;
    }

    @Override
    protected void initView() {
        initTitle();
        initRecyclerView();
        initData();
    }

    private void initData() {
        final Map<String,Double> map = new HashMap<>();
        User userInfo = UserUtils.getUserInfo();
        Intent intent = getIntent();
        long startTime = intent.getLongExtra("startTime", 0);
        long endTime = intent.getLongExtra("endTime", 0);
        final ArrayList<Integer> machinesId = intent.getIntegerArrayListExtra("machinesId");
        Observable<AddMaterialInfo> observable = DataApi.getAddMaterial(userInfo.getToken(), startTime, endTime, 0, machinesId, 1, 30);
        JijiaHttpSubscriber subscriber = JijiaHttpSubscriber.buildSubscriber(this).setOnNextListener(new SubscriberOnNextListener<AddMaterialInfo>() {
            @Override
            public void onNext(AddMaterialInfo materials) {
                Object o = materials.getCount();
                Map maps = (LinkedTreeMap) o;
                Set<String> keySet = maps.keySet();
                for (String key : keySet) {
                    double value = (double) maps.get(key);
                    map.put(key,value);
                }
                adapter.setData(map);
            }
        }).setProgressDialog(mProgressDlg).build();
        observable.subscribe(subscriber);
    }

    private void initRecyclerView() {
        rvFeedOverview.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        adapter = new FeedOverViewAdapter(this, null);
        rvFeedOverview.setAdapter(adapter);
    }

    private void initTitle() {
        titleBar.setTitle(R.string.feed_overview);
        titleBar.setTitleBold(true);
        titleBar.setTitleColor(Color.WHITE);
        titleBar.setLeftImageResource(R.drawable.icon_left);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
