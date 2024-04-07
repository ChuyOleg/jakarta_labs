package kpi.lab_2.model.dao;

import java.util.List;
import java.util.Optional;
import kpi.lab_2.model.Category;

public interface CategoryDao {

  Optional<Category> findById(Long id);

  List<Category> findAll();

  List<Category> findAllRootCategories();

  List<Category> findAllLeafCategories();

  List<Category> findAllNonLeafCategories();

  Category save(Category category);

  boolean update(Category updatedCategory, Long id);

  boolean deleteById(Long id);

}
