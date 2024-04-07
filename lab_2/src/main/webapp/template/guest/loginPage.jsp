<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isELIgnored="false" %>

<html>
<head>
    <title>Login Page</title>

    <link rel="stylesheet" href="<c:url value="/static/css/styles.css" />">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>

<div id="login">
    <div class="container">
        <div id="login-row" class="row justify-content-center align-items-center mt-4">
            <div class="login-form mt-5 p-4 col-lg-6 col-10">
                <form action="" method="post" autocomplete="off">
                    <h3 class="text-center mt-4">Login</h3>

                    <c:if test="${requestScope.authenticationException}">
                        <p class="text-center error-message mt-3">Your credentials are incorrect</p>
                    </c:if>
                    <div>
                        <label for="username">Username:</label><br>
                        <input type="text" name="username" id="username" class="form-control">
                    </div>
                    <div>
                        <label for="password">Password:</label><br>
                        <input type="password" name="password" id="password" class="form-control">
                    </div>
                    <div class="text-center mt-3">
                        <button type="submit" class="btn btn-primary">Login</button>
                    </div>

                    <div class="text-center mt-4">
                        <a class="btn btn-info" href="catalog">Login as a Guest</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<%@include file="../partial/footer.jspf" %>

</body>
</html>
