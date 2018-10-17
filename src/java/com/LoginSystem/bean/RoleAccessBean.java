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
public class RoleAccessBean {
     private String rid;
    private String interface_id;
    private String interface_name;
    private String function_id;
    private String function_name;

    public String getIf_id() {
        return if_id;
    }

    public RoleAccessBean(String rid, String i_id, String i_name, String f_id, String f_name, String if_id) {
        this.rid = rid;
        this.interface_id = i_id;
        this.interface_name = i_name;
        this.function_id = f_id;
        this.function_name = f_name;
        this.if_id = if_id;
    }
    private String if_id;

    public RoleAccessBean(String rid, String i_id, String i_name, String f_id, String f_name) {
        this.rid = rid;
        this.interface_id = i_id;
        this.interface_name = i_name;
        this.function_id = f_id;
        this.function_name = f_name;
    }

    public RoleAccessBean(String i_id, String i_name, String f_id, String f_name) {
        this.interface_id = i_id;
        this.interface_name = i_name;
        this.function_id = f_id;
        this.function_name = f_name;
    }

    public String getRid() {
        return rid;
    }

    public String getInterface_id() {
        return interface_id;
    }

    public String getInterface_name() {
        return interface_name;
    }

    public String getFunction_id() {
        return function_id;
    }

    public String getFunction_name() {
        return function_name;
    }
}
