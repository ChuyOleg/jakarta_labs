package kpi.lab_2.controller.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class JspFilePath {

  private static final String GUEST_DIR = "/template/guest/";
  private static final String COMMON_DIR = "/template/common/";
  private static final String ADMIN_DIR = "/template/admin/";

  public static final String LOGIN = GUEST_DIR + "loginPage.jsp";
  public static final String CATALOG = COMMON_DIR + "catalogPage.jsp";
  public static final String PRODUCT = COMMON_DIR + "productPage.jsp";

  public static final String ADMIN_CREATE_PRODUCT = ADMIN_DIR + "createProduct.jsp";
  public static final String ADMIN_UPDATE_PRODUCT = ADMIN_DIR + "updateProduct.jsp";

  public static final String ADMIN_CREATE_CATEGORY = ADMIN_DIR + "createCategory.jsp";
  public static final String ADMIN_UPDATE_CATEGORY = ADMIN_DIR + "updateCategory.jsp";

}
