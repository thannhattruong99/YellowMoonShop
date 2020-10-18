<%-- 
    Document   : create
    Created on : Sep 16, 2020, 11:05:02 AM
    Author     : truongtn
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Account Page</title>
    </head>
    <body>
        <form action="MainController" method="Post">
            <c:set var="errors" value="${requestScope.CREATEERRORS}"/>
            Email: <input type="text" name="txtEmail" value ="${param.txtEmail}" />(6-30 chars)<br/>
            <c:if test="${not empty errors.emailLengthErr}">
                <font color="red">
                ${errors.emailLengthErr}
                </font><br/>
            </c:if>
            <c:if test="${not empty errors.emailTypeErr}">
                <font color="red">
                ${errors.emailTypeErr}
                </font><br/>
            </c:if> 
            <c:if test="${not empty errors.emailIsExisted}">
                <font color="red">
                ${errors.emailIsExisted}
                </font><br/>
            </c:if>
            Password: <input type="password" name="txtPassword"/>(6-20 chars)<br/>
            <c:if test="${not empty errors.passwordLengthErr}">
                <font color ="red">
                ${errors.passwordLengthErr}
                </font><br/>
            </c:if>
            Confirm: <input type="password" name="txtConfirm"/><br/>
            <c:if test="${not empty errors.confirmNotMatched}">
                <font color="red">
                ${errors.confirmNotMatched}
                </font><br/>
            </c:if>
            Full name: <input type="text" name="txtFullname" value="${param.txtFullname}"/>(2-50 chars)<br/>
            <c:if test="${not empty errors.fullnameLengthErr}">
                <font color="red">
                ${errors.fullnameLengthErr}
                </font><br/>
            </c:if>
            <input type="submit" name="btnAction" value="Create New Account"/>
            <input type="reset" value="Reset"/>
        </form>
        <a href="MainController">Back to login</a>
    </body>
</html>
