<%-- 
    Document   : verify
    Created on : Sep 16, 2020, 11:30:52 AM
    Author     : truongtn
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Verify page</title>
    </head>
    <body>

        <form action="MainController" method="POST">
            Verify code in your mail <input type="hidden" name="txtEmail" value="${param.txtEmail}" readonly="true"> <br/>
            <input type="text" name="txtVerifyCode"/><br/>
            <c:set var = "error" value="${requestScope.ERROR}"/>
            <c:if test="${not empty error}">
                <font color="red">
                ${error}
                </font><br/>
            </c:if>
            <input type="submit" name="btnAction" value="Verify"/>
            <input type="submit" name="btnAction" value="Resend Code"/>
        </form>
        <a href="StartApplication">Back to login</a>
    </body>
</html>
