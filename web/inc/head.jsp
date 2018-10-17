<%-- 
    Document   : head
    Created on : Sep 25, 2018, 12:55:45 PM
    Author     : suren_v
--%>

<%@page import="com.LoginSystem.dao.LoginDao"%>
<%@page import="com.LoginSystem.listener.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page errorPage="index.jsp" autoFlush="true" %>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<!-- Alertify -->
<script src="assets/js/alertify.min.js"></script>
<script src="assets/js/alertify.js"></script>
<link rel="stylesheet" href="assets/css/alertify.bootstrap.css"/>

<link rel="stylesheet" href="assets/css/alertify.core.css"/>
<span class="pull-right btn btn-default btn-sm" style="margin-right: 20px;font-size: 10px;"><a href="<%=request.getContextPath()%>/LogoutServlet">Logout</a></span>
<h3 style="color:white;margin-left: 50px;">Welcome ${username}</h3>
