package kpi.lab_3.model.dao.stub;

import jakarta.ejb.Singleton;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import kpi.lab_3.config.DaoLayerConfig;
import kpi.lab_3.model.dao.CategoryDao;
import kpi.lab_3.model.entity.Category;

@Singleton
public class CategoryDaoStub implements CategoryDao {

  private static Long NEXT_ID = 200L;

  private final List<Category> categories;

  public CategoryDaoStub() {
    categories = DaoLayerConfig.initCategoriesStub();
  }

  @Override
  public Optional<Category> findById(Long id) {
    return categories.stream()
        .filter(category -> category.getId().equals(id))
        .findFirst();
  }

  @Override
  public List<Category> findAll() {
    return categories;
  }

  @Override
  public List<Category> findAllRootCategories() {
    return categories.stream()
        .filter(category -> category.getParentCategory()  == null)
        .sorted((cat1, cat2) -> cat2.getId().intValue() - cat1.getId().intValue())
        .toList();
  }

  @Override
  public List<Category> findAllLeafCategories() {
    return categories.stream()
        .filter(Category::isLeaf)
        .sorted((cat1, cat2) -> cat2.getId().intValue() - cat1.getId().intValue())
        .toList();
  }

  @Override
  public List<Category> findAllNonLeafCategories() {
    return categories.stream()
        .filter(category -> !category.isLeaf())
        .sorted((cat1, cat2) -> cat2.getId().intValue() - cat1.getId().intValue())
        .toList();
  }

  @Override
  public boolean hasProductsById(Long id) {
    Optional<Category> category = findById(id);

    return category.isPresent() &&
        category.get().getChildCategories() != null &&
        category.get().getChildCategories().size() > 0;
  }

  @Override
  public Category save(Category category) {
    category.setId(getNextId());
    categories.add(category);

    if (category.getParentCategory() != null) {
      category.getParentCategory().getChildCategories().add(category);
    }

    return category;
  }

  @Override
  public boolean update(Category updatedCategory, Long id) {
    Optional<Category> categoryToUpdateOptional = findById(id);

    if (categoryToUpdateOptional.isPresent()) {
      Category categoryToUpdate = categoryToUpdateOptional.get();
      categoryToUpdate.setName(updatedCategory.getName());

      if (categoryToUpdate.isLeaf() && !updatedCategory.isLeaf()) {
        categoryToUpdate.setChildCategories(new HashSet<>());
      }
      categoryToUpdate.setLeaf(updatedCategory.isLeaf());

      if (categoryToUpdate.getParentCategory() != null) {
        categoryToUpdate.getParentCategory().getChildCategories()
            .removeIf(category -> category.getId().equals(categoryToUpdate.getId()));
      }

      categoryToUpdate.setParentCategory(updatedCategory.getParentCategory());

      if (categoryToUpdate.getParentCategory() != null) {
        categoryToUpdate.getParentCategory().getChildCategories().add(categoryToUpdate);
      }

      return true;
    } else {
      return false;
    }
  }

  @Override
  public boolean deleteById(Long id) {
    Optional<Category> categoryToRemove = findById(id);

    return categoryToRemove.map(categories::remove).orElse(false);
  }

  private static synchronized Long getNextId() {
    return NEXT_ID++;
  }

}
