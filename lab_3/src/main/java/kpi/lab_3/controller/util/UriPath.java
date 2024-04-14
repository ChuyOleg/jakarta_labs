package kpi.lab_3.controller.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UriPath {

  public static final String CONTEXT_PATH = "/lab_3-1.0-SNAPSHOT";

  public static final String ADMIN_PREFIX = CONTEXT_PATH + "/admin";

  public static final String LOGIN = CONTEXT_PATH + "/login";
  public static final String LOGOUT = CONTEXT_PATH + "/logout";

  public static final String CATALOG = CONTEXT_PATH + "/catalog";
  public static final String PRODUCTS = CONTEXT_PATH + "/products";

  public static final String ADMIN_UPDATE_PRODUCT = CONTEXT_PATH + "/admin/products/update";
  public static final String ADMIN_UPDATE_CATEGORY = CONTEXT_PATH + "/admin/categories/update";

  public static final String STATIC_RESOURCES_PREFIX = CONTEXT_PATH + "/static";

}
