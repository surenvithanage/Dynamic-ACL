/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.LoginSystem.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author suren
 */
public class DBConnection {
    
    public static Connection createConnection() {
         Connection con = null;
        try {
           
            InitialContext initialContext = new InitialContext();
             Context context = (Context) initialContext.lookup("java:comp/env");
             DataSource dataSource = (DataSource) context.lookup("jdbc/loginepic");
             con = dataSource.getConnection();
            
        } catch (NamingException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }
}
