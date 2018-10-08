/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.LoginSystem.connection;

import com.LoginSystem.bean.FunctionBean;
import com.LoginSystem.bean.FunctionInterfaceBean;
import com.LoginSystem.bean.InterfaceBean;
import com.LoginSystem.bean.PrivilageBean;
import com.LoginSystem.bean.RoleBean;
import com.LoginSystem.dao.LoginDao;
import com.LoginSystem.dao.PageDao;
import com.LoginSystem.dao.RoleDao;
import com.LoginSystem.dao.UserDao;
import java.awt.BorderLayout;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author suren_v
 */
public class PageServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        String action = request.getParameter("functionInterfaceID");
        String update = request.getParameter("InterfaceID");
        String submitUpdate = request.getParameter("submitUpdate");

        PageDao pageDetails = new PageDao();
        HttpSession session = request.getSession();
        //Updating the records
        if (submitUpdate != null) {
            //Obtaining the values
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            String interfaceId = request.getParameter("interfaceId");

            ArrayList<FunctionBean> functionIdList, functionList, totFunction;
            //Selected Functions
            functionIdList = new ArrayList<>();
            functionIdList = pageDetails.getFunctionId(interfaceId);
            //All the functions
            functionList = new ArrayList<>();
            functionList = pageDetails.PrintFunctions();
            //Newly selected functions
            String[] selectedId = request.getParameterValues("functions");

            //Converting the selected Id to an Arraylist
            List<String> selectedNew = new CopyOnWriteArrayList<String>();
            for (int i = 0; i < selectedId.length; i++) {
                selectedNew.add(selectedId[i]);
            }
            //Converting the db saved id to CopyOnWriteArrayList as well
            List<String> savedId = new CopyOnWriteArrayList<String>();
            for (int i = 0; i < functionIdList.size(); i++) {
                savedId.add(functionIdList.get(i).getId());
            }

            for (String x : selectedNew) {
                for (String y : savedId) {
                    if (x.equals(y)) {
                        selectedNew.remove(x);
                        savedId.remove(y);
                    }
                }
            }
            //After deleting the items
            for (int i = 0; i < savedId.size(); i++) {
                System.out.println("Saved Id " + savedId.get(i));
            }

            for (int i = 0; i < selectedNew.size(); i++) {
                System.out.println("Selected Id " + selectedNew.get(i));
            }
            //Saving a new Function 
            ArrayList<FunctionInterfaceBean> ifIdValues = new ArrayList<>();
            if (selectedNew.size() > 0) {
                for (int i = 0; i < selectedNew.size(); i++) {
                    pageDetails.insertFunctionInterface(interfaceId, selectedNew.get(i));
                    ifIdValues = pageDetails.getFunctionInterfaceId(interfaceId, selectedNew.get(i));
                }
            }

            String roleid = session.getAttribute("roleID").toString();

            for (int i = 0; i < ifIdValues.size(); i++) {
                pageDetails.InsertPID(roleid, ifIdValues.get(i).getFunctionInterfaceId());
            }
            //Deleting a saved function
            ArrayList<FunctionInterfaceBean> deletedIF_ID = new ArrayList<>();
            if (savedId.size() > 0) {
                for (int i = 0; i < savedId.size(); i++) {
                    deletedIF_ID = pageDetails.getFunctionInterfaceId(interfaceId , savedId.get(i));
                }
            }
            
            //Getting the PID VALUES
            ArrayList<PrivilageBean> pidID = new ArrayList();
            for (int i = 0; i < deletedIF_ID.size(); i++) {
                pidID = pageDetails.getPIDValues(deletedIF_ID.get(i).getFunctionInterfaceId());
            }
            
            for (int i = 0; i < pidID.size(); i++) {
                pageDetails.deletePID(pidID.get(i).getPid());
            }
            
            for (int i = 0; i < deletedIF_ID.size(); i++) {
                pageDetails.deleteIFID(deletedIF_ID.get(i).getFunctionInterfaceId());
            }

            //Updating the details
            pageDetails.updateInterfaces(name, description, interfaceId);

            //Interface Details
            ArrayList<FunctionInterfaceBean> functionInterfaceDetails = new ArrayList<>();
            functionInterfaceDetails = pageDetails.getFunctionInterfaceList();
            session.setAttribute("functionInterfaceDetails", functionInterfaceDetails);

            response.sendRedirect("add_page.jsp");
        }

        //Deleting the records
        if (action != null) {
            PageDao page = new PageDao();
            page.DeleteFunctionDetails(action);
            //ArrayList for functions
            ArrayList<FunctionBean> functions = new ArrayList<>();
            //PageDao object
            PageDao pageFunction = new PageDao();
            try {
                functions = pageFunction.getFunctions();
            } catch (NamingException ex) {
                Logger.getLogger(PageServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            //Interface Details
            ArrayList<FunctionInterfaceBean> functionInterfaceDetails = new ArrayList<>();

            functionInterfaceDetails = pageDetails.getFunctionInterfaceList();

            ArrayList<InterfaceBean> pages = null;
            LoginDao loginDetails = new LoginDao();
            try {
                pages = loginDetails.getPages();
            } catch (NamingException ex) {
                Logger.getLogger(PageServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            //Session
            session.setAttribute("functionInterfaceDetails", functionInterfaceDetails);
            session.setAttribute("functions", functions);
            session.setAttribute("pages", pages);

            response.sendRedirect("add_page.jsp");
        }

        //Retrieving the update records
        if (update != null) {
            ArrayList<FunctionInterfaceBean> updateDetails = new ArrayList<>();
            ArrayList<FunctionBean> Func = new ArrayList<>();
            updateDetails = pageDetails.loadAllPageData(update);
            Func = pageDetails.PrintFunctions();

            String[] resultSet = new String[Func.size()];
            String func, updatedValue;
            for (int i = 0; i < Func.size(); i++) {
                func = Func.get(i).getName();
                boolean result = false;
                for (int j = 0; j < updateDetails.get(0).getFunction().size(); j++) {
                    updatedValue = updateDetails.get(0).getFunction().get(j).getName();
                    if (updatedValue.equals(func)) {
                        result = true;
                        break;
                    }

                }

                if (result == false) {
                    resultSet[i] = "0";
                } else {
                    resultSet[i] = "1";
                }
            }

            session.setAttribute("resultSet", resultSet);
            session.setAttribute("Func", Func);
            session.setAttribute("updateDetails", updateDetails);
            response.sendRedirect("update_page.jsp");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
            //Retrieving the values from the form
            String name = request.getParameter("name");
            String url = request.getParameter("url");
            String description = request.getParameter("description");

            //Session
            HttpSession session = request.getSession();
            PageDao pageDao = new PageDao();

            //Assigning the Values
            InterfaceBean page = new InterfaceBean();
            page.setName(name);
            page.setUrl(url);
            page.setDescription(description);
            //Retrieving the functions checked
            String[] functionList = request.getParameterValues("functions");
            //Insert Data to the database

            PageDao.insertPageData(page);
            //Obtaining interface id
            String interfaceID = pageDao.getId(name);

            String roleId = session.getAttribute("roleID").toString();

            for (int i = 0; i < functionList.length; i++) {
                pageDao.insertFunctionInterface(interfaceID, functionList[i]);
            }

            pageDao.setFunctionInterfaceId(roleId, interfaceID);

            //loading page functions
            LoginDao loginDetails = new LoginDao();
            ArrayList<InterfaceBean> pages;
            pages = loginDetails.getPages();
            session.setAttribute("pages", pages);
            //Loading role details
            UserDao user = new UserDao();
            ArrayList<RoleBean> roleDetails = new ArrayList<>();
            roleDetails = user.listAllRoles();
            session.setAttribute("roleDetails", roleDetails);
            //Loading Permissions
            ArrayList<InterfaceBean> interfaceNames = new ArrayList<>();
            RoleDao role = new RoleDao();
            interfaceNames = role.getInterfaces();
            session.setAttribute("interfaceNames", interfaceNames);
            //Interface function Details
            ArrayList<FunctionInterfaceBean> functionInterface = new ArrayList<>();
            RoleDao roleDao = new RoleDao();
            functionInterface = roleDao.getFunctionList();
            session.setAttribute("functionInterface", functionInterface);
            //FunctionsOnly
            ArrayList<FunctionInterfaceBean> functionDetails = new ArrayList<>();
            PageDao pageDetails = new PageDao();
            functionDetails = pageDetails.getFunctionInterfaceList();
            System.out.println(functionDetails);
            session.setAttribute("functionDetails", functionDetails);

            response.sendRedirect("role.jsp");
        } catch (NamingException ex) {
            Logger.getLogger(PageServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
