package kpi.lab_3.controller.admin.category;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import kpi.lab_3.controller.mapper.CategoryInfoMapper;
import kpi.lab_3.controller.util.JspFilePath;
import kpi.lab_3.controller.util.StringParser;
import kpi.lab_3.controller.util.UriPath;
import kpi.lab_3.controller.validator.CategoryValidator;
import kpi.lab_3.exception.category.CategoryIsLeafAndHasChildrenAtTheSameTimeException;
import kpi.lab_3.model.dto.CategoryDto;
import kpi.lab_3.model.entity.Category;
import kpi.lab_3.service.CategoryService;
import lombok.extern.log4j.Log4j2;

@Log4j2
@WebServlet(name = "AdminUpdateCategoryServlet", value = "/admin/categories/update")
public class AdminUpdateCategoryServlet extends HttpServlet {

  private static final String LEAF_AND_HAS_CHILDREN_SAME_TIME = "leafAndHasChildrenException";

  private static final String CATEGORY_ID_PARAM = "categoryId";
  private static final String NON_LEAF_CATEGORIES_ATTR = "nonLeafCategories";

  private static final String URL_ID_PARAMETER = "?categoryId=";
  private static final String URL_SUCCESS_PARAMETER = "&success";

  @EJB
  private CategoryService categoryService;
  @EJB
  private CategoryValidator categoryValidator;

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
      try {
        categoryService.update(categoryDto, categoryId);
        response.sendRedirect(UriPath.ADMIN_UPDATE_CATEGORY + URL_ID_PARAMETER + categoryId + URL_SUCCESS_PARAMETER);
        return;
      } catch (CategoryIsLeafAndHasChildrenAtTheSameTimeException e) {
        request.setAttribute(LEAF_AND_HAS_CHILDREN_SAME_TIME, true);
      }
    }

    CategoryInfoMapper.insertCategoryDtoIntoRequest(categoryDto, request);
    request.setAttribute(NON_LEAF_CATEGORIES_ATTR, categoryService.getParentOptionsById(categoryId));

    request.getRequestDispatcher(JspFilePath.ADMIN_UPDATE_CATEGORY).forward(request, response);
  }

}
