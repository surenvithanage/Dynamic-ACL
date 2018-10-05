/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.LoginSystem.connection;

import com.LoginSystem.bean.InterfaceBean;
import com.LoginSystem.bean.LoginBean;
import com.LoginSystem.dao.LoginDao;
import com.LoginSystem.util.LoggerDetails;
import com.LoginSystem.util.PasswordSHA;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
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
 * @author suren
 */
public class LoginServlet extends HttpServlet {

  
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
     
    }

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = null;
            processRequest(request, response);
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String Encrypted = "";
            //Password encryption
            try {
                Encrypted = PasswordSHA.getEncryptedPassword(password);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            session = request.getSession();         //Creating a session
            
            LoginBean loginbean = new LoginBean();
            
            loginbean.setUsername(username);
            loginbean.setPassword(password);
            
            LoginDao loginDetails = new LoginDao();
            
            String Users_name = loginDetails.getUsername(loginbean);
            session.setAttribute("username", username);
            
            int roleID = loginDetails.getRoleId(loginbean);
            session.setAttribute("roleID", roleID);         //Saving the roleID in a session
            
            int userID = loginDetails.getUserId(loginbean);
            session.setAttribute("userID",userID);          //Saving the userID in a session
            
            ArrayList<InterfaceBean> pages;
            //Logger Details
            LoggerDetails logger = new LoggerDetails();
            if(loginDetails.getAuthentication(loginbean) != false){     //Checking Authentication status of user
                
                
                pages = loginDetails.getPages();     //Getting Page Details
                
                request.setAttribute("pages", pages);     //Sending the pages through request
                session.setAttribute("pages", pages);
                
                
                logger.getLogger("login Logger Details", "info", Users_name, request);
                
                
                request.getRequestDispatcher("view/home.jsp").forward(request, response);
            }else{
                response.sendRedirect("index.jsp");
            }
        } catch (NamingException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    

}
