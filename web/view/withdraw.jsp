<%-- 
    Document   : withdraw
    Created on : Sep 24, 2018, 7:39:06 PM
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
    </head>
    <body>
        <div class="container-fluid">
            <div class="col-md-2">
                <jsp:include page="../inc/sidebar.jsp"></jsp:include>
            </div>
            <div class="col-md-9">
                <c:forEach var="item" items="${functions}">
                    <a href="#"><c:out value="${item.getName()}"></c:out></a><br/>                                                                   
                </c:forEach>
            </div>
        </div>
        
        
    </body>
</html>
