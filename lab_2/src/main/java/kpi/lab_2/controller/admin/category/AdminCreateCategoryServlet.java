package kpi.lab_2.controller.admin.category;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import kpi.lab_2.config.ServiceLayerConfig;
import kpi.lab_2.controller.mapper.CategoryInfoMapper;
import kpi.lab_2.controller.util.JspFilePath;
import kpi.lab_2.controller.util.UriPath;
import kpi.lab_2.controller.validator.CategoryValidator;
import kpi.lab_2.model.dto.CategoryDto;
import kpi.lab_2.service.CategoryService;

@WebServlet(name = "AdminCreateCategoryServlet", value = "/admin/categories")
public class AdminCreateCategoryServlet extends HttpServlet {

  private static final String NON_LEAF_CATEGORIES_ATTR = "nonLeafCategories";

  private CategoryService categoryService;
  private CategoryValidator categoryValidator;

  @Override
  public void init() throws ServletException {
    super.init();

    categoryService = ServiceLayerConfig.buildCategoryServiceStub();
    categoryValidator = ServiceLayerConfig.buildCategoryValidator();
  }

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
