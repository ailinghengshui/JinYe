package com.hzjytech.operation.module.task;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hzjytech.operation.R;
import com.hzjytech.operation.base.BaseActivity;
import com.hzjytech.operation.utils.ScreenUtil;
import com.hzjytech.operation.widgets.HackyViewPager;
import com.hzjytech.operation.widgets.TitleBar;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by werther on 16/8/3.
 */
public class PicsPageViewActivity extends BaseActivity {
    @BindView(R.id.titleBar)
    TitleBar mTitleBar;
    private ArrayList<String> images;
    private int width;
    private int height;
    private HackyViewPager mViewPager;
    private TextView tvCount;

    @Override
    protected int getResId() {
        return R.layout.activity_pics_page_view___img;
    }

    @Override
    protected void initView() {
        int screenWidth = ScreenUtil.getScreenWidth(this);
        int screenHeight = ScreenUtil.getScreenHeight(this);
        width = Math.round(screenWidth * 3 / 2);
        height = Math.round(screenHeight * 5 / 2);
        FrameLayout bottomLayout = (FrameLayout) findViewById(R.id.bottom_layout);
        mViewPager = (HackyViewPager) findViewById(R.id.pager);
        Intent intent = getIntent();
        images = intent.getStringArrayListExtra("images");
        int position = intent.getIntExtra("position", 0);
        mViewPager.setAdapter(new SamplePagerAdapter());
        mViewPager.setCurrentItem(position);
        mTitleBar.setTitle(position + 1 + "/" + this.images.size());
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mTitleBar.setTitle(position + 1 + "/" + PicsPageViewActivity.this.images.size());
            }
        });
       mTitleBar.setLeftImageResource(R.drawable.icon_left);
        mTitleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        initBottomView(bottomLayout);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    private class SamplePagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return images == null ? 0 : images.size();
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            final ImageView view = new ImageView(PicsPageViewActivity.this);
            String imgUrl = images.get(position);
            Glide.with(PicsPageViewActivity.this)
                    .load(imgUrl)
                    .fitCenter()
                    .into(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        /*    ima.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float x, float y) {

                }
            });*/
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }

    public void initBottomView(FrameLayout bottomLayout) {
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
