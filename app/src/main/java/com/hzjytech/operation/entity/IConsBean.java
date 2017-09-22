package com.hzjytech.operation.entity;

/**
 * Created by hehongcan on 2017/8/22.
 */

public class IConsBean {
    private String iconName;
    private Integer icon;

    public IConsBean(String iconName, Integer icon) {
        this.iconName = iconName;
        this.icon = icon;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public Integer getIcon() {
        return icon;
    }

    public void setIcon(Integer icon) {
        this.icon = icon;
    }
}
