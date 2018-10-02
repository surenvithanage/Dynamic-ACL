/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.LoginSystem.connection;

import com.LoginSystem.bean.FunctionBean;
import com.LoginSystem.bean.FunctionInterfaceBean;
import com.LoginSystem.bean.InterfaceBean;
import com.LoginSystem.bean.RoleBean;
import com.LoginSystem.dao.LoginDao;
import com.LoginSystem.dao.PageDao;
import com.LoginSystem.dao.RoleDao;
import com.LoginSystem.dao.UserDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

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
        processRequest(request, response);

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
        } catch (SQLException ex) {
            Logger.getLogger(PageServlet.class.getName()).log(Level.SEVERE, null, ex);
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

}
