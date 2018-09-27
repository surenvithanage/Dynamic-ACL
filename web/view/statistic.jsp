<%-- 
    Document   : statistic
    Created on : Sep 24, 2018, 7:38:53 PM
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
