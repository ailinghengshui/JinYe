package com.hzjytech.operation.adapters.data;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hzjytech.operation.R;
import com.hzjytech.operation.adapters.data.viewholders.SelectMachineViewHolder;
import com.hzjytech.operation.entity.GroupInfo;
import com.hzjytech.operation.entity.Groups;
import com.hzjytech.operation.entity.Node;
import com.hzjytech.operation.module.data.TreeNodeHelper;
import com.hzjytech.operation.utils.DensityUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.hzjytech.operation.entity.Groups.SubGroupsBean.SubMachinesBean;

/**
 * Created by hehongcan on 2017/5/18.
 */
public class SelectMachineAdapter extends RecyclerView.Adapter {
    private  List<Node> allNodes;
    private  List<Node> visibilityNodes;
    private  Context context;
    private  List<Groups> groups;
    private LayoutInflater inflater;
    private OnSureClickableListener onSureClickable;
    private Set<Integer> mSelectedNodes=new HashSet<>();

    public SelectMachineAdapter(Context context, List<Groups> groups) {
        this.context=context;
        this.groups=groups;

    }

    public void setData(Context context, List<Groups> groups) {
        this.groups=groups;
        inflater = LayoutInflater.from(context);
        try {
            allNodes = TreeNodeHelper.getSortedNodes(groups);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        visibilityNodes = TreeNodeHelper.getVisibilityNodes(allNodes);
        reConfirmSelectNodes();

        notifyDataSetChanged();
    }

    /**
     * 搜索的咖啡机列表
     * @param machines
     */
    public void setData(List<SubMachinesBean>machines) {
        allNodes = TreeNodeHelper.getSimpleNodes(machines);
        visibilityNodes = allNodes;
        reConfirmSelectNodes();

        notifyDataSetChanged();
    }

    /**
     * 根据已有的selectNodes,重新确定选择的nodes
     */
    private void reConfirmSelectNodes() {
        if(mSelectedNodes!=null){
            for (Integer i: mSelectedNodes) {
                for (Node node : allNodes) {
                    if(node.getIsTwoOneOrMachine()==0&&node.getId()==i){
                        node.setCheck(true);
                        isParentSelect(node);
                    }
                }
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_select_machine_group, parent, false);
        final SelectMachineViewHolder holder = new SelectMachineViewHolder(view);
        return holder;
    }
    private boolean isAllChecked(List<Node>nodes){
    for (Node node1 : nodes) {
      if(!node1.isCheck()){
          return false;
      }
    }
    return true;
}
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final Node node = visibilityNodes.get(position);
        final SelectMachineViewHolder machineViewHolder = (SelectMachineViewHolder) holder;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        int dp2px = DensityUtil.dp2px(context,30);
        machineViewHolder .llContainer.setPadding(dp2px*node.getLevel(),0,0,0);
        machineViewHolder.ivSelectMachineIsChecked.setChecked(node.isCheck());
        switch (node.getIsTwoOneOrMachine()){
            case 0:
                machineViewHolder.itemBlank.setVisibility(View.GONE);
                machineViewHolder.tvSelectMachineIcon.setBackgroundResource(R.drawable.shape_home_rect);
                machineViewHolder.tvSelectMachineIcon.setText(node.getId()+"");
                machineViewHolder.tvSelectMachineName.setText(node.getTitle());
                machineViewHolder.tvSelectMachineDes.setText(node.getDes());
                machineViewHolder.tvSelectMachineDes.setVisibility(View.VISIBLE);
                machineViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectMachine(position);
                    }
                });
                machineViewHolder.ll_check.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectMachine(position);
                    }
                });
                break;
            case 1:
                machineViewHolder.tvSelectMachineIcon.setText("");
                machineViewHolder.tvSelectMachineIcon.setBackgroundResource(R.drawable.icon_group);
                machineViewHolder.tvSelectMachineName.setText(node.getTitle());
                machineViewHolder.tvSelectMachineDes.setVisibility(View.GONE);
                if(node.getParent()==null){
                    machineViewHolder.itemBlank.setVisibility(View.VISIBLE);
                  //  machineViewHolder.itemLine.setVisibility(View.GONE);
                }else{
                    machineViewHolder.itemBlank.setVisibility(View.GONE);
                 //   machineViewHolder.itemLine.setVisibility(View.VISIBLE);
                }
                machineViewHolder.ll_check.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectMachine(position);
                    }
                });
                machineViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        expandOrCollapse(position);
                    }
                });
                break;
            case 2:
                machineViewHolder.tvSelectMachineIcon.setText("");
                machineViewHolder.tvSelectMachineIcon.setBackgroundResource(R.drawable.icon_groups);
                machineViewHolder.tvSelectMachineName.setText(node.getTitle());
                machineViewHolder.tvSelectMachineDes.setVisibility(View.GONE);
                machineViewHolder.itemBlank.setVisibility(View.VISIBLE);
                machineViewHolder.ll_check.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectMachine(position);
                    }
                });
                machineViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        expandOrCollapse(position);
                    }
                });
                break;
            default:
                break;
        }
        if(position==0){
            machineViewHolder.itemBlank.setVisibility(View.GONE);
        }
        if(node.getIcon()==-1){
            machineViewHolder.ivSelectMachineDown.setVisibility(View.INVISIBLE);
        }else{
            machineViewHolder.ivSelectMachineDown.setVisibility(View.VISIBLE);
            machineViewHolder.ivSelectMachineDown.setImageResource(node.getIcon());
        }


    }


    @Override
    public int getItemCount() {
        return visibilityNodes==null?0:visibilityNodes.size();
    }
   private void selectMachine(int position){
       Node nowNode = visibilityNodes.get(position);
       //保存选择的咖啡机
       nowNode.setCheck(!nowNode.isCheck());
       if(nowNode.getIsTwoOneOrMachine()==0){
           if(nowNode.isCheck()){
               mSelectedNodes.add(nowNode.getId());
           }else {
               mSelectedNodes.remove(nowNode.getId());
           }

       }
       //处理选择组以后的向下连锁操作
        isChildSelect(nowNode);
       //判断是否全部选中,如果是,则将父节点置为选中
       isParentSelect(nowNode);
       if(onSureClickable!=null){
           if(getSelectedMachine()!=null&&getSelectedMachine().size()>0){
               onSureClickable.clickable(true);
           }else{
               onSureClickable.clickable(false);
           }
       }
       notifyDataSetChanged();
   }
    /**
     * 处理选择组以后的向下连锁操作
     */
    private void isChildSelect(Node nowNode){
        List<Node> childs = nowNode.getChilds();
        if(childs.size()!=0){
            for (Node child : childs) {
                child.setCheck(nowNode.isCheck());
                if(child.getIsTwoOneOrMachine()==0){
                    if(child.isCheck()){
                        mSelectedNodes.add(child.getId());
                    }else {
                        mSelectedNodes.remove(child.getId());
                    }

                }
                List<Node> childChilds = child.getChilds();
                if(childChilds.size()!=0){
                    for (Node childChild : childChilds) {
                        childChild.setCheck(nowNode.isCheck());
                        if(childChild.getIsTwoOneOrMachine()==0){
                            if(childChild.isCheck()){
                                mSelectedNodes.add(childChild.getId());
                            }else {
                                mSelectedNodes.remove(childChild.getId());
                            }

                        }
                    }
                }
            }
        }
    }
    /**
     * 处理选择一台咖啡机后向上的连锁操作
     */
    private void isParentSelect(Node nowNode){
        Node parent1 = nowNode.getParent();
        if(parent1!=null){
            List<Node> childs1 = parent1.getChilds();
            boolean allChecked = isAllChecked( childs1);
            if(allChecked){
                parent1.setCheck(true);
            }else{
                parent1.setCheck(false);
            }
            //还要进一步判断二级菜单
            Node parent2 = parent1.getParent();
            if(parent2!=null){
                List<Node> childs2 = parent2.getChilds();
                boolean allChecked2 = isAllChecked(childs2);
                if(allChecked2){
                    parent2.setCheck(true);
                }else{
                    parent2.setCheck(false);
                }
            }
        }
    }
    /**
     * 展开收缩
     * @param position
     */
    private void expandOrCollapse(int position){
        Node node = visibilityNodes.get(position);
        if(node!=null){
            node.setExpand(!node.isExpand());
            visibilityNodes= TreeNodeHelper.getVisibilityNodes(allNodes);
            notifyDataSetChanged();
        }
    }
    /**
     *  获取选中的咖啡机
     */
   public List<GroupInfo.SubMachinesBean> getSelectedMachine(){
       ArrayList<GroupInfo.SubMachinesBean> list = new ArrayList<>();
       for (Node allNode : allNodes) {
           if(allNode.getIsTwoOneOrMachine()==0&&allNode.isCheck()){
               list.add(new GroupInfo.SubMachinesBean(allNode.getId(),allNode.getTitle(),allNode.getDes()));
           }
       }
     return list;
   }


    //全选
    public void setAllSelected() {
        if(!getIsSlectAll()){
            for (Node allNode : allNodes) {
                allNode.setCheck(true);
                if(allNode.getIsTwoOneOrMachine()==0){
                    mSelectedNodes.add(allNode.getId());
                }

            }
        }else{
            for (Node allNode : allNodes) {
                allNode.setCheck(false);
                if(allNode.getIsTwoOneOrMachine()==0){
                    mSelectedNodes.remove(allNode.getId());
                }
            }
        }
        if(onSureClickable!=null){
            if(getSelectedMachine()!=null&&getSelectedMachine().size()>0){
                onSureClickable.clickable(true);
            }else{
                onSureClickable.clickable(false);
            }
        }
        notifyDataSetChanged();
    }

    private boolean getIsSlectAll() {
        if(allNodes==null){
            return false;
        }
        for (Node node : allNodes) {
            if(!node.isCheck()){
                return false;
            }
        }
        return true;
    }

    public interface OnSureClickableListener {
        void clickable(boolean b);
    }
    public void setOnSureClickableListener(OnSureClickableListener onSureClickable){
        this.onSureClickable=onSureClickable;
    }
}
