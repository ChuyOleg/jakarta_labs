package kpi.lab_3.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryDto {

  private Long id;
  private String name;
  private Long parentCategoryId;
  private String parentCategoryName;
  private boolean isLeaf;

}
