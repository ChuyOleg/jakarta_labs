package kpi.lab_3.controller.admin.product;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import kpi.lab_3.controller.mapper.ProductInfoMapper;
import kpi.lab_3.controller.util.JspFilePath;
import kpi.lab_3.controller.util.UriPath;
import kpi.lab_3.model.dto.ProductDto;
import kpi.lab_3.service.CategoryService;
import kpi.lab_3.service.ProductService;

@WebServlet(name = "AdminCreateProductServlet", value = "/admin/products")
public class AdminCreateProductServlet extends HttpServlet {

  private static final String LEAF_CATEGORIES_ATTR = "leafCategories";

  @EJB
  private ProductService productService;
  @EJB
  private CategoryService categoryService;

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
