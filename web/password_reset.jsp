<%-- 
    Document   : password_reset.jsp
    Created on : Oct 9, 2018, 2:42:58 PM
    Author     : suren_v
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <jsp:include page="inc/head.jsp"></jsp:include>
            <link rel="stylesheet" href="inc/reset.css"/>
            <link rel="stylesheet" href="assets/css/body.css"/>
        </head>
        <body>
            <div class="container-fluid">
                <div class="row" style="margin : auto;">
                    <div class="col-md-2" >
                    <jsp:include page="inc/sidebar.jsp"></jsp:include>
                </div>
                <div class="col-md-6 col-md-offset-1" style="color:white;margin-top: 20px;">
                    <h1 style="margin-bottom: 50px;">Password Reset</h1>
                    <form method="POST" action="PasswordServlet" name="passwordReset">
                        <input type="hidden" name="username" value="${username}"/>
                        <label>Current Password</label>
                        <div class="form-group"> 
                            <input type="password" name="password" class="form-control" placeholder="Current Password" required> 
                        </div> 
                        <label>New Password</label>
                        <div class="form-group"> 
                            <input type="password" name="newPassword" minlength="8" class="form-control" placeholder="New Password" required> 
                        </div> 
                        <label>Confirm Password</label>
                        <div class="form-group"> 
                            <input type="password" name="confirmPassword" minlength="8" class="form-control" placeholder="Confirm Password" required> 
                        </div>
                        <div class="form-group" style="margin-bottom: 215px;"> 
                            <input type="submit" class="btn btn-success" value="Submit"/>&nbsp;&nbsp;
                            <input type="reset" class="btn btn-danger" value="Clear"/>
                        </div>
                    </form>
                </div>  
            </div>

        </div>
    </body>
</html>

<script>
    function validateForm() {
    var password = document.forms["passwordReset"]["password"].value;
    var newPassword = document.forms["passwordReset"]["password"].value;
    var confirmPassword = document.forms["passwordReset"]["password"].value;
    if (x == "") {
        alert("Name must be filled out");
        return false;
    }
}
</script>