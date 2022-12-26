package com.app.ydxdc.ui.entity;

import java.util.List;


public class UserInfo {

    private String msg;
    private int code;
    private List<Roles> roles;
    private User user;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<Roles> getRoles() {
        return roles;
    }

    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static class User {
        private Object searchValue;
        private Object createBy;
        private String createTime;
        private Object updateBy;
        private Object updateTime;
        private Object remark;
        private Object beginTime;
        private Object endTime;
        private Object nowTime;
        private String userId;
        private String deptId;
        private String loginName;
        private String password;
        private String userName;
        private String status;
        private String phonenumber;
        private String sex;
        private Object personnelNumbers;
        private Object sort;
        private Object educationBackground;
        private Object post;
        private Object directSupervisor;
        private Object age;
        private Object hiredate;
        private Object forestProtectionName;
        private Object checkPoint;
        private Object oaUserId;
        private List<String> roleIds;
        private String deptName;
        private Object children;
        private String label;
        private Object lon;
        private Object lat;
        private Object parentId;
        private boolean admin;


    }


    public static class Roles {
        private Object searchValue;
        private Object createBy;
        private Object createTime;
        private Object updateBy;
        private Object updateTime;
        private Object remark;
        private Object beginTime;
        private Object endTime;
        private Object nowTime;
        private String roleId;
        private String roleName;
        private Object sort;
        private Object status;
        private boolean admin;


    }
}
