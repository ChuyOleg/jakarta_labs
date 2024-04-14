package kpi.lab_3.controller.admin.category;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import kpi.lab_3.controller.mapper.CategoryInfoMapper;
import kpi.lab_3.controller.util.JspFilePath;
import kpi.lab_3.controller.util.UriPath;
import kpi.lab_3.controller.validator.CategoryValidator;
import kpi.lab_3.model.dto.CategoryDto;
import kpi.lab_3.service.CategoryService;

@WebServlet(name = "AdminCreateCategoryServlet", value = "/admin/categories")
public class AdminCreateCategoryServlet extends HttpServlet {

  private static final String NON_LEAF_CATEGORIES_ATTR = "nonLeafCategories";

  @EJB
  private CategoryService categoryService;
  @EJB
  private CategoryValidator categoryValidator;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    request.setAttribute(NON_LEAF_CATEGORIES_ATTR, categoryService.getAllNonLeafCategories());

    request.getRequestDispatcher(JspFilePath.ADMIN_CREATE_CATEGORY).forward(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    CategoryDto categoryDto = CategoryInfoMapper.fetchCategoryFromRequest(request);

    final boolean categoryIsValid = categoryValidator.validate(categoryDto, request);

    if (categoryIsValid) {
      categoryService.create(categoryDto);
      response.sendRedirect(UriPath.CATALOG);
      return;
    }

    CategoryInfoMapper.insertCategoryDtoIntoRequest(categoryDto, request);
    request.setAttribute(NON_LEAF_CATEGORIES_ATTR, categoryService.getAllNonLeafCategories());

    request.getRequestDispatcher(JspFilePath.ADMIN_CREATE_CATEGORY).forward(request, response);
  }
}
