package kpi.lab_3.model.dao;

import java.util.List;
import java.util.Optional;
import kpi.lab_3.model.entity.Product;

public interface ProductDao {

  Optional<Product> findById(Long id);

  List<Product> findAll();

  List<Product> findAllByCategoryId(Long categoryId);

  Product save(Product product);

  boolean update(Product updatedProduct, Long id);

  boolean deleteById(Long id);

  Integer deleteByCategoryId(Long categoryId);

}
