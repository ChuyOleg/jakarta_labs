<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="role" required="true" %>

<c:choose>

    <c:when test="${role.equals('UNKNOWN')}">
        <%@include file="../../template/partial/guestHeader.jspf" %>
    </c:when>

    <c:when test="${role.equals('ADMIN')}">
        <%@include file="../../template/partial/adminHeader.jspf" %>
    </c:when>

</c:choose>