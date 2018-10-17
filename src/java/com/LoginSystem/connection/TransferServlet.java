/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.LoginSystem.connection;

import com.LoginSystem.bean.FunctionBean;
import com.LoginSystem.bean.InterfaceBean;
import com.LoginSystem.common.CommonAction;
import com.LoginSystem.dao.LoginDao;
import com.LoginSystem.util.Function;
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
 * @author suren
 */
public class TransferServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
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
        try {
            if (session.getAttribute("username") == null) {
                response.sendRedirect("index.jsp");
            }
            response.setContentType("text/html;charset=UTF-8");
            ArrayList<InterfaceBean> pages;
            LoginDao loginDetails = new LoginDao();
            pages = loginDetails.getPages();
            request.setAttribute("pages", pages);
        } catch (NamingException ex) {
            Logger.getLogger(TransferServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
        String interfaceID = (String) request.getParameter("interfaceID");
        String roleId = session.getAttribute("roleID").toString();
        ArrayList<FunctionBean> functions;
        functions = Function.getPageFunctions(roleId, interfaceID);
        request.setAttribute("functions", functions);
        request.getRequestDispatcher("view/transfer.jsp").forward(request, response);

    }



}
