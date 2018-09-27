/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.LoginSystem.util;

import com.LoginSystem.bean.FunctionBean;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author suren
 */
public class Function {
    public static  ArrayList<FunctionBean> getPageFunctions(String roleid , String interfaceid){
        ArrayList<FunctionBean> pageFunctions = new ArrayList<>();
        try {
            Connection con = DBConnection.createConnection();
            Statement statement = con.createStatement();
            String sql = "select DISTINCT f.functionid, f.name  from privilage p , interface i , func_interface fi , function f where p.if_id = fi.if_id and fi.functionid = f.functionid and p.roleid ='"+roleid+"' and fi.interfaceid ='"+interfaceid+"'";
            ResultSet resultSet = statement.executeQuery(sql);
            
            while(resultSet.next()){
                FunctionBean functionBean = new FunctionBean(resultSet.getString("f.functionid"),resultSet.getString("f.name"));
                pageFunctions.add(functionBean);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Function.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Function.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pageFunctions;
        
    }
}
