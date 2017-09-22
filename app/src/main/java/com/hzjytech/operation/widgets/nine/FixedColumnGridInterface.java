package com.hzjytech.operation.widgets.nine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.hzjytech.operation.R;
import com.hzjytech.operation.entity.Photo;

import java.util.List;


/**
 * 九宫格样式，每行固定显示3个
 * Created by chen_bin on 2017/5/23.
 */
public class FixedColumnGridInterface implements JijiaGridView.GridInterface<Photo> {

    private int space;

    private int count = 3;

    public FixedColumnGridInterface(int space) {
        this.space = space;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public void setViewValue(Context context, View itemView, int position, List<Photo> data) {

    }

    @Override
    public View getItemView(ViewGroup viewGroup) {
        return LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.photos_item___mf, viewGroup, false);
    }

    @Override
    public int getColumnCount(int size) {
        return count;
    }

    @Override
    public int getSpacing(int size, JijiaGridView.MeasureType type) {
        return space;
    }

    @Override
    public int getItemSize(int totalWidth, int size, JijiaGridView.MeasureType type) {
        int mColumnCount = getColumnCount(size);
        int mHorizontalSpacing = getSpacing(size, JijiaGridView.MeasureType.HORIZONTAL);
        return (totalWidth - mHorizontalSpacing * (mColumnCount - 1)) / mColumnCount;
    }

    @Override
    public JijiaGridImageView getGridImageView(View itemView) {
        JijiaGridImageView imageView = (JijiaGridImageView) itemView.getTag();
        if (imageView == null) {
            imageView = (JijiaGridImageView) itemView.findViewById(R.id.photo);
            imageView.setImageViewInterface(new JijiaGridImageView.GridImageViewInterface<Photo>() {
                @Override
                public void load(
                        Context context,
                        JijiaGridImageView imageView,
                        Photo item,
                        int width,
                        int height) {
                    Glide.with(context)
                           // .load(ImageUtil.getImagePath2ForWxH(item.getImagePath(), width, height))
                            .load(item.getImagePath())
                            .asBitmap()
                            .placeholder(R.drawable.bg_photo)
                            .into(imageView);
                }
            });
            itemView.setTag(imageView);
        }
        return imageView;
    }
}
