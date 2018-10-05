/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.LoginSystem.connection;

import com.LoginSystem.bean.InterfaceBean;
import com.LoginSystem.bean.RoleBean;
import com.LoginSystem.dao.RoleDao;
import com.LoginSystem.dao.UserDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.input.KeyCode;
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
    }

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String roleName = request.getParameter("roleName");
        String[] function = request.getParameterValues("FunctionInterfaceID");
        RoleDao role = new RoleDao();
        HttpSession session = request.getSession();
        role.insertPrivilage(roleName, function);

        ArrayList<RoleBean> roleDetails = new ArrayList<>();
        
        UserDao user = new UserDao();
        roleDetails = user.listAllRoles();
        session.setAttribute("roleDetails", roleDetails);

        //Saving the roles in a session
        response.sendRedirect("role.jsp");

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
