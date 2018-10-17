<%-- 
    Document   : sidebar
    Created on : Sep 25, 2018, 12:34:41 PM
    Author     : suren_v
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<div id="wrapper">
    <!-- Sidebar -->
    <div id="sidebar-wrapper">
        <nav id="spy">
            <ul class="sidebar-nav nav">
                <li style="margin-top : 25px;">
                    <c:forEach var="item" items="${pages}">
                        <form action="${item.getUrl()}" method="post">
                            <input type="hidden" name="interfaceID" value="<c:out value="${item.getInterfaceid()}" ></c:out>">
                            <button class="btn btn-warning" style="width : 100%; padding:25px 10px; margin-top:5px;" type="submit"><c:out value="${item.getName()}"></c:out></button>
                        </form>
                    </c:forEach>
                </li>
            </ul>
        </nav>
    </div>
</div>