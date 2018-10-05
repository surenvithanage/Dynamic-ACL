<%-- 
    Document   : home
    Created on : Sep 23, 2018, 7:26:40 PM
    Author     : suren
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <jsp:include page="../inc/head.jsp"></jsp:include>
            <link rel="stylesheet" href="assets/css/body.css"/>
        </head>
        <body>
            <div class="container-fluid">
                <div class="row">
                    <div class="col-md-2">
                    <jsp:include page="../inc/sidebar.jsp"></jsp:include>
                </div>
                <div class="col-md-8 col-md-offset-1">
                    <h1 style="color : white;">HOME PAGE</h1>
                </div>
            </div>

        </div>



    </body>
</html>
