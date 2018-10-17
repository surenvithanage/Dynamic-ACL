/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.LoginSystem.bean;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author suren_v
 */
public class RoleBean {
    private String id;
    private String rolename;
    private ArrayList<InterfaceBean> interfaceBean;
    private List<RoleAccessBean> roleAccessBean;


    public RoleBean(String id, String rolename) {
        this.id = id;
        this.rolename = rolename;
    }

    public RoleBean(String id, String rolename, ArrayList<InterfaceBean> interfaceBean) {
        this.id = id;
        this.rolename = rolename;
        this.interfaceBean = interfaceBean;
    }

    public ArrayList<InterfaceBean> getInterfaceBean() {
        return interfaceBean;
    }

    public List<RoleAccessBean> getRoleAccess_bean() {
        return roleAccessBean;
    }

    public RoleBean(String id, String rolename, List<RoleAccessBean> roleAccessBean) {
        this.id = id;
        this.rolename = rolename;
        this.roleAccessBean = roleAccessBean;
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
