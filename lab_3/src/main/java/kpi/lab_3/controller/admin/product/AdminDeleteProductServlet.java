package kpi.lab_3.controller.admin.product;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import kpi.lab_3.controller.util.StringParser;
import kpi.lab_3.controller.util.UriPath;
import kpi.lab_3.service.ProductService;
import lombok.extern.log4j.Log4j2;

@Log4j2
@WebServlet(name = "AdminDeleteProductServlet", value = "/admin/products/delete")
public class AdminDeleteProductServlet extends HttpServlet {

  private static final String PRODUCT_ID_PARAM = "productId";
  private static final String CATEGORY_ID_FILTER_PARAM = "categoryIdFilter";

  @EJB
  private ProductService productService;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    final Long productId = StringParser.toLong(request.getParameter(PRODUCT_ID_PARAM));

    if (productId == null) {
      log.warn("Product_ID is null, so product cannot be deleted");
    } else {
      productService.deleteById(productId);
    }

    StringBuilder uriToRedirectBuilder = new StringBuilder(UriPath.CATALOG);

    final Long categoryIdFilter = StringParser.toLong(request.getParameter(CATEGORY_ID_FILTER_PARAM));
    if (categoryIdFilter != null) {
      uriToRedirectBuilder.append("?categoryId=").append(categoryIdFilter);
    }

    response.sendRedirect(uriToRedirectBuilder.toString());
  }

}
