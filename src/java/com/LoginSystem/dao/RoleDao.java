/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.LoginSystem.dao;

import com.LoginSystem.bean.FunctionInterfaceBean;
import com.LoginSystem.bean.InterfaceBean;
import com.LoginSystem.bean.RoleBean;
import com.LoginSystem.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author suren_v
 */
public class RoleDao {

    public ArrayList<FunctionInterfaceBean> getFunctionList() {
        ArrayList<FunctionInterfaceBean> getList = new ArrayList<>();
        try {
            Connection con = DBConnection.createConnection();
            Statement statement = con.createStatement();
            String sql = "SELECT i.name , f.name , fi.if_id FROM interface i , func_interface fi , function f where i.interfaceid = fi.interfaceid and f.functionid = fi.functionid";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                FunctionInterfaceBean functionInterface = new FunctionInterfaceBean(resultSet.getString("fi.if_id"), resultSet.getString("i.name"), resultSet.getString("f.name"));
                getList.add(functionInterface);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RoleDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(RoleDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return getList;
    }
    
    public ArrayList<InterfaceBean> getInterfaces(){
        ArrayList<InterfaceBean> list = new ArrayList<>();  
        try {
            Connection con = DBConnection.createConnection();
            Statement statement = con.createStatement();
            String sql = "select * from interface";
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                InterfaceBean interfaces = new InterfaceBean(resultSet.getString("name"));
                list.add(interfaces);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RoleDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(RoleDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public void insertPrivilage(String rolename, String[] function) {
        String roleID = "";
        try {
            Connection con = DBConnection.createConnection();
            String sql = "INSERT INTO role(rolename) VALUES (? )";
            PreparedStatement statement = con.prepareCall(sql);
            statement.setString(1, rolename);
            statement.executeUpdate();

            String getRoleId = "Select * from role where rolename = '" + rolename + "'";
            Statement stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery(getRoleId);
            while (resultSet.next()) {
                roleID = resultSet.getString("roleid");
            }

            for (int i = 0; i < function.length; i++) {
                String result = "INSERT INTO privilage(roleid , if_id) VALUES (? , ?)";
                PreparedStatement stat = con.prepareStatement(result);
                stat.setString(1, roleID);
                stat.setString(2, function[i]);
                stat.executeUpdate();
            }

            con.close();        //Closing the connection
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RoleDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(RoleDao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
