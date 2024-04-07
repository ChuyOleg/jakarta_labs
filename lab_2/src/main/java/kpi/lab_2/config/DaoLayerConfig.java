package kpi.lab_2.config;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import kpi.lab_2.model.Category;
import kpi.lab_2.model.Product;
import kpi.lab_2.model.dao.CategoryDao;
import kpi.lab_2.model.dao.ProductDao;
import kpi.lab_2.model.dao.stub.CategoryDaoStub;
import kpi.lab_2.model.dao.stub.ProductDaoStub;

public class DaoLayerConfig {

  private DaoLayerConfig() {}

  private static volatile ProductDao productDaoStub;
  private static volatile CategoryDao categoryDaoStub;

  private static List<Product> productsStub;
  private static volatile Map<Long, Category> categoriesMapStub;

  public static ProductDao buildProductDaoStub() {
    if (productDaoStub == null) {
      synchronized (DaoLayerConfig.class) {
        if (productDaoStub == null) {
          initProductsStub();
          productDaoStub = new ProductDaoStub(productsStub);
        }
      }
    }

    return productDaoStub;
  }

  public static CategoryDao buildCategoryDaoStub() {
    if (categoryDaoStub == null) {
      synchronized (DaoLayerConfig.class) {
        if (categoryDaoStub == null) {
          initCategoriesStub();
          categoryDaoStub = new CategoryDaoStub(
              new CopyOnWriteArrayList<>(categoriesMapStub.values()));
        }
      }
    }

    return categoryDaoStub;
  }

  private static void initProductsStub() {
    initCategoriesStub();

    productsStub = new CopyOnWriteArrayList<>(List.of(
        new Product(1L, "BOSCH KGN39VI306", categoriesMapStub.get(100L)),
        new Product(2L, "Samsung XYZ123", categoriesMapStub.get(100L)),
        new Product(3L, "LG ABC456", categoriesMapStub.get(101L)),
        new Product(4L, "Philips T100", categoriesMapStub.get(113L)),
        new Product(5L, "Braun K2000", categoriesMapStub.get(114L)),
        new Product(6L, "Samsung Galaxy S21", categoriesMapStub.get(20L)),
        new Product(7L, "iPhone 13", categoriesMapStub.get(20L)),
        new Product(8L, "Dell XPS 13", categoriesMapStub.get(21L)),
        new Product(9L, "HP Spectre x360", categoriesMapStub.get(21L)),
        new Product(10L, "Sony Bravia 4K", categoriesMapStub.get(22L)),
        new Product(11L, "LG OLED55CX", categoriesMapStub.get(22L))
    ));
  }

  private static void initCategoriesStub() {
    if (categoriesMapStub == null) {
      synchronized (ServiceLayerConfig.class) {
        if (categoriesMapStub == null) {
          Category homeAppliances = Category.builder().id(1L).name("Home Appliances").isLeaf(false)
              .build();

          Category largeHomeAppliances = Category.builder().id(10L).name("Large Home Appliances")
              .parentCategory(homeAppliances).isLeaf(false).build();
          Category smallHomeAppliances = Category.builder().id(11L).name("Small Home Appliances")
              .parentCategory(homeAppliances).isLeaf(false).build();

          homeAppliances.setChildCategories(new HashSet<>(Set.of(
              largeHomeAppliances, smallHomeAppliances
          )));

          Category refrigerators = Category.builder().id(100L).name("Refrigerators")
              .parentCategory(largeHomeAppliances).isLeaf(true).build();
          Category washingMachines = Category.builder().id(101L).name("Washing Machines")
              .parentCategory(largeHomeAppliances).isLeaf(true).build();
          Category toasters = Category.builder().id(113L).name("Toasters")
              .parentCategory(smallHomeAppliances).isLeaf(true).build();
          Category kettles = Category.builder().id(114L).name("Kettles")
              .parentCategory(smallHomeAppliances).isLeaf(true).build();

          largeHomeAppliances.setChildCategories(new HashSet<>(Set.of(
              refrigerators, washingMachines
          )));

          smallHomeAppliances.setChildCategories(new HashSet<>(Set.of(
              toasters, kettles
          )));

          Category electronics = Category.builder().id(2L).name("Electronics").isLeaf(false)
              .build();

          Category smartphones = Category.builder().id(20L).name("Smartphones")
              .parentCategory(electronics).isLeaf(true).build();
          Category laptops = Category.builder().id(21L).name("Laptops").parentCategory(electronics)
              .isLeaf(true).build();
          Category televisions = Category.builder().id(22L).name("Televisions")
              .parentCategory(electronics).isLeaf(true).build();

          electronics.setChildCategories(new HashSet<>(Set.of(
              smartphones, laptops, televisions
          )));

          categoriesMapStub = new ConcurrentHashMap<>(Map.of(
              1L, homeAppliances,
              10L, largeHomeAppliances, 11L, smallHomeAppliances,
              100L, refrigerators, 101L, washingMachines, 113L, toasters, 114L, kettles,

              2L, electronics,
              20L, smartphones, 21L, laptops
          ));

          categoriesMapStub.putIfAbsent(22L, televisions);
        }
      }
    }
  }

}
