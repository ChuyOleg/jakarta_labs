<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="col-5 border border-2 rounded text-center mb-4">
  <p class="mt-1">${product.name}</p>
  <p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled</p>

  <div class="text-center mt-2 mb-3">
    <a href="products/${product.id}" class="btn btn-outline-primary">Details</a>
  </div>

  <c:if test="${sessionScope.role.equals('ADMIN')}">
    <div class="row justify-content-around">
      <form class="m-1" method="get" action="admin/products/update">
        <input type="text" name="productId" value="${product.id}" hidden>

        <button type="submit" class="btn btn-outline-warning fw-bold w-100">Update</button>
      </form>

      <form class="m-1" method="post" action="admin/products/delete">
        <input type="text" name="productId" value="${product.id}" hidden>
        <input type="hidden" name="categoryIdFilter" value="<%= request.getParameter("categoryId") %>">

        <button type="submit" class="btn btn-outline-danger fw-bold w-100">Delete</button>
      </form>
    </div>
  </c:if>

</div>