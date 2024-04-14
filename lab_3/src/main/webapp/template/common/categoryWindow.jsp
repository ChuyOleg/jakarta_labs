<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${param.isRoot == 'true'}">
    <div class="row align-content-between">
        <div class="col h5 mb-3 fw-bold"><c:out value="${category.name}"/></div>

        <c:if test="${sessionScope.role.equals('ADMIN')}">
            <form class="col-2" method="get" action="/lab_3-1.0-SNAPSHOT/admin/categories/update">
                <input type="text" name="categoryId" value="${category.id}" hidden>
                <button class="text-center btn-sm btn-outline-warning" type="submit">e</button>
            </form>

            <form class="col-2" method="post" action="/lab_3-1.0-SNAPSHOT/admin/categories/delete">
                <input type="text" name="categoryId" value="${category.id}" hidden>
                <button class="text-center btn-sm btn-outline-danger" type="submit">x</button>
            </form>
        </c:if>
    </div>
</c:if>
<c:if test="${param.isRoot != 'true'}">

    <c:if test="${category.isLeaf()}">
        <div class="row align-content-between">
            <form class="col" style="padding-left: ${param.leftPadding}px" method="get" action="catalog">
                <input type="text" name="categoryId" value="${category.id}" hidden>
                <c:if test="${currentCategory.id == category.id}">
                    <button class="btn btn-success" type="submit">${category.name}</button>
                </c:if>
                <c:if test="${currentCategory.id != category.id}">
                    <button class="btn btn-outline-info" type="submit">${category.name}</button>
                </c:if>
            </form>

            <c:if test="${sessionScope.role.equals('ADMIN')}">
                <form class="col-2" method="get" action="/lab_3-1.0-SNAPSHOT/admin/categories/update">
                    <input type="text" name="categoryId" value="${category.id}" hidden>
                    <button class="text-center btn-sm btn-outline-warning" type="submit">e</button>
                </form>

                <form class="col-2" method="post" action="/lab_3-1.0-SNAPSHOT/admin/categories/delete">
                    <input type="text" name="categoryId" value="${category.id}" hidden>
                    <button class="text-center btn-sm btn-outline-danger" type="submit">x</button>
                </form>
            </c:if>
        </div>
    </c:if>
    <c:if test="${!category.isLeaf()}">
        <div class="row align-content-between">
            <div class="col h6 mb-3 fw-bold" style="padding-left: ${param.leftPadding}px">
                <c:out value="${category.name}"/>
            </div>

            <c:if test="${sessionScope.role.equals('ADMIN')}">
                <form class="col-2" method="get" action="/lab_3-1.0-SNAPSHOT/admin/categories/update">
                    <input type="text" name="categoryId" value="${category.id}" hidden>
                    <button class="text-center btn-sm btn-outline-warning" type="submit">e</button>
                </form>

                <form class="col-2" method="post" action="/lab_3-1.0-SNAPSHOT/admin/categories/delete">
                    <input type="text" name="categoryId" value="${category.id}" hidden>
                    <button class="text-center btn-sm btn-outline-danger" type="submit">x</button>
                </form>
            </c:if>
        </div>
    </c:if>

</c:if>

<c:forEach var="childCategory" items="${category.childCategories}">
    <c:set var="category" value="${childCategory}" scope="request"/>
    <jsp:include page="categoryWindow.jsp">
        <jsp:param name="isRoot" value="false"/>
        <jsp:param name="leftPadding" value="${param.leftPadding + 20}"/>
    </jsp:include>
</c:forEach>
