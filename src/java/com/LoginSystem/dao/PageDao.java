/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.LoginSystem.dao;

import com.LoginSystem.bean.FunctionBean;
import com.LoginSystem.bean.FunctionInterfaceBean;
import com.LoginSystem.bean.InterfaceBean;
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
public class PageDao {

    public static void insertPageData(InterfaceBean interfaceBean) {
        String name = interfaceBean.getName();
        String url = interfaceBean.getUrl();
        String description = interfaceBean.getUrl();

        try {
            Connection con = DBConnection.createConnection();
            String sql = "INSERT INTO interface(name , url , description) VALUES ( ? , ? , ? ) ";
            PreparedStatement statement = con.prepareCall(sql);
            statement.setString(1, name);
            statement.setString(2, url);
            statement.setString(3, description);
            statement.executeUpdate();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PageDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PageDao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public ArrayList<FunctionBean> getFunctions() {
        ArrayList<FunctionBean> functionBean = new ArrayList<>();
        try {
            Connection con = DBConnection.createConnection();
            Statement statement = con.createStatement();
            String sql = "SELECT * FROM function";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                FunctionBean functions = new FunctionBean(resultSet.getString("functionid"), resultSet.getString("name"));
                functionBean.add(functions);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PageDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PageDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return functionBean;
    }

    public String getId(String name) {
        String id = "";
        try {
            Connection con = DBConnection.createConnection();
            Statement statement = con.createStatement();
            String sql = "SELECT * FROM interface where name = '" + name + "'";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                id = resultSet.getString("interfaceid");
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PageDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PageDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    public void insertFunctionInterface(String interfaceId, String functionId) {
        try {
            Connection con = DBConnection.createConnection();
            String sql = "INSERT INTO func_interface(interfaceid , functionid) VALUES ( ? , ? ) ";
            PreparedStatement statement = con.prepareCall(sql);
            statement.setString(1, interfaceId);
            statement.setString(2, functionId);

            statement.executeUpdate();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PageDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PageDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setFunctionInterfaceId(String roleid, String interfaceid) {
        String interfaceFunctionId;
        try {
            Connection con = DBConnection.createConnection();
            Statement statement = con.createStatement();
            String sql = "SELECT if_id FROM func_interface where interfaceid = '" + interfaceid + "'";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                interfaceFunctionId = resultSet.getString("if_id");
                String query = "INSERT INTO privilage (roleid , if_id) VALUES (?,?)";
                PreparedStatement stat = con.prepareCall(query);
                stat.setString(1, roleid);
                stat.setString(2, interfaceFunctionId);
                stat.executeUpdate();
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PageDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PageDao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void DeletePageDetails(String id) {
        Connection con;
        try {
            con = DBConnection.createConnection();
            String sql = "DELETE FROM `func_interface` WHERE if_id = '" + id + "'";
            PreparedStatement statement = con.prepareCall(sql);
            statement.executeUpdate();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PageDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PageDao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String getPrivilageId(String id) {
        String privilageId = "";
        Connection con;
        try {
            con = DBConnection.createConnection();
            String sql = "SELECT pid FROM privilage where if_id = '" + id + "'";
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                privilageId = resultSet.getString("pid");
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PageDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PageDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return privilageId;
    }

    public void DeletePrivilagePageDetails(String id) {
        Connection con;
        try {
            con = DBConnection.createConnection();
            String sql = "DELETE FROM `func_interface` WHERE if_id = '" + id + "'";
            PreparedStatement statement = con.prepareCall(sql);
            statement.executeUpdate();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PageDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PageDao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public ArrayList<FunctionInterfaceBean> getOnlyFunctions(String id) {
        ArrayList<FunctionInterfaceBean> getFunctions = new ArrayList<>();
        try {
            Connection con = DBConnection.createConnection();
            String sql = "select fi.if_id , f.name from func_interface fi , function f , interface i where fi.interfaceid = i.interfaceid and fi.functionid = f.functionid and i.interfaceid = '" + id + "'";
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                FunctionInterfaceBean functionName = new FunctionInterfaceBean(resultSet.getString("fi.if_id"), resultSet.getString("f.name"));
                getFunctions.add(functionName);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PageDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PageDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return getFunctions;
    }

    public ArrayList<FunctionInterfaceBean> getFunctionInterfaceList() {
        ArrayList<FunctionInterfaceBean> getList = new ArrayList<>();
        
        try {
            Connection con = DBConnection.createConnection();
            String sql = "select DISTINCT i.interfaceid, i.name from func_interface fi , interface i where fi.interfaceid = i.interfaceid";
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                ArrayList<FunctionBean> functions = new ArrayList<>();
                String interfaceID = resultSet.getString("i.interfaceid");
                String query = "select f.functionid , f.name from func_interface fi , function f , interface i where fi.interfaceid = i.interfaceid and fi.functionid = f.functionid and i.interfaceid = '" + interfaceID + "'";
                ResultSet res = statement.executeQuery(query);

                while (res.next()) {
                    FunctionBean func = new FunctionBean(resultSet.getString("f.functionid"), resultSet.getString("f.name"));
                    functions.add(func);
                }

                FunctionInterfaceBean functionBean = new FunctionInterfaceBean(resultSet.getString("i.interfaceid"), resultSet.getString("i.name"), functions);
                getList.add(functionBean);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PageDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PageDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return getList;
    }

}
