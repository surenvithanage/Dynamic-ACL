/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.LoginSystem.bean;

import java.util.ArrayList;

/**
 *
 * @author suren_v
 */
public class FunctionInterfaceBean {

    private String functionInterfaceId;
    private String interfaceName;
    private String functionName;
    private ArrayList<FunctionBean> function;
    private String url;
    private String description;

    public FunctionInterfaceBean(String functionInterfaceId) {
        this.functionInterfaceId = functionInterfaceId;
    }

    public FunctionInterfaceBean() {
    }

    public ArrayList<FunctionBean> getFunction() {
        return function;
    }
    public FunctionInterfaceBean(String functionInterfaceId, String interfaceName, ArrayList<FunctionBean> f){
        this.functionInterfaceId = functionInterfaceId;
        this.interfaceName = interfaceName;
        this.function = f;
    }
    
    public FunctionInterfaceBean(String functionInterfaceId, String interfaceName, ArrayList<FunctionBean> f , String url , String description) {
        this.functionInterfaceId = functionInterfaceId;
        this.interfaceName = interfaceName;
        this.function = f;
        this.url = url;
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }

    public FunctionInterfaceBean(String functionInterfaceId, String interfaceName, String functionName) {
        this.functionInterfaceId = functionInterfaceId;
        this.interfaceName = interfaceName;
        this.functionName = functionName;
    }

    public FunctionInterfaceBean(String functionInterfaceId, String functionName) {
        this.functionInterfaceId = functionInterfaceId;
        this.functionName = functionName;
    }

    public String getFunctionInterfaceId() {
        return functionInterfaceId;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionInterfaceId(String functionInterfaceId) {
        this.functionInterfaceId = functionInterfaceId;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

}
