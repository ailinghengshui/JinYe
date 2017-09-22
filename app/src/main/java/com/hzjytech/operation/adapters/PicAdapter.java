package com.hzjytech.operation.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hzjytech.operation.R;
import com.hzjytech.operation.adapters.task.viewholder.BaseDraggableItemViewHolder;
import com.hzjytech.operation.module.task.PicsPageViewActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hehongcan on 2017/6/15.
 */
public class PicAdapter extends RecyclerView.Adapter{
    private  int imageSize;
    private  Context context;
    private   List<String> list;
    private final LayoutInflater mInflater;

    public PicAdapter(Context context, int imageSize, List<String> list) {
        this.context=context;
        this.list=list;
        this.imageSize=imageSize;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PicViewHolder(mInflater.inflate(R.layout.draggable_img_grid_item,
                parent,
                false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((PicViewHolder)holder).setViewData(context,list,position,0);
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    public void setData(ArrayList<String> data) {
        list = data;
        notifyDataSetChanged();
    }

    //添加的空视图
    public class PicViewHolder extends BaseDraggableItemViewHolder<List<String>> {
        @BindView(R.id.container)
        FrameLayout container;
        @BindView(R.id.img_cover)
        ImageView imgCover;
        @BindView(R.id.img_play)
        ImageView imgPlay;
        @BindView(R.id.btn_delete)
        ImageView btnDelete;

        public PicViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            imgCover.getLayoutParams().width = imageSize;
            imgCover.getLayoutParams().height = imageSize;
        }


        @Override
        public void setViewData(final Context context, final List<String> urls, final int position, int viewType) {
            Glide.with(context).load(urls.get(position)).fitCenter().dontAnimate().placeholder(R.drawable.bg_photo).into(imgCover);
            //Glide.with(context).load(urls.get(position)).into(imgCover);
            btnDelete.setVisibility(View.GONE);
            imgCover.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   ArrayList<String> mImages = new ArrayList<>();
                    mImages.clear();
                    for (String url : urls) {
                        mImages.add(url);
                    }
                    Intent intent = new Intent(context, PicsPageViewActivity.class);
                    intent.putStringArrayListExtra("images", mImages);
                    intent.putExtra("position",position);
                    context.startActivity(intent);
                }
            });

        }
    }

}
