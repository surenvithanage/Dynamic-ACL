/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.LoginSystem.connection;

import com.LoginSystem.bean.FunctionBean;
import com.LoginSystem.util.Function;
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
public class TransferServlet extends HttpServlet {

  
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
        processRequest(request, response);
        String interfaceID = (String) request.getParameter("interfaceID");
   
        HttpSession session = request.getSession();
        String roleId = session.getAttribute("roleID").toString();
        
        ArrayList<FunctionBean> functions;
        
        functions = Function.getPageFunctions(roleId, interfaceID);
        
        request.setAttribute("functions", functions);
        
        request.getRequestDispatcher("view/transfer.jsp").forward(request, response);
    }

   
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
