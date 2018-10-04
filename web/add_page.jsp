<%-- 
    Document   : add_page
    Created on : Oct 1, 2018, 2:06:49 PM
    Author     : suren_v
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="inc/head.jsp"></jsp:include>
            <script src= "https://code.jquery.com/jquery-3.3.1.js"></script>
            <script src= "https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
            <script src= "https://cdn.datatables.net/1.10.19/js/dataTables.bootstrap.min.js"></script>
            <link rel="stylesheet" href="https://cdn.datatables.net/1.10.19/css/dataTables.bootstrap.min.css"/>
            <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"/>
            <title>JSP Page</title>
        </head>
        <body>
            <div class="container-fluid">
                <div class="row">
                    <div class="col-md-2">
                    <jsp:include page="inc/sidebar.jsp"></jsp:include>
                    </div>
                    <div class="col-md-7 col-md-offset-1">

                        <div class="row">
                            <form method="POST" action="PageServlet" style="margin-top:50px;">
                                <div class="form-group">
                                    <label for="name">Page Name</label>
                                    <input type="text" class="form-control" id="name" name="name" aria-describedby="name" placeholder="Enter name" required>
                                </div>
                                <div class="form-group">
                                    <label for="url">URL</label>
                                    <input type="text" class="form-control" id="url" name="url" placeholder="url" required>
                                </div>
                                <div class="form-group">
                                    <label for="description">Description</label>
                                    <input type="text" class="form-control" id="description" name="description" placeholder="description" required>
                                </div>
                                <h3><strong>Functions</strong></h3>
                                <div class="form-check">
                                <c:forEach var="items" items="${functions}">
                                    <input type="checkbox" class="form-check-input" name="functions" value="<c:out value="${items.getId()}"></c:out>">
                                    <label class="form-check-label"><c:out value="${items.getName()}"></c:out></label>
                                </c:forEach>
                            </div>
                            <button type="submit" class="btn btn-success">Submit</button>
                        </form>
                    </div>
                    <div class="row">
                        <h2 style="margin-bottom: 50px;margin-top: 100px;">Interface Details </h2>
                        <table class="table" id="Table"">
                            <thead>
                                <tr>
                                    <th>Interface Name</th>
                                    <th>Function Name</th>
                                    <th>Option</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="items" items="${functionInterfaceDetails}">
                                   
                                    <tr>
                                        <td><c:out value="${items.getInterfaceName()}"></c:out></td>
                                        <td>
                                        <c:forEach var="func" items="${items.getFunction()}">
                                            <strong><c:out value="${func.getName()}"></c:out></strong><br/>
                                        </c:forEach>
                                        </td>
                                        
                                        <td><span class="pull-left" style="margin-right: 10px;">
                                                <form method="GET" action="PageServlet">
                                                    <input type="hidden" name="InterfaceID" value="<c:out value="${items.getFunctionInterfaceId()}"></c:out>">
                                                    <button class="btn btn-success">Update</button>
                                                </form></span>
                                                <form method="GET" action="PageServlet">
                                                    <input type="hidden" name="functionInterfaceID" value="<c:out value="${items.getFunctionInterfaceId()}"></c:out>">
                                                    <button class="btn btn-danger" onclick="return confirm('Are you sure you want to delete this item?');">Delete</button>
                                                </form>
                                            </td>
                                        </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div
    </body>
</html>
<script>
    $(document).ready(function () {
        $('#Table').DataTable();
    });
</script>