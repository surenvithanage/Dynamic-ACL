<%-- 
    Document   : user
    Created on : Sep 24, 2018, 7:39:13 PM
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
                <div class="col-md-8 col-md-offset-1"> 
                    <div class="row">
                        <div class="col-md-12">
                            <center>
                                <h1>User Management</h1>
                                <span class="pull-left">
                                <c:forEach var="item" items="${functions}">
                                    <c:if test="${item.getName() == 'Add'}">
                                        <a style="margin-left: 20px;" class="btn btn-primary" href="view/add_user.jsp"><c:out value="${item.getName()}"></c:out></a>
                                    </c:if>

                                    <c:if test="${item.getName() == 'Search'}">
                                        <a style="margin-left: 20px;" class="btn btn-primary" href="#"><c:out value="${item.getName()}"></c:out></a>
                                    </c:if>
                                </c:forEach>
                            </span>
                        </center>
                        <div align="center">
                            <!--                                    <a href="user_management?parameter=list" disabled>View Details</a>-->
                            <table class="table">
                                <caption><h2>List of Users</h2></caption>
                                <thead>
                                    <tr>
                                        <th>UserId</th>
                                        <th>RoleId</th>
                                        <th>Username</th>
                                        <th>Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="user" items="${listUsers}">
                                        <tr>
                                            <td><c:out value="${user.getUserid()}"></c:out></td>
                                            <td><c:out value="${user.getRoleid()}"></c:out></td>
                                            <td><c:out value="${user.getUsername()}"></c:out></td>
                                            <td>
                                                <c:forEach var="item" items="${functions}">
                                                    <c:if test = "${item.getName() == 'Update'}">
                                                        <form method="POST" action="user_management">
                                                            <input type="hidden" name="userID" value="${user.getUserid()}"/>
                                                            <input type="hidden" name="actionUPDATE" value="UPDATE"/>
                                                            <button type="submit" class="btn btn-primary btn-sm"><c:out value="${item.getName()}"></c:out></button>
                                                        </form>
                                                    </c:if>&nbsp;&nbsp;&nbsp;
                                                    <c:if test = "${item.getName() == 'Delete'}">
                                                        <form method="POST" action="user_management">
                                                            <input type="hidden" name="getUserID" value="${user.getUserid()}"/>
                                                            <input type="hidden" name="actionDELETE" value="DELETE"/>
                                                            <button type="submit" class="btn btn-danger btn-sm"><c:out value="${item.getName()}"></c:out></button>
                                                            </form>
                                                    </c:if>
                                                </c:forEach>
                                            </td>
                                        </tr>
                                    </c:forEach> 
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
