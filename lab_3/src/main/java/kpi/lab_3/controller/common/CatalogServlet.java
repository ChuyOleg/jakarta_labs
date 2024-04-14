package kpi.lab_3.controller.common;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import kpi.lab_3.controller.util.JspFilePath;
import kpi.lab_3.controller.util.StringParser;
import kpi.lab_3.controller.util.UriPath;
import kpi.lab_3.model.entity.Category;
import kpi.lab_3.model.entity.Product;
import kpi.lab_3.service.CategoryService;
import kpi.lab_3.service.ProductService;
import lombok.SneakyThrows;

@WebServlet(name = "CatalogServlet", value = "/catalog")
public class CatalogServlet extends HttpServlet {

  private static final String CATEGORY_ID_PARAM = "categoryId";

  private static final String ROOT_CATEGORIES_ATTR = "rootCategories";
  private static final String PRODUCTS_ATTR = "products";
  private static final String CURRENT_CATEGORY = "currentCategory";

  @EJB
  private CategoryService categoryService;
  @EJB
  private ProductService productService;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    List<Category> rootCategories = categoryService.getAllRootCategories();
    request.setAttribute(ROOT_CATEGORIES_ATTR, rootCategories);

    String categoryIdString = request.getParameter(CATEGORY_ID_PARAM);

    if (categoryIdString != null) {
      doGetProductsByCategoryId(categoryIdString, request, response);
    } else {
      doGetAllProducts(request, response);
    }
  }

  @SneakyThrows
  private void doGetAllProducts(HttpServletRequest request, HttpServletResponse response) {
    List<Product> products = productService.getAll();
    request.setAttribute(PRODUCTS_ATTR, products);
    request.getRequestDispatcher(JspFilePath.CATALOG).forward(request, response);
  }

  @SneakyThrows
  private void doGetProductsByCategoryId(
      String categoryIdString, HttpServletRequest request, HttpServletResponse response) {

    Long categoryId = StringParser.toLong(categoryIdString);

    if (categoryId != null) {
      Optional<Category> pickedCategoryOptional = categoryService.getById(categoryId);

      if (pickedCategoryOptional.isPresent()) {
        Category pickedCategory = pickedCategoryOptional.get();
        List<Product> products = productService.getAllByCategoryId(pickedCategory.getId());

        request.setAttribute(CURRENT_CATEGORY, pickedCategory);
        request.setAttribute(PRODUCTS_ATTR, products);
        request.getRequestDispatcher(JspFilePath.CATALOG).forward(request, response);
        return;
      }
    }

    response.sendRedirect(UriPath.CATALOG);
  }

}
