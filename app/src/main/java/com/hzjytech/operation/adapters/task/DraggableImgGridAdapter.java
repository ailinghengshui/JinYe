package com.hzjytech.operation.adapters.task;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hzjytech.operation.R;
import com.hzjytech.operation.adapters.task.viewholder.BaseDraggableItemViewHolder;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 发布动态9宫格图片adapter
 * Created by chen_bin on 16/7/16.
 */
public class DraggableImgGridAdapter extends RecyclerView
        .Adapter<BaseDraggableItemViewHolder<LocalMedia>>  {
    private Context context;
    private ArrayList<LocalMedia> photos;
    private boolean canDrag = true;
    private boolean canDelete = true;
    private int imageSize;
    private int picSizeLimit;
    private LayoutInflater inflater;
    private OnItemAddListener onItemAddListener;
    private OnItemDeleteListener onItemDeleteListener;
    private OnItemClickListener onItemClickListener;
    private final static int ITEM_TYPE_EMPTY = 0;
    private final static int ITEM_TYPE_PHOTO = 1;
    private final static int ITEM_TYPE_VIDEO = 2;

    public DraggableImgGridAdapter(
            Context context, ArrayList<LocalMedia> photos, int imageSize, int picSizeLimit) {
        this.context = context;
        this.photos = photos;
        this.imageSize = imageSize;
        this.picSizeLimit = picSizeLimit;
        this.inflater = LayoutInflater.from(context);
       // setHasStableIds(true);
    }

    public void setOnItemAddListener(OnItemAddListener onItemAddListener) {
        this.onItemAddListener = onItemAddListener;
    }

    public void setOnItemDeleteListener(OnItemDeleteListener onItemDeleteListener) {
        this.onItemDeleteListener = onItemDeleteListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    //能否拖拽
    public void setCanDrag(boolean canDrag) {
        this.canDrag = canDrag;
    }

    //能否删除
    public void setCanDelete(boolean canDelete) {
        this.canDelete = canDelete;
    }

    @Override
    public int getItemCount() {
        if(photos==null||photos.size()==0){
            return 1;
        }else{
            return photos.size() + ((photos.size() < picSizeLimit) & canDelete ? 1 : 0);
        }

    }

  /*  @Override
    public long getItemId(int position) {
        if (position >= photos.size()) {
            return super.getItemId(position);
        } else {
            return photos.get(position)
                    .ge;
        }
    }*/



    @Override
    public int getItemViewType(int position) {
        if (photos==null||photos.size()==0||position == photos.size()) {
            return ITEM_TYPE_EMPTY;
        } else {
            return ITEM_TYPE_PHOTO;
        }
    }

    @Override
    public BaseDraggableItemViewHolder<LocalMedia> onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM_TYPE_EMPTY:
                return new EmptyViewHolder(inflater.inflate(R.layout.draggable_img_grid_item,
                        parent,
                        false));
            case ITEM_TYPE_PHOTO:
                return new PhotoViewHolder(inflater.inflate(R.layout.draggable_img_grid_item,
                        parent,
                        false));
            default:
                  return null;
        }
    }

    @Override
    public void onBindViewHolder(BaseDraggableItemViewHolder<LocalMedia> holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case ITEM_TYPE_EMPTY:
                holder.setView(context, null, 0, viewType);
                break;
            default:
                holder.setView(context, photos.get(position), position, viewType);
                break;
        }
    }

    public void setList(ArrayList<LocalMedia> selectList) {
        this.photos=selectList;
        notifyDataSetChanged();
    }

    //添加的空视图
    public class EmptyViewHolder extends BaseDraggableItemViewHolder<LocalMedia> {
        @BindView(R.id.container)
        FrameLayout container;
        @BindView(R.id.img_cover)
        ImageView imgCover;
        @BindView(R.id.img_play)
        ImageView imgPlay;
        @BindView(R.id.btn_delete)
        ImageView btnDelete;

        public EmptyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            imgCover.getLayoutParams().width = imageSize;
            imgCover.getLayoutParams().height = imageSize;
        }


        @Override
        public void setViewData(Context context, LocalMedia photo, int position, int viewType) {
            imgCover.setImageResource(R.drawable.icon_add);
            if (onItemAddListener != null) {
                container.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onItemAddListener.onItemAdd();
                    }
                });
            }
        }
    }

    //图片viewHolder
    public class PhotoViewHolder extends EmptyViewHolder {
        @BindView(R.id.container)
        FrameLayout container;
        @BindView(R.id.img_cover)
        ImageView imgCover;
        @BindView(R.id.img_play)
        ImageView imgPlay;
        @BindView(R.id.btn_delete)
        ImageView btnDelete;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            btnDelete.setVisibility(canDelete ? View.VISIBLE : View.GONE);
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemDeleteListener != null) {
                        onItemDeleteListener.onItemDelete(getAdapterPosition());
                    }
                }
            });
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(getAdapterPosition(),getItem());
                    }
                }
            });
        }

        @Override
        public void setViewData(
                Context context, final LocalMedia photo, final int position, int viewType) {
            String imagePath = photo.getPath();
            if (!TextUtils.isEmpty(imagePath) && (imagePath.startsWith("com.hzjytech14://") || imagePath
                    .startsWith(
                    "https://"))) {
               /* imagePath = ImagePath.buildPath(imagePath)
                        .width(imageSize)
                        .cropPath();*/
            }
            Glide.with(context)
                    .load(imagePath)
                    .into(imgCover);
        }
    }



    public interface OnItemDeleteListener {
        void onItemDelete(int position);
    }

    public interface OnItemAddListener {
        void onItemAdd();
    }
    public interface OnItemClickListener {
        void onItemClick(int adapterPosition, LocalMedia item);
    }

}