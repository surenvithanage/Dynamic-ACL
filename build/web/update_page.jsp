<%-- 
    Document   : update_page
    Created on : Oct 3, 2018, 2:23:29 PM
    Author     : suren_v
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <jsp:include page="inc/head.jsp"></jsp:include>
        <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    </head>
    <body>
        <div class="container-fluid">
            <div class="col-md-12">
                <div class="col-md-2">
                    <jsp:include page="inc/sidebar.jsp"></jsp:include>
                    </div>
                    <div class="col-md-8 col-md-offset-1">
                        <div class="row">
                        <c:forEach var="items" items="${updateDetails}">
                            <form method="POST" action="PageServlet" style="margin-top:50px;">

                                <div class="form-group">
                                    <label for="name">Page Name</label>
                                    <input type="text" class="form-control" id="name" name="name" aria-describedby="name" value="<c:out value="${items.getInterfaceName()}"></c:out>"required>
                                    </div>
                                    <div class="form-group">
                                        <label for="url">URL</label>
                                        <input type="text" class="form-control" id="url" name="url" value="<c:out value="${items.getUrl()}"></c:out>" disabled="true">
                                    </div>
                                    <div class="form-group">
                                        <label for="description">Description</label>
                                        <input type="text" class="form-control" id="description" name="description" value="<c:out value="${items.getDescription()}"></c:out>" required>
                                    </div>
                                    <h3><strong>Functions</strong></h3>
                                    <div class="form-check">
                                    <c:forEach var="items" items="${functions}" varStatus="result1">
                                        <c:forEach var="result" items="${resultSet}" varStatus="result2">
                                            <c:if test="${result1.count == result2.count}">
                                                <c:if test="${result == 1}">
                                                    <input type="checkbox" checked="true" class="form-check-input" name="functions" value="<c:out value="${items.getId()}"></c:out>">
                                                    <label class="form-check-label"><c:out value="${items.getName()}"></c:out></label>
                                                </c:if>
                                                <c:if test="${result == 0}">
                                                    <input type="checkbox" class="form-check-input" name="functions" value="<c:out value="${items.getId()}"></c:out>">
                                                    <label class="form-check-label"><c:out value="${items.getName()}"></c:out></label>                                               
                                                </c:if>
                                            </c:if>
                                        </c:forEach>
                                    </c:forEach>
                                </div>
                                <button type="submit" class="btn btn-success">Submit</button>
                            </form>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>

    </body>
</html>
