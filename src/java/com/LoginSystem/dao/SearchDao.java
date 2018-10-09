/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.LoginSystem.dao;

import com.LoginSystem.bean.UserBean;
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
 * @author suren_v
 */
public class SearchDao {

    public ArrayList<UserBean> getSearch(String keyword) {
        Connection con = DBConnection.createConnection();
        ArrayList<UserBean> result = new ArrayList<>();
        try {
            String sql = "select * from user where username like '" + "%" + keyword + "%" + "'";
            Statement statemenet = con.createStatement();
            ResultSet resultSet = statemenet.executeQuery(sql);
            while (resultSet.next()) {
                UserBean search = new UserBean(resultSet.getString("userid"), resultSet.getString("roleid"), resultSet.getString("username"), resultSet.getString("password"));
                result.add(search);
            }

            return result;
        } catch (SQLException ex) {
            Logger.getLogger(SearchDao.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(SearchDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }
}
