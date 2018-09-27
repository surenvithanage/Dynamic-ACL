/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.LoginSystem.connection;

import com.LoginSystem.bean.FunctionBean;
import com.LoginSystem.bean.LoginBean;
import com.LoginSystem.bean.UserBean;
import com.LoginSystem.dao.UserDao;
import com.LoginSystem.util.Function;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    static String username ;
    static String userid; 
    static String password ;
    static String roleid;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        String roleId = session.getAttribute("roleID").toString();
        functions = Function.getPageFunctions(roleId, interfaceId);
        request.setAttribute("functions", functions);
        listUser(request, response);
        request.getRequestDispatcher("view/user.jsp").forward(request, response);

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        String roleId = session.getAttribute("roleID").toString();

        String userID = request.getParameter("getUserID");

        USERID = request.getParameter("userID");

        //GETTING BUTTON VALUES FROM THE FORM
        String ADD = request.getParameter("actionType");
        String DELETE = request.getParameter("actionDELETE");
        String UPDATE = request.getParameter("actionUPDATE");
        String updateUser = request.getParameter("updateActionType");

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

            request.setAttribute("getUserInfo", getUserInfo);
            request.getRequestDispatcher("view/update_user.jsp").forward(request, response);

        } else if ("updateUser".equals(updateUser)) {
            try {
                username = request.getParameter("username");
                userid = request.getParameter("userid");
                password = request.getParameter("password");
                roleid  = request.getParameter("roleid");

                updateUser(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
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

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    protected void listUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {

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
        
        UserBean userBean = new UserBean(userid ,roleid, username, password);
        user.updateUser(userBean);
        processRequest(request, response);
    }

}
