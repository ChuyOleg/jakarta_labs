package kpi.lab_2.config;

import kpi.lab_2.controller.validator.CategoryValidator;
import kpi.lab_2.service.CategoryService;
import kpi.lab_2.service.ProductService;
import kpi.lab_2.service.UserService;
import kpi.lab_2.service.stub.CategoryServiceImpl;
import kpi.lab_2.service.stub.ProductServiceImpl;
import kpi.lab_2.service.stub.UserServiceImpl;

public class ServiceLayerConfig {

  private ServiceLayerConfig() {}

  private static volatile ProductService productService;
  private static volatile CategoryService categoryService;
  private static volatile UserService userService;

  private static volatile CategoryValidator categoryValidator;

  public static ProductService buildProductServiceStub() {
    if (productService == null) {
      synchronized (ServiceLayerConfig.class) {
        if (productService == null) {
          productService = new ProductServiceImpl(
              DaoLayerConfig.buildProductDaoStub(), DaoLayerConfig.buildCategoryDaoStub());
        }
      }
    }

    return productService;
  }

  public static CategoryService buildCategoryServiceStub() {
    if (categoryService == null) {
      synchronized (ServiceLayerConfig.class) {
        if (categoryService == null) {
          categoryService = new CategoryServiceImpl(
              DaoLayerConfig.buildCategoryDaoStub(), DaoLayerConfig.buildProductDaoStub());
        }
      }
    }

    return categoryService;
  }

  public static UserService buildUserServiceStub() {
    if (userService == null) {
      synchronized (ServiceLayerConfig.class) {
        if (userService == null) {
          userService = new UserServiceImpl();
        }
      }
    }

    return userService;
  }

  public static CategoryValidator buildCategoryValidator() {
    if (categoryValidator == null) {
      synchronized (ServiceLayerConfig.class) {
        if (categoryValidator == null) {
          categoryValidator = new CategoryValidator();
        }
      }
    }

    return categoryValidator;
  }

}
