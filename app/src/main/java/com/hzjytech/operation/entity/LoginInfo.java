package com.hzjytech.operation.entity;

import java.util.List;

/**
 * Created by hehongcan on 2017/4/17.
 */
public class LoginInfo {

    /**
     * id : null
     * login : 13971023577
     * password : ***
     * email : null
     * phone : null
     * name : null
     * status : null
     * lastLogin : null
     * wxpayOpenid : null
     * createdAt : null
     * updatedAt : null
     * roleId : null
     * checkCode : null
     * errorTimes : null
     * token : eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJZdW5XZWlTeXN0ZW0iLCJpYXQiOjE0OTI0MjE0NzAsInN1YiI6IntcImlkXCI6ODAsXCJsb2dpblwiOlwiMTM5NzEwMjM1NzdcIixcInJvbGVJZFwiOjJ9IiwiZXhwIjoxNDkyNDMxMDcwfQ.3dih61RqgX7su72HXmRyCdYPgOo6yMVQPpaA6af-MCM
     * authEntity : null
     * url : null
     * auth : [[],["2.0"],["3.1","3.2","3.3","3.4","3.5","3.6","3.7"]]
     * systemRole : 2
     * adminId : 80
     */

    private Object id;
    private String login;
    private String password;
    private Object email;
    private Object phone;
    private Object name;
    private Object status;
    private Object lastLogin;
    private Object wxpayOpenid;
    private Object createdAt;
    private Object updatedAt;
    private Object roleId;
    private Object checkCode;
    private Object errorTimes;
    private String token;
    private Object authEntity;
    private Object url;
    private int systemRole;
    private int adminId;
    private List<List<?>> auth;

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public Object getPhone() {
        return phone;
    }

    public void setPhone(Object phone) {
        this.phone = phone;
    }

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    public Object getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Object lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Object getWxpayOpenid() {
        return wxpayOpenid;
    }

    public void setWxpayOpenid(Object wxpayOpenid) {
        this.wxpayOpenid = wxpayOpenid;
    }

    public Object getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Object createdAt) {
        this.createdAt = createdAt;
    }

    public Object getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Object updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Object getRoleId() {
        return roleId;
    }

    public void setRoleId(Object roleId) {
        this.roleId = roleId;
    }

    public Object getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(Object checkCode) {
        this.checkCode = checkCode;
    }

    public Object getErrorTimes() {
        return errorTimes;
    }

    public void setErrorTimes(Object errorTimes) {
        this.errorTimes = errorTimes;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Object getAuthEntity() {
        return authEntity;
    }

    public void setAuthEntity(Object authEntity) {
        this.authEntity = authEntity;
    }

    public Object getUrl() {
        return url;
    }

    public void setUrl(Object url) {
        this.url = url;
    }

    public int getSystemRole() {
        return systemRole;
    }

    public void setSystemRole(int systemRole) {
        this.systemRole = systemRole;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public List<List<?>> getAuth() {
        return auth;
    }

    public void setAuth(List<List<?>> auth) {
        this.auth = auth;
    }
}
