package com.hzjytech.operation.adapters.home;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hzjytech.operation.R;
import com.hzjytech.operation.adapters.home.viewholders.DetailMessageViewHolder;
import com.hzjytech.operation.entity.AdminInfo;
import com.hzjytech.operation.entity.MachineInfo;
import com.hzjytech.operation.entity.RoleInfo;
import com.hzjytech.operation.inter.OnInfoClickListener;
import com.hzjytech.operation.inter.OnRoleClicklistener;
import com.hzjytech.operation.inter.OnSbCheckChangeListener;
import com.hzjytech.operation.module.data.ErrorListActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hehongcan on 2017/4/26.
 */
public class DetailMachineAdapter extends RecyclerView.Adapter {
    private static final int TYPE_DETAIL_MESSAGE = 0;
    private static final int TYPE_ROLE = 1;
    private static final int TYPE_ERROR_HISTORY = 2;
    private MachineInfo machineInfo;
    private Context mContext;
    private final LayoutInflater inflater;
    private List<MachineInfo.StaffInfoBean> mStaffList;
    private HashMap<String, List<MachineInfo.StaffInfoBean>> mRoleMap;
    private List<String> nameList = new ArrayList<>();
    private OnInfoClickListener mOnInfoClickListener;
    private ArrayList<Integer> mSelectIds;
    private List<RoleInfo> mRoles;
    private OnSbCheckChangeListener mOnSbCheckChangeListener;
    private OnRoleClicklistener mOnRoleClicklistener;
    private int machineId;
    private boolean mBasicInfoAuth;
    private boolean mStaffInfoAuth;

    public DetailMachineAdapter(Context context, MachineInfo machineInfo) {
        this.mContext = context;
        this.machineInfo = machineInfo;
        inflater = LayoutInflater.from(mContext);
    }

    public void setData(
            MachineInfo machineInfo,
            ArrayList<Integer> selectIds,
            List<RoleInfo> roles,
            int machineId) {
        this.machineInfo = machineInfo;
        this.mSelectIds=selectIds;
        this.mRoles=roles;
        this.machineId=machineId;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_DETAIL_MESSAGE) {
            View view = inflater.inflate(R.layout.item_detail_message, parent, false);
            return new DetailMessageViewHolder(view);
        } else if (viewType == TYPE_ROLE) {
            View view = inflater.inflate(R.layout.item_machine_role, parent, false);
            return new RoleViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.item_error_record, parent, false);
            return new ErrorHistoryViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DetailMessageViewHolder) {
            ((DetailMessageViewHolder) holder).setMessageData(mContext, machineInfo,mOnInfoClickListener,mOnSbCheckChangeListener,mBasicInfoAuth,mStaffInfoAuth);
        } else if (holder instanceof RoleViewHolder) {
            ((RoleViewHolder) holder).setRoleData(machineInfo.getStaffInfo(),mSelectIds,mRoles, position - 1);
        } else if (holder instanceof ErrorHistoryViewHolder) {
        }
    }

    @Override
    public int getItemCount() {
        if (machineInfo == null) {
            return 0;
        } else {
            return 2 + mSelectIds.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_DETAIL_MESSAGE;
        } else if (position == 1 + mSelectIds.size()) {
            return TYPE_ERROR_HISTORY;
        } else {
            return TYPE_ROLE;
        }
    }

    public void setOnInfoClickListener(OnInfoClickListener onInfoClickListener) {
        mOnInfoClickListener = onInfoClickListener;
    }
    public void setOnSbCheckChangeLisener(OnSbCheckChangeListener onSbCheckChangeListener){
        mOnSbCheckChangeListener=onSbCheckChangeListener;
    }
    public void setOnRoleClickListener(OnRoleClicklistener onRoleClickListener){
        mOnRoleClicklistener=onRoleClickListener;
    }

    /**
     * 设置权限问题
     * @param basicInfoAuth 基础信息权限
     * @param staffInfoAuth  人员配置权限
     */
    public void setAuths(boolean basicInfoAuth, boolean staffInfoAuth) {
        this.mBasicInfoAuth=basicInfoAuth;
        this.mStaffInfoAuth=staffInfoAuth;
    }



    public class ErrorHistoryViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rl_add_role)
        RelativeLayout mRlAddRole;
        public ErrorHistoryViewHolder(
                View view) {
            super(view);
            //R.layout.item_error_record
            ButterKnife.bind(this,view);
            mRlAddRole.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ErrorListActivity.class);
                    intent.putExtra("startTime",0);
                    intent.putExtra("endTime",System.currentTimeMillis());
                    ArrayList<Integer> ids = new ArrayList<>();
                    ids.add(machineId);
                    intent.putIntegerArrayListExtra("machinesId",ids);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    public class RoleViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_machine_role)
        TextView mTvMachineRole;
        @BindView(R.id.tv_machine_role_name)
        TextView mTvMachineRoleName;
        @BindView(R.id.rl_role_people)
        RelativeLayout mRlRolePeople;

        public RoleViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void setRoleData(
                List<MachineInfo.StaffInfoBean> staffInfo,
                ArrayList<Integer> selectIds,
                List<RoleInfo> roles,
                int position) {
            final Integer id = selectIds.get(position);
            for (RoleInfo role : roles) {
                if(role.getId()==id){
                    mTvMachineRole.setText(role.getName());
                }
            }
            ArrayList<String> names = new ArrayList<>();
            final ArrayList<AdminInfo> admins = new ArrayList<>();
            final ArrayList<Integer> selectUserIds = new ArrayList<>();
            for (MachineInfo.StaffInfoBean staffInfoBean : staffInfo) {
                if(staffInfoBean.getRoleId()==id){
                    names.add(staffInfoBean.getUserName());
                    selectUserIds.add(staffInfoBean.getUserId());
                    admins.add(new AdminInfo(staffInfoBean.getUserId(),staffInfoBean.getUserName()));
                }
            }
            String s;
            if(names.size()==0){
                s="";
            }
            else if (names.size() > 1) {
                s = names.get(0)
                        + "等";
            } else {
                s = names.get(0)
                       ;
            }
            mTvMachineRoleName.setText(s);
            mRlRolePeople.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     if(mOnRoleClicklistener!=null){
                         mOnRoleClicklistener.click(id,admins,mTvMachineRole.getText().toString());
                     }
                }
            });

        }
    }

}
