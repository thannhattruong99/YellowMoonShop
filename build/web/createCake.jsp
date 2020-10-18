<%-- 
    Document   : createCake
    Created on : Oct 16, 2020, 1:41:07 PM
    Author     : truongtn
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Cake Page</title>
    </head>
    <body>
        <%@include file = "header.jsp" %>
        <form action="createCake" enctype="multipart/form-data" method="Post">
            <c:set var="errors" value="${requestScope.CREATE_ERROR}"/>
            Product name: <input type="text" name="txtProductName" value="${param.txtProductName}"/><br/>
            <c:if test="${not empty errors.productNameLengErr}">
                <font color="red">
                ${errors.productNameLengErr}
                </font><br/>
            </c:if>
            <c:if test="${not empty errors.productNameDuplicateErr}">
                <font color="red">
                ${errors.productNameDuplicateErr}
                </font><br/>
            </c:if>
            Category: <input type="text" name="txtCategory" value="${param.txtCategory}"/><br/>
            <c:if test="${not empty errors.categoryLengthErr}">
                ${errors.categoryLengthErr}
            </c:if>
            Price: <input type="number" name="txtPrice" value="${param.txtPrice}"/><br/>
            <c:if test="${not empty errors.priceTypeErr}">
                <font color="red">
                ${errors.priceTypeErr}
                </font><br/>
            </c:if>
            Quantity: <input type="number" name="txtQuantity" value="${param.txtQuantity}"/><br/>
            <c:if test="${not empty errors.quantityTypeErr}">
                <font color="red">
                ${errors.quantityTypeErr}
                </font><br/>
            </c:if>
            Description: <textarea name="txtDescription">${param.txtDescription}</textarea><br/>
            <c:if test="${not empty errors.descriptionLengthErr}">
                <font color="red">
                ${errors.descriptionLengthErr}
                </font><br/>
            </c:if>
            Create Date: <input type="date" name="txtCreatedDate"/><br/>
            <c:if test="${not empty errors.createDateErr}">
                <font color="red">
                ${errors.createDateErr}
                </font><br/>
            </c:if>
            Expiration Date: <input type="date" name="txtExpirationDate"/><br/>
            <c:if test="${not empty errors.expirationDateErr}">
                <font color="red">
                    ${errors.expirationDateErr}
                </font><br/>
            </c:if>
                <input type="hidden" readonly="true" name="userId" value="${user.userId}"/>
            <p><input type="file"  accept="image/*" name="image" id="file" onchange="loadFile(event)"></p>
            <p><img id="output" width="200" /></p>
            <input type="submit" name="btnAction" value="Create Cake"/>
            <input type="reset" value="Reset"/>
        </form>
        
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
