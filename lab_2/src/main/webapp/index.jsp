<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Simple JSP Page</title>
</head>
<body>

<h2>Welcome to our Online Store</h2>

<c:if test="${5 + 2 > 6}">
    <p>Hello</p>
</c:if>

<c:choose>
    <c:when test="${empty user}">
        <p>Hello, Guest! Please sign in.</p>
    </c:when>
    <c:otherwise>
        <p>Hello, ${user}! Welcome back.</p>
    </c:otherwise>
</c:choose>

<ul>
    <c:forEach var="item" items="${items}">
        <li>${item}</li>
    </c:forEach>
</ul>


</body>
</html>
