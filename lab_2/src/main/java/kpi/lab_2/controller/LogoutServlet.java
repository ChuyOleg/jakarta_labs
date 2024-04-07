package kpi.lab_2.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import kpi.lab_2.controller.util.SessionAttributes;
import kpi.lab_2.controller.util.UriPath;
import kpi.lab_2.model.Role;

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
