/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.LoginSystem.connection;

import com.LoginSystem.bean.InterfaceBean;
import com.LoginSystem.bean.LoginBean;
import com.LoginSystem.dao.LoginDao;
import com.LoginSystem.listener.User;
import com.LoginSystem.util.LoggerDetails;
import com.LoginSystem.util.SessionVarList;
import java.io.IOException;
import java.text.ParseException;
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
            session = request.getSession();         //Creating a session

            LoginBean loginbean = new LoginBean();
            LoginDao loginDetails = new LoginDao();

            //Assigning
            loginbean.setUsername(username);
            loginbean.setPassword(password);

            String Users_name = loginDetails.getUsername(loginbean);
            session.setAttribute("username", username);

            getServletContext().setAttribute("username", this);

            int roleID = loginDetails.getRoleId(loginbean);
            session.setAttribute("roleID", roleID);         //Saving the roleID in a session

            int userID = loginDetails.getUserId(loginbean);
            session.setAttribute("userID", userID);          //Saving the userID in a session

            ArrayList<InterfaceBean> pages;
            //Logger Details
            LoggerDetails logger = new LoggerDetails();

            //Checking single user single login
            User user = new User();
            user.setUsername(username);
            session.setAttribute("user", user);
            
            
            
            
            //Mulitiple Login Check
            System.out.println("Start Checking Session");
            //Retrieving the servlet context
            ServletContext context = getServletConfig().getServletContext();
            //HashMap
            HashMap<String,String> usermap = (HashMap<String,String>) context.getAttribute(SessionVarList.USERMAP);
            //Storing username and sessionId inside the Hashmap
            if(usermap == null){
                //Creating a new map if a map doesnt exists
                usermap = new HashMap<>();
                context.setAttribute(SessionVarList.USERMAP , usermap);
            }

            //Obtaining the IP Address
            String newIP = request.getRemoteAddr(); 
            HashMap<String , String> userdev = new HashMap<>();
            //Setting the data inside the SessionVarlist
            context.setAttribute(SessionVarList.USERDEVICE, userdev);   
            
            String sessionID = session.getId();     //Obtaining the session  ID
            usermap.put(username, sessionID);
            userdev.put(username, newIP);
            
            //Checking the IP Address
            System.out.println("SESSION ID : " + sessionID);
            
            //Storing the sessionID 
            session.setAttribute("currentIP", newIP);

            //End Of Mulitple Login Check
            
            if (loginDetails.initialLogin(loginbean) == true) {
                request.setAttribute("username", username);
                request.getRequestDispatcher("password_reset.jsp").forward(request, response);
            } else if (loginDetails.getAuthentication(loginbean) != false) {
                try {
                    //Checking Authentication status of user
                    if (loginDetails.passwordValid(loginbean) == true) {

                        try {
                            //Getting Page Details
                            pages = loginDetails.getPages();
                            request.setAttribute("pages", pages);
                            logger.getLogger("login Logger Details", "info", Users_name, request);

                            request.getRequestDispatcher("view/home.jsp").forward(request, response);
                        } catch (NamingException ex) {
                            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    } else {
                        request.setAttribute("username", username);
                        request.getRequestDispatcher("password_reset.jsp").forward(request, response);
                    }
                } catch (NamingException | ParseException ex) {
                    Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
//                response.sendRedirect("index.jsp");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }

        } catch (NamingException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
