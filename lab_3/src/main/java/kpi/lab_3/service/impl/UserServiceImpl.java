package kpi.lab_3.service.impl;

import jakarta.ejb.Singleton;
import kpi.lab_3.exception.AuthenticationException;
import kpi.lab_3.model.entity.Role;
import kpi.lab_3.model.entity.User;
import kpi.lab_3.service.UserService;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Singleton
public class UserServiceImpl implements UserService {

  private static final Long ADMIN_ID = 1L;
  private static final String ADMIN_USERNAME_STUB = "admin";
  private static final String ADMIN_PASSWORD_STUB = "test1234";

  @Override
  public User doAuthentication(String username, String password) throws AuthenticationException {
    if (isAdmin(username, password)) {
      log.info("Admin [{}] has logged in the system.", username);
      return getAdminUser();
    } else {
      log.warn("Failed attempt to log into Admin account.");
      throw new AuthenticationException("Authentication issue");
    }
  }

  private boolean isAdmin(String username, String password) {
    return ADMIN_USERNAME_STUB.equals(username) && ADMIN_PASSWORD_STUB.equals(password);
  }

  private User getAdminUser() {
    return new User(ADMIN_ID, ADMIN_USERNAME_STUB, ADMIN_PASSWORD_STUB, Role.ADMIN);
  }

}
