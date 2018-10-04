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
public class PrivilageBean {
    private String pid;
    private String roleid;
    private String if_id;

    public PrivilageBean(String pid) {
        this.pid = pid;
    }

    
    public PrivilageBean(String pid, String roleid, String if_id) {
        this.pid = pid;
        this.roleid = roleid;
        this.if_id = if_id;
    }

    public String getPid() {
        return pid;
    }

    public String getRoleid() {
        return roleid;
    }

    public String getIf_id() {
        return if_id;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    public void setIf_id(String if_id) {
        this.if_id = if_id;
    }
    
    
}
