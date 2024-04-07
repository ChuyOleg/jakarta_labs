package kpi.lab_2.controller.admin.product;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import kpi.lab_2.config.ServiceLayerConfig;
import kpi.lab_2.controller.mapper.ProductInfoMapper;
import kpi.lab_2.controller.util.JspFilePath;
import kpi.lab_2.controller.util.UriPath;
import kpi.lab_2.model.dto.ProductDto;
import kpi.lab_2.service.CategoryService;
import kpi.lab_2.service.ProductService;

@WebServlet(name = "AdminCreateProductServlet", value = "/admin/products")
public class AdminCreateProductServlet extends HttpServlet {

  private static final String LEAF_CATEGORIES_ATTR = "leafCategories";

  private ProductService productService;
  private CategoryService categoryService;

  @Override
  public void init() throws ServletException {
    super.init();

    productService = ServiceLayerConfig.buildProductServiceStub();
    categoryService = ServiceLayerConfig.buildCategoryServiceStub();
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    request.setAttribute(LEAF_CATEGORIES_ATTR, categoryService.getAllLeafCategories());

    request.getRequestDispatcher(JspFilePath.ADMIN_CREATE_PRODUCT).forward(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    ProductDto productDto = ProductInfoMapper.fetchProductFromRequest(request);

    productService.create(productDto);
    response.sendRedirect(UriPath.CATALOG);
  }
}
