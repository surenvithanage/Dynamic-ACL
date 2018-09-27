<%-- 
    Document   : index
    Created on : Sep 25, 2018, 4:42:56 PM
    Author     : suren_v
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form method="post" action="LoginServlet">
            username <input type="text" name="username" id="username">
            password <input type="password" name="password" id="password">
            <input type="submit" value="submit"/>
        </form>
    </body>
</html>
