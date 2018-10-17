/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.LoginSystem.dao;

import com.LoginSystem.bean.InterfaceBean;
import com.LoginSystem.bean.RoleAccessBean;
import com.LoginSystem.bean.RoleBean;
import com.LoginSystem.util.DBConnection;
import java.sql.Connection;
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
public class InterfaceDao {
    
    public static ArrayList<InterfaceBean> loadAllInterfaces() {
        
        ArrayList<InterfaceBean> interfaces = new ArrayList<>();
        Connection con = DBConnection.createConnection();
        try {
            String sql = "SELECT * FROM interface";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                InterfaceBean in = new InterfaceBean(rs.getString("interfaceid"), rs.getString("name"), rs.getString("url"), rs.getString("description"));
                interfaces.add(in);
            }
        } catch (SQLException ex) {
            Logger.getLogger(InterfaceDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(InterfaceDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(InterfaceDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return interfaces;
    }
    
    
    public static ArrayList<RoleBean> loadRoleAcessibleFunctions(String selected_rid) {
        ArrayList<RoleBean> rbean = new ArrayList<>();
        Connection con = DBConnection.createConnection();
        String rid = selected_rid;
        try {
            String sql1 = "SELECT * FROM role where roleid='" + rid + "'";
            Statement statement1 = con.createStatement();
            ResultSet rs1 = statement1.executeQuery(sql1);
          
            while (rs1.next()) {
//                String rid = rs1.getString("roleid");
                String rname = rs1.getString("rolename");
               
                String sql2 = "select fi.if_id, i.interfaceid,i.name,f.functionid, f.name from interface i inner join func_interface fi on i.interfaceid=fi.interfaceid inner join function f on f.functionid=fi.functionid where fi.if_id in (select if_id from privilage where roleid in (select roleid from role where roleid='" + rid + "'))";
                Statement statement2 = con.createStatement();
                ResultSet rs2 = statement2.executeQuery(sql2);
                List<RoleAccessBean> ra_bean = new ArrayList<>();
                while (rs2.next()) {
                    String iid = rs2.getString("i.interfaceid");
                    String iname = rs2.getString("i.name");
                    String fid = rs2.getString("f.functionid");
                    String fname = rs2.getString("f.name");
                    String ifid = rs2.getString("fi.if_id");
                   
                    RoleAccessBean ra = new RoleAccessBean(rid, iid, iname, fid, fname, ifid);
                    ra_bean.add(ra);
                }
                RoleBean rb = new RoleBean( rid, rname, ra_bean);
                rbean.add(rb);
            }
        } catch (SQLException ex) {
            Logger.getLogger(InterfaceDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(InterfaceDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return rbean;
    }
}
