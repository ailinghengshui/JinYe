package com.hzjytech.operation.adapters.menu.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hzjytech.operation.R;
import com.hzjytech.operation.entity.MenuInfo;
import com.hzjytech.operation.inter.MorePackClickListener;
import com.hzjytech.operation.utils.DensityUtil;
import com.hzjytech.operation.utils.MyMath;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hehongcan on 2017/5/3.
 */
public class MenuPackViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.ll_menu_pack_container)
    public LinearLayout llMenuPackContainer;

    public MenuPackViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    public void setMenuPackData(
            Context context,
            List<MenuInfo.PacksBean> packs,
            final MorePackClickListener morePackClickListener) {
        View head = llMenuPackContainer.getChildAt(0);
        llMenuPackContainer.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(context);
        if (packs == null || packs.size() == 0) {
            llMenuPackContainer.setVisibility(View.GONE);
        } else {
            llMenuPackContainer.addView(head);
            if (packs.size() == 1) {
                View view = inflater.inflate(R.layout.menu_item_pack, null, false);
                llMenuPackContainer.addView(view);
                TextView tv_order = (TextView) view.findViewById(R.id.tv_pack_order);
                ImageView iv_pic = (ImageView) view.findViewById(R.id.iv_pack_pic);
                TextView tv_name = (TextView) view.findViewById(R.id.tv_pack_name);
                TextView tv_sold_type = (TextView) view.findViewById(R.id.tv_pack_sold_type);
                TextView tv_orgin_price = (TextView) view.findViewById(R.id.tv_pack_orgin_price);
                TextView tv_now_price = (TextView) view.findViewById(R.id.tv_pack_now_price);
                TextView tv_weixin_price = (TextView) view.findViewById(R.id.tv_pack_weixin_price);
                TextView tv_zfb_price = (TextView) view.findViewById(R.id.tv_pack_zfb_price);
                LinearLayout ll_pack_item_container = (LinearLayout) view.findViewById(R.id
                        .ll_pack_item_container);
                MenuInfo.PacksBean packsBean = packs.get(0);
                tv_order.setText(1 + "");
            /*   iv_pic.setImageResource(0);
                iv_pic.setScaleType(ImageView.ScaleType.FIT_CENTER);
                DisplayImageOptions options=new DisplayImageOptions.Builder()
                        .cacheInMemory(true)
                        .cacheOnDisk(true)
                        .bitmapConfig(Bitmap.Config.RGB_565)
                        .build();
                String app_image = packsBean.getUrl();
                ImageLoader.getInstance().displayImage(app_image,iv_pic,options);*/
                tv_name.setText(packsBean.getNameCh() + "/" + packsBean.getNameEn());
                tv_sold_type.setText(packsBean.isSaleStatus() ? "在售" : "告罄");
                tv_sold_type.setBackgroundResource(packsBean.isSaleStatus() ? R.drawable
                        .bg_sold_type_yes : R.drawable.bg_sold_type_no);
                tv_orgin_price.setText(context.getString(R.string.original_price,
                        MyMath.getTwoRoundNum(packsBean.getOriginPrice())));
                tv_now_price.setText(context.getString(R.string.now_price,
                        MyMath.getTwoRoundNum(packsBean.getNowPrice())));
                tv_weixin_price.setText(context.getString(R.string.weixin_price,
                        MyMath.getTwoRoundNum(packsBean.getWxpayPrice())));
                tv_zfb_price.setText(context.getString(R.string.ali_price,
                        MyMath.getTwoRoundNum(packsBean.getAlipayPrice())));
                List<MenuInfo.PacksBean.CoffeesBean> coffees = packsBean.getCoffees();
                if (coffees != null && coffees.size() > 0) {
                    for (int j = 0; j < coffees.size(); j++) {
                        View itemView = inflater.inflate(R.layout.item_pack_item, null, false);
                        TextView tv_name_count = (TextView) itemView.findViewById(R.id
                                .tv_pack_item_name_count);
                        if (coffees.get(j)
                                .getCount() > -1) {
                            tv_name_count.setText(coffees.get(j)
                                    .getItemName() + "*" + coffees.get(j)
                                    .getCount());
                        } else {
                            tv_name_count.setText(coffees.get(j)
                                    .getItemName());
                        }
                        if (j != 0) {
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                    ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT);
                            layoutParams.setMargins(DensityUtil.dp2px(context, 10), 0, 0, 0);
                            itemView.setLayoutParams(layoutParams);
                        }
                        ll_pack_item_container.addView(itemView);
                    }
                }
            } else {
                for (int i = 0; i < 2; i++) {
                    View view = inflater.inflate(R.layout.menu_item_pack, null, false);
                    llMenuPackContainer.addView(view);
                }
                for (int i = 0; i < 2; i++) {
                    View view = llMenuPackContainer.getChildAt(i + 1);
                    TextView tv_order = (TextView) view.findViewById(R.id.tv_pack_order);
                    ImageView iv_pic = (ImageView) view.findViewById(R.id.iv_pack_pic);
                    TextView tv_name = (TextView) view.findViewById(R.id.tv_pack_name);
                    TextView tv_sold_type = (TextView) view.findViewById(R.id.tv_pack_sold_type);
                    TextView tv_orgin_price = (TextView) view.findViewById(R.id
                            .tv_pack_orgin_price);
                    TextView tv_now_price = (TextView) view.findViewById(R.id.tv_pack_now_price);
                    TextView tv_weixin_price = (TextView) view.findViewById(R.id
                            .tv_pack_weixin_price);
                    TextView tv_zfb_price = (TextView) view.findViewById(R.id.tv_pack_zfb_price);
                    LinearLayout ll_pack_item_container = (LinearLayout) view.findViewById(R.id
                            .ll_pack_item_container);
                    MenuInfo.PacksBean packsBean = packs.get(i);
                    tv_order.setText("1");
               /* iv_pic.setImageResource(0);
                iv_pic.setScaleType(ImageView.ScaleType.FIT_CENTER);
                DisplayImageOptions options=new DisplayImageOptions.Builder()
                        .cacheInMemory(true)*//*缓存至内存*//*
                        .cacheOnDisk(true)*//*缓存值SDcard*//*
                        .bitmapConfig(Bitmap.Config.RGB_565)
                        .build();
                String app_image = packsBean.getUrl();
                ImageLoader.getInstance().displayImage(app_image,iv_pic,options);*/
                    tv_name.setText(packsBean.getNameCh() + "/" + packsBean.getNameEn());
                    tv_sold_type.setText(packsBean.isSaleStatus() ? "在售" : "告罄");
                    tv_sold_type.setBackgroundResource(packsBean.isSaleStatus() ? R.drawable
                            .bg_sold_type_yes : R.drawable.bg_sold_type_no);
                    tv_orgin_price.setText(context.getString(R.string.original_price,
                            MyMath.getTwoRoundNum(packsBean.getOriginPrice())));
                    tv_now_price.setText(context.getString(R.string.now_price,
                            MyMath.getTwoRoundNum(packsBean.getNowPrice())));
                    tv_weixin_price.setText(context.getString(R.string.weixin_price,
                            MyMath.getTwoRoundNum(packsBean.getWxpayPrice())));
                    tv_zfb_price.setText(context.getString(R.string.ali_price,
                            MyMath.getTwoRoundNum(packsBean.getAlipayPrice())));
                    List<MenuInfo.PacksBean.CoffeesBean> coffees = packsBean.getCoffees();
                    if (coffees != null && coffees.size() > 0) {
                        for (int j = 0; j < coffees.size(); j++) {
                            View itemView = inflater.inflate(R.layout.item_pack_item, null, false);
                            TextView tv_name_count = (TextView) itemView.findViewById(R.id
                                    .tv_pack_item_name_count);
                            if (coffees.get(j)
                                    .getCount() > -1) {
                                tv_name_count.setText(coffees.get(j)
                                        .getItemName() + "*" + coffees.get(j)
                                        .getCount());
                            } else {
                                tv_name_count.setText(coffees.get(j)
                                        .getItemName());
                            }
                            if (j != 0) {
                                LinearLayout.LayoutParams layoutParams = new LinearLayout
                                        .LayoutParams(
                                        ViewGroup.LayoutParams.WRAP_CONTENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT);
                                layoutParams.setMargins(DensityUtil.dp2px(context, 10), 0, 0, 0);
                                itemView.setLayoutParams(layoutParams);
                            }
                            ll_pack_item_container.addView(itemView);
                        }
                    }
                }
                if (packs.size() > 2) {
                    View view = inflater.inflate(R.layout.item_root_check_more, null, false);

                    llMenuPackContainer.addView(view);
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            morePackClickListener.click();
                        }
                    });
                }
            }
        }
    }
}
