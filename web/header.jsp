<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<body>
    <c:set var="user" value="${sessionScope.USER}"/>
    <c:if test="${not empty user}">
        <font color ="Blue">
        Welcome, ${user.fullname}
        </font><br/>
        <a href="logout?btnAction=logout">Log Out</a>
        <br/><br/>
        <c:if test="${user.roleId == 2}">
            <a href="createCake.jsp">Create a cake</a><br/><br/>
        </c:if>
        <c:if test="${user.roleId == 1}">
            <c:url var="ordersUrl" value="ViewOrders">
            </c:url>
            <a href="${ordersUrl}">View orders</a><br/><br/>
        </c:if>
    </c:if>       

    <c:url var="homeUrl" value="StartApplication">
    </c:url>
    <c:url var="cartUrl" value="cart.jsp">
        
    </c:url>        
    <a href="${homeUrl}">Home</a>
    <a href="${cartUrl}">Cart</a>
    <c:if test="${empty user}">
        <a href="login.html">Login</a>
    </c:if> 
    <br/>
</body>
