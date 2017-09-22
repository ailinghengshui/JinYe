package com.hzjytech.operation.inter;

import com.hzjytech.operation.entity.AdminInfo;

import java.util.List;

/**
 * Created by hehongcan on 2017/9/6.
 */

public interface OnRoleClicklistener {
    void click(int roleId, List<AdminInfo> selectUserIds, String s);
}
