/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.LoginSystem.connection;

import com.LoginSystem.bean.InterfaceBean;
import com.LoginSystem.bean.LoginBean;
import com.LoginSystem.dao.LoginDao;
import com.LoginSystem.util.DBConnection;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author suren
 */
public class LoginServlet extends HttpServlet {

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
        HttpSession session = null;
        processRequest(request, response);
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        session = request.getSession();         //Creating a session
        
        LoginBean loginbean = new LoginBean();
        
        loginbean.setUsername(username);
        loginbean.setPassword(password);
        
        LoginDao loginDetails = new LoginDao();
        
        int roleID = loginDetails.getRoleId(loginbean);
        session.setAttribute("roleID", roleID);         //Saving the roleID in a session
        
        int userID = loginDetails.getUserId(loginbean);
        session.setAttribute("userID",userID);          //Saving the userID in a session
        
        ArrayList<InterfaceBean> pages;
        
        if(loginDetails.getAuthentication(loginbean) != false){     //Checking Authentication status of user
             
        
        pages = loginDetails.getPages();     //Getting Page Details
        
        request.setAttribute("pages", pages);     //Sending the pages through request
        session.setAttribute("pages", pages);
        
        request.getRequestDispatcher("view/home.jsp").forward(request, response);
        }else{
            response.sendRedirect("index.jsp");
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
