package kpi.lab_2.service;

import java.util.List;
import java.util.Optional;
import kpi.lab_2.model.Product;
import kpi.lab_2.model.dto.ProductDto;

public interface ProductService {

  Optional<Product> getById(Long id);

  List<Product> getAll();

  List<Product> getAllByCategoryId(Long categoryId);

  void create(ProductDto productDto);

  void update(ProductDto productDto, Long id);

  void deleteById(Long id);

}
