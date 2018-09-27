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
    </head>
    <body>
        <div class="container-fluid">
            <div class="col-md-3">

            </div>
            <div class="col-md-6 col-md-offset-1">
                <form action="../user_management" method="post" style="margin-top: 100px;">
                    <input type="hidden" name="actionType" value="addUser"/>
                    <div class="form-group">
                        <label for="username">Username</label>
                        <input type="text" class="form-control"  name="username" placeholder="Enter username">
                    </div>
                    <div class="form-group">
                        <label for="password">Password</label>
                        <input type="password" class="form-control"  name="password" placeholder="Password">
                    </div>
                    <div class="form-group">
                        <label for="roleid">role</label>
                        <select class="form-control"  name="roleid">
                            <option value="1">Admin</option>
                            <option value="2">User</option>
                        </select>
                    </div>
                    <input type="submit" class="btn btn-primary" value="Submit"/>
                </form>
            </div>
        </div>
    </body>
</html>
