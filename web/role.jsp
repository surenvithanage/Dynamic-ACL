<%-- 
    Document   : role
    Created on : Sep 27, 2018, 2:14:33 PM
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
                        <table class="table" style="color:white;">
                            <thead>
                            <th>Role ID</th>
                            <th>Role Name</th>
                            </thead>
                            <tbody>
                            <c:forEach var="item" items="${roleDetails}">
                                <tr>
                                    <td><c:out value="${item.getId()}"></c:out></td>
                                    <td><c:out value="${item.getRolename()}"></c:out></td>
                                    </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    <hr/>
                    <h3 style="text-align: center;color:white;">ROLE FORM</h3>
                    <form method="POST" action="RoleServlet">
                        <div class="form-group">
                            <label for="Role Name" style="color:white;">Role Name</label>
                            <input type="text" class="form-control" id="roleName" name="roleName"  placeholder="Enter Role Name" required>
                        </div>
                        <div class="form-check">
                            <c:forEach var="names" items="${interfaceNames}">
                                <div class="dropdown">
                                    <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown" style="float: left; margin-left: 10px;"><c:out value="${names.getName()}" ></c:out><span class="caret"></span></button>
                                        <ul class="dropdown-menu dropdown-menu" style="margin-top:50px;" >
                                        <c:forEach var="items" items="${functionInterface}">
                                            <c:if test="${names.getName().equals(items.getInterfaceName()) }">
                                                <li><input type="checkbox" class="form-check-input" name="FunctionInterfaceID" value="${items.getFunctionInterfaceId()}">
                                                    <label class="form-check-label"><c:out value="${items.getFunctionName()}"></c:out></label></li>
                                                </c:if>    
                                            </c:forEach>
                                    </ul>
                                </c:forEach>

                            </div>

                            <span class="pull-right" style="margin-top: 50px; margin-bottom: 100px;"><button type="submit" class="btn btn-success">Submit</button></span>
                    </form>
                </div>
            </div>
        </div>       

    </body>
</html>

