package com.hzjytech.operation.entity;

import java.util.ArrayList;

/**
 * Created by hehongcan on 2017/8/7.
 * "total": "integer,总条数"
 */


public class TaskList {
    private int total;
    private ArrayList<TaskListInfo> taskList;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<TaskListInfo> getTasks() {
        return taskList;
    }

    public void setTasks(ArrayList<TaskListInfo> tasks) {
        this.taskList = tasks;
    }
}
