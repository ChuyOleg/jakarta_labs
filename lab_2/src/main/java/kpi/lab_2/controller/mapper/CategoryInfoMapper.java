package kpi.lab_2.controller.mapper;

import jakarta.servlet.http.HttpServletRequest;
import kpi.lab_2.controller.util.StringParser;
import kpi.lab_2.model.Category;
import kpi.lab_2.model.dto.CategoryDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CategoryInfoMapper {

  private static final String NAME = "name";
  private static final String PARENT_CATEGORY_ID = "parentCategoryId";
  private static final String PARENT_CATEGORY_NAME = "parentCategoryName";
  private static final String IS_LEAF = "isLeaf";

  private static final String CATEGORY_DTO_ATTR = "categoryDto";

  public static CategoryDto fetchCategoryFromRequest(HttpServletRequest req) {
    return CategoryDto.builder()
        .name(req.getParameter(NAME))
        .parentCategoryId(StringParser.toLong(req.getParameter(PARENT_CATEGORY_ID)))
        .parentCategoryName(req.getParameter(PARENT_CATEGORY_NAME))
        .isLeaf(StringParser.toBoolean(req.getParameter(IS_LEAF)))
        .build();
  }

  public static void insertCategoryIntoRequest(Category category, HttpServletRequest req) {
    var categoryDtoBuilder = CategoryDto.builder()
        .id(category.getId())
        .name(category.getName())
        .isLeaf(category.isLeaf());

    if (category.getParentCategory() != null) {
      categoryDtoBuilder = categoryDtoBuilder
          .parentCategoryId(category.getParentCategory().getId())
          .parentCategoryName(category.getParentCategory().getName());
    }

    insertCategoryDtoIntoRequest(categoryDtoBuilder.build(), req);
  }

  public void insertCategoryDtoIntoRequest(CategoryDto categoryDto, HttpServletRequest req) {
    req.setAttribute(CATEGORY_DTO_ATTR, categoryDto);
  }

}
