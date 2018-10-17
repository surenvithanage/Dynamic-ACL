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
            <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"/>
            <link rel="stylesheet" href="assets/css/body.css"/>
            <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
            <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        </head>
        <body>
            <div class="container-fluid">
                <div class="col-md-2">
                <jsp:include page="../inc/sidebar.jsp"></jsp:include>
                </div>
                <div class="col-md-8 col-md-offset-1" > 
                    <div class="row" >
                        <div class="row">
                            <center>
                                <h1  style="color : white;">User Management</h1>
                                <span class="pull-left">
                                <c:forEach var="item" items="${functions}">
                                    <span style="float:left; margin-right:50px;">
                                        <c:if test="${item.getName() == 'Add'}">
                                            <form method="post" action="user_management">
                                                <input type="hidden" name="add_user" value="add_user"/>
                                                <button type="submit" class="btn btn-primary"  style="color : white;"><c:out value="${item.getName()}"></c:out></button>
                                                </form>
                                        </c:if>
                                    </span>
                                    <span style="float:left;">
                                        <c:if test="${item.getName() == 'Search'}">
                                            <form method="post" action="SearchServlet">
                                                <button type="submit" class="btn btn-info"  ><c:out value="${item.getName()}"></c:out></button>
                                                </form>
                                        </c:if>
                                    </span>
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
                    <div class="col-md-12" style="color : white;">
                        <div class="dropdown">
                            <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">No Of Records
                                <span class="caret"></span></button>
                            <ul class="dropdown-menu">
                                <li>
                                    <form method="post" action="user_management">
                                        <input type="hidden" name="recordsPerPage" value="3"/>
                                        <input type="submit" class="btn btn-danger btn-sm" style="width: 100%;" value="03 Records"/>
                                    </form>
                                </li>
                                <li>
                                    <form method="post" action="user_management">
                                        <input type="hidden" name="recordsPerPage" value="5"/>
                                        <input type="submit" class="btn btn-danger btn-sm" style="width: 100%;" value="05 Records"/>
                                    </form>
                                </li>
                                <li>
                                    <form method="post" action="user_management">
                                        <input type="hidden" name="recordsPerPage" value="8"/>
                                        <input type="submit" class="btn btn-danger btn-sm " style="width: 100%;" value="08 Records"/>
                                    </form>
                                </li>
                                <li>
                                    <form method="post" action="user_management">
                                        <input type="hidden" name="recordsPerPage" value="5"/>
                                        <input type="submit" class="btn btn-danger btn-sm" style="width: 100%;" value="10 Records"/>
                                    </form>
                                </li>
                                <li>
                                    <form method="post" action="user_management">
                                        <input type="hidden" name="recordsPerPage" value="5"/>
                                        <input type="submit" class="btn btn-danger btn-sm" style="width: 100%;" value="15 Records"/>
                                    </form>
                                </li>
                            </ul>
                        </div>
                        <table class="table" id="Table"  style="color : white;">
                            <caption><h2  style="color : white;">List of Users</h2></caption>
                            <thead>
                                <tr>
                                    <th>UserId</th>
                                    <th>RoleId</th>
                                    <th>Username</th>
                                    <th>Duration</th>
                                    <th>Action</th>
                                    <th>Activate/Deactivate</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="user" items="${requestScope.userList}">
                                    <tr>
                                        <td><c:out value="${user.getUserid()}"></c:out></td>
                                        <td><c:out value="${user.getRoleid()}"></c:out></td>
                                        <td><c:out value="${user.getUsername()}"></c:out></td>
                                        <td><c:out value="${user.getDays()}"></c:out></td>
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
                                        <td>
                                            <input type="checkbox" value="${user.getStatus()}" <c:if test="${user.getStatus() == 0 }">checked</c:if> disabled="True"  />Active User
                                            <input type="checkbox" value="${user.getStatus()}" <c:if test="${user.getStatus() == 1 }">checked</c:if> disabled="True" />Deactivate User
                                            </td>
                                        </tr>
                                </c:forEach> 
                            </tbody>
                        </table>
                        <%--For displaying Previous link except for the 1st page --%>
                        <c:if test="${currentPage != 1}">
                            <td>
                                <form method="post" action="user_management" style="margin:20px;">
                                    <input type="hidden" name="page" value="${currentPage - 1}"/>
                                    <input type="submit" class="btn btn-danger btn-sm" value="Previous"/>
                                </form>
                            </td>
                        </c:if>

                        <%--For displaying Page numbers. 
                        The when condition does not display a link for the current page--%>
                        <div class="col-md-2">
                            <table border="1" cellpadding="5" cellspacing="5" class="table">
                                <tr>
                                    <c:forEach begin="1" end="${noOfPages}" var="i">
                                        <c:choose>
                                            <c:when test="${currentPage eq i}">
                                                <td>${i}</td>
                                            </c:when>
                                            <c:otherwise>
                                                <td>
                                                    <form method="post" action="user_management" >
                                                        <input type="hidden" name="page" value="${i}"/>
                                                        <input type="submit" class="btn btn-danger btn-sm" value="${i}" />
                                                    </form>
                                                </td>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </tr>
                            </table>
                            <%--For displaying Next link --%>
                            <c:if test="${currentPage lt noOfPages}">
                                <td>
                                    <form method="post" action="user_management">
                                        <input type="hidden" name="page" value="${currentPage + 1}"/>
                                        <input type="hidden" name="interfaceDetails" value="<%= request.getAttribute("interface_ID") %>"/>
                                        <input type="hidden" class="btn btn-danger btn-sm" value="Next"/>
                                    </form>
                                </td>
                            </c:if>
                        </div> 
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
