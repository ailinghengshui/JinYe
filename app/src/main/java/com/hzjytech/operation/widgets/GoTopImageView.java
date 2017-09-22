package com.hzjytech.operation.widgets;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/**
 * 返回顶部的自定义imageview
 * Created by hehongcan on 2017/4/25.
 */
public class GoTopImageView extends ImageView {

    private GoTopImageView recyclerviewToTopImageView;
    private RecyclerView recyclerView;

    public GoTopImageView(Context context) {
        this(context,null);
    }

    public GoTopImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public GoTopImageView(final Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        recyclerviewToTopImageView = this;
        recyclerviewToTopImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recyclerView!=null){
                    LinearLayoutManager manager = new LinearLayoutManager(context);
                    int firstItem = manager.findFirstVisibleItemPosition();
                    int lastItem = manager.findLastVisibleItemPosition();
                    if (0 <= firstItem) {
                        recyclerView.scrollToPosition(0);
                    } else if (0 <= lastItem) {
                        int top = recyclerView.getChildAt(0 - firstItem).getTop();
                       recyclerView.scrollBy(0, top);
                    } else {
                        recyclerView.scrollToPosition(0);
                    }
                }
            }
        });
    }
    public void setRecyclerView(RecyclerView recyclerView){
        this.recyclerView=recyclerView;
         recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
             @Override
             public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                 super.onScrollStateChanged(recyclerView, newState);
             }

             @Override
             public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                 super.onScrolled(recyclerView, dx, dy);
                 RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                 if (layoutManager instanceof LinearLayoutManager) {
                     LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                     int firstVisibleItemPosition = linearManager.findFirstVisibleItemPosition();
                     if (firstVisibleItemPosition > 5) {
                         recyclerviewToTopImageView.setVisibility(VISIBLE);
                     } else {
                         recyclerviewToTopImageView.setVisibility(INVISIBLE);
                     }
                 }
             }
         });
    }
}
