package kpi.lab_2.controller.common;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.Optional;
import kpi.lab_2.config.ServiceLayerConfig;
import kpi.lab_2.controller.util.JspFilePath;
import kpi.lab_2.controller.util.StringParser;
import kpi.lab_2.controller.util.UriPath;
import kpi.lab_2.model.Product;
import kpi.lab_2.service.ProductService;
import lombok.extern.log4j.Log4j2;

@Log4j2
@WebServlet(name = "ProductServlet", value = "/products/*")
public class ProductServlet extends HttpServlet {

  private static final String PRODUCT_ATTRIBUTE = "product";

  private ProductService productService;

  @Override
  public void init() throws ServletException {
    super.init();

    this.productService = ServiceLayerConfig.buildProductServiceStub();
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Long productId = extractProductIdFromPathInfo(request.getPathInfo());

    if (productId != null) {
      Optional<Product> productOptional = productService.getById(productId);

      if (productOptional.isPresent()) {
        request.setAttribute(PRODUCT_ATTRIBUTE, productOptional.get());

        request.getRequestDispatcher(JspFilePath.PRODUCT).forward(request, response);
        return;
      }
    }

    log.debug("Path [{}], redirecting to Catalog page.", request.getPathInfo());
    response.sendRedirect(UriPath.CATALOG);
  }

  private Long extractProductIdFromPathInfo(String pathInfo) {
    if (pathInfo != null) {
      String productIdString = pathInfo.substring(pathInfo.indexOf("/") + 1);

      return StringParser.toLong(productIdString);
    }

    return null;
  }

}
