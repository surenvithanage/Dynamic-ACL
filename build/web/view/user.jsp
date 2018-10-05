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
            <script src= "https://code.jquery.com/jquery-3.3.1.js"></script>
            <script src= "https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
            <script src= "https://cdn.datatables.net/1.10.19/js/dataTables.bootstrap.min.js"></script>
            <link rel="stylesheet" href="https://cdn.datatables.net/1.10.19/css/dataTables.bootstrap.min.css"/>
            <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"/>
            <link rel="stylesheet" href="assets/css/body.css"/>
        </head>
        <body >
            <div class="container-fluid">
                <div class="col-md-2">
                <jsp:include page="../inc/sidebar.jsp"></jsp:include>
                </div>
                <div class="col-md-8 col-md-offset-1"> 
                    <div class="row">
                        <div class="row">
                            <center>
                                <h1  style="color : white;">User Management</h1>
                                <span class="pull-left">
                                <c:forEach var="item" items="${functions}">
                                    <c:if test="${item.getName() == 'Add'}">
                                        <form method="post" action="user_management">
                                            <input type="hidden" name="add_user" value="add_user"/>
                                            <button type="submit" class="btn btn-primary"  style="color : white;"><c:out value="${item.getName()}"></c:out></button>
                                            </form>
                                    </c:if>

                                    <c:if test="${item.getName() == 'Search'}">
                                        <form method="post" action="#">
                                            <a style="margin-left: 20px; " class="btn btn-primary"  style="color : white;" href="#"><c:out value="${item.getName()}"></c:out></a>
                                            </form>
                                    </c:if>
                                </c:forEach>
                            </span>
                            <form method="POST" action="user_management">
                                <input type="hidden" name="actionRole" value="DISPLAYROLE"/>
                                <span class="pull-right"><button class="btn btn-success  btn-sm" style="margin-left:10px;">Add Role </button></span>
                            </form>
                            <form method="POST" action="user_management">
                                <input type="hidden" name="actionPage" value="DISPLAYPAGE"/>
                                <span class="pull-right"><button class="btn btn-danger  btn-sm">Add Page </button></span>
                            </form>
                        </center>
                    </div><br/><br/><br/>
                    <div class="col-md-12">
                        <!--                                    <a href="user_management?parameter=list" disabled>View Details</a>-->
                        <table class="table" id="Table"  style="color : white;">
                            <caption><h2  style="color : white;">List of Users</h2></caption>
                            <thead>
                                <tr>
                                    <th>UserId</th>
                                    <th>RoleId</th>
                                    <th>Username</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="user" items="${requestScope.listUsers}">
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
                                                        <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure you want to delete this item?');"><c:out value="${item.getName()}"></c:out></button>
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
    </body>
</html>
<script>
    $(document).ready(function () {
        $('#Table').DataTable();
    });
</script>