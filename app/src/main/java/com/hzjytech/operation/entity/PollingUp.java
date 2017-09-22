package com.hzjytech.operation.entity;

import java.util.ArrayList;

/**
 * Created by hehongcan on 2017/7/5.
 */
public class PollingUp {
   private int inspectId;
    private ArrayList<String> urls;

    public PollingUp(int inspectId, ArrayList urls) {
        this.inspectId = inspectId;
        this.urls = urls;
    }

    public int getInspectId() {
        return inspectId;
    }

    public void setInspectId(int inspectId) {
        this.inspectId = inspectId;
    }

    public ArrayList<String> getUrl() {
        return urls;
    }

    public void setUrl(ArrayList<String> urls) {
        this.urls = urls;
    }

    @Override
    public String toString() {
        return "PollingUp{" +
                "inspectId=" + inspectId +
                ", url='" + urls + '\'' +
                '}';
    }
}
