package kpi.lab_3.service.impl;

import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import kpi.lab_3.exception.category.CategoryIsLeafAndHasChildrenAtTheSameTimeException;
import kpi.lab_3.model.dao.CategoryDao;
import kpi.lab_3.model.dao.ProductDao;
import kpi.lab_3.model.dto.CategoryDto;
import kpi.lab_3.model.entity.Category;
import kpi.lab_3.service.CategoryService;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Singleton
public class CategoryServiceImpl implements CategoryService {

  @EJB
  private CategoryDao categoryDao;
  @EJB
  private ProductDao productDao;

  @Override
  public Optional<Category> getById(Long id) {
    return categoryDao.findById(id);
  }

  @Override
  public List<Category> getAll() {
    return categoryDao.findAll();
  }

  @Override
  public List<Category> getAllRootCategories() {
    return categoryDao.findAllRootCategories();
  }

  @Override
  public List<Category> getAllLeafCategories() {
    return categoryDao.findAllLeafCategories();
  }

  @Override
  public List<Category> getAllNonLeafCategories() {
    return categoryDao.findAllNonLeafCategories();
  }

  @Override
  public List<Category> getParentOptionsById(Long id) {
    Optional<Category> categoryOptional = getById(id);
    if (categoryOptional.isEmpty()) {
      log.warn("Category [ID={}] does not exist.", id);
      throw new IllegalArgumentException("Category is not found");
    }

    List<Category> nonLeafCategories = getAllNonLeafCategories();

    return nonLeafCategories.stream()
        .filter(possibleParent -> !possibleParent.getId().equals(categoryOptional.get().getId()))
        .filter(possibleChild -> !haveParentChildRelation(categoryOptional.get(), possibleChild))
        .toList();
  }

  @Override
  public void create(CategoryDto categoryDto) {
    Category category = new Category(categoryDto);

    if (categoryDto.getParentCategoryId() != null) {
      Optional<Category> parentCategory = categoryDao.findById(categoryDto.getParentCategoryId());

      if (parentCategory.isPresent()) {
        category.setParentCategory(parentCategory.get());
      } else {
        log.warn("Category [ID={}] does not exist.", categoryDto.getParentCategoryId());
        throw new IllegalArgumentException("Category is not found");
      }
    }

    Category savedCategory = categoryDao.save(category);

    log.info("Category ({}, [ID={}]) has been created.",
        savedCategory.getName(), savedCategory.getId());
  }

  @Override
  public void update(CategoryDto categoryDto, Long id)
      throws CategoryIsLeafAndHasChildrenAtTheSameTimeException {

    Category categoryNewVersion = new Category(categoryDto);

    if (categoryDto.isLeaf() && categoryDao.hasProductsById(id)) {
      log.warn("Category is leaf and has children at the same time.");
      throw new CategoryIsLeafAndHasChildrenAtTheSameTimeException();
    }

    if (categoryDto.getParentCategoryId() != null) {
      Optional<Category> parentOptional = categoryDao.findById(categoryDto.getParentCategoryId());

      if (parentOptional.isPresent()) {
        categoryNewVersion.setParentCategory(parentOptional.get());
      } else {
        log.warn("Category [id = {}] does not exist.", categoryDto.getParentCategoryId());
        throw new IllegalArgumentException("Category is not found");
      }
    }

    final boolean updateIsSuccess = categoryDao.update(categoryNewVersion, id);

    if (updateIsSuccess) {
      log.info("Category [ID={}] has been updated.", id);
    } else {
      log.warn("Category [ID={}] does not exist.", id);
    }
  }

  @Override
  public void deleteById(Long id) {
    Optional<Category> categoryToDeleteOptional = getById(id);

    if (categoryToDeleteOptional.isPresent()) {
      Category categoryToDelete = categoryToDeleteOptional.get();

      if (categoryToDelete.isLeaf()) {
        deleteLeafCategory(categoryToDelete);
      } else {
        deleteNonLeafCategory(categoryToDelete);
      }

      Category parentCategory = categoryToDelete.getParentCategory();
      if (parentCategory != null) {
        parentCategory.getChildCategories().remove(categoryToDelete);
        log.info("Untied Category [id = {}] from parent", id);
      }

    } else {
      log.warn("Category [id = {}] does not exist.", id);
    }
  }

  private void deleteLeafCategory(Category leafCategory) {
    Integer deletedProductsCount = productDao.deleteByCategoryId(leafCategory.getId());
    log.info("{} products have been deleted before removing Category [id = {}]",
        deletedProductsCount, leafCategory.getId());

    final boolean deleteIsSuccess = categoryDao.deleteById(leafCategory.getId());

    if (deleteIsSuccess) {
      log.info("Category [id = {}] has been deleted.", leafCategory.getId());
    } else {
      log.warn("Category [id = {}] has not been deleted.", leafCategory.getId());
    }
  }

  private void deleteNonLeafCategory(Category nonLeafCategory) {
    nonLeafCategory.getChildCategories()
        .forEach(childCategory -> {
          if (childCategory.isLeaf()) {
            deleteLeafCategory(childCategory);
          } else {
            deleteNonLeafCategory(childCategory);
          }
        });

    final boolean deleteIsSuccess = categoryDao.deleteById(nonLeafCategory.getId());

    if (deleteIsSuccess) {
      log.info("Category [id = {}] has been deleted.", nonLeafCategory.getId());
    } else {
      log.warn("Category [id = {}] has not been deleted.", nonLeafCategory.getId());
    }
  }

  private boolean haveParentChildRelation(Category possibleParent, Category possibleChild) {
    if (possibleParent.isLeaf()) {
      return false;
    }

    Set<Category> children = possibleParent.getChildCategories();

    if (children.contains(possibleChild)) {
      return true;
    }

    return children.stream()
        .anyMatch(child -> haveParentChildRelation(child, possibleChild));
  }

}
