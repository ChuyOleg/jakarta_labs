package kpi.lab_3.model.dao;

import java.util.List;
import java.util.Optional;
import kpi.lab_3.model.entity.Category;

public interface CategoryDao {

  Optional<Category> findById(Long id);

  List<Category> findAll();

  List<Category> findAllRootCategories();

  List<Category> findAllLeafCategories();

  List<Category> findAllNonLeafCategories();

  boolean hasProductsById(Long id);

  Category save(Category category);

  boolean update(Category updatedCategory, Long id);

  boolean deleteById(Long id);

}
