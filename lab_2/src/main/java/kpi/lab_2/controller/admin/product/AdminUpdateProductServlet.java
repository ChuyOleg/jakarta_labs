package kpi.lab_2.controller.admin.product;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.Optional;
import kpi.lab_2.config.ServiceLayerConfig;
import kpi.lab_2.controller.mapper.ProductInfoMapper;
import kpi.lab_2.controller.util.JspFilePath;
import kpi.lab_2.controller.util.StringParser;
import kpi.lab_2.controller.util.UriPath;
import kpi.lab_2.model.Product;
import kpi.lab_2.model.dto.ProductDto;
import kpi.lab_2.service.CategoryService;
import kpi.lab_2.service.ProductService;

@WebServlet(name = "AdminUpdateProductServlet", value = "/admin/products/update")
public class AdminUpdateProductServlet extends HttpServlet {

  private static final String PRODUCT_ID_PARAM = "productId";
  private static final String LEAF_CATEGORIES_ATTR = "leafCategories";

  private static final String URL_ID_PARAMETER = "?productId=";
  private static final String URL_SUCCESS_PARAMETER = "&success";

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

    Long productId = StringParser.toLong(request.getParameter(PRODUCT_ID_PARAM));

    if (productId != null) {
      Optional<Product> productOptional = productService.getById(productId);

      if (productOptional.isPresent()) {
        ProductInfoMapper.insertProductIntoRequest(productOptional.get(), request);
        request.setAttribute(LEAF_CATEGORIES_ATTR, categoryService.getAllLeafCategories());

        request.getRequestDispatcher(JspFilePath.ADMIN_UPDATE_PRODUCT).forward(request, response);
        return;
      }
    }

    response.sendRedirect(UriPath.CATALOG);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Long productId = StringParser.toLong(request.getParameter(PRODUCT_ID_PARAM));

    if (productId != null) {
      ProductDto productDto = ProductInfoMapper.fetchProductFromRequest(request);
      productService.update(productDto, productId);
      response.sendRedirect(UriPath.ADMIN_UPDATE_PRODUCT + URL_ID_PARAMETER + productId + URL_SUCCESS_PARAMETER);
      return;
    }

    response.sendRedirect(UriPath.CATALOG);
  }

}
