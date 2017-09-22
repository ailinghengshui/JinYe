package com.hzjytech.operation.adapters.task;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hzjytech.operation.R;
import com.hzjytech.operation.entity.PersonInfo;
import com.hzjytech.operation.inter.OnRemoveClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hehongcan on 2017/6/15.
 */
public class PersonAdapter extends BaseAdapter {
    private  Context context;
    private  List<String> list;
    private OnRemoveClickListener onRemoveClickListener;

    public PersonAdapter(
            Context context, List<PersonInfo> selectPersonList) {
        this.context=context;
        if(selectPersonList!=null){
            ArrayList<String> names = new ArrayList<>();
            for (PersonInfo personInfo : selectPersonList) {
                     names.add(personInfo.getName());
            }
            this.list=names;
        }

    }

    public PersonAdapter(Context context, List<String> dutyName,boolean isNameList) {
        this.context=context;
        this.list=dutyName;
    }

    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(
            final int position, View convertView, ViewGroup parent) {
        MyViewHolder holder;
        if (convertView == null) {
             holder = new MyViewHolder();
            convertView =LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
            holder.tv_person_name= (TextView) convertView.findViewById(R.id.tv_person_name);
            holder.tv_remove= (TextView) convertView.findViewById(R.id.tv_remove);
            convertView.setTag(holder);

        }else{
             holder = (MyViewHolder) convertView.getTag();
        }
        holder.tv_person_name.setText(list.get(position));
        //点击删除回调
        holder.tv_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onRemoveClickListener!=null){
                    onRemoveClickListener.onRemoveClick(position);
                }
            }
        });
        return convertView;
    }

    public void setData(List<PersonInfo> data) {
        list.clear();
        for (PersonInfo personInfo : data) {
            list.add(personInfo.getName());
        }
        notifyDataSetChanged();
    }

    public void setStringData(List<String> dutyName) {
        list.clear();
        list.addAll(dutyName);
        notifyDataSetChanged();
    }

    public static class MyViewHolder{
          public TextView tv_person_name;
          public TextView tv_remove;
    }
    public void setOnRemoveClickListener(OnRemoveClickListener onRemoveClickListener){
        this.onRemoveClickListener=onRemoveClickListener;
    }
}
