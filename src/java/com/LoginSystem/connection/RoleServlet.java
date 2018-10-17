/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.LoginSystem.connection;

import com.LoginSystem.bean.FunctionInterfaceBean;
import com.LoginSystem.bean.InterfaceBean;
import com.LoginSystem.bean.RoleAccessBean;
import com.LoginSystem.bean.RoleBean;
import com.LoginSystem.common.CommonAction;
import com.LoginSystem.dao.InterfaceDao;
import com.LoginSystem.dao.LoginDao;
import com.LoginSystem.dao.RoleDao;
import com.LoginSystem.util.SessionVarList;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author suren_v
 */
public class RoleServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        if (session.getAttribute("username") == null) {
            response.sendRedirect("index.jsp");
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        HttpSession session = request.getSession();
        //Updating role
        String update = request.getParameter("action");
        ArrayList<RoleBean> roleData = new ArrayList<>();
        ArrayList<RoleBean> roleDetails = new ArrayList<>();
        ArrayList<InterfaceBean> roleInterfaceData = new ArrayList<>();
        ArrayList<InterfaceBean> interfaces = new ArrayList<>();
        String newIP = session.getAttribute("currentIP").toString();
        String uname = session.getAttribute("username").toString();
        ServletContext context = getServletConfig().getServletContext();
        //Retrieving the user and machine data for SessionVarList
        HashMap<String, String> usermap = (HashMap<String, String>) context.getAttribute(SessionVarList.USERMAP);
        HashMap<String, String> userdev = (HashMap<String, String>) context.getAttribute(SessionVarList.USERDEVICE);

        CommonAction commonAction = new CommonAction();
        boolean logged = commonAction.checkUserLogin(usermap, uname);
        boolean sameDevice = commonAction.checkUserDevice(userdev, uname, newIP);

        if (logged == true && sameDevice == false) {
            request.setAttribute("AlreadyLogged", "Logged in using another device");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
        RoleDao dao = new RoleDao();
        InterfaceDao interfaceDao = new InterfaceDao();

        if (update != null) {
            try {
                String selected_roleid = request.getParameter("roleid");
                //Retrieving all the details related to the role
                roleData = dao.loadAllRoleNames();
                //Retrieving ifid , iname , fname
                roleInterfaceData = RoleDao.loadAllInterfaceFunctions();
                //Retrieving all the interfaces
                interfaces = InterfaceDao.loadAllInterfaces();
                //Retrieving roleid , iid , iname ,fid , fname , ifid
                roleDetails = InterfaceDao.loadRoleAcessibleFunctions(selected_roleid);

                String[] permission = new String[roleInterfaceData.size()];

                for (int i = 0; i < roleDetails.size(); i++) {
                    RoleBean get = roleDetails.get(i);
                }

                for (int i = 0; i < roleInterfaceData.size(); i++) {
                    InterfaceBean getValues = roleInterfaceData.get(i);

                    for (int j = 0; j < roleDetails.get(0).getRoleAccess_bean().size(); j++) {
                        RoleAccessBean getResults = roleDetails.get(0).getRoleAccess_bean().get(j);
                        if (getValues.getUrl().equals(getResults.getFunction_name())) {     //Checking whether the two function names are equal       
                            permission[i] = "1";
                            break;
                        }
                        
                    }
                    if(permission[i] == null){
                            permission[i] = "0";
                        }
                }

                System.out.println("\n");
                ArrayList<String> per = new ArrayList<>();
                for (int i = 0; i < permission.length; i++) {
                    String string = permission[i];
                    per.add(string);
                    System.out.println(string);
                }

                request.setAttribute("data", permission);
                request.setAttribute("roles", roleData);
                request.setAttribute("funcs", roleInterfaceData);
                request.setAttribute("inter", interfaces);
                request.setAttribute("alldata", roleDetails);

                //Loading all Page Details
                ArrayList<InterfaceBean> pages;
                LoginDao loginDetails = new LoginDao();
                pages = loginDetails.getPages();
                request.setAttribute("pages", pages);

                request.getRequestDispatcher("role_update.jsp").forward(request, response);
            } catch (NamingException ex) {
                System.out.println(ex);
            }

        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String newIP = session.getAttribute("currentIP").toString();
        String uname = session.getAttribute("username").toString();
        ServletContext context = getServletConfig().getServletContext();
        //Retrieving the user and machine data for SessionVarList
        HashMap<String, String> usermap = (HashMap<String, String>) context.getAttribute(SessionVarList.USERMAP);
        HashMap<String, String> userdev = (HashMap<String, String>) context.getAttribute(SessionVarList.USERDEVICE);

        CommonAction commonAction = new CommonAction();
        boolean logged = commonAction.checkUserLogin(usermap, uname);
        boolean sameDevice = commonAction.checkUserDevice(userdev, uname, newIP);

        if (logged == true && sameDevice == false) {
            request.setAttribute("AlreadyLogged", "Logged in using another device");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
        RoleDao dao = new RoleDao();
        String option = request.getParameter("action");
        if (option != null) {
            try {
                String roleID = request.getParameter("roleid");
                ArrayList<RoleBean> roleDetails = new ArrayList<>();
                ArrayList<InterfaceBean> interfaceNames = new ArrayList<>();
                ArrayList<InterfaceBean> pages;
                ArrayList<FunctionInterfaceBean> functionInterface = new ArrayList<>();
                //Loading all Role Details

                roleDetails = dao.loadRoleUserData();
                request.setAttribute("roleDetails", roleDetails);

                //Loading all Page Details
                LoginDao loginDetails = new LoginDao();
                pages = loginDetails.getPages();
                request.setAttribute("pages", pages);

                //Loading all Interface Details
                interfaceNames = dao.getInterfaces();
                request.setAttribute("interfaceNames", interfaceNames);

                functionInterface = dao.getFunctionList();
                request.setAttribute("functionInterface", functionInterface);
                switch (option) {
                    case "delete_role":
                        Boolean status = dao.roleDeletePermission(roleID);
                        if (status == true) {
                            request.setAttribute("Error", "Cannot Delete A Role Assigned To An User");
                            request.getRequestDispatcher("role.jsp").forward(request, response);

                        } else {
                            dao.deleteRole(roleID);
                            request.getRequestDispatcher("role.jsp").forward(request, response);
                        }
                        break;

                }
            } catch (NamingException ex) {
                Logger.getLogger(RoleServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                String roleName = request.getParameter("roleName");
                String[] function = request.getParameterValues("FunctionInterfaceID");
                RoleDao role = new RoleDao();
                role.insertPrivilage(roleName, function);

                ArrayList<RoleBean> roleDetails = new ArrayList<>();

                roleDetails = role.loadRoleUserData();

                request.setAttribute("roleDetails", roleDetails);
                //Loading all Page Details
                ArrayList<InterfaceBean> pages;
                LoginDao loginDetails = new LoginDao();
                pages = loginDetails.getPages();
                request.setAttribute("pages", pages);
                //Loading all Interface Details
                ArrayList<InterfaceBean> interfaceNames = new ArrayList<>();
                interfaceNames = role.getInterfaces();
                request.setAttribute("interfaceNames", interfaceNames);

                //Saving the roles in a session
                request.getRequestDispatcher("role.jsp").forward(request, response);
            } catch (NamingException ex) {
                Logger.getLogger(RoleServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
