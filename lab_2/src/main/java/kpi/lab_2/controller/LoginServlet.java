package kpi.lab_2.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import kpi.lab_2.config.ServiceLayerConfig;
import kpi.lab_2.controller.util.UriPath;
import kpi.lab_2.exception.AuthenticationException;
import kpi.lab_2.model.User;
import kpi.lab_2.service.UserService;
import kpi.lab_2.controller.util.JspFilePath;

import java.io.IOException;
import lombok.extern.log4j.Log4j2;

@Log4j2
@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {

  private static final String USERNAME = "username";
  private static final String PASSWORD = "password";
  private static final String USER_ID = "userId";
  private static final String ROLE = "role";
  private static final String AUTHENTICATION_EXCEPTION = "authenticationException";

  private UserService userService;

  @Override
  public void init() throws ServletException {
    super.init();

    this.userService = ServiceLayerConfig.buildUserServiceStub();
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.getRequestDispatcher(JspFilePath.LOGIN).forward(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String username = request.getParameter(USERNAME);
    String password = request.getParameter(PASSWORD);

    try {
      User user = userService.doAuthentication(username, password);

      request.getSession().setAttribute(USER_ID, user.id());
      request.getSession().setAttribute(ROLE, user.role().toString());

      response.sendRedirect(UriPath.CATALOG);
      return;
    } catch (AuthenticationException e) {
      request.setAttribute(AUTHENTICATION_EXCEPTION, true);
    }

    request.getRequestDispatcher(JspFilePath.LOGIN).forward(request, response);
  }

}
