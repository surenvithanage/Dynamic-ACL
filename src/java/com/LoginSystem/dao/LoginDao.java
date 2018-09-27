/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.LoginSystem.dao;

import com.LoginSystem.bean.InterfaceBean;
import com.LoginSystem.bean.LoginBean;
import com.LoginSystem.util.DBConnection;
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
public class LoginDao {
    Statement statement;
    ResultSet resultSet;
    Connection con;
    String username = "";
    String password = "";
    String roleID ="";
    
    public boolean getAuthentication(LoginBean loginbean){
 
        username = loginbean.getUsername();
        password = loginbean.getPassword();
      
        try {
            con = DBConnection.createConnection();
                             
            statement = con.createStatement();
          
            String sql = "SELECT * FROM user";
            
            resultSet = statement.executeQuery(sql);

            while(resultSet.next()){
                if(username.equals(resultSet.getString("username")) && password.equals(resultSet.getString("password"))){
                    return true;
                }
            }
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LoginDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(LoginDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public int getUserId(LoginBean loginbean){
        
        username = loginbean.getUsername();
        password = loginbean.getPassword();
        
        try {
            con = DBConnection.createConnection();
            String sql = "SELECT * FROM user";
            statement = con.createStatement();
            resultSet = statement.executeQuery(sql);
            
            while(resultSet.next()){
                if(username.equals(resultSet.getString("username")) && password.equals(resultSet.getString("password")))
                    return resultSet.getInt("userid");
            }
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LoginDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(LoginDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -99;
    }
    
    public int getRoleId(LoginBean loginbean){
        
        username = loginbean.getUsername();
        password = loginbean.getPassword();
        
        try {
            con = DBConnection.createConnection();
            String sql = "SELECT * FROM user u , role r where u.roleid = r.roleid";
            statement = con.createStatement();
            resultSet = statement.executeQuery(sql);
            
            while(resultSet.next()){
                if(username.equals(resultSet.getString("u.username")) && password.equals(resultSet.getString("u.password"))){
                    loginbean.setRoleid(resultSet.getString("u.roleid"));
                    loginbean.setUsername(resultSet.getString("u.username"));
                    return resultSet.getInt("u.roleid");
                }
            }
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LoginDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(LoginDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -99;
    }
    
    public ArrayList<LoginBean> getUserData(LoginBean loginbean){
        username = loginbean.getUsername();
        password = loginbean.getPassword();
        
        ArrayList<LoginBean> arraylist = new ArrayList<>();
        try {
            con = DBConnection.createConnection();
            String sql = "SELECT * FROM user u , role r where u.roleid = r.roleid";
            statement = con.createStatement();
            resultSet = statement.executeQuery(sql);
            
            while(resultSet.next()){
                if(username.equals(resultSet.getString("u.username")) && password.equals(resultSet.getString("u.password"))){
                    LoginBean logDetails = new LoginBean(resultSet.getString("u.userid") , resultSet.getString("u.roleid"),resultSet.getString("u.username") , resultSet.getString("u.password"));
                    arraylist.add(logDetails);
                }
                   
            }
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LoginDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(LoginDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return arraylist;
    }
    
    public ArrayList<InterfaceBean> getPages(){
        ArrayList<InterfaceBean> page = new ArrayList<>();
         try {
            con = DBConnection.createConnection();
            String sql = "select DISTINCT i.interfaceid, i.name , i.url from privilage p , func_interface fi , interface i where p.if_id = fi.if_id and fi.interfaceid = i.interfaceid and p.roleid = 1";
            statement = con.createStatement();
            resultSet = statement.executeQuery(sql);
            
            while(resultSet.next()){
                   InterfaceBean interfaceBean = new InterfaceBean(resultSet.getString("i.interfaceid"),resultSet.getString("i.name"),resultSet.getString("i.url"));
                   page.add(interfaceBean);
            }
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LoginDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(LoginDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return page;
    }
}
