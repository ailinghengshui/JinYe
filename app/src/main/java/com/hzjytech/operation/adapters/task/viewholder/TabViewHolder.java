package com.hzjytech.operation.adapters.task.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hzjytech.operation.R;
import com.hzjytech.operation.inter.GroupOrHistoryClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hehongcan on 2017/6/19.
 */
public class TabViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_comment)
    TextView mTvComment;
    @BindView(R.id.line_comment)
    View mLineComment;
    @BindView(R.id.ll_comment)
    LinearLayout mLlComment;
    @BindView(R.id.tv_history)
    TextView mTvHistory;
    @BindView(R.id.line_history)
    View mLineHistory;
    @BindView(R.id.ll_history)
    LinearLayout mLlHistory;
    private Context mData;

    public TabViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    public void setData(Context context,GroupOrHistoryClickListener groupOrHistoryClickListener) {
        this.mGroupOrHistoryClickListener=groupOrHistoryClickListener;
        final int heavyGrey = context.getResources().getColor(R.color.heavy_grey);
        final int standardGrey = context.getResources().getColor(R.color.standard_grey);
        mLlComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击评论
                if(mGroupOrHistoryClickListener!=null){
                    mGroupOrHistoryClickListener.onIsCommentClick(true);
                }
                mTvComment.setTextColor(heavyGrey);
                mTvHistory.setTextColor(standardGrey);
                mLineComment.setVisibility(View.VISIBLE);
                mLineHistory.setVisibility(View.INVISIBLE);
            }
        });
        mLlHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击历史动态
                if(mGroupOrHistoryClickListener!=null){
                    mGroupOrHistoryClickListener.onIsCommentClick(false);
                }
                mTvComment.setTextColor(standardGrey);
                mTvHistory.setTextColor(heavyGrey);
                mLineComment.setVisibility(View.INVISIBLE);
                mLineHistory.setVisibility(View.VISIBLE);
            }
        });
    }
    private GroupOrHistoryClickListener mGroupOrHistoryClickListener;
}
