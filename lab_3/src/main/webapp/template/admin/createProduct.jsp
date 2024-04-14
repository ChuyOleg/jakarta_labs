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

<div id="create-product">
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-12 col-lg-6 mt-5 mb-4 p-3 justify-content-center product-creation-block">
        <form id="create-product-form" autocomplete="off" class="form" action="" method="post">
          <h3 class="text-center mt-2">New product</h3>

          <div class="row justify-content-center mt-4">
            <div class="text-center col-10">
              <label class="mb-1" for="name">Name:</label><br>
              <input type="text" value="${requestScope.productDto.name}" required
                     name="name" maxlength="64" id="name" class="form-control">
            </div>
          </div>

          <div class="row justify-content-center mt-2">
            <div class="col-6 text-center">
              <label class="mb-1" for="category">Category:</label><br>
              <select class="form-select" name="categoryId" id="category" required>
                <option value="" hidden></option>

                  <c:forEach var="category" items="${requestScope.leafCategories}">
                      <c:if test="${requestScope.productDto.categoryId.equals(category.id)}" >
                          <option selected value="${category.id}">${category.name}</option>
                      </c:if>
                      <c:if test="${!requestScope.productDto.categoryId.equals(category.id)}" >
                          <option value="${category.id}">${category.name}</option>
                      </c:if>
                  </c:forEach>
              </select>
            </div>
          </div>

          <div class="text-center mt-4">
            <button type="submit" class="btn btn-primary">Create</button>
          </div>

        </form>
      </div>
    </div>
  </div>
</div>

<%@include file="../partial/footer.jspf" %>

</body>
</html>
