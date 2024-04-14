package kpi.lab_3.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDto {

  private Long id;
  private String name;
  private Long categoryId;
  private String categoryName;

}
