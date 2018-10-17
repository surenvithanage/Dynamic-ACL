<%-- 
    Document   : role_update
    Created on : Oct 16, 2018, 9:21:30 AM
    Author     : suren_v
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Role Page</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <jsp:include page="inc/head.jsp"></jsp:include>
            <link rel="stylesheet" href="assets/css/body.css"/>
        </head>
        <body>
            <div class="row">
                <div class="col-md-12">
                    <div class="col-md-2">
                    <jsp:include page="inc/sidebar.jsp"></jsp:include>
                    </div>
                    <div class="col-md-6 col-md-offset-1">
                        <form class="form-signin" action="UserFunctions" method="post">
                        <c:forEach var="urole" items="${alldata}">
                            <p style="color:white;">Role Name :</p><input type="text" class="form-control" name="role_name" value="${urole.getRolename()}" disabled="true" style="width: 250px;">
                            <input type="hidden" name="rid" value="${urole.getId()}">
                            <div class="row" style="height: 25px;"></div>
                            <c:forEach var="inter" items="${inter}">
                                <div class="dropdown">
                                    <button type="button" class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" style="float: left; margin-left: 10px;"><c:out value="${inter.getName()}"></c:out><span class="caret"></span></button>
                                        <ul class="dropdown-menu" style="margin-top: 30px; inline-box-align: inherit;">    
                                        <c:forEach var="ib" items="${funcs}" varStatus="func_count">
                                            <c:if test="${inter.getName()==ib.getName()}">
                                                <c:forEach var="permission" items="${data}" varStatus="permission_count">
                                                    <c:if test="${func_count.count == permission_count.count}">
                                                        <c:if test="${permission == '1'}">
                                                            <li><input type="checkbox" value="${ib.getInterfaceid()}" checked="true" name="functions">&nbsp;&nbsp;<c:out value="${ib.getUrl()}"></c:out></li>
                                                            </c:if>
                                                            <c:if test="${permission == '0'}">
                                                            <li><input type="checkbox" value="${ib.getInterfaceid()}" name="functions">&nbsp;&nbsp;<c:out value="${ib.getUrl()}"></c:out></li>
                                                            </c:if>
                                                        </c:if>
                                                    </c:forEach>
                                                </c:if>
                                            </c:forEach>
                                    </ul>
                                </div>
                            </c:forEach>
                        </c:forEach>
                        
                        <div class="row" style="height: 25px;"></div>
                        <input type="hidden" name="action" value="update_role">
                        <button class="btn btn-primary" type="submit" name="Submit" value="Submit">Update</button>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
