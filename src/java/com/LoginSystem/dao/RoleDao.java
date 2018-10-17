/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.LoginSystem.dao;

import com.LoginSystem.bean.FunctionInterfaceBean;
import com.LoginSystem.bean.InterfaceBean;
import com.LoginSystem.bean.PrivilageBean;
import com.LoginSystem.bean.RoleAccessBean;
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
        Connection con = DBConnection.createConnection();
        try {

            Statement statement = con.createStatement();
            String sql = "SELECT i.name , f.name , fi.if_id FROM interface i , func_interface fi , function f where i.interfaceid = fi.interfaceid and f.functionid = fi.functionid";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                FunctionInterfaceBean functionInterface = new FunctionInterfaceBean(resultSet.getString("fi.if_id"), resultSet.getString("i.name"), resultSet.getString("f.name"));
                getList.add(functionInterface);
            }

        } catch (SQLException ex) {
            Logger.getLogger(RoleDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(RoleDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return getList;
    }

    public ArrayList<InterfaceBean> getInterfaces() {
        ArrayList<InterfaceBean> list = new ArrayList<>();
        Connection con = DBConnection.createConnection();
        try {

            Statement statement = con.createStatement();
            String sql = "select * from interface";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                InterfaceBean interfaces = new InterfaceBean(resultSet.getString("name"));
                list.add(interfaces);
            }

        } catch (SQLException ex) {
            Logger.getLogger(RoleDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(RoleDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return list;
    }

    public void insertPrivilage(String rolename, String[] function) {
        String roleID = "";
        Connection con = DBConnection.createConnection();
        try {

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

        } catch (SQLException ex) {
            Logger.getLogger(RoleDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(RoleDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public ArrayList<RoleBean> loadRoleUserData() {
        Connection con = DBConnection.createConnection();
        ArrayList<RoleBean> roleBean = new ArrayList<>();
        try {

            String sql = "select * from role";
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String roleid = resultSet.getString("roleid");
                String rolename = resultSet.getString("rolename");

                String query = "select i.interfaceid,i.name,f.functionid, f.name from interface i inner join func_interface fi on i.interfaceid=fi.interfaceid inner join function f on f.functionid=fi.functionid where fi.if_id in (select if_id from privilage where roleid in (select roleid from role where roleid='" + roleid + "'))";
                Statement stmt = con.createStatement();
                ResultSet res = stmt.executeQuery(query);

                ArrayList<RoleAccessBean> RoleAccessDetails = new ArrayList<>();

                while (res.next()) {

                    RoleAccessBean roleAccess = new RoleAccessBean(roleid, res.getString("i.interfaceid"), res.getString("i.name"), res.getString("f.functionid"), res.getString("f.name"));

                    RoleAccessDetails.add(roleAccess);
                }

                RoleBean roleOutput = new RoleBean(roleid, rolename, RoleAccessDetails);

                roleBean.add(roleOutput);

            }
        } catch (SQLException ex) {
            Logger.getLogger(RoleDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return roleBean;
    }

    public ArrayList<RoleBean> loadAllRoleNames() {
        ArrayList<RoleBean> roleData = new ArrayList<>();
        Connection con = DBConnection.createConnection();
        try {
            String sql = "select * from  role";
            Statement statement = con.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {

                RoleBean roleBean = new RoleBean(resultSet.getString("roleid"), resultSet.getString("rolename"));
                System.out.println("id " + resultSet.getString("roleid"));
                System.out.println("name " + resultSet.getString("rolename"));
                roleData.add(roleBean);
            }

            System.out.println("RoleData size " + roleData.size());
        } catch (SQLException ex) {
            Logger.getLogger(RoleDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(RoleDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return roleData;
    }

    public static ArrayList<InterfaceBean> loadAllInterfaceFunctions() {
        Connection con = DBConnection.createConnection();
        ArrayList<InterfaceBean> ibean = new ArrayList<>();

        try {
            String sql = "select DISTINCT fi.if_id, i.name, f.name from interface i inner join func_interface fi ON fi.interfaceid=i.interfaceid inner join function f on f.functionid=fi.functionid order BY fi.if_id";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                InterfaceBean ib = new InterfaceBean(rs.getString("fi.if_id"), rs.getString("i.name"), rs.getString("f.name"));
                ibean.add(ib);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RoleDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(RoleDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return ibean;
    }

    public boolean roleDeletePermission(String roleid) {
        Connection con = DBConnection.createConnection();
        try {
            Statement statement = con.createStatement();
            String sql = "select * from user";
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                if(roleid.equals(resultSet.getString("roleid")))
                    return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(RoleDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public void deleteRole(String id){
        Connection con = DBConnection.createConnection();
        try {
            ArrayList<PrivilageBean> pidValues = new ArrayList<>();
            Statement statement = con.createStatement();
            String sql = "select * from privilage where roleid = '" + id + "'";
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                PrivilageBean privilageBean = new PrivilageBean(resultSet.getString("pid"));
                pidValues.add(privilageBean);
            }
            
            for (int i = 0; i < pidValues.size(); i++) {
                deletePrivilageID(pidValues.get(i).getPid());
            }
            
            deleteRoleID(id);
            
        } catch (SQLException ex) {
            Logger.getLogger(RoleDao.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
    
    public void deletePrivilageID(String id){
        Connection con = DBConnection.createConnection();
        try {

            String sql = "DELETE FROM `privilage` WHERE pid = '" + id + "'";
            PreparedStatement statement = con.prepareCall(sql);
            statement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(PageDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(PageDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void deleteRoleID(String id){
        Connection con = DBConnection.createConnection();
        try {

            String sql = "DELETE FROM `role` WHERE roleid = '" + id + "'";
            PreparedStatement statement = con.prepareCall(sql);
            statement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(PageDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(PageDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
}
