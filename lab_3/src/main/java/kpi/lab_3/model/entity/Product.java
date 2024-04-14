package kpi.lab_3.model.entity;

import kpi.lab_3.model.dto.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
public class Product {

  private static Long NEXT_ID = 50L;

  private Long id;
  private String name;
  @ToString.Exclude
  private Category category;

  public Product(ProductDto productDto) {
    name = productDto.getName();
  }

}
