package kpi.lab_3.service;

import java.util.List;
import java.util.Optional;
import kpi.lab_3.exception.category.CategoryIsLeafAndHasChildrenAtTheSameTimeException;
import kpi.lab_3.model.dto.CategoryDto;
import kpi.lab_3.model.entity.Category;

public interface CategoryService {

  List<Category> getAll();

  List<Category> getAllRootCategories();

  List<Category> getAllLeafCategories();

  List<Category> getAllNonLeafCategories();

  List<Category> getParentOptionsById(Long id);

  Optional<Category> getById(Long id);
  void create(CategoryDto categoryDto);

  void update(CategoryDto categoryDto, Long id)
      throws CategoryIsLeafAndHasChildrenAtTheSameTimeException;

  void deleteById(Long id);

}
