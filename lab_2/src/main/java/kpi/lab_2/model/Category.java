package kpi.lab_2.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import kpi.lab_2.model.dto.CategoryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;

import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
public class Category {

  private Long id;

  private String name;

  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private Category parentCategory;

  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private Set<Category> childCategories;

  private boolean isLeaf;

  @Default
  private LocalDateTime createdAt = LocalDateTime.now();

  public Category(CategoryDto categoryDto) {
    name = categoryDto.getName();
    isLeaf = categoryDto.isLeaf();

    if (!isLeaf) {
      childCategories = new HashSet<>();
    }
  }

}
