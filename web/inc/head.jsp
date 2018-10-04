<%-- 
    Document   : head
    Created on : Sep 25, 2018, 12:55:45 PM
    Author     : suren_v
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<span class="pull-right btn btn-default btn-sm" style="margin-right: 20px;font-size: 10px;"><a href="<%=request.getContextPath()%>/LogoutServlet">Logout</a></span>
<h3 style="color:white;margin-left: 50px;">Welcome ${username}</h3>
 
<%
  String username = session.getAttribute("username").toString();
  
  if(username == ""){
      response.sendRedirect("index.jsp");
  }
%>
