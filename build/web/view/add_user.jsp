<%-- 
    Document   : userForm
    Created on : Sep 25, 2018, 1:24:53 PM
    Author     : suren_v
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <jsp:include page="../inc/head.jsp"></jsp:include>
        <link rel="stylesheet" href="../assets/css/body.css"/>
        </head>
        <body >
            <div class="container-fluid">
                <div class="col-md-2">
                    <jsp:include page="../inc/sidebar.jsp"></jsp:include>
                </div>
                <div class="col-md-6 col-md-offset-1">
                    <h2 style="color:white;">Add New User</h2>
                    <form action="../user_management" method="post" style="margin-top: 100px;color:white;">
                        <input type="hidden" name="actionType" value="addUser"/>
                        <div class="form-group">
                            <label for="username">Username</label>
                            <input type="text" class="form-control"  name="username" placeholder="Enter username" required>
                        </div>
                        <div class="form-group">
                            <label for="password">Password</label>
                            <input type="password" class="form-control" minlength="5"  name="password" placeholder="Password" required>
                        </div>
                        <div class="form-group">
                            <label for="roleid">role</label>
                            <select class="form-control"  name="roleid" required>
                            <c:forEach var="items" items="${roleData}">
                                <option value="${items.getId()}"> <c:out value="${items.getRolename()}"></c:out></option>
                            </c:forEach>
                        </select>
                    </div>
                    <input type="submit" class="btn btn-primary" value="Submit"/>
                </form>
            </div>
        </div>
    </body>
</html>
