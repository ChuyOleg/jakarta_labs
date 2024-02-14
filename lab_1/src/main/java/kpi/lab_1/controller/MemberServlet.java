package kpi.lab_1.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "MainPageServlet", value = "/members")
public class MemberServlet extends HttpServlet {

  private static final String MEMBER_PARAM = "memberName";

  private static final String MEMBER_YAROSLAVA = "Yaroslava";
  private static final String MEMBER_ANDREW = "Andrew";
  private static final String MEMBER_OLEH = "Oleh";
  private static final String MEMBER_IVAN = "Ivan";

  private static final String MAIN_PAGE_HTML = "/index.html";
  private static final String YAROSLAVA_HTML = "/static/html/Yaroslava.html";
  private static final String ANDREW_HTML = "/static/html/Andrew.html";
  private static final String OLEH_HTML = "/static/html/Oleh.html";
  private static final String IVAN_HTML = "/static/html/Ivan.html";

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String memberName = req.getParameter(MEMBER_PARAM);

    RequestDispatcher requestDispatcher = switch (memberName) {
      case MEMBER_YAROSLAVA -> req.getRequestDispatcher(YAROSLAVA_HTML);
      case MEMBER_ANDREW -> req.getRequestDispatcher(ANDREW_HTML);
      case MEMBER_OLEH -> req.getRequestDispatcher(OLEH_HTML);
      case MEMBER_IVAN -> req.getRequestDispatcher(IVAN_HTML);
      default -> req.getRequestDispatcher(MAIN_PAGE_HTML);
    };

    requestDispatcher.forward(req, resp);
  }

}
