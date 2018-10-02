/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.LoginSystem.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author suren
 */
public class DBConnection {
    public static Connection createConnection() throws ClassNotFoundException, SQLException{
//      Connection Through MariaDB        
        
//      static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
//      static final String url = "jdbc:mariadb://192.168.1.159/login2";
//       
//      Database credentials
//      static final String username = "mytest";
//      static final String password = "password";
//      Class.forName("org.mariadb.jdbc.Driver");

        String url = "jdbc:mysql://localhost:3306/loginepic";
        String username = "root";
        String password = "";
        
        Connection con = null;
        
       Class.forName("com.mysql.jdbc.Driver");
       con = DriverManager.getConnection(url, username, password);
        
        return con;
    }
}
