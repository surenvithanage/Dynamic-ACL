<%-- 
    Document   : transfer
    Created on : Sep 24, 2018, 7:42:40 PM
    Author     : suren
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <jsp:include page="../inc/head.jsp"></jsp:include>
        <link rel="stylesheet" href="assets/css/body.css"/>
    </head>
    <body >
        <div class="container-fluid">
            <div class="col-md-2">
                <jsp:include page="../inc/sidebar.jsp"></jsp:include>
            </div>
            <div class="col-md-9">
                <c:forEach var="item" items="${functions}">
                    <a href="#" class="btn btn-info" style="margin-bottom: 20px;"><c:out value="${item.getName()}"></c:out></a><br/>
                </c:forEach>
            </div>
        </div>
    </body>
</html>
