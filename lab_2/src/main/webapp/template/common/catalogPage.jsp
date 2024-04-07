<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tf" %>

<html>
<head>
    <title>Catalog Page</title>

    <link rel="stylesheet" href="<c:url value="/static/css/styles.css" />">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>

<tf:chooseHeader role="${sessionScope.role}"/>

<div id="catalog">
    <div class="container">
        <h2 class="text-center mb-4 mt-3">Catalog</h2>

        <div class="row mb-3 border border-2 border-dark rounded rounded-3">

            <div class="col border border-2 border-dark">
                <h4 class="text-center mb-3 mt-2">Categories</h4>

                <c:forEach var="category" items="${requestScope.rootCategories}">
                    <c:set var="category" value="${category}" scope="request"/>
                    <jsp:include page="categoryWindow.jsp">
                        <jsp:param name="isRoot" value="true"/>
                        <jsp:param name="leftPadding" value="${0}"/>
                    </jsp:include>
                    <hr>
                </c:forEach>
            </div>

            <div class="col-8 border border-2 border-dark">
                <h4 class="text-center mb-3 mt-2">Products</h4>

                <div class="row justify-content-around">
                    <c:forEach var="product" items="${requestScope.products}">
                        <c:set var="product" value="${product}" scope="request"/>
                        <jsp:include page="productWindow.jsp"/>
                    </c:forEach>
                </div>

            </div>

        </div>
    </div>
</div>

<%@include file="../partial/footer.jspf" %>

</body>
</html>
