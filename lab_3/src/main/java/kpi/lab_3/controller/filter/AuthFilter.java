package kpi.lab_3.controller.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import kpi.lab_3.controller.util.SessionAttributes;
import kpi.lab_3.controller.util.UriPath;
import kpi.lab_3.model.entity.Role;
import lombok.extern.log4j.Log4j2;

@Log4j2
@WebFilter(filterName = "AuthFilter", urlPatterns = { "/*" })
public class AuthFilter implements Filter {

  public void init(FilterConfig config) throws ServletException {
  }

  public void destroy() {
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws ServletException, IOException {

    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse resp = (HttpServletResponse) response;
    HttpSession session = req.getSession();
    final String URI = req.getRequestURI();

    if (session.getAttribute(SessionAttributes.ROLE) == null) {
      session.setAttribute(SessionAttributes.ROLE, Role.UNKNOWN.toString());
    }

    Role role = Role.valueOf(session.getAttribute(SessionAttributes.ROLE).toString());

    if (!checkResources(URI) && !checkAccess(URI, role)) {
      if (role.equals(Role.UNKNOWN)) {
        log.info("Redirect guest to Login page from URI ({})", URI);
        resp.sendRedirect(UriPath.LOGIN);
        return;
      }
      log.warn("User (id = {}) Forbidden uri: {}", session.getAttribute(SessionAttributes.USER_ID), URI);
      resp.sendError(403);
      return;
    }

    chain.doFilter(req, resp);
  }

  private boolean checkAccess(String uri, Role roleEnum) {
    if (Role.ADMIN.equals(roleEnum)) {
      return checkAdminAccess(uri);
    }

    return checkGuestAccess(uri);
  }

  private boolean checkAdminAccess(String uri) {
    return checkCommonAccess(uri) || uri.startsWith(UriPath.ADMIN_PREFIX) || uri.equals(UriPath.LOGOUT);
  }

  private boolean checkGuestAccess(String uri) {
    return checkCommonAccess(uri) || uri.equals(UriPath.LOGIN);
  }

  private boolean checkCommonAccess(String uri) {
    return uri.equals(UriPath.CATALOG) || uri.startsWith(UriPath.PRODUCTS);
  }

  private boolean checkResources(String uri) {
    return uri.startsWith(UriPath.STATIC_RESOURCES_PREFIX);
  }

}
