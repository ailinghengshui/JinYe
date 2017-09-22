package com.hzjytech.operation.adapters.menu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hzjytech.operation.R;
import com.hzjytech.operation.entity.MenuInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hehongcan on 2017/5/3.
 */
public class SingleItemAdapter extends RecyclerView.Adapter {
   
    private Context context;
    private List<MenuInfo.ItemsBean> items;
    private final LayoutInflater inflater;
    public void setItemData(List<MenuInfo.ItemsBean> items){
        this.items=items;
        notifyDataSetChanged();
    }
    public SingleItemAdapter(Context context, List<MenuInfo.ItemsBean> items) {
        this.context = context;
        this.items = items;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.menu_item, parent, false);
        return new SingleItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SingleItemViewHolder holder1 = (SingleItemViewHolder) holder;
        MenuInfo.ItemsBean bean = items.get(position);
        holder1.tvOrder.setText(position+1+"");
        holder1.tvName.setText(bean.getNameCh()+"/"+bean.getNameEn());
       // holder1.ivPic.setScaleType(ImageView.ScaleType.FIT_CENTER);
      /*  DisplayImageOptions options=new DisplayImageOptions.Builder()
                .cacheInMemory(true)*//*缓存至内存*//*
                .cacheOnDisk(true)*//*缓存值SDcard*//*
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();*/
        String app_image = bean.getUrl();
        Glide.with(context).load(app_image).fitCenter().placeholder(R.drawable.bg_photo).dontAnimate().into(holder1.ivPic);
        holder1.tvReceipeType.setText("配方种类： "+bean.getRecipeName());
        holder1.tvSoldType.setText(bean.isSaleStatus()?"在售":"告罄");
        holder1.tvSoldType.setBackgroundResource(bean.isSaleStatus()?R.drawable.bg_sold_type_yes:R.drawable.bg_sold_type_no);
        holder1.tvVolume.setText("体 积： "+bean.getVolume()+"ml");
        holder1.tvOrginPrice.setText("原    价： "+bean.getPrice()+"元");
        holder1.tvNowPrice.setText("现        价： "+bean.getDiscountPrice()+"元");
        holder1.tvWeixinPrice.setText("微信价： "+bean.getWxpayPrice()+"元");
        holder1.tvZfbPrice.setText("支付宝价： "+bean.getAlipayPrice()+"元");
        holder1.bgHot.setBackgroundResource(bean.isIsHot()?R.color.color_green:R.color.bg_grey);
        holder1.bgNew.setBackgroundResource(bean.isIsNew()?R.color.color_green:R.color.bg_grey);
        holder1.bgIced.setBackgroundResource(bean.isIsIced()?R.color.color_green:R.color.bg_grey);
        holder1.bgSweet.setBackgroundResource(bean.isIsSweet()?R.color.color_green:R.color.bg_grey);
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public class SingleItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_order)
        public TextView tvOrder;
        @BindView(R.id.iv_pic)
        ImageView ivPic;
        @BindView(R.id.tv_name)
        public TextView tvName;
        @BindView(R.id.tv_receipe_type)
        public TextView tvReceipeType;
        @BindView(R.id.tv_sold_type)
        public TextView tvSoldType;
        @BindView(R.id.tv_volume)
        public TextView tvVolume;
        @BindView(R.id.tv_orgin_price)
        public TextView tvOrginPrice;
        @BindView(R.id.tv_weixin_price)
        public TextView tvWeixinPrice;
        @BindView(R.id.tv_now_price)
        public TextView tvNowPrice;
        @BindView(R.id.tv_zfb_price)
        public TextView tvZfbPrice;
        @BindView(R.id.bg_hot)
        public View bgHot;
        @BindView(R.id.bg_new)
        public View bgNew;
        @BindView(R.id.bg_iced)
        public View bgIced;
        @BindView(R.id.bg_sweet)
        public View bgSweet;
        public SingleItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }
}
