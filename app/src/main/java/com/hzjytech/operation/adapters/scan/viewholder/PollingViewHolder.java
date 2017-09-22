package com.hzjytech.operation.adapters.scan.viewholder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hzjytech.operation.R;
import com.hzjytech.operation.adapters.task.DraggableImgGridAdapter;
import com.hzjytech.operation.entity.PollingInfo;
import com.hzjytech.operation.inter.OnAddPicClickListener;
import com.hzjytech.operation.inter.OnImageDelClickListener;
import com.hzjytech.operation.module.task.PicsPageViewActivity;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hehongcan on 2017/6/7.
 */
public class PollingViewHolder extends RecyclerView.ViewHolder implements DraggableImgGridAdapter.OnItemAddListener, DraggableImgGridAdapter.OnItemClickListener, DraggableImgGridAdapter.OnItemDeleteListener {
    @BindView(R.id.polling_top_head)
    View mPollingTopHead;
    @BindView(R.id.tv_polling_title)
    TextView mTvPollingTitle;
    @BindView(R.id.checkbox_polling_isChecked)
    CheckBox mCheckboxPollingIsChecked;
    @BindView(R.id.tv_polling_content)
    TextView mTvPollingContent;
    @BindView(R.id.tv_polling_line)
    View mTvPollingLine;
    @BindView(R.id.ll_polling_container)
    LinearLayout ll_container;
    @BindView(R.id.ll_check)
    LinearLayout mLLCheck;
    @BindView(R.id.recycler_photos)
    RecyclerView mRecyclerPhotos;
    private ArrayList<Object> selectList;
    private int photoSizeLimit;
    private DisplayMetrics dm;
    private int imageSize;
    private InputMethodManager imm;
    private GridLayoutManager layoutManager;
    private DraggableImgGridAdapter mGridAdapter;
    private OnAddPicClickListener onAddPicClickListener;
    private int position;
    private Context context;
    private OnImageDelClickListener onImageDelClickListener;
    private List<PollingInfo> list;

    public PollingViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    public void setData(
            Context context,
            final int position,
            final List<PollingInfo> list,
            OnAddPicClickListener onAddPicClickListener,
            OnImageDelClickListener onImageDelClickListener) {
        this.context=context;
        this. onAddPicClickListener=onAddPicClickListener;
        this.onImageDelClickListener=onImageDelClickListener;
        this.position=position;
        this.list=list;
        final PollingInfo info = list.get(position);
        if(position!=0){
            mPollingTopHead.setVisibility(View.GONE);
        }else{
            mPollingTopHead.setVisibility(View.VISIBLE);
        }
        if(position==list.size()-1){
            mTvPollingLine.setVisibility(View.INVISIBLE);
        }else{
            mTvPollingLine.setVisibility(View.VISIBLE);
        }
        mTvPollingTitle.setText(info.getId()+"."+info.getTitle());
        mTvPollingContent.setText(info.getContent());
        mCheckboxPollingIsChecked.setChecked(info.isCheck());
        mLLCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCheckboxPollingIsChecked.setChecked(!mCheckboxPollingIsChecked.isChecked());
                boolean check = info.isCheck();
                list.get(position).setCheck(!check);
            }
        });
        //九宫格
        photoSizeLimit = 9;
        dm = context.getResources().getDisplayMetrics();
        imageSize = (int) ((dm.widthPixels - 62 * dm.density) / 3);
        imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        layoutManager = new GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false);
        mGridAdapter = new DraggableImgGridAdapter(context, null, imageSize, photoSizeLimit);
        mGridAdapter.setOnItemAddListener(this);
        mGridAdapter.setOnItemClickListener(this);
        mGridAdapter.setOnItemDeleteListener(this);
        mRecyclerPhotos.setLayoutManager(layoutManager);
        mRecyclerPhotos.setAdapter(mGridAdapter);
        mGridAdapter.setList(info.getUrls());
        addNewButtonAndRefresh();

    }

    @Override
    public void onItemAdd() {
        if(onAddPicClickListener!=null){
            onAddPicClickListener.addClick(position);
        }
    }

    @Override
    public void onItemClick(int adapterPosition, LocalMedia item) {
        ArrayList<String> mImages = new ArrayList<>();
        mImages.clear();
        if(list.get(position).getUrls()!=null){
            for (LocalMedia localMedia :list.get(position).getUrls()) {
                mImages.add(localMedia.getPath());
            }
        }

        Intent intent = new Intent(context, PicsPageViewActivity.class);
        intent.putStringArrayListExtra("images", mImages);
        intent.putExtra("position",adapterPosition);
        context.startActivity(intent);
    }

    @Override
    public void onItemDelete(int imagePosition) {
     if(onImageDelClickListener!=null){
         onImageDelClickListener.deletClcik(position,imagePosition);
}
    }
    public void addNewButtonAndRefresh() {
        int size = mGridAdapter.getItemCount();
        int rows = 1;
        if (size > 6) {
            rows = 3;
        } else if (size > 3) {
            rows = 2;
        }
        mRecyclerPhotos.getLayoutParams().height = Math.round(imageSize * rows + rows * 10 * dm
                .density);
        mGridAdapter.notifyDataSetChanged();
    }
}
