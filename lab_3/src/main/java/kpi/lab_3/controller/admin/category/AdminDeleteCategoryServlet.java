package kpi.lab_3.controller.admin.category;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import kpi.lab_3.controller.util.StringParser;
import kpi.lab_3.controller.util.UriPath;
import kpi.lab_3.service.CategoryService;
import lombok.extern.log4j.Log4j2;

@Log4j2
@WebServlet(name = "AdminDeleteCategoryServlet", value = "/admin/categories/delete")
public class AdminDeleteCategoryServlet extends HttpServlet {

  private static final String CATEGORY_ID_PARAM = "categoryId";

  @EJB
  private CategoryService categoryService;

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
