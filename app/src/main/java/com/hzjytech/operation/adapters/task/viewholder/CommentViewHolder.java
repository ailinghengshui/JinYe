package com.hzjytech.operation.adapters.task.viewholder;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import com.hzjytech.operation.R;
import com.hzjytech.operation.adapters.PicAdapter;
import com.hzjytech.operation.entity.DetailTaskInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hehongcan on 2017/6/19.
 */
public class CommentViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_comment_name)
    TextView mTvCommentName;
    @BindView(R.id.tv_comment_time)
    TextView mTvCommentTime;
    @BindView(R.id.tv_comment)
    TextView mTvComment;
    @BindView(R.id.rv_comment_pic)
    RecyclerView mRvCommentPic;
    private DisplayMetrics mDm;
    private int mImageSize;
    private PicAdapter mPicAdapter;

    public CommentViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    public void setData(
            Context context, List<DetailTaskInfo.TaskCommentDOListBean> taskCommentDOList, int position) {
         if(taskCommentDOList==null||taskCommentDOList.size()==0){
            return;
        }
        DetailTaskInfo.TaskCommentDOListBean bean = taskCommentDOList.get
                (position);
        mTvCommentName.setText(bean.getCreaterName());
        mTvCommentTime.setText(bean.getCreatedAt());
        if(bean.getComment()!=null&&!bean.getComment().equals("")){
            mTvComment.setText(bean.getComment());
        }
       if(bean.getUrl()!=null&&!bean.getUrl().equals("")){
           String[] urls = bean.getUrl()
                   .split(",");
           List<String> urlList = new ArrayList<>();
           for (String url : urls) {
               urlList.add(url.trim());
           }
           initPicRey(context,urlList);
       }else{
           initPicRey(context,null);
           mRvCommentPic.getLayoutParams().height =0;
           mPicAdapter.notifyDataSetChanged();
       }

    }
    /**
     * 初始化评论图片recyclerview
     */
    private void initPicRey(Context context, List<String> urlList) {
        mDm = context.getResources().getDisplayMetrics();
        mImageSize = (int) ((mDm.widthPixels - 62 * mDm.density) / 3);
        mPicAdapter = new PicAdapter(context,mImageSize, urlList);
        GridLayoutManager layoutManager = new GridLayoutManager(context,
                3,
                GridLayoutManager.VERTICAL,
                false);
        mRvCommentPic.setLayoutManager(layoutManager);
        mRvCommentPic.setAdapter(mPicAdapter);
        addNewButtonAndRefresh();
    }
    public void addNewButtonAndRefresh() {
        int size = mPicAdapter.getItemCount();
        int rows = 1;
        if (size > 6) {
            rows = 3;
        } else if (size > 3) {
            rows = 2;
        }
        mRvCommentPic.getLayoutParams().height = Math.round(mImageSize * rows + rows * 10 *
                mDm.density);
        mPicAdapter.notifyDataSetChanged();
    }
}
