<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tf" %>

<html>
<head>
    <title>Product Page</title>

    <link rel="stylesheet" href="<c:url value="/static/css/styles.css" />">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>

<tf:chooseHeader role="${sessionScope.role}"/>


<div id="productPage">
    <div class="container">
        <div class="row mt-5">
            <div class="col-md-6 mx-auto text-center">
                <h3>${product.name}</h3>
            </div>
        </div>

        <div class="row">
            <div class="col-md-6 mx-auto text-center">
                <p class="fw-bold">${product.category.name}</p>
            </div>
        </div>

        <div class="row">
            <div class="col-md-6 mx-auto text-center">
                <p>Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of "de Finibus Bonorum et Malorum" (The Extremes of Good and Evil) by Cicero, written in 45 BC. </p>
            </div>
        </div>

    </div>
</div>

<%@include file="../partial/footer.jspf" %>

</body>
</html>
