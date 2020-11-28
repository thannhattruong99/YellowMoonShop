<%-- 
    Document   : login
    Created on : Sep 17, 2020, 10:49:27 AM
    Author     : truongtn
--%>

<%@page import="truongtn.api.APIWrapper"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
        <script>
            window.fbAsyncInit = function () {
                FB.init({
                    appId: '548837722387883',
                    cookie: true,
                    xfbml: true,
                    version: 'v5.0'
                });

                FB.AppEvents.logPageView();

            };

            (function (d, s, id) {
                var js, fjs = d.getElementsByTagName(s)[0];
                if (d.getElementById(id)) {
                    return;
                }
                js = d.createElement(s);
                js.id = id;
                js.src = "https://connect.facebook.net/en_US/sdk.js";
                fjs.parentNode.insertBefore(js, fjs);
            }(document, 'script', 'facebook-jssdk'));


            FB.getLoginStatus(function (response) {
                statusChangeCallback(response);
            });


            function checkLoginState() {
                FB.getLoginStatus(function (response) {
                    statusChangeCallback(response);
                });
            }
        </script>
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
        <a href="<%=APIWrapper.getDialogLink()%>">Login via Facebook</a>


    <fb:login-button 
        scope="public_profile,email"
        onlogin="checkLoginState();">
        Facebook
    </fb:login-button>
</body>
</html>
