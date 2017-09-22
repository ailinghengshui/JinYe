package com.hzjytech.operation.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hzjytech.operation.R;
import com.hzjytech.operation.adapters.data.viewholders.ItemSelectMachineFooterViewHolder;
import com.hzjytech.operation.adapters.data.viewholders.ItemSelectMachineListViewholer;
import com.hzjytech.operation.adapters.data.viewholders.ItemSelectMachineViewHolder;
import com.hzjytech.operation.adapters.data.viewholders.TimeViewHolder;
import com.hzjytech.operation.entity.GroupInfo;
import com.hzjytech.operation.inter.OnItemDelClickListener;
import com.hzjytech.operation.inter.OnLongTimePickerListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hehongcan on 2017/5/19.
 */
public class SaleTableAdapter extends RecyclerView.Adapter {
    private static final int TYPE_TIME = 0;
    private static final int TYPE_SELECT_MACHINE = 1;
    private static final int TYPE_FOOTER = 2;
    private static final int TYPE_MACHINE_LIST = 3;
    private  Context context;
    private  List<GroupInfo.SubMachinesBean> list;
    private final LayoutInflater inflater;
    private OnSelectMoreMachineClickListener onSelectMoreMachineClickListener;
    private TimeViewHolder timeViewHolder;
    private View timeView;
    private OnClearAllMachineClickListener onCLearAllMachineClickListener;
    private OnLongTimePickerListener onLongTimePickerListener;
    private boolean canOnlyChooseOneMachine=false;
    private OnItemDelClickListener onItemDelClickListener;
    private boolean isTimeVisiable=true;

    public SaleTableAdapter(Context context, List<GroupInfo.SubMachinesBean> list) {
        this.context=context;
        this.list=list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==TYPE_TIME) {
            timeView = inflater.inflate(R.layout.item_select_time, parent, false);
            timeViewHolder = new TimeViewHolder(timeView);
            return timeViewHolder;
        }else if(viewType==TYPE_SELECT_MACHINE){
            View view = inflater.inflate(R.layout.item_select_machine, parent, false);
            return new ItemSelectMachineViewHolder(view);
        }else if(viewType==TYPE_FOOTER){
            View view = inflater.inflate(R.layout.item_select_machine_footer, parent, false);
            return new ItemSelectMachineFooterViewHolder(view);
        }else{
            View view = inflater.inflate(R.layout.item_select_machine_list, parent, false);
            return new ItemSelectMachineListViewholer(view);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if(holder instanceof TimeViewHolder){
            if(onLongTimePickerListener!=null){
                ((TimeViewHolder) holder).llTimePicker.setOnLongTimePickerListener(onLongTimePickerListener);
                if(!isTimeVisiable){
                    ViewGroup.LayoutParams pa = ((TimeViewHolder) holder)
                            .ll_time_container.getLayoutParams();
                    pa.height=0;
                    ((TimeViewHolder) holder).ll_time_container.setLayoutParams(pa);
                }
            }
        } else if(holder instanceof ItemSelectMachineViewHolder){
            if(canOnlyChooseOneMachine){
                ((ItemSelectMachineViewHolder) holder).tvSelectMachine.setText("选择一台咖啡机");
            }
             holder.itemView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     if(onSelectMoreMachineClickListener!=null){
                         onSelectMoreMachineClickListener.onClick();
                     }
                 }
             });
         }else if(holder instanceof ItemSelectMachineListViewholer){
             ItemSelectMachineListViewholer machineListViewholer = (ItemSelectMachineListViewholer) holder;
             GroupInfo.SubMachinesBean bean = list.get(position - 2);
             machineListViewholer.tvSelectId.setText(bean.getId()+"");
             machineListViewholer.tvSelectName.setText(bean.getName());
             machineListViewholer.tvSelectDes.setText(bean.getAddress());
            machineListViewholer.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemDelClickListener!=null){
                        onItemDelClickListener.onDelClick(holder.getLayoutPosition());
                    }
                }
            });
         }else if(holder instanceof ItemSelectMachineFooterViewHolder){
             ItemSelectMachineFooterViewHolder footerViewHolder = (ItemSelectMachineFooterViewHolder) holder;
            if(list==null|| list.size()==0){
                footerViewHolder.itemView.setVisibility(View.GONE);
                return;
            }else{
                footerViewHolder.itemView.setVisibility(View.VISIBLE);
                footerViewHolder.tvSelectCount.setText("已选择"+(list.size())+"台咖啡机");
                footerViewHolder.tvRemoveAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(onCLearAllMachineClickListener!=null){
                            onCLearAllMachineClickListener.onClear();
                        }
                    }
                });
            }

         }
    }

    @Override
    public int getItemViewType(int position) {
        if(list==null){
            if(position==0){
                return TYPE_TIME;
            }else if(position==1){
                return TYPE_SELECT_MACHINE;
            }else{
                return TYPE_FOOTER;
            }
        }else{
            if(position==0){
                return TYPE_TIME;
            }else if(position==1){
                return TYPE_SELECT_MACHINE;
            }else if(position==list.size()+2){
                return TYPE_FOOTER;
            }else{
                return TYPE_MACHINE_LIST;
            }
        }

    }

    @Override
    public int getItemCount() {
        return list==null?3:3+list.size();
    }

    public void setData(ArrayList<GroupInfo.SubMachinesBean> machineList) {
        this.list=machineList;
        notifyDataSetChanged();
    }

    public void setCanOnlyChoooseOne(boolean canOnlyChoooseOne) {
        canOnlyChooseOneMachine = canOnlyChoooseOne;
    }

     public void setTimeVisiable(boolean isTimeVisiable){
         this.isTimeVisiable=isTimeVisiable;
     }
    public interface OnSelectMoreMachineClickListener{
        void onClick();
    }
    public interface OnClearAllMachineClickListener{
        void onClear();
    }
    public void setOnSelectMoreMachineClickListener(OnSelectMoreMachineClickListener onSelectMoreMachineClickListener){
        this.onSelectMoreMachineClickListener=onSelectMoreMachineClickListener;
    }
    public void setOnCLearAllMachineClickListener(OnClearAllMachineClickListener onCLearAllMachineClickListener){
        this.onCLearAllMachineClickListener=onCLearAllMachineClickListener;
    }
    public void setOnLongTimePickerListener(OnLongTimePickerListener onLongTimePickerListener){
        this.onLongTimePickerListener=onLongTimePickerListener;
    }
    public void setOnItemDelClickListener(OnItemDelClickListener onItemDelClickListener) {
        this.onItemDelClickListener = onItemDelClickListener;
    }
}
