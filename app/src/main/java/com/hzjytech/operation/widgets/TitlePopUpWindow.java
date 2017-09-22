package com.hzjytech.operation.widgets;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hzjytech.operation.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hehongcan on 2017/5/9.
 */
public class TitlePopUpWindow extends PopupWindow {

    private Context context;
    private RecyclerView rv_pop;
    private OnTitlePopWindowSelectListener onTitlePopWindowSelectListener;
    private final PopAdapter mPopAdapter;

    public TitlePopUpWindow(Context context, List<String> list, int checked) {
        super(context);
        View inflate = LayoutInflater.from(context).inflate(R.layout.popview_title_item, null, false);
        setContentView(inflate);
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        setTouchable(true);
        setFocusable(true);
        setBackgroundDrawable(new BitmapDrawable());
        setAnimationStyle(R.style.popwin_anim_style);
        setOutsideTouchable(true);
        rv_pop = (RecyclerView) inflate.findViewById(R.id.rv_pop);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_pop.setLayoutManager(manager);
        mPopAdapter = new PopAdapter(context, list, checked);
        rv_pop.setAdapter(mPopAdapter);
    }

    /**
     * 设置选中的item
     *
     * @param position
     */
    public void setPositionChecked(int position) {
        mPopAdapter.setCheckedPostion(position);
    }

    @Override
    public void showAsDropDown(View anchor) {
        super.showAsDropDown(anchor);
    }

    private class PopAdapter extends RecyclerView.Adapter {
        private Context context;
        private List<String> list;
        private int checked;

        public PopAdapter(Context context, List<String> list, int checked) {
            this.context = context;
            this.list = list;
            this.checked = checked;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.layout_item_title_pop, parent, false);
            return new PopViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            PopViewHolder popHolder = (PopViewHolder) holder;
            popHolder.tvItem.setText(list.get(position));
            popHolder.ivItem.setVisibility(position==checked?View.VISIBLE:View.INVISIBLE);
            popHolder.rlItem.setSelected(position==checked?true:false);
            popHolder.rlItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(checked==position){
                        return;
                    }
                    checked=position;
                    notifyDataSetChanged();
                    if(onTitlePopWindowSelectListener!=null){
                        onTitlePopWindowSelectListener.select(position);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return list == null ? 0 : list.size();
        }

        /**
         * 设置选中的位置
         * @param checkedPostion
         */
        public void setCheckedPostion(int checkedPostion) {
            checked = checkedPostion;
            notifyDataSetChanged();
        }
    }

    public class PopViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_item)
        public TextView tvItem;
        @BindView(R.id.iv_item)
        public ImageView ivItem;
        @BindView(R.id.rl_item)
        public RelativeLayout rlItem;

        public PopViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
    public interface OnTitlePopWindowSelectListener{
        void select(int position);
    }
    public void setOnTitlePopWindowSelectListener(OnTitlePopWindowSelectListener onTitlePopWindowSelectListener){
        this.onTitlePopWindowSelectListener=onTitlePopWindowSelectListener;
    }
}
