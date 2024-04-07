package kpi.lab_2.controller.validator;

import jakarta.servlet.http.HttpServletRequest;
import kpi.lab_2.exception.category.CategoryIsRootAndLeafAtTheSameTimeException;
import kpi.lab_2.model.dto.CategoryDto;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class CategoryValidator {

  private static final String ROOT_AND_LEAF_SAME_TIME = "rootAndLeafException";

  public boolean validate(CategoryDto categoryDto, HttpServletRequest req) {
    try {
      validateCategoryDtoIsNotRootAndLeafAtTheSameTime(categoryDto);
      return true;
    } catch (CategoryIsRootAndLeafAtTheSameTimeException e) {
      log.warn("Category is root and leaf at the same time.");
      req.setAttribute(ROOT_AND_LEAF_SAME_TIME, true);
    }

    return false;
  }

  private static void validateCategoryDtoIsNotRootAndLeafAtTheSameTime(CategoryDto categoryDto)
      throws CategoryIsRootAndLeafAtTheSameTimeException {

    if (categoryDto.getParentCategoryId() == null && categoryDto.isLeaf()) {
      throw new CategoryIsRootAndLeafAtTheSameTimeException();
    }
  }

}
