<%-- 
    Document   : viewAllCake
    Created on : Oct 18, 2020, 2:54:15 PM
    Author     : truongtn
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View all cake Page</title>
    </head>
    <body>
        <%@include file = "header.jsp" %>
        <br/>
        <br/>
        <form action="viewAllProduct" method="Post">
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
            <c:set var="categories" value="${sessionScope.CATEGORIES}"/>
            <c:set var="status" value="${sessionScope.STATUS}"/>
            <c:forEach var="dto" items="${result}">
                <div>
                    <form action="updateProduct" enctype="multipart/form-data" method="Post">
                        <b>Id: </b>${dto.productId}<br/>
                        <input type="hidden" readonly="true" name="txtProductId" value="${dto.productId}"/>
                        <b>Name: </b> <input type="text" name="txtProductName" value="${dto.productName}"/> <br/>
                        <b>Description: </b><textarea name="txtDescription">${dto.description}</textarea> <br/>
                        <b>Create Date: </b><input type="date" name="txtCreatedDate" value="${dto.createdDate}"/><br/>
                        <b>Expiration Date: </b><input type="date" name="txtExpirationDate" value="${dto.expirationDate}"/><br/>
                        <b>Quantity: </b><input type="number" name="txtQuantity" value="${dto.quantity}"/><br/>
                        <b>Price: </b><input type="number" name="txtPrice" value="${dto.price}"/> <br/>
                        <b>Category: </b>
                        <select name="txtCategory" value="${dto.categoryId}">
                            <c:forEach var="cateDTO" items="${categories}">
                                <option value="${cateDTO.categoryId}"<c:if test="${cateDTO.categoryId == dto.categoryId}">selected</c:if>>${cateDTO.categoryName}</option>
                            </c:forEach>
                        </select>  <br/>
                        
                        
                        <b>Status: </b>
                        <select name="txtStatusId" value="${dto.statusId}">
                            <c:forEach var="statusDTO" items="${status}">
                                <option value="${statusDTO.statusId}"<c:if test="${statusDTO.statusId == dto.statusId}">selected</c:if> >${statusDTO.statusName}</option>
                            </c:forEach>
                        </select><br/>
                        <b>Creator Id: </b>${dto.userId} <br/>
                        <input type="file"  accept="image/*" name="image" value="${dto.image}" id="file" onchange="loadFile(event)"><br/>
                        <c:if test="${not empty dto.image}">
                            <img id="output" src="${dto.image}" width="200" /><br/>
                        </c:if>
                        <input type="hidden" readonly="true" name="userId" value="${user.userId}"/>
                        <input type="submit" name="btnAction" value="Update"/>
                    </form>
                </div><br/><br/><br/><br/><br/><br/>
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
                
        <script>
            var loadFile = function (event) {
                var image = document.getElementById('output');
                image.src = URL.createObjectURL(event.target.files[0]);
                var imageElement = document.getElementById("file").files[0].name;
                var imageName = document.getElementById("imageId");
                imageName.value = imageElement;
            };
        </script>
    </body>
</html>
