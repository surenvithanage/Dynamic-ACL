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
public class RoleBean {
    private String id;
    private String rolename;

    public RoleBean(String id, String rolename) {
        this.id = id;
        this.rolename = rolename;
    }

    public RoleBean() {
    }

    public String getId() {
        return id;
    }

    public String getRolename() {
        return rolename;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }
    
    
}
