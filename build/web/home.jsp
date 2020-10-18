<%-- 
    Document   : home
    Created on : Oct 7, 2020, 9:26:41 PM
    Author     : truongtn
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="./js/validate.js"></script>
        <title>Home Page</title>
    </head>
    <body>
        <%@include file = "header.jsp" %>
        <br/>
        <br/>
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
        <c:set var="searchValue" value="${sessionScope.SEARCH_VALUE.searchValue}"/>
        <c:set var="result" value="${sessionScope.PRODUCTS}"/>
        <c:if test="${not empty result}">
            <c:forEach var="dto" items="${result}">
                <div>
                    <b>Name: </b> ${dto.productName} <br/>
                    <b>Description: </b> ${dto.description} <br/>
                    <b>Create Date: </b>${dto.createdDate} <br/>
                    <b>Expiration Date: </b>${dto.expirationDate} <br/>
                    <b>Price: </b>${dto.price} <br/>
                    <b>Category: </b>${dto.categoryStr} <br/>
                    <c:if test="${not empty dto.image}">
                        <image src="${dto.image}" width="200px"><br/>
                    </c:if>
                    <form action="addToCart" method="Post">
                        <input type="hidden" readonly="true" name="txtProductId" value="${dto.productId}"/>
                        <input type="hidden" readonly="true" name="txtProductName" value="${dto.productName}"/>
                        Quantity: <input type="number" id="quantity" name="txtQuantity" value="1"/>
                        <input type="hidden" readonly="true" name="txtPrice" value="${dto.price}"/>
                        <p id="quantityNotifie" style="color: blue;"></p>
                        <input type="submit" name="btnAction" value="Add to cart" onclick="return validateNumber();"/>
                    </form>
                    <br/>
                    <br/>
                    <br/>
                </div>
            </c:forEach>


            <c:forEach var="i" begin="1" end="${requestScope.TOTAL_PAGE}">
                <c:url var="pageNumberUrl" value="paging">
                    <c:param name="btnAction" value="paging"/>                    
                    <c:param name="txtNumberPage" value="${i}"/>
                </c:url> 
                <a href="${pageNumberUrl}">${i}</a>
            </c:forEach>
        </c:if>

        <c:if test="${empty result}">
            <font color="red">
            No records.
            </font><br/>
            <c:if test="${empty searchValue}">
                <font color="red">
                Please input search value.
                </font><br/>
            </c:if>
        </c:if>
    </body>
</html>
