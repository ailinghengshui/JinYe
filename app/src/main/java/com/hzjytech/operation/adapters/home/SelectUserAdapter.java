package com.hzjytech.operation.adapters.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigkoo.pickerview.OptionsPickerView;
import com.hzjytech.operation.R;
import com.hzjytech.operation.adapters.home.viewholders.UserHeadViewHolder;
import com.hzjytech.operation.adapters.home.viewholders.UserViewHolder;
import com.hzjytech.operation.entity.AdminInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hehongcan on 2017/9/6.
 */

public class SelectUserAdapter extends RecyclerView.Adapter{
    private static final int TYPE_HEAD = 0;
    private static final int TYPE_USER = 1;
    private  Context context;
    //选择的adminid，第一步使用后，后面不再作为数据处理的依据
    private ArrayList<Integer> selectUserIds;
    //未被选择的list集合
    private List<AdminInfo> list;
    private final LayoutInflater mInflater;
    private List<AdminInfo> mSelectAdmins=new ArrayList<>();
    private boolean staffAuth;

    public SelectUserAdapter(
            Context context, List<AdminInfo> list, boolean staffAuth) {
        this.context=context;
        mInflater = LayoutInflater.from(context);
        mSelectAdmins.clear();
        if(list!=null){
            mSelectAdmins.addAll(list);
        }
        this.staffAuth=staffAuth;
    }

    public void setData(List<AdminInfo> list) {
        if(list==null){
            return;
        }
        this.list=list;
        if(mSelectAdmins!=null){
            list.removeAll(mSelectAdmins);
        }
        notifyDataSetChanged();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==TYPE_HEAD){
            View view = mInflater.inflate(R.layout.item_select_user_head, null, false);
            return new UserHeadViewHolder(view);
        }else{
            View view = mInflater.inflate(R.layout.item_select_users, null, false);
            return new UserViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
      if(holder instanceof UserHeadViewHolder){
          final ArrayList<String> unSelctAdminNames = new ArrayList<>();
          if(list==null){
              return;
          }
              for (AdminInfo adminInfo : list) {
                  unSelctAdminNames.add(adminInfo.getAdminName());
              }
          ((UserHeadViewHolder) holder).mRlAddAdmin.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  //条件选择器
                  OptionsPickerView pvOptions = new OptionsPickerView.Builder(context,
                          new OptionsPickerView.OnOptionsSelectListener() {
                              @Override
                              public void onOptionsSelect(int options1, int option2, int options3, View v) {
                                  AdminInfo addAdmin = list.remove(options1);
                                  mSelectAdmins.add(addAdmin);
                                  notifyDataSetChanged();

                              }
                          }).build();
                  pvOptions.setPicker(unSelctAdminNames);
                  pvOptions.show();
              }
          });
      }else if(holder instanceof UserViewHolder){
          final int realPosition=staffAuth?position-1:position;
          if(staffAuth){
              ((UserViewHolder) holder).mTvRemoveAdmin.setVisibility(View.VISIBLE);
          }else{
              ((UserViewHolder) holder).mTvRemoveAdmin.setVisibility(View.GONE);
          }
          ((UserViewHolder) holder).mTvAdminName.setText(mSelectAdmins.get(realPosition).getAdminName());
          ((UserViewHolder) holder).mTvRemoveAdmin.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  AdminInfo removeAdmin = mSelectAdmins.remove(realPosition);
                  list.add(removeAdmin);
                  notifyDataSetChanged();
              }
          });
      }
    }

    @Override
    public int getItemViewType(int position) {
        if(staffAuth){
            if(position==0){
                return TYPE_HEAD;
            }else{
                return TYPE_USER;
            }
        }else{
            return TYPE_USER;
        }


    }

    @Override
    public int getItemCount() {
        if(staffAuth){
            return mSelectAdmins==null?1:mSelectAdmins.size()+1;
        }else {
            return mSelectAdmins==null?0:mSelectAdmins.size();
        }

    }
    public List<AdminInfo> getSelectAdmins(){
        return mSelectAdmins ;
    }
}
