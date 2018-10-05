/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.LoginSystem.dao;

import com.LoginSystem.bean.FunctionBean;
import com.LoginSystem.bean.LoginBean;
import com.LoginSystem.bean.RoleBean;
import com.LoginSystem.bean.UserBean;
import com.LoginSystem.util.DBConnection;
import com.LoginSystem.util.Function;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author suren_v
 */
public class UserDao {

    private String username;
    private String password;
    private String roleid;

    private Statement statement;

    public boolean insertUser(UserBean userBean) {
        Connection con = DBConnection.createConnection();
        boolean rowInserted = false;
        try {
            String sql = "INSERT INTO user(roleid , username , password) VALUES (?,?,?)";

            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, userBean.getRoleid());
            statement.setString(2, userBean.getUsername());
            statement.setString(3, userBean.getPassword());

            rowInserted = statement.executeUpdate() > 0;
            statement.close();

        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return rowInserted;
    }

    public List<UserBean> listAllUsers() {
        List<UserBean> listUsers = null;
        Connection con = DBConnection.createConnection();
        try {

            listUsers = new ArrayList<>();
            String sql = "SELECT * FROM user";

            statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                UserBean userBean = new UserBean(resultSet.getString("userid"), resultSet.getString("roleid"), resultSet.getString("username"), resultSet.getString("password"));
                listUsers.add(userBean);
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listUsers;
    }

    public ArrayList<RoleBean> listAllRoles() {
        Connection con = DBConnection.createConnection();
        ArrayList<RoleBean> listRoles = new ArrayList<>();
        try {
            String sql = "SELECT * FROM role";

            statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                RoleBean roleBean = new RoleBean(resultSet.getString("roleid"), resultSet.getString("rolename"));
                listRoles.add(roleBean);
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listRoles;
    }

    public boolean deleteUser(UserBean userBean) {
        Connection con = DBConnection.createConnection();
        boolean rowDeleted = false;
        try {
            String sql = "DELETE FROM user WHERE userid = ?";

            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, userBean.getUserid());

            rowDeleted = statement.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return rowDeleted;
    }

    public boolean updateUser(UserBean userBean) {
        Connection con = DBConnection.createConnection();
        boolean rowUpdated = false;
        try {

            String sql = "UPDATE user set roleid = ? , username = ? , password = ? where userid = ?";

            PreparedStatement statement = con.prepareStatement(sql);

            statement.setString(1, userBean.getRoleid());
            statement.setString(2, userBean.getUsername());
            statement.setString(3, userBean.getPassword());
            statement.setString(4, userBean.getUserid());

            rowUpdated = statement.executeUpdate() > 0;
            statement.close();

        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return rowUpdated;
    }

    public ArrayList<FunctionBean> getPageFunctions(String roleid, String interfaceid) {
        ArrayList<FunctionBean> pageFunctions = new ArrayList<>();
        Connection con = DBConnection.createConnection();
        try {

            Statement statement = con.createStatement();
            String sql = "select DISTINCT f.functionid, f.name  from privilage p , interface i , func_interface fi , function f where p.if_id = fi.if_id and fi.functionid = f.functionid and p.roleid ='" + roleid + "' and fi.interfaceid ='" + interfaceid + "'";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                FunctionBean functionBean = new FunctionBean(resultSet.getString("f.functionid"), resultSet.getString("f.name"));
                pageFunctions.add(functionBean);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Function.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return pageFunctions;

    }

    public ArrayList<LoginBean> loggedUserDetails(String userid) {
        ArrayList<LoginBean> userInfo = new ArrayList<>();
        Connection con = DBConnection.createConnection();
        try {
            String sql = "SELECT * FROM user where userid ='" + userid + "'";

            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                LoginBean loginDetails = new LoginBean(resultSet.getString("userid"), resultSet.getString("roleid"), resultSet.getString("username"), resultSet.getString("password"));
                userInfo.add(loginDetails);
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return userInfo;
    }

}
