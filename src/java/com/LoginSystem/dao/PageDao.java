/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.LoginSystem.dao;

import com.LoginSystem.bean.FunctionBean;
import com.LoginSystem.bean.FunctionInterfaceBean;
import com.LoginSystem.bean.InterfaceBean;
import com.LoginSystem.bean.PrivilageBean;
import com.LoginSystem.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

/**
 *
 * @author suren_v
 */
public class PageDao {

    public static void insertPageData(InterfaceBean interfaceBean) throws NamingException {
        String name = interfaceBean.getName();
        String url = interfaceBean.getUrl();
        String description = interfaceBean.getUrl();
        Connection con = DBConnection.createConnection();
        try {

            String sql = "INSERT INTO interface(name , url , description) VALUES ( ? , ? , ? ) ";
            PreparedStatement statement = con.prepareCall(sql);
            statement.setString(1, name);
            statement.setString(2, url);
            statement.setString(3, description);
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

    public ArrayList<FunctionBean> getFunctions() throws NamingException {
        ArrayList<FunctionBean> functionBean = new ArrayList<>();
        Connection con = DBConnection.createConnection();
        try {

            Statement statement = con.createStatement();
            String sql = "SELECT * FROM function";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                FunctionBean functions = new FunctionBean(resultSet.getString("functionid"), resultSet.getString("name"));
                functionBean.add(functions);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PageDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(PageDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return functionBean;
    }

    public String getId(String name) throws NamingException {
        String id = "";
        Connection con = DBConnection.createConnection();
        try {

            Statement statement = con.createStatement();
            String sql = "SELECT * FROM interface where name = '" + name + "'";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                id = resultSet.getString("interfaceid");
            }

        } catch (SQLException ex) {
            Logger.getLogger(PageDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(PageDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return id;
    }

    public void insertFunctionInterface(String interfaceId, String functionId) {
        Connection con = DBConnection.createConnection();
        try {

            String sql = "INSERT INTO func_interface(interfaceid , functionid) VALUES ( ? , ? ) ";
            PreparedStatement statement = con.prepareCall(sql);
            statement.setString(1, interfaceId);
            statement.setString(2, functionId);

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

    public void setFunctionInterfaceId(String roleid, String interfaceid) {
        String interfaceFunctionId;
        Connection con = DBConnection.createConnection();
        try {

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

    public void DeletePageDetails(String id) {
        Connection con = DBConnection.createConnection();
        try {

            String sql = "DELETE FROM `func_interface` WHERE if_id = '" + id + "'";
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

    public String getPrivilageId(String id) {
        String privilageId = "";
        Connection con = DBConnection.createConnection();
        try {

            String sql = "SELECT pid FROM privilage where if_id = '" + id + "'";
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                privilageId = resultSet.getString("pid");
            }

        } catch (SQLException ex) {
            Logger.getLogger(PageDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(PageDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return privilageId;
    }

    public void DeletePrivilagePageDetails(String id) {
        Connection con = DBConnection.createConnection();
        try {

            String sql = "DELETE FROM `func_interface` WHERE if_id = '" + id + "'";
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

    public ArrayList<FunctionInterfaceBean> getOnlyFunctions(String id) {
        ArrayList<FunctionInterfaceBean> getFunctions = new ArrayList<>();
        Connection con = DBConnection.createConnection();
        try {

            String sql = "select fi.if_id , f.name from func_interface fi , function f , interface i where fi.interfaceid = i.interfaceid and fi.functionid = f.functionid and i.interfaceid = '" + id + "'";
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                FunctionInterfaceBean functionName = new FunctionInterfaceBean(resultSet.getString("fi.if_id"), resultSet.getString("f.name"));
                getFunctions.add(functionName);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PageDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(PageDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return getFunctions;
    }

    public ArrayList<FunctionInterfaceBean> getFunctionInterfaceList() {
        ArrayList<FunctionInterfaceBean> getList = new ArrayList<>();
        Connection con = DBConnection.createConnection();
        try {

            String sql = "select DISTINCT i.interfaceid, i.name from func_interface fi , interface i where fi.interfaceid = i.interfaceid";
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                ArrayList<FunctionBean> functions = new ArrayList<>();
                String interfaceID = resultSet.getString("i.interfaceid");
                Statement stmt = con.createStatement();
                String query = "select f.functionid , f.name from func_interface fi , function f , interface i where fi.interfaceid = i.interfaceid and fi.functionid = f.functionid and i.interfaceid = '" + interfaceID + "'";
                ResultSet res = stmt.executeQuery(query);
                while (res.next()) {
                    FunctionBean func = new FunctionBean(res.getString("f.functionid"), res.getString("f.name"));
                    functions.add(func);
                }
                FunctionInterfaceBean functionBean = new FunctionInterfaceBean(resultSet.getString("i.interfaceid"), resultSet.getString("i.name"), functions);
                getList.add(functionBean);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PageDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(PageDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return getList;
    }
    int totalCount;
    int i;

    public void DeleteFunctionDetails(String interfaceid) {
        System.out.println("Inside delete function details");
        String[] functionInterfaceID = null;
        String[] privilageID = null;
        int count = 0;
        int itr = 0;
        Connection con = DBConnection.createConnection();
        try {

            Statement statement = con.createStatement();
            Statement getStatementCount = con.createStatement();
            String sql = "SELECT if_id FROM `func_interface` WHERE interfaceid = '" + interfaceid + "'";
            ResultSet resultSet = statement.executeQuery(sql);
            ResultSet getCount = getStatementCount.executeQuery(sql);
            while (getCount.next()) {
                getCount.getString("if_id");
                count++;
            }
            //Saving the functionInterface ID in an array
            functionInterfaceID = new String[count];
            while (resultSet.next()) {
                functionInterfaceID[itr] = resultSet.getString("if_id");
                itr++;
            }
            ArrayList<PrivilageBean> pb = new ArrayList<>();
            ArrayList<PrivilageBean> pb2 = new ArrayList<>();
            //Obtaining the PID values into an array
            for (int j = 0; j < functionInterfaceID.length; j++) {
                pb = getPIDValues(functionInterfaceID[j]);
                for (int i = 0; i < pb.size(); i++) {
                    PrivilageBean p = new PrivilageBean(pb.get(i).getPid());
//                System.out.println(pb.get(i).getPid());
                    pb2.add(p);
                }
            }

            //Retrieved All the PID Values
            String[] pidAll = new String[pb2.size()];

            for (int j = 0; j < pb2.size(); j++) {
                PrivilageBean get = pb2.get(j);
                pidAll[j] = get.getPid();
            }

            for (int j = 0; j < pidAll.length; j++) {
                deletePID(pidAll[j]);   //Deleting all the pid for a specific page and role
            }
            //Deleting the if_id
            for (int j = 0; j < functionInterfaceID.length; j++) {
                deleteIFID(functionInterfaceID[j]);
            }

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

    //Obtaining the PID Values
    public ArrayList<PrivilageBean> getPIDValues(String id) {
        ArrayList<PrivilageBean> idValues = new ArrayList<>();
        Connection con = DBConnection.createConnection();
        try {

            Statement statement = con.createStatement();
            String sql = "select pid from privilage where if_id = '" + id + "'";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                PrivilageBean beanData = new PrivilageBean(resultSet.getString("pid"));
                idValues.add(beanData);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PageDao.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(PageDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return idValues;
    }

    //Deleting PID Values 
    public void deletePID(String id) {
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

    public void deleteIFID(String id) {
        Connection con = DBConnection.createConnection();
        try {
            String sql = "DELETE FROM `func_interface` WHERE if_id = '" + id + "'";
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

    //Loading Page Details for update
    public ArrayList<FunctionInterfaceBean> loadAllPageData(String interfaceid) {
        ArrayList<FunctionInterfaceBean> getData = new ArrayList<>();
        Connection con = DBConnection.createConnection();
        try {
            Statement statement = con.createStatement();
            String sql = "SELECT DISTINCT i.interfaceid, i.name , i.url , i.description FROM func_interface fi, interface i where i.interfaceid = '" + interfaceid + "'";

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Statement stmt = con.createStatement();
                ArrayList<FunctionBean> data = new ArrayList<>();

                String query = "select f.functionid , f.name from func_interface fi , function f , interface i where fi.interfaceid = i.interfaceid and fi.functionid = f.functionid and i.interfaceid = '" + interfaceid + "'";
                ResultSet result = stmt.executeQuery(query);
                while (result.next()) {
                    FunctionBean beanData = new FunctionBean(result.getString("f.functionid"), result.getString("f.name"));
                    data.add(beanData);
                }
                FunctionInterfaceBean functionBean = new FunctionInterfaceBean(resultSet.getString("i.interfaceid"), resultSet.getString("i.name"), data, resultSet.getString("i.url"), resultSet.getString("i.description"));
                getData.add(functionBean);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PageDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(PageDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return getData;
    }

    public ArrayList<FunctionBean> PrintFunctions() {
        ArrayList<FunctionBean> data = new ArrayList<>();
        Connection con = DBConnection.createConnection();
        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT * FROM `function` ";

            ResultSet resultSet = stmt.executeQuery(sql);
            while (resultSet.next()) {
                FunctionBean funtionBean = new FunctionBean(resultSet.getString("functionid"), resultSet.getString("name"));
                data.add(funtionBean);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PageDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(PageDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }
    
    //Update an interface Detail
    public void updateInterfaces(String name , String description , String interfaceId){
        try {
            Connection con = DBConnection.createConnection();
            
            String sql = "UPDATE `interface` SET `name`=?,`description`=? WHERE interfaceid = ?";
            PreparedStatement statement = con.prepareCall(sql);
            statement.setString(1, name);
            statement.setString(2, description);
            statement.setString(3, interfaceId);
            
            statement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(PageDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ArrayList<FunctionBean> getFunctionId(String id){
        ArrayList<FunctionBean> idList = new ArrayList<>();
        try {
            Connection con = DBConnection.createConnection();
            
            String sql = "SELECT functionid from func_interface where interfaceid = '"+id+"'";
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                FunctionBean funcion = new FunctionBean(resultSet.getString("functionid"));
                idList.add(funcion);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(PageDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return idList;
    }

}
