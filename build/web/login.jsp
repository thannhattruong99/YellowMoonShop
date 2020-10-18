<%-- 
    Document   : login
    Created on : Sep 17, 2020, 10:49:27 AM
    Author     : truongtn
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
    </head>
    <body>
        <form action="login" method="Post">
            <c:set var="error" value="${sessionScope.ERROR}"/>
            Email: <input type="text" name="txtEmail"/><br/>
            Password: <input type="password" name="txtPassword"/><br/>
            <c:if test="${not empty error}">
                <font color="red">
                ${error}
                </font><br/>
            </c:if>
            <input type="submit" name="btnAction" value="Login"/>
            <input type="reset" value="Reset"/>
            <a href="create.html">Create account</a>
        </form>
    </body>
</html>
