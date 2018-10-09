/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.LoginSystem.bean;

/**
 *
 * @author suren_v
 */
public class UserBean {
    private String userid;
    private String roleid;
    private String username;
    private String password;
    private String status;

    public UserBean(String userid, String roleid, String username, String password, String status) {
        this.userid = userid;
        this.roleid = roleid;
        this.username = username;
        this.password = password;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
    
    public UserBean(String userid, String roleid, String username, String password) {
        this.userid = userid;
        this.roleid = roleid;
        this.username = username;
        this.password = password;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
    
    public UserBean(String roleid, String username, String password) {
        this.roleid = roleid;
        this.username = username;
        this.password = password;
    }

    public UserBean(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserBean() {
    }

    public String getRoleid() {
        return roleid;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
}
