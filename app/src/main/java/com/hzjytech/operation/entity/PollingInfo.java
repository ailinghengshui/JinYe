package com.hzjytech.operation.entity;

import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;

/**
 * Created by hehongcan on 2017/6/7.
 */
public class PollingInfo {
    private String title;
    private String content;
    private int no;
    private ArrayList<LocalMedia>urls;
    private ArrayList<String>urlStrs;
    private boolean isCheck;

    public PollingInfo() {
    }



    public PollingInfo(String title, String content, int no, ArrayList<LocalMedia> urls,boolean isCheck) {
        this.title = title;
        this.content = content;
        this.no = no;
        this.urls=urls;
        this.isCheck = isCheck;
    }

    public PollingInfo(
            String title, String content, int no, ArrayList<LocalMedia> urls,ArrayList<String> urlStrs, boolean isCheck) {
        this.title = title;
        this.content = content;
        this.no = no;
        this.urls=urls;
        this.urlStrs = urlStrs;
        this.isCheck = isCheck;
    }

    public ArrayList<String> getUrlStrs() {
        return urlStrs;
    }

    public void setUrlStrs(ArrayList<String> urlStrs) {
        this.urlStrs = urlStrs;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public ArrayList<LocalMedia> getUrls() {
        return urls;
    }

    public void setUrls(ArrayList<LocalMedia> urls) {
        this.urls = urls;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return no;
    }

    public void setId(int id) {
        this.no = id;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    @Override
    public String toString() {
        return "PollingInfo{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", no=" + no +
                ", urls=" + urls +
                ", urlStrs=" + urlStrs +
                ", isCheck=" + isCheck +
                '}';
    }
}
