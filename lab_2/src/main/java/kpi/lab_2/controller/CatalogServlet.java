package kpi.lab_2.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import kpi.lab_2.config.ServiceLayerConfig;
import kpi.lab_2.controller.util.JspFilePath;
import kpi.lab_2.controller.util.StringParser;
import kpi.lab_2.controller.util.UriPath;
import kpi.lab_2.model.Category;
import kpi.lab_2.model.Product;
import kpi.lab_2.service.CategoryService;
import kpi.lab_2.service.ProductService;
import lombok.SneakyThrows;

@WebServlet(name = "CatalogServlet", value = "/catalog")
public class CatalogServlet extends HttpServlet {

  private static final String CATEGORY_ID_PARAM = "categoryId";

  private static final String ROOT_CATEGORIES_ATTR = "rootCategories";
  private static final String PRODUCTS_ATTR = "products";
  private static final String CURRENT_CATEGORY = "currentCategory";

  private CategoryService categoryService;
  private ProductService productService;

  @Override
  public void init() throws ServletException {
    super.init();

    this.categoryService = ServiceLayerConfig.buildCategoryServiceStub();
    this.productService = ServiceLayerConfig.buildProductServiceStub();
  }

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
