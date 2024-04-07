package kpi.lab_2.controller.admin.category;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import kpi.lab_2.config.ServiceLayerConfig;
import kpi.lab_2.controller.util.StringParser;
import kpi.lab_2.controller.util.UriPath;
import kpi.lab_2.service.CategoryService;
import lombok.extern.log4j.Log4j2;

@Log4j2
@WebServlet(name = "AdminDeleteCategoryServlet", value = "/admin/categories/delete")
public class AdminDeleteCategoryServlet extends HttpServlet {

  private static final String CATEGORY_ID_PARAM = "categoryId";

  private CategoryService categoryService;

  @Override
  public void init() throws ServletException {
    super.init();

    this.categoryService = ServiceLayerConfig.buildCategoryServiceStub();
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    final Long categoryId = StringParser.toLong(request.getParameter(CATEGORY_ID_PARAM));

    if (categoryId == null) {
      log.warn("Category_ID is null, so category cannot be deleted");
    } else {
      categoryService.deleteById(categoryId);
    }

    response.sendRedirect(UriPath.CATALOG);
  }

}
