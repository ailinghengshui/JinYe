package com.hzjytech.operation.widgets.nine;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by wangtao on 2017/1/13.
 */

public class JijiaGridImageView extends ImageView {

    private GridImageViewInterface imageViewInterface;
    private Object item;

    public JijiaGridImageView(Context context) {
        super(context);
    }

    public JijiaGridImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public JijiaGridImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setItem(Object item) {
        this.item = item;
        int w = getWidth();
        int h = getHeight();
        if (w > 0 && h > 0 && imageViewInterface != null) {
            imageViewInterface.load(getContext(), this, item, w, h);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (imageViewInterface != null) {
            imageViewInterface.load(getContext(), this, item, w, h);
        }
    }

    public void setImageViewInterface(GridImageViewInterface imageViewInterface) {
        this.imageViewInterface = imageViewInterface;
    }

    public interface GridImageViewInterface<T> {
        void load(Context context, JijiaGridImageView imageView, T item, int width, int height);
    }
}
