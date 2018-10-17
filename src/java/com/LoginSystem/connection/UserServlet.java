/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.LoginSystem.connection;

import com.LoginSystem.bean.FunctionBean;
import com.LoginSystem.bean.FunctionInterfaceBean;
import com.LoginSystem.bean.InterfaceBean;
import com.LoginSystem.bean.LoginBean;
import com.LoginSystem.bean.RoleBean;
import com.LoginSystem.bean.UserBean;
import com.LoginSystem.common.CommonAction;
import com.LoginSystem.dao.LoginDao;
import com.LoginSystem.dao.PageDao;
import com.LoginSystem.dao.RoleDao;
import com.LoginSystem.dao.UserDao;
import com.LoginSystem.util.Function;
import com.LoginSystem.util.SessionVarList;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author suren
 */
public class UserServlet extends HttpServlet {

    //Initializing the attributes
    ArrayList<FunctionBean> functions;
    ArrayList<UserBean> users;
    String interfaceId;
    String USERID;
    String username;
    String userid;
    String password;
    String roleid;
    String status;
    int days;
    private static String interface_ID;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        doGet(request, response);
        response.setContentType("text/html;charset=UTF-8");
        //Http Session
        HttpSession session = request.getSession();
        //Assigning values
        ArrayList<InterfaceBean> pages;
        LoginDao loginDetails = new LoginDao();
        String roleId = session.getAttribute("roleID").toString();
        //Printing results
        String newIP = session.getAttribute("currentIP").toString();
        String username = session.getAttribute("username").toString();
        //Create a context servlet
        ServletContext context = getServletConfig().getServletContext();
        //Retrieving the user and machine data for SessionVarList
        HashMap<String, String> usermap = (HashMap<String, String>) context.getAttribute(SessionVarList.USERMAP);
        HashMap<String, String> userdev = (HashMap<String, String>) context.getAttribute(SessionVarList.USERDEVICE);
        //Calling the method Common Action to check user validation
        CommonAction commonAction = new CommonAction();
        boolean logged = commonAction.checkUserLogin(usermap, username);
        boolean sameDevice = commonAction.checkUserDevice(userdev, username, newIP);
        //Checking user status
        if (logged == true && sameDevice == false) {
            request.setAttribute("AlreadyLogged", "Logged in using another device");
            request.getRequestDispatcher("index.jsp").include(request, response);
        }
        try {
            //Obtaining the functions assigned for the user
            functions = Function.getPageFunctions(roleId, interfaceId);
            request.setAttribute("functions", functions);

            //Loading the pages for the assigned user
            pages = loginDetails.getPages();
            request.setAttribute("pages", pages);

            //Displaying all the records of the registered users
            listUser(request, response, roleId);

            request.getRequestDispatcher("view/user.jsp").forward(request, response);
        } catch (NamingException ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        //Assigning values
        String newIP = session.getAttribute("currentIP").toString();
        String username = session.getAttribute("username").toString();
        String roleId = session.getAttribute("roleID").toString();
        String userID = request.getParameter("getUserID");
        //Displaying the outcome
        System.out.println("currentIP " + newIP);
        System.out.println("username " + username);
        //Creating a context servlet
        ServletContext context = getServletConfig().getServletContext();
        //Retrieving the user and machine data for SessionVarList
        HashMap<String, String> usermap = (HashMap<String, String>) context.getAttribute(SessionVarList.USERMAP);
        HashMap<String, String> userdev = (HashMap<String, String>) context.getAttribute(SessionVarList.USERDEVICE);

        CommonAction commonAction = new CommonAction();
        boolean logged = commonAction.checkUserLogin(usermap, username);
        boolean sameDevice = commonAction.checkUserDevice(userdev, username, newIP);
        //Validating the logged in users credentials
        if (logged == true && sameDevice == false) {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
        //end validating
        //Retrieving the users id
        USERID = request.getParameter("userID");

        //GETTING BUTTON VALUES FROM THE FORM
        String ADD = request.getParameter("actionType");
        String DELETE = request.getParameter("actionDELETE");
        String UPDATE = request.getParameter("actionUPDATE");
        String updateUser = request.getParameter("updateActionType");
        String DISPLAYROLE = request.getParameter("actionRole");
        String ADDUSER = request.getParameter("add_user");
        String DISPLAYPAGE = request.getParameter("actionPage");

        //Adding a new user to the application
        if ("addUser".equals(ADD)) {
            try {
                insertUser(request, response);

            } catch (SQLException ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                processRequest(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if ("DELETE".equals(DELETE)) {       //Deleting a user from the application
            try {
                deleteUser(request, response, userID);
            } catch (SQLException ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if ("UPDATE".equals(UPDATE)) {       //Redirecting the user to the update page
            try {
                //Initializing
                ArrayList<InterfaceBean> pages;
                ArrayList<LoginBean> getUserInfo = new ArrayList<>();
                ArrayList<RoleBean> role = new ArrayList<>();
                UserDao userDetails = new UserDao();
                LoginDao loginDetails = new LoginDao();
                //Retrieving all the details of the logged in user
                getUserInfo = userDetails.loggedUserDetails(USERID);

                role = userDetails.listAllRoles();
                session.setAttribute("role", role);
                pages = loginDetails.getPages();
                request.setAttribute("pages", pages);
                request.setAttribute("getUserInfo", getUserInfo);
                request.getRequestDispatcher("view/update_user.jsp").forward(request, response);
            } catch (NamingException ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if ("updateUser".equals(updateUser)) {       //Updating a user in the application
            try {
                //Assigning the values
                userid = request.getParameter("userid");
                password = request.getParameter("password");
                roleid = request.getParameter("roleid");
                status = request.getParameter("status");
                days = Integer.parseInt(request.getParameter("reset"));
                //Updating the user
                updateUser(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if ("DISPLAYPAGE".equals(DISPLAYPAGE)) {     //Displaying all the records
            try {
                //ArrayList for functions
                ArrayList<InterfaceBean> pages;
                ArrayList<FunctionBean> functions = new ArrayList<>();
                ArrayList<FunctionInterfaceBean> functionInterfaceDetails = new ArrayList<>();
                //PageDao objects
                PageDao pageFunction = new PageDao();
                PageDao pageDetails = new PageDao();
                //LoginDao objects
                LoginDao loginDetails = new LoginDao();

                try {
                    functions = pageFunction.getFunctions();
                } catch (NamingException ex) {
                    Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                }

                functionInterfaceDetails = pageDetails.getFunctionInterfaceList();
                pages = loginDetails.getPages();
                request.setAttribute("pages", pages);

                request.setAttribute("functionInterfaceDetails", functionInterfaceDetails);
                request.setAttribute("functions", functions);

                request.getRequestDispatcher("add_page.jsp").forward(request, response);

            } catch (NamingException ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if ("DISPLAYROLE".equals(DISPLAYROLE)) {     //Displaying all the roles
            try {
                // Initializing
                ArrayList<InterfaceBean> pages;
                ArrayList<RoleBean> roleDetails = new ArrayList<>();
                ArrayList<InterfaceBean> interfaceNames = new ArrayList<>();
                ArrayList<FunctionInterfaceBean> functionInterface = new ArrayList<>();
                RoleDao role = new RoleDao();
                RoleDao roleDao = new RoleDao();
                roleDetails = role.loadRoleUserData();
                LoginDao loginDetails = new LoginDao();

                request.setAttribute("roleDetails", roleDetails);
                functionInterface = roleDao.getFunctionList();
                interfaceNames = role.getInterfaces();
                request.setAttribute("interfaceNames", interfaceNames);
                request.setAttribute("functionInterface", functionInterface);
                pages = loginDetails.getPages();
                request.setAttribute("pages", pages);

                request.getRequestDispatcher("role.jsp").forward(request, response);
            } catch (NamingException ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if ("add_user".equals(ADDUSER)) {
            try {
                //Initializing
                ArrayList<InterfaceBean> pages;
                ArrayList<RoleBean> roleData = new ArrayList<>();
                UserDao user = new UserDao();
                LoginDao loginDetails = new LoginDao();
                //Retrieving the values
                roleData = user.listAllRoles();
                pages = loginDetails.getPages();
                request.setAttribute("roleData", roleData);
                request.setAttribute("pages", pages);
                request.getRequestDispatcher("add_user.jsp").forward(request, response);
            } catch (NamingException ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                ArrayList<InterfaceBean> pages;
                LoginDao loginDetails = new LoginDao();
                pages = loginDetails.getPages();
                request.setAttribute("pages", pages);
                listUser(request, response, roleId);
            } catch (SQLException ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NamingException ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            interfaceId = request.getParameter("interfaceID");

            functions = Function.getPageFunctions(roleId, interfaceId);
            request.setAttribute("functions", functions);
            request.getRequestDispatcher("view/user.jsp").forward(request, response);
        }

    }

    protected void listUser(HttpServletRequest request, HttpServletResponse response, String roleId)
            throws ServletException, IOException, SQLException, NamingException {
        HttpSession session = request.getSession();
        List<UserBean> listUsers = new ArrayList<>();
        UserDao user = new UserDao();
        listUsers = user.listAllUsers();
        int page = 1;
        int recordsPerPage;

        if (request.getParameter("recordsPerPage") != null) {
            recordsPerPage = Integer.parseInt(request.getParameter("recordsPerPage"));
        } else {
            recordsPerPage = 3;
        }
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        UserDao dao = new UserDao();
        List<UserBean> list = dao.viewAllUsers((page - 1) * recordsPerPage,
                recordsPerPage);
        int noOfRecords = dao.getNoOfRecords();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        request.setAttribute("userList", list);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);
        interfaceId = request.getParameter("interfaceID");
        
        if(interfaceId != null){
            interface_ID = interfaceId;
        }
        request.setAttribute("interface_ID", interface_ID);

        functions = Function.getPageFunctions(roleId, interface_ID);
        request.setAttribute("functions", functions);
        //Loading the pages for the assigned user
        ArrayList<InterfaceBean> pages;
        LoginDao loginDetails = new LoginDao();
        pages = loginDetails.getPages();
        request.setAttribute("pages", pages);
        RequestDispatcher view = request.getRequestDispatcher("view/user.jsp");
        view.forward(request, response);
    }

    private void insertUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        UserDao user = new UserDao();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String roleid = request.getParameter("roleid");
        String days = request.getParameter("reset");
        int da = Integer.parseInt(days);
        UserBean userBean = new UserBean(roleid, username, password, da);
        user.insertUser(userBean);
        processRequest(request, response);
    }

    public void deleteUser(HttpServletRequest request, HttpServletResponse response, String userId)
            throws SQLException, IOException, ServletException {
        UserDao user = new UserDao();
        UserBean userBean = new UserBean();
        userBean.setUserid(userId);
        user.deleteUser(userBean);
        processRequest(request, response);
    }

    public void updateUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        UserDao user = new UserDao();
        UserBean userBean = new UserBean(userid, roleid, username, password, status, days);
        user.updateUser(userBean);
        processRequest(request, response);
    }

}
