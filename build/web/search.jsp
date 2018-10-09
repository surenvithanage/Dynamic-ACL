<%-- 
    Document   : search
    Created on : Oct 8, 2018, 11:26:33 AM
    Author     : suren_v
--%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search Page</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <jsp:include page="inc/head.jsp"></jsp:include>
            <link rel="stylesheet" href="assets/css/body.css"/>
            <style> 
                input[type=text] {
                    width: 130px;
                    box-sizing: border-box;
                    border: 2px solid #ccc;
                    border-radius: 4px;
                    font-size: 16px;
                    background-color: white;
                    background-image: url('searchicon.png');
                    background-position: 10px 10px; 
                    background-repeat: no-repeat;
                    padding: 12px 20px 12px 40px;
                    -webkit-transition: width 0.4s ease-in-out;
                    transition: width 0.4s ease-in-out;
                }
                input[type=text]:focus {
                    width: 100%;
                }
            </style>
        </head>
        <body>
            <div class="container-fluid">
                <div class="row">
                    <div class="col-md-2">
                    <jsp:include page="inc/sidebar.jsp"></jsp:include>
                    </div>
                    <div class="col-md-8 col-md-offset-1"> 
                        <h1 style="color:white;">Search Users</h1>
                        <form method="post" action="SearchServlet">
                            <input type="text" name="search" placeholder="Search..">
                            <input type="submit" class="btn btn-success" value="Search" style="margin-top:20px;"/>
                        </form>

                        <table class="table" style="color:white;">
                        <c:if test="${fn:length(result) gt 0}">
                            <thead>
                            <th>Name</th>
                            <th>Phone NO </th>
                            <th>Email</th>
                            </thead>
                            <tbody>
                                <c:forEach var="items" items="${result}">
                                    <tr>
                                        <td>  <c:out value="${items.getUserid()}"></c:out></td>
                                        <td><c:out value="${items.getRoleid()}"></c:out></td>
                                        <td> <c:out value="${items.getUsername()}"></c:out></td>
                                        </tr>
                                </c:forEach>
                            </tbody>
                        </c:if>
                        <c:if test="${fn:length(result) <= 0}">
                            <h3 style="color:white;">Search using a keyword</h3>
                        </c:if>
                    </table>
                </div>
            </div>
        </div>
    </body>
</html>
