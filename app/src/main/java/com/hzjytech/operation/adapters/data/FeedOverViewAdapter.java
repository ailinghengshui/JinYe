package com.hzjytech.operation.adapters.data;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hzjytech.operation.R;
import com.hzjytech.operation.adapters.data.viewholders.AddMaterialViewHolder;
import com.hzjytech.operation.utils.MyMath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * Created by hehongcan on 2017/5/24.
 */
public class FeedOverViewAdapter extends RecyclerView.Adapter {
    private  Context context;
    private  LayoutInflater inflater;
    private Map<String, Double> data;
    private final ArrayList<String> keyList;

    public FeedOverViewAdapter(Context context, Map<String, Double> data) {
        this.context=context;
        this.data=data;
        inflater = LayoutInflater.from(context);
        keyList = new ArrayList<>();
    }
    public void setData(Map<String, Double> data) {
        this.data = data;
        notifyDataSetChanged();
        Set<String> strings = data.keySet();
        for (String string : strings) {
          keyList.add(string);
        }
        Collections.sort(keyList);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_add_material, parent, false);
        return new AddMaterialViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        AddMaterialViewHolder addMaterialViewHolder = (AddMaterialViewHolder) holder;
        if(position!=0){
            addMaterialViewHolder.viewAddMaterialHead.setVisibility(View.GONE);
        }else{
            addMaterialViewHolder.viewAddMaterialHead.setVisibility(View.VISIBLE);
        }
        addMaterialViewHolder.tvMateriallName.setText(keyList.get(position));
        String s = MyMath.getNormalString(data.get(keyList.get(position)) + "");
        double aDouble = Double.parseDouble(s);
        if(keyList.get(position).equals("杯子")){
            addMaterialViewHolder.tvMaterialValue.setText((int)aDouble+"个");
        } else{
            addMaterialViewHolder.tvMaterialValue.setText(MyMath.getIntOrDouble(aDouble)+"克");
        }

    }

    @Override
    public int getItemCount() {
        return data==null?0:data.size();
    }



}
