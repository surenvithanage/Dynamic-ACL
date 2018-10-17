/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.LoginSystem.connection;

import com.LoginSystem.bean.InterfaceBean;
import com.LoginSystem.bean.UserBean;
import com.LoginSystem.common.CommonAction;
import com.LoginSystem.dao.LoginDao;
import com.LoginSystem.dao.SearchDao;
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
public class SearchServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        session.getAttribute("pages");
        if (session.getAttribute("username") == null) {
            response.sendRedirect("index.jsp");
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
            processRequest(request, response);

            String search = request.getParameter("search");
            SearchDao daoSearch = new SearchDao();
            ArrayList<UserBean> getResult = new ArrayList<>();
            getResult = daoSearch.getSearch(search);
            request.setAttribute("result", getResult);
            ArrayList<InterfaceBean> pages;
            LoginDao loginDetails = new LoginDao();
            pages = loginDetails.getPages();
            request.setAttribute("pages", pages);
            request.getRequestDispatcher("search.jsp").forward(request, response);
        } catch (NamingException ex) {
            Logger.getLogger(SearchServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
