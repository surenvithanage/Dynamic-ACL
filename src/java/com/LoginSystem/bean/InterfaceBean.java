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
public class InterfaceBean {
    private String interfaceid;
    private String name;
    private String description;
    private String url;
    
    

    public InterfaceBean(String interfaceid ,String name , String url ) {
        this.interfaceid = interfaceid;
        this.name = name;
        this.url = url;
    }

    public InterfaceBean(String name) {
        this.name = name;
    }

    public InterfaceBean() {
        
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getInterfaceid() {
        return interfaceid;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setInterfaceid(String interfaceid) {
        this.interfaceid = interfaceid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
}
