package com.hzjytech.operation.entity;

import java.io.Serializable;

/**
 * Created by Hades on 2016/2/16.
 */
public class User implements Serializable {

    /**
     * adminId : 3
     * adminName : 郑昊
     * roleName : 管理员
     * openId : xxx
     * phone : 15267157673
     * email : zhenghao@hzjytech.com
     * password : 62cea5d91d9bd31f787fd0653655ee9a
     * token:lajdfjalkdfjla;jfd;lsajsl;kajf
     */

    private int adminId;
    private String adminName;
    private String roleName;
    private String openId;
    private String phone;
    private String email;
    private String password;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "adminId=" + adminId +
                ", adminName='" + adminName + '\'' +
                ", roleName='" + roleName + '\'' +
                ", openId='" + openId + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +"token="+token+
                '}';
    }
}
