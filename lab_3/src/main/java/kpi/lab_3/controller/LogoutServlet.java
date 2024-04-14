package kpi.lab_3.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import kpi.lab_3.controller.util.SessionAttributes;
import kpi.lab_3.controller.util.UriPath;
import kpi.lab_3.model.entity.Role;

@WebServlet(name = "LogoutServlet", value = "/logout")
public class LogoutServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    request.getSession().setAttribute(SessionAttributes.USER_ID, null);
    request.getSession().setAttribute(SessionAttributes.ROLE, Role.UNKNOWN.name());

    response.sendRedirect(UriPath.LOGIN);
  }

}
