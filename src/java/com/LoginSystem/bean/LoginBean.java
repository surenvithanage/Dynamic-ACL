/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.LoginSystem.bean;

/**
 *
 * @author suren
 */
public class LoginBean {
    String userid;
    String roleid;
    String username;
    String password;
    String status;
    int days;

    public LoginBean(String userid, String roleid, String username, String password, String status, int days) {
        this.userid = userid;
        this.roleid = roleid;
        this.username = username;
        this.password = password;
        this.status = status;
        this.days = days;
    }

    public LoginBean(String userid, String roleid, String username, String password, String status) {
        this.userid = userid;
        this.roleid = roleid;
        this.username = username;
        this.password = password;
        this.status = status;
    }

    public int getDays() {
        return days;
    }

    public String getStatus() {
        return status;
    }
    
    public LoginBean(){
        
    }

    public LoginBean(String userid, String roleid, String username, String password) {
        this.userid = userid;
        this.roleid = roleid;
        this.username = username;
        this.password = password;
    }

    public String getUserid() {
        return userid;
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

    @Override
    public String toString() {
        return "LoginBean{" + "userid=" + userid + ", roleid=" + roleid + ", username=" + username + ", password=" + password + '}';
    }

    public void setUserid(String userid) {
        this.userid = userid;
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
