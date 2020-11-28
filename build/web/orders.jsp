<%-- 
    Document   : orders
    Created on : Oct 17, 2020, 4:45:37 PM
    Author     : truongtn
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Orders Page</title>
    </head>
    <body>
        <%@include file = "header.jsp" %>

        <br/>
        <form action="searchOrder" method="Post">
            Search by order id: <input type="text" name="txtOrderId" value="${param.ORDERID}"/>
            <input type="submit" name="btnAction" value="Search by order id"/>
        </form>

        <br/>
        <c:set var="orders" value="${sessionScope.ORDERS}"/>
        <c:if test="${not empty orders}">
            <table border="1">
                <thead>
                    <tr>
                        <th>No.</th>
                        <th>Order Id</th>
                        <th>Shipping Address</th>
                        <th>Order Date</th>
                        <th>Total Price</th>
                        <th>Status</th>
                        <th>Payment Method</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="dto" items="${orders}" varStatus="counter">
                        <tr>
                            <td>${counter.count}</td>
                            <td>
                                <c:url var="orderUrl" value="ViewOrderDetail">
                                    <c:param name="txtOrderId" value="${dto.orderId}"/>
                                </c:url>
                                <a href="${orderUrl}">${dto.orderId}</a>
                            </td>
                            <td>${dto.shippingAddress}</td>
                            <td>${dto.orderDate}</td>
                            <td>${dto.totalPrice}</td>
                            <td>
                                <c:if test="${dto.statusId == 4}">Checked out</c:if>
                                <c:if test="${dto.statusId != 4}">No check out</c:if>
                                </td>
                                <td>
                                <c:if test="${dto.cashed}">Cash</c:if>
                                <c:if test="${!dto.cashed}">Card</c:if>
                                </td>
                            </tr>
                    </c:forEach>
                </tbody>
            </table>

            <br/>
            <br/>


            <c:set var="orderId" value="${requestScope.ORDERID}"/>
            <c:set var="products" value="${requestScope.ORDER_DETAILS}"/>
            <c:if test="${not empty orderId && not empty products}">
                <b>List product by order id: </b> ${orderId}<br/>
                <table border="1">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>Product Name</th>
                            <th>Quantity</th>
                            <th>Price</th>
                            <th>Total</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="dto" items="${products}" varStatus="counter">
                            <tr>
                                <td>${counter.count}</td>
                                <td>${dto.productName}</td>
                                <td>${dto.quantity}</td>
                                <td>${dto.price}</td>
                                <td>${dto.totalPrice}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </c:if>

        <c:set var="error" value="${requestScope.ERROR}"/>
        <c:if test="${empty orders}">
            <c:if test="${not empty error}">
                <font color="red">
                ${error}
                </font>
            </c:if>
        </c:if>
    </body>
</html>
