package kpi.lab_2.controller.admin.category;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.Optional;
import kpi.lab_2.config.ServiceLayerConfig;
import kpi.lab_2.controller.mapper.CategoryInfoMapper;
import kpi.lab_2.controller.util.JspFilePath;
import kpi.lab_2.controller.util.StringParser;
import kpi.lab_2.controller.util.UriPath;
import kpi.lab_2.controller.validator.CategoryValidator;
import kpi.lab_2.model.Category;
import kpi.lab_2.model.dto.CategoryDto;
import kpi.lab_2.service.CategoryService;
import lombok.extern.log4j.Log4j2;

@Log4j2
@WebServlet(name = "AdminUpdateCategoryServlet", value = "/admin/categories/update")
public class AdminUpdateCategoryServlet extends HttpServlet {

  private static final String CATEGORY_ID_PARAM = "categoryId";
  private static final String NON_LEAF_CATEGORIES_ATTR = "nonLeafCategories";

  private static final String URL_ID_PARAMETER = "?categoryId=";
  private static final String URL_SUCCESS_PARAMETER = "&success";

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

    Long categoryId = StringParser.toLong(request.getParameter(CATEGORY_ID_PARAM));

    if (categoryId != null) {
      Optional<Category> categoryOptional = categoryService.getById(categoryId);

      if (categoryOptional.isPresent()) {
        CategoryInfoMapper.insertCategoryIntoRequest(categoryOptional.get(), request);
        request.setAttribute(NON_LEAF_CATEGORIES_ATTR, categoryService.getParentOptionsById(categoryId));

        request.getRequestDispatcher(JspFilePath.ADMIN_UPDATE_CATEGORY).forward(request, response);
        return;
      }
    }

    response.sendRedirect(UriPath.CATALOG);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Long categoryId = StringParser.toLong(request.getParameter(CATEGORY_ID_PARAM));

    if (categoryId == null) {
      response.sendRedirect(UriPath.CATALOG);
    }

    CategoryDto categoryDto = CategoryInfoMapper.fetchCategoryFromRequest(request);

    final boolean categoryIsValid = categoryValidator.validate(categoryDto, request);

    if (categoryIsValid) {
      categoryService.update(categoryDto, categoryId);
      response.sendRedirect(UriPath.ADMIN_UPDATE_CATEGORY + URL_ID_PARAMETER + categoryId + URL_SUCCESS_PARAMETER);
      return;
    }

    CategoryInfoMapper.insertCategoryDtoIntoRequest(categoryDto, request);
    request.setAttribute(NON_LEAF_CATEGORIES_ATTR, categoryService.getParentOptionsById(categoryId));

    request.getRequestDispatcher(JspFilePath.ADMIN_UPDATE_CATEGORY).forward(request, response);
  }

}
