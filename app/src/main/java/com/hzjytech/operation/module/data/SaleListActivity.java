package com.hzjytech.operation.module.data;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hzjytech.operation.R;
import com.hzjytech.operation.adapters.RecyclerViewAdapter;
import com.hzjytech.operation.adapters.SpaceItemDecoration;
import com.hzjytech.operation.adapters.data.SaleListAdapter;
import com.hzjytech.operation.adapters.data.viewholders.RecyclerViewHolder;
import com.hzjytech.operation.base.BaseActivity;
import com.hzjytech.operation.entity.CurrentDataInfo;
import com.hzjytech.operation.entity.StickyHeadEntity;
import com.hzjytech.operation.entity.TenBean;
import com.hzjytech.operation.widgets.TitleBar;
import com.hzjytech.operation.widgets.stickyitemdecoration.DividerHelper;
import com.hzjytech.operation.widgets.stickyitemdecoration.StickyHeadContainer;
import com.hzjytech.operation.widgets.stickyitemdecoration.StickyItemDecoration;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hehongcan on 2017/5/16.
 */
public class SaleListActivity extends BaseActivity {
    @BindView(R.id.titleBar)
    TitleBar titleBar;
    @BindView(R.id.rv_sale_list)
    RecyclerView rvSaleList;
    @BindView(R.id.iv_saleList_isTenTop)
    ImageView mIvSaleListIsTenTop;
    @BindView(R.id.tv_isTenTop)
    TextView mTvIsTenTop;
    @BindView(R.id.shc)
    StickyHeadContainer mShc;
    private SaleListAdapter saleListAdapter;

    @Override
    protected int getResId() {
        return R.layout.activity_sale_list;
    }

    @Override
    protected void initView() {
        initTitle();
        initRecyclerView();
        initData();
    }

    /**
     * 初始化标题栏
     */
    private void initTitle() {
        titleBar.setTitle(R.string.daily_sale_list);
        titleBar.setTitleColor(Color.WHITE);
        titleBar.setTitleBold(true);
        titleBar.setLeftImageResource(R.drawable.icon_left);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 初始化recyclerview
     */
    private void initRecyclerView() {
        final int red = ContextCompat.getColor(this, R.color.color_red);
         final int green = ContextCompat.getColor(this, R.color.color_green);
        mShc.setDataCallback(new StickyHeadContainer.DataCallback() {
            @Override
            public void onDataChange(int pos) {
                TenBean item = saleListAdapter.getData().get(pos).getData();
                mIvSaleListIsTenTop.setBackgroundColor(pos==0?red:green);
                mTvIsTenTop.setText(item.getStickyHeadName());
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvSaleList.setLayoutManager(manager);
        rvSaleList.addItemDecoration(new StickyItemDecoration(mShc, RecyclerViewAdapter.TYPE_STICKY_HEAD));
        rvSaleList.addItemDecoration(new SpaceItemDecoration(this));
        saleListAdapter = new SaleListAdapter(this);
        rvSaleList.setAdapter(saleListAdapter);
    }

    private void initData() {
        Intent intent = getIntent();
        CurrentDataInfo currentData = intent.getParcelableExtra("currentData");
        List<TenBean> topTen = currentData.getTopTen();
        List<TenBean> bottomTen = currentData.getBottomTen();
        List<TenBean> list = new ArrayList<>();
        list.add(new TenBean(RecyclerViewAdapter.TYPE_STICKY_HEAD,"销售前10名"));
        for (TenBean tenBean : topTen) {
            tenBean.setItemType(RecyclerViewAdapter.TYPE_DATA);
            list.add(tenBean);
        }
        list.add(new TenBean(RecyclerViewAdapter.TYPE_STICKY_HEAD,"销售后10名"));
        for (TenBean tenBean : bottomTen) {
            tenBean.setItemType(RecyclerViewAdapter.TYPE_DATA);
            list.add(tenBean);
        }
        List<StickyHeadEntity<TenBean>> data = new ArrayList<>(list.size());
        for (TenBean info : list) {
            data.add(new StickyHeadEntity<>(info, info.getItemType(), info.stickyHeadName));
        }
        saleListAdapter.setData(data);
    }
    public static class SpaceItemDecoration extends RecyclerView.ItemDecoration {

        private final int[] ATTRS = new int[]{android.R.attr.listDivider};

        private Drawable mDivider;

        public SpaceItemDecoration(Context context) {
            final TypedArray a = context.obtainStyledAttributes(ATTRS);
            mDivider = a.getDrawable(0);
            a.recycle();
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                DividerHelper.drawBottomAlignItem(c, mDivider, child, params);
            }
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int type = parent.getAdapter().getItemViewType(parent.getChildAdapterPosition(view));
            if (type != RecyclerViewAdapter.TYPE_DATA && type != RecyclerViewAdapter.TYPE_SMALL_STICKY_HEAD_WITH_DATA) {
                outRect.set(0, 0, 0, 0);
            } else {
                outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
            }
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
