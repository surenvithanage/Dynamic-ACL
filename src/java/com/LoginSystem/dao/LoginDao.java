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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

/**
 *
 * @author suren
 */
public class LoginDao {

    Statement statement;
    ResultSet resultSet;

    String username = "";
    String password = "";
    String roleID = "";

    public boolean getAuthentication(LoginBean loginbean) throws NamingException {
        Connection con = DBConnection.createConnection();
        username = loginbean.getUsername();
        password = loginbean.getPassword();

        try {
            statement = con.createStatement();
            String sql = "SELECT * FROM user where user_status = 0";
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                if (username.equals(resultSet.getString("username")) && password.equals(resultSet.getString("password"))) {
                    return true;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LoginDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    public String getUsername(LoginBean loginBean) throws NamingException {
        username = loginBean.getUsername();
        password = loginBean.getPassword();
        Connection con = DBConnection.createConnection();
        String Username = "";
        try {

            statement = con.createStatement();

            String sql = "SELECT * FROM user";

            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                if (username.equals(resultSet.getString("username")) && password.equals(resultSet.getString("password"))) {
                    Username = resultSet.getString("username");
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(LoginDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LoginDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return Username;
    }

    public int getUserId(LoginBean loginbean) throws NamingException {

        username = loginbean.getUsername();
        password = loginbean.getPassword();
        Connection con = DBConnection.createConnection();

        try {

            String sql = "SELECT * FROM user";
            statement = con.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                if (username.equals(resultSet.getString("username")) && password.equals(resultSet.getString("password"))) {
                    return resultSet.getInt("userid");
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(LoginDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LoginDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return -99;
    }

    public int getRoleId(LoginBean loginbean) throws NamingException {

        username = loginbean.getUsername();
        password = loginbean.getPassword();
        Connection con = DBConnection.createConnection();
        try {

            String sql = "SELECT * FROM user u , role r where u.roleid = r.roleid";
            statement = con.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                if (username.equals(resultSet.getString("u.username")) && password.equals(resultSet.getString("u.password"))) {
                    loginbean.setRoleid(resultSet.getString("u.roleid"));
                    loginbean.setUsername(resultSet.getString("u.username"));
                    return resultSet.getInt("u.roleid");
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(LoginDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LoginDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return -99;
    }

    public ArrayList<LoginBean> getUserData(LoginBean loginbean) throws NamingException {
        username = loginbean.getUsername();
        password = loginbean.getPassword();
        Connection con = DBConnection.createConnection();
        ArrayList<LoginBean> arraylist = new ArrayList<>();
        try {

            String sql = "SELECT * FROM user u , role r where u.roleid = r.roleid";
            statement = con.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                if (username.equals(resultSet.getString("u.username")) && password.equals(resultSet.getString("u.password"))) {
                    LoginBean logDetails = new LoginBean(resultSet.getString("u.userid"), resultSet.getString("u.roleid"), resultSet.getString("u.username"), resultSet.getString("u.password"));
                    arraylist.add(logDetails);
                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(LoginDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LoginDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return arraylist;
    }

    public ArrayList<InterfaceBean> getPages() throws NamingException {
        ArrayList<InterfaceBean> page = new ArrayList<>();
        Connection con = DBConnection.createConnection();
        try {

            String sql = "select DISTINCT i.interfaceid, i.name , i.url from privilage p , func_interface fi , interface i where p.if_id = fi.if_id and fi.interfaceid = i.interfaceid and p.roleid = 1";
            statement = con.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                InterfaceBean interfaceBean = new InterfaceBean(resultSet.getString("i.interfaceid"), resultSet.getString("i.name"), resultSet.getString("i.url"));
                page.add(interfaceBean);
            }

        } catch (SQLException ex) {
            Logger.getLogger(LoginDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LoginDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return page;
    }

    public boolean initialLogin(LoginBean loginbean) throws NamingException {
        Connection con = DBConnection.createConnection();
        username = loginbean.getUsername();
        password = loginbean.getPassword();

        try {
            statement = con.createStatement();
            String sql = "SELECT * FROM user where status = 0";
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                if (username.equals(resultSet.getString("username")) && password.equals(resultSet.getString("password"))) {
                    return true;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LoginDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    public ArrayList<LoginBean> LoggedUser(String username) {
        ArrayList<LoginBean> details = new ArrayList<>();
        Connection con = DBConnection.createConnection();
        try {
            statement = con.createStatement();
            String sql = "SELECT * FROM user where username = '" + username + "'";
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                LoginBean login = new LoginBean(resultSet.getString("userid"), resultSet.getString("roleid"), resultSet.getString("username"), resultSet.getString("password"), resultSet.getString("status"));
                details.add(login);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LoginDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return details;
    }

    public void updatePasswordReset(String password, String username, String today) {
        Connection con = DBConnection.createConnection();
        try {

            String sql = "UPDATE user SET `password`=?,`status`=? , reset_time = ? WHERE username = ?";
            PreparedStatement statement = con.prepareCall(sql);
            statement.setString(1, password);
            statement.setString(2, "1");
            statement.setString(3, today);
            statement.setString(4, username);
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

    public boolean passwordValid(LoginBean loginbean) throws NamingException, ParseException {
        Connection con = DBConnection.createConnection();
        username = loginbean.getUsername();
        password = loginbean.getPassword();
        String passwordDuration = null;
        Date resetPasswordTime = null ;
        int DayCount;
        
        try {
            statement = con.createStatement();
            String sql = "SELECT * FROM user where status = 1";
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                if (username.equals(resultSet.getString("username")) && password.equals(resultSet.getString("password"))) {
                    passwordDuration = resultSet.getString("reset_duration");
                    resetPasswordTime = resultSet.getDate("reset_time");
                }
            }
                       
            int diffInDays = (int) ( (new java.util.Date()).getTime() - resetPasswordTime.getTime());
            int Days = (diffInDays / (1000 * 60 * 60 * 24)) ;
            
            if(Integer.parseInt(passwordDuration) > Days ){
                return true;
            }        
            
        } catch (SQLException ex) {
            Logger.getLogger(LoginDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LoginDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
    
    
    
}
