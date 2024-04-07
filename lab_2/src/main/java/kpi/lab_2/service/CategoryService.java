package kpi.lab_2.service;

import java.util.List;
import java.util.Optional;
import kpi.lab_2.model.Category;
import kpi.lab_2.model.dto.CategoryDto;

public interface CategoryService {

  List<Category> getAll();

  List<Category> getAllRootCategories();

  List<Category> getAllLeafCategories();

  List<Category> getAllNonLeafCategories();

  List<Category> getParentOptionsById(Long id);

  Optional<Category> getById(Long id);
  void create(CategoryDto categoryDto);

  void update(CategoryDto categoryDto, Long id);

  void deleteById(Long id);

}
