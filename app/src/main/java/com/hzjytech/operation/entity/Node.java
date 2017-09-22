package com.hzjytech.operation.entity;

import com.hzjytech.operation.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hehongcan on 2017/4/14.
 */
public class Node {
    private int id;
    private int parentId;
    private String title;
    private String des;
    private int icon;
    private Node parent;
    private List<Node> childs=new ArrayList<>();
    private int level;
    private boolean isFooter;
    private boolean isLeaf;
    private boolean isCheck=false;
     private boolean isExpand;
    private int isTwoOneOrMachine;
    private boolean isParentExpand;

    public Node(int id, int parentId, String title, String des) {
        this.id = id;
        this.parentId = parentId;
        this.title = title;
        this.des = des;
    }

    public void setFooter(boolean footer) {
        isFooter = footer;
    }

    public int getIsTwoOneOrMachine() {
        return isTwoOneOrMachine;
    }

    public void setIsTwoOneOrMachine(int isTwoOneOrMachine) {
        this.isTwoOneOrMachine = isTwoOneOrMachine;
    }

    public void setParentExpand(boolean parentExpand) {
        isParentExpand = parentExpand;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public List<Node> getChilds() {
        return childs;
    }

    public void setChilds(List<Node> childs) {
        this.childs = childs;
    }

    public int getLevel() {
        if(getParent()==null){
            level=0;
        }else{
            level=getParent().getLevel()+1;
        }
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isFooter() {
        if(getParent()==null){
            return true;
        }
        return false;
    }

    public boolean isLeaf() {
       return childs.size()==0;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }


    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean expand) {
        if(isLeaf()){
            setIcon(-1);
            return;
        }
        isExpand = expand;
        if(isExpand){
            setIcon(R.drawable.icon_arrow_up);
        }else{
            setIcon(R.drawable.icon_arrow_down);
        }
        if(!isExpand){
            for (Node child : childs) {
                child.setExpand(false);
            }
        }
    }

    public boolean isParentExpand() {
        if(parent==null){
            return false;
        }
        return parent.isExpand();
    }

    @Override
    public String toString() {
        return "Node{" + "id=" + id + ", parentId=" + parentId + ", title='" + title + '\'' + ", " +
                "des='" + des + '\'' + ", icon=" + icon + ", parent=" + parent + ", childs=" +
                childs + ", level=" + level + ", isFooter=" + isFooter + ", isLeaf=" + isLeaf +
                ", isCheck=" + isCheck + ", isExpand=" + isExpand + ", isTwoOneOrMachine=" +
                isTwoOneOrMachine + ", isParentExpand=" + isParentExpand + '}';
    }
}
