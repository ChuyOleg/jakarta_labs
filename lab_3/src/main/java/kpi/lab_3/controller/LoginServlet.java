package kpi.lab_3.controller;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import kpi.lab_3.controller.util.JspFilePath;
import kpi.lab_3.controller.util.UriPath;
import kpi.lab_3.exception.AuthenticationException;
import kpi.lab_3.model.entity.User;
import kpi.lab_3.service.UserService;
import lombok.extern.log4j.Log4j2;

@Log4j2
@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {

  private static final String USERNAME = "username";
  private static final String PASSWORD = "password";
  private static final String USER_ID = "userId";
  private static final String ROLE = "role";
  private static final String AUTHENTICATION_EXCEPTION = "authenticationException";

  @EJB
  private UserService userService;

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

      log.error("After");

      request.getSession().setAttribute(USER_ID, user.id());
      request.getSession().setAttribute(ROLE, user.role().toString());

      response.sendRedirect(UriPath.CATALOG);
      return;
    } catch (AuthenticationException e) {
      log.error("Catch block");
      request.setAttribute(AUTHENTICATION_EXCEPTION, true);
    }

    request.getRequestDispatcher(JspFilePath.LOGIN).forward(request, response);
  }

}
