package com.hzjytech.operation.adapters.home.viewholders;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hzjytech.operation.R;
import com.hzjytech.operation.constants.Constants;
import com.hzjytech.operation.entity.Machies;
import com.hzjytech.operation.inter.SearchViewClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by hehongcan on 2017/4/24.
 */
public class SearchViewHolder extends RecyclerView.ViewHolder {
    private SearchViewClickListener searchViewClickListener;
    @BindView(R.id.tv_home_search)
    public TextView searchView;
    @BindView(R.id.rl_item_search)
    public RelativeLayout rl_item_search;
    public SearchViewHolder(View searchView) {
        super(searchView);
        ButterKnife.bind(this, searchView);
    }

    public SearchViewHolder(View searchView, SearchViewClickListener searchTextChangeListener) {
        this(searchView);
        this.searchViewClickListener = searchTextChangeListener;
    }

    public void setViewData(Context mContext, Machies machies, int position, int viewType, final int type) {
        switch (type){
            case Constants.state_opteration:
                searchView.setText("搜索-" + machies.getOperation() + "台咖啡机运营中");
                break;
            case Constants.state_error:
                searchView.setText("搜索-" + machies.getSick() + "台咖啡机错误");
                break;
            case Constants.state_lack:
                searchView.setText("搜索-" + machies.getLack() + "台咖啡机余料不足");
                break;
            case Constants.state_lock:
                searchView.setText("搜索-" + machies.getLocked() + "台咖啡机已锁定");
                break;
            case Constants.state_offline:
                searchView.setText("搜索-" + machies.getOffline() + "台咖啡机离线状态");
                break;
            case Constants.state_unoperation:
                searchView.setText("搜索-" + machies.getOffOperation() + "台咖啡机未运营");
                break;
            case Constants.state_single_machine:
                searchView.setText("查找咖啡机");
                break;
            case Constants.state_single_group:
                searchView.setText("查找分组");
                break;
            case Constants.state_single_menu:
                searchView.setText("查找菜单");
                break;
            case Constants.state_my_realse_task:
                searchView.setText(R.string.search_task);
                break;
            case Constants.state_remain_task:
                searchView.setText(R.string.search_task);
                break;
            case Constants.state_finished_task:
                searchView.setText(R.string.search_task);
                break;
            default:break;
        }

        rl_item_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchViewClickListener!=null){
                    searchViewClickListener.searchViewClick(type);
                }
            }
        });

    }


}
