package kpi.lab_3.model.dao.stub;

import jakarta.ejb.Singleton;
import java.util.List;
import java.util.Optional;
import kpi.lab_3.config.DaoLayerConfig;
import kpi.lab_3.model.dao.ProductDao;
import kpi.lab_3.model.entity.Product;

@Singleton
public class ProductDaoStub implements ProductDao {

  private static Long NEXT_ID = 50L;

  private final List<Product> products;

  public ProductDaoStub() {
    products = DaoLayerConfig.initProductsStub();
  }

  @Override
  public Optional<Product> findById(Long id) {
    return products.stream()
        .filter(product -> product.getId().equals(id))
        .findFirst();
  }

  @Override
  public List<Product> findAll() {
    return products;
  }

  @Override
  public List<Product> findAllByCategoryId(Long categoryId) {
    return products.stream()
        .filter(product -> product.getCategory().getId().equals(categoryId))
        .toList();
  }

  @Override
  public Product save(Product product) {
    product.setId(getNextId());
    products.add(product);
    return product;
  }

  @Override
  public boolean update(Product updatedProduct, Long id) {
    Optional<Product> productToUpdateOptional = products.stream()
        .filter(product -> product.getId().equals(id))
        .findFirst();

    if (productToUpdateOptional.isPresent()) {
      Product productToUpdate = productToUpdateOptional.get();
      productToUpdate.setName(updatedProduct.getName());
      productToUpdate.setCategory(updatedProduct.getCategory());
      return true;
    } else {
      return false;
    }
  }

  @Override
  public boolean deleteById(Long id) {
    Optional<Product> productToDelete = findById(id);

    return productToDelete.map(products::remove).orElse(false);
  }

  @Override
  public Integer deleteByCategoryId(Long categoryId) {
    List<Product> productsToDelete = findAllByCategoryId(categoryId);

    products.removeAll(productsToDelete);

    return productsToDelete.size();
  }

  private static synchronized Long getNextId() {
    return NEXT_ID++;
  }

}
