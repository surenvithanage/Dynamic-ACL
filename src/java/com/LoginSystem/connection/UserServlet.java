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
import com.LoginSystem.dao.PageDao;
import com.LoginSystem.dao.RoleDao;
import com.LoginSystem.dao.UserDao;
import com.LoginSystem.util.Function;
import com.LoginSystem.util.PasswordSHA;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
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

    ArrayList<FunctionBean> functions;
    ArrayList<UserBean> users;
    static String interfaceId;
    static String USERID;
    static String username;
    static String userid;
    static String password;
    static String roleid;

   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        String roleId = session.getAttribute("roleID").toString();
        functions = Function.getPageFunctions(roleId, interfaceId);
        session.setAttribute("functions", functions);
        listUser(request, response);

        request.getRequestDispatcher("view/user.jsp").forward(request, response);

    }

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        String roleId = session.getAttribute("roleID").toString();
        System.out.println("Role id : " + roleId);
        String userID = request.getParameter("getUserID");

        USERID = request.getParameter("userID");

        //GETTING BUTTON VALUES FROM THE FORM
        String ADD = request.getParameter("actionType");
        String DELETE = request.getParameter("actionDELETE");
        String UPDATE = request.getParameter("actionUPDATE");
        String updateUser = request.getParameter("updateActionType");
        String DISPLAYROLE = request.getParameter("actionRole");
        String ADDUSER = request.getParameter("add_user");
        String DISPLAYPAGE = request.getParameter("actionPage");

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
        } else if ("DELETE".equals(DELETE)) {
            try {
                deleteUser(request, response, userID);
            } catch (SQLException ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if ("UPDATE".equals(UPDATE)) {
            UserDao userDetails = new UserDao();
            ArrayList<LoginBean> getUserInfo = new ArrayList<>();
            getUserInfo = userDetails.loggedUserDetails(USERID);

            //Passing the roles
            ArrayList<RoleBean> role = new ArrayList<>();
            role = userDetails.listAllRoles();
            session.setAttribute("role", role);

            request.setAttribute("getUserInfo", getUserInfo);
            request.getRequestDispatcher("view/update_user.jsp").forward(request, response);

        } else if ("updateUser".equals(updateUser)) {
            try {
                username = request.getParameter("username");
                userid = request.getParameter("userid");
                password = request.getParameter("password");
                roleid = request.getParameter("roleid");

                updateUser(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if ("DISPLAYPAGE".equals(DISPLAYPAGE)) {
            //ArrayList for functions
            ArrayList<FunctionBean> functions = new ArrayList<>();
            //PageDao object
            PageDao pageFunction = new PageDao();
            try {
                functions = pageFunction.getFunctions();
            } catch (NamingException ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            //Interface Details
            ArrayList<FunctionInterfaceBean> functionInterfaceDetails = new ArrayList<>();
            PageDao pageDetails = new PageDao();
            functionInterfaceDetails = pageDetails.getFunctionInterfaceList();

            //Session
            session.setAttribute("functionInterfaceDetails", functionInterfaceDetails);
            session.setAttribute("functions", functions);

            response.sendRedirect("add_page.jsp");
        } else if ("DISPLAYROLE".equals(DISPLAYROLE)) {
            // ROLE DETAILS
            ArrayList<RoleBean> roleDetails = new ArrayList<>();
            UserDao user = new UserDao();
            roleDetails = user.listAllRoles();
            //Saving the roles in a session
            session.setAttribute("roleDetails", roleDetails);
            //Interface function Details
            ArrayList<FunctionInterfaceBean> functionInterface = new ArrayList<>();
            RoleDao roleDao = new RoleDao();
            functionInterface = roleDao.getFunctionList();
            //InterfaceNames
            ArrayList<InterfaceBean> interfaceNames = new ArrayList<>();
            RoleDao role = new RoleDao();
            interfaceNames = role.getInterfaces();
            session.setAttribute("interfaceNames", interfaceNames);     //Interface Names
            //Saving the Functions in a session
            session.setAttribute("functionInterface", functionInterface);
            //Dispatcher
            response.sendRedirect("role.jsp");
        } else if ("add_user".equals(ADDUSER)) {

            ArrayList<RoleBean> roleData = new ArrayList<>();
            UserDao user = new UserDao();
            roleData = user.listAllRoles();
            session.setAttribute("roleData", roleData);
            response.sendRedirect("view/add_user.jsp");
        } else {

            try {
                listUser(request, response);        //Displaying Users

            } catch (SQLException ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            interfaceId = request.getParameter("interfaceID");
            functions = Function.getPageFunctions(roleId, interfaceId);
            request.setAttribute("functions", functions);

            request.getRequestDispatcher("view/user.jsp").forward(request, response);
        }
    }

   
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    protected void listUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        HttpSession session = request.getSession();
        List<UserBean> listUsers = new ArrayList<>();
        UserDao user = new UserDao();
        listUsers = user.listAllUsers();
        request.setAttribute("listUsers", listUsers);
    }

    private void insertUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        UserDao user = new UserDao();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String roleid = request.getParameter("roleid");

        UserBean userBean = new UserBean(roleid, username, password);
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
        UserBean userBean = new UserBean(userid, roleid, username, password);
        user.updateUser(userBean);
        processRequest(request, response);
    }

}
