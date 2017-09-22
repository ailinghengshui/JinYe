package com.hzjytech.operation.module.data;

import com.hzjytech.operation.entity.Groups;
import com.hzjytech.operation.entity.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hehongcan on 3017/4/14.
 */
public class TreeNodeHelper {
    public static List<Node> convertDataToNodes(List<Groups>datas)throws IllegalAccessException{
        ArrayList<Node> nodes = new ArrayList<>();
        /**
         * 循环比较两个node，设置节点关系
         */
        for (Groups data : datas) {
            Node node = new Node(data.getId(), 0, data.getName(), "我是二级菜单");
            node.setParent(null);
            node.setFooter(true);
            node.setLevel(0);
            node.setExpand(false);
            nodes.add(node);
            if((data.getSubGroups()==null||data.getSubGroups().size()==0)&&(data.getSubMachines()==null||data.getSubMachines().size()==0)){
                node.setLeaf(true);
                node.setIsTwoOneOrMachine(1);
            }else{
                if(data.getSubGroups()!=null&&data.getSubGroups().size()!=0){
                    node.setIsTwoOneOrMachine(2);
                    for (Groups.SubGroupsBean subGroupsBean : data.getSubGroups()) {
                        Node node2 = new Node(subGroupsBean.getGroupId(), 0, subGroupsBean.getGroupName(), "我是一级菜单");
                        node2.setParent(node);
                        node2.setLevel(1);
                        node.getChilds().add(node2);
                        node2.setIsTwoOneOrMachine(1);
                        nodes.add(node2);
                        for (Groups.SubGroupsBean.SubMachinesBean subMachinesBean : subGroupsBean.getSubMachines()) {
                            Node node3 = new Node(subMachinesBean.getMachineId(), 0, subMachinesBean.getMachineName(), subMachinesBean.getAddress());
                            node3.setParent(node2);
                            node3.setLeaf(true);
                            node3.setLevel(2);
                            node2.getChilds().add(node3);
                            node3.setIsTwoOneOrMachine(0);
                            nodes.add(node3);
                        }

                    }
                }
                if(data.getSubMachines()!=null&&data.getSubMachines().size()!=0){
                    for (Groups.SubGroupsBean.SubMachinesBean subMachinesBean : data.getSubMachines()) {
                    Node node1 = new Node(subMachinesBean.getMachineId(), 0, subMachinesBean.getMachineName(), subMachinesBean.getAddress());
                    node1.setParent(node);
                    node1.setLeaf(true);
                    node1.setLevel(1);
                        node.getChilds().add(node1);
                    nodes.add(node1);
                        if(node.getIsTwoOneOrMachine()!=2){
                            node.setIsTwoOneOrMachine(1);
                        }
                        node1.setIsTwoOneOrMachine(0);

                }}


            }
        }
    /*   for(int i=0;i<nodes.size();i++){
           Node node = nodes.get(i);
           for (int j=i+1;j<nodes.size();j++){
               Node m = nodes.get(j);
               if(m.getId()==node.getParentId()){
                   m.getChilds().add(node);
                   node.setParent(m);
               }else if(m.getParentId()==node.getId()){
                   node.getChilds().add(m);
                   m.setParent(node);
               }

           }
       }*/
        /**
         * 为节点设置图标
         * 1、如果当前节点有子节点，并处于展开状态，设置收起的图标
         * 3、如果当前节点有子节点，并处于不展开状态，设置展开的图标
         * 3、如果当前节点无子节点，穿参-1，不设置图标
         */
        for (Node node : nodes) {
            setNodeIcon(node);
        }
        return nodes;
    }

    private static void setNodeIcon(Node node) {
        if(node.getChilds().size()>0&&node.isExpand()){
            //node.setIcon(R.drawable.icon_arrow_up);
        }else if(node.getChilds().size()>0&&!node.isExpand()){
          //  node.setIcon(R.drawable.icon_arrow_down);
        }else {
            node.setIcon(-1);
        }
    }
    public static List<Node> getSortedNodes(List<Groups>data) throws IllegalAccessException {
        List<Node> results = new ArrayList<Node>();
        List<Node> nodes = convertDataToNodes(data);
        List<Node> rootNodes = getRootNodes(nodes);
        for (Node rootNode : rootNodes) {
            addNode(results,rootNode);
        }
        return results;
    }

    /**
     * 设置层级关系，并根据层级和默认显示层级确认是否打开
     * @param results
     * @param node 当前节点
     */
    private static void addNode(List<Node> results, Node node) {
        results.add(node);
       node.setExpand(false);
        if(node.isLeaf()){
            return;
        }else{
            for (Node n : node.getChilds()) {
                addNode(results,n);
            }
        }
    }

    /**
     * 获取根节点
     * @param nodes
     * @return
     */
    private static List<Node> getRootNodes(List<Node> nodes) {
        ArrayList<Node> roots = new ArrayList<>();
        for (Node node : nodes) {
            if (node.isFooter()){
                roots.add(node);
            }

        }
        return roots;
    }
    /**
     * 过滤可见节点
     */
    public static  List<Node> getVisibilityNodes(List<Node> nodes){
        ArrayList<Node> visibleNodes = new ArrayList<>();
        for (Node node : nodes) {
            if(node.isFooter()||node.getParent().isExpand()){
                visibleNodes.add(node);
            }
        }
        return visibleNodes;
    }

    /**
     * 单一咖啡机nodes
     * @param machines
     * @return
     */
    public static List<Node> getSimpleNodes(List<Groups.SubGroupsBean.SubMachinesBean> machines) {
        ArrayList<Node> nodes = new ArrayList<>();
        for (Groups.SubGroupsBean.SubMachinesBean machine : machines) {
            Node node = new Node(machine.getMachineId(),
                    0,
                    machine.getMachineName(),
                    machine.getAddress());
            node.setParent(null);
            node.setLeaf(true);
            node.setLevel(0);
            node.setIsTwoOneOrMachine(0);
            nodes.add(node);

        }
        return nodes;
    }
}
