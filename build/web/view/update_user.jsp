<%-- 
    Document   : update_user
    Created on : Sep 26, 2018, 3:45:29 PM
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
        </head>
        <body>
            <div class="container-fluid">
                <div class="col-md-2">
                <jsp:include page="../inc/sidebar.jsp"></jsp:include>
                </div>
                <div class="col-md-6 col-md-offset-1">
                <c:forEach var="item" items="${getUserInfo}">
                    <form action="user_management" method="post" style="margin-top: 100px;">
                        <input type="hidden" name="updateActionType" value="updateUser"/>
                        <input type="hidden" name="userid" value="<c:out value="${item.getUserid()}"></c:out>"/>
                            <div class="form-group">
                                <label for="username">Username</label>
                                <input type="text" class="form-control"  name="username" value="<c:out value="${item.getUsername()}"></c:out>">
                            </div>
                            <div class="form-group">
                                <label for="password">Password</label>
                                <input type="password" class="form-control"  name="password" value="<c:out value="${item.getPassword()}"></c:out>">
                            </div>
                            <div class="form-group">
                                <label for="roleid">role</label>
                                <select class="form-control"  name="roleid" >
                                <c:forEach var="role" items="${role}">
                                    <c:choose>
                                        <c:when test = "${role.getId() == item.getRoleid()}">
                                            <option value="${role.getId()}"selected><c:out value="${role.getRolename()}"></c:out></option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${role.getId()}"><c:out value="${role.getRolename()}"></c:out></option>
                                        </c:otherwise>
                                    </c:choose>     
                                </c:forEach>
                            </select>
                        </div>
                        <input type="submit" class="btn btn-primary" value="Submit"/>
                    </form>
                </c:forEach>
            </div>
        </div>
    </body>
</html>

