package kpi.lab_3.service;

import java.util.List;
import java.util.Optional;
import kpi.lab_3.model.dto.ProductDto;
import kpi.lab_3.model.entity.Product;

public interface ProductService {

  Optional<Product> getById(Long id);

  List<Product> getAll();

  List<Product> getAllByCategoryId(Long categoryId);

  void create(ProductDto productDto);

  void update(ProductDto productDto, Long id);

  void deleteById(Long id);

}
