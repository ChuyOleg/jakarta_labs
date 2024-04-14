package kpi.lab_3.controller.admin.product;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import kpi.lab_3.controller.mapper.ProductInfoMapper;
import kpi.lab_3.controller.util.JspFilePath;
import kpi.lab_3.controller.util.StringParser;
import kpi.lab_3.controller.util.UriPath;
import kpi.lab_3.model.dto.ProductDto;
import kpi.lab_3.model.entity.Product;
import kpi.lab_3.service.CategoryService;
import kpi.lab_3.service.ProductService;

@WebServlet(name = "AdminUpdateProductServlet", value = "/admin/products/update")
public class AdminUpdateProductServlet extends HttpServlet {

  private static final String PRODUCT_ID_PARAM = "productId";
  private static final String LEAF_CATEGORIES_ATTR = "leafCategories";

  private static final String URL_ID_PARAMETER = "?productId=";
  private static final String URL_SUCCESS_PARAMETER = "&success";

  @EJB
  private ProductService productService;
  @EJB
  private CategoryService categoryService;

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
