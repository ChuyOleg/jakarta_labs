package kpi.lab_3.controller.mapper;

import jakarta.servlet.http.HttpServletRequest;
import kpi.lab_3.controller.util.StringParser;
import kpi.lab_3.model.dto.ProductDto;
import kpi.lab_3.model.entity.Product;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProductInfoMapper {

  private static final String NAME = "name";
  private static final String CATEGORY_ID = "categoryId";
  private static final String CATEGORY_NAME = "categoryName";

  private static final String PRODUCT_DTO_ATTR = "productDto";

  public static ProductDto fetchProductFromRequest(HttpServletRequest req) {
    return ProductDto.builder()
        .name(req.getParameter(NAME))
        .categoryId(StringParser.toLong(req.getParameter(CATEGORY_ID)))
        .categoryName(req.getParameter(CATEGORY_NAME))
        .build();
  }

  public static void insertProductIntoRequest(Product product, HttpServletRequest req) {
    ProductDto productDto = ProductDto.builder()
        .id(product.getId())
        .name(product.getName())
        .categoryId(product.getCategory().getId())
        .categoryName(product.getCategory().getName())
        .build();

    insertProductDtoIntoRequest(productDto, req);
  }

  private void insertProductDtoIntoRequest(ProductDto productDto, HttpServletRequest req) {
    req.setAttribute(PRODUCT_DTO_ATTR, productDto);
  }

}
