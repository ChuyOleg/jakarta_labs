package kpi.lab_3.controller.common;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import kpi.lab_3.controller.util.JspFilePath;
import kpi.lab_3.controller.util.StringParser;
import kpi.lab_3.controller.util.UriPath;
import kpi.lab_3.model.entity.Product;
import kpi.lab_3.service.ProductService;
import lombok.extern.log4j.Log4j2;

@Log4j2
@WebServlet(name = "ProductServlet", value = "/products/*")
public class ProductServlet extends HttpServlet {

  private static final String PRODUCT_ATTRIBUTE = "product";

  @EJB
  private ProductService productService;

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
