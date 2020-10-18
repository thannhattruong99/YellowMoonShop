<%-- 
    Document   : cart
    Created on : Oct 16, 2020, 12:11:56 AM
    Author     : truongtn
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="./js/validate.js"></script>
        <title>Cart Page</title>
    </head>
    <body>
        <%@include file = "header.jsp" %>
        <form action="searchProduct" method="Post">
            Search: <input type="text" name="txtSearchValue" value="${sessionScope.SEARCH_VALUE.searchValue}"/>
            <br/>
            <c:set var="ranges" value="${sessionScope.MONEY_RANGE}"/>
            <c:set var="category" value="${sessionScope.CATEGORY}"/>

            <c:if test="${not empty ranges}">
                <br/>
                <input type="radio" name="txtMinPrice" value="-1" <c:if test="${-1 == sessionScope.SEARCH_VALUE.minPrice || sessionScope.SEARCH_VALUE.minPrice == null}" var="checkedResult"> checked</c:if>>
                    <label>None filter</label></br>
                <c:forEach var="dto" items="${ranges}">
                    <input type="radio" name="txtMinPrice" value="${dto.minPrice}" <c:if test="${dto.minPrice == sessionScope.SEARCH_VALUE.minPrice}" var="checkedResult"> checked</c:if>>
                    <label>From ${dto.minPrice} VND to ${dto.maxPrice} VND</label><br>
                </c:forEach>
            </c:if>

            <c:if test="${not empty category}">
                <br/>
                <input type="radio" name="txtCategoryId" value="-1" <c:if test="${-1 == sessionScope.SEARCH_VALUE.categoryId || sessionScope.SEARCH_VALUE.categoryId == null}">checked</c:if>>
                    <label>None filter</label></br>
                <c:forEach var="dto" items="${category}">
                    <input type="radio" name="txtCategoryId" value="${dto.categoryId}" <c:if test="${dto.categoryId == sessionScope.SEARCH_VALUE.categoryId}">checked</c:if>>
                    <label>${dto.categoryName}</label><br>
                </c:forEach>
            </c:if>        
            <br/>
            <input type="submit" name="btnAction" value="Search"/>
        </form>
        <br/>
        <br/>   
        <c:set var="cart" value="${sessionScope.CART}"/>
        <c:if test="${not empty cart.items}">
            <table border="1">
                <thead>
                    <tr>
                        <th>No</th>
                        <th>Name</th>
                        <th>Price</th>
                        <th>Amount</th>
                        <th>Total</th>
                        <th colspan="2">Action</th>
                    </tr>
                </thead>
                <c:forEach var="dto" items="${cart.items}" varStatus="counter">
                    <c:set var="productDTO" value="${dto.value}"/>
                    <form action="AddToCart" method="Post">
                        <tr>
                            <td>${counter.count}</td>
                            <td>${productDTO.productName}</td>
                            <td>${productDTO.price}</td>
                            <td><input type="number" name="txtQuantity" id="quantity" value="${productDTO.quantity}"/></td>
                            <td>${productDTO.total}</td>
                        <input type="hidden" readonly="true" name="txtProductId" value="${dto.key}"/>
                        <input type="hidden" readonly="true" name="txtProductName" value="${productDTO.productName}"/>
                        <input type="hidden" readonly="true" name="txtPrice" value="${productDTO.price}"/>
                        <td><input type="submit" name="btnAction" value="Remove" onclick="return removeProduct();"/></td>
                        <td><input type="submit" name="btnAction" value="Update" onclick="return validateNumber();"/></td>
                        </tr>
                    </form>
                </c:forEach>
                <tr>
                    <td colspan="4"><b>Total price</b></td>
                    <td colspan="3">${cart.totalPrice} VND</td>
                </tr>
            </table>      
            <form action="CheckOut" method="Post">
                <c:if test="${empty user}">
                    You must login or input informations to check out.<br/>
                    Number phone: <input type="number" name="txtUserId" id="txtNumberPhone" value=""/><br/>
                    You name: <input type="text" name="txtFullname" value=""/><br/>
                </c:if>
                <c:if test="${not empty user}">
                    <input type="hidden" readonly="true" name="txtUserId" id="txtUserId" value="${user.userId}"/>
                    <input type="hidden" readonly="true" name="txtFullname" value="${user.fullname}"/>
                </c:if>    
                Shipping address: <input type="text" id="txtShippingAddr" name="txtShippingAddr" value="${user.address}"/><br/>
                <input type="submit" name="btnAction" value="Checkout by cash" onclick="return checkOut();">
                <input type="submit" name="btnAction" value="Checkout online" onclick="return checkOutOnline();">
            </form>
        </c:if>
        <c:set var="errors" value="${sessionScope.ERRORS}"/>
        <c:if test="${not empty errors}">
            <c:forEach var="dto" items="${errors}">
                <p style="color: red">${dto}</p><br/>
            </c:forEach>
            Please check you cart and check out again!!!
        </c:if>
        <c:if test="${empty cart.items}">
            You have no items.
        </c:if>   
    </body>
</html>
