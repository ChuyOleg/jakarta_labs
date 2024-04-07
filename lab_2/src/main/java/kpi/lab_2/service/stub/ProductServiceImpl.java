package kpi.lab_2.service.stub;

import java.util.List;
import java.util.Optional;
import kpi.lab_2.model.Category;
import kpi.lab_2.model.Product;
import kpi.lab_2.model.dao.CategoryDao;
import kpi.lab_2.model.dao.ProductDao;
import kpi.lab_2.model.dto.ProductDto;
import kpi.lab_2.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductDao productDao;
  private final CategoryDao categoryDao;

  @Override
  public Optional<Product> getById(Long id) {
    return productDao.findById(id);
  }

  @Override
  public List<Product> getAll() {
    return productDao.findAll();
  }

  @Override
  public List<Product> getAllByCategoryId(Long categoryId) {
    return productDao.findAllByCategoryId(categoryId);
  }

  @Override
  public void create(ProductDto productDto) {
    Product product = new Product(productDto);
    Optional<Category> category = categoryDao.findById(productDto.getCategoryId());

    if (category.isPresent()) {
      product.setCategory(category.get());
    } else {
      log.warn("Category [ID={}] does not exist.", productDto.getCategoryId());
      throw new IllegalArgumentException("Category is not found");
    }

    Product savedProduct = productDao.save(product);

    log.info("Product ({}, [ID={}]) has been created.",
        savedProduct.getName(), savedProduct.getId());
  }

  @Override
  public void update(ProductDto productDto, Long id) {
    Product productNewVersion = new Product(productDto);
    Optional<Category> categoryOptional = categoryDao.findById(productDto.getCategoryId());

    if (categoryOptional.isPresent()) {
      productNewVersion.setCategory(categoryOptional.get());
    } else {
      log.warn("Category [id = {}] does not exist.", productDto.getCategoryId());
      throw new IllegalArgumentException("Category is not found");
    }

    final boolean updateIsSuccess = productDao.update(productNewVersion, id);

    if (updateIsSuccess) {
      log.info("Product [ID={}] has been updated.", id);
    } else {
      log.warn("Product [ID={}] does not exist.", id);
    }
  }

  @Override
  public void deleteById(Long id) {
    final boolean deleteIsSuccess = productDao.deleteById(id);

    if (deleteIsSuccess) {
      log.info("Product [id = {}] has been deleted.", id);
    } else {
      log.warn("Product [id = {}] has not been deleted.", id);
    }
  }

}
