/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: jetty/9.4.28.v20200408
 * Generated at: 2022-01-08 20:47:50 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.example.web.*;
import java.util.ArrayList;
import java.util.Collections;
import java.sql.*;

public final class showProfile_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("java.sql");
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("com.example.web");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("java.util.Collections");
    _jspx_imports_classes.add("java.util.ArrayList");
  }

  private volatile javax.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public javax.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
      throws java.io.IOException, javax.servlet.ServletException {

    final java.lang.String _jspx_method = request.getMethod();
    if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method) && !javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
      response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSPs only permit GET POST or HEAD");
      return;
    }

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n");
      out.write("    <title>Accueil</title>\r\n");
      out.write("    <link href=\"/CSS/bootstrap/css/bootstrap.min.css\" rel=\"stylesheet\">\r\n");
      out.write("    <script src=\"/CSS/bootstrap/js/bootstrap.min.js\"></script>\r\n");
      out.write("    <script src=\"http://code.jquery.com/jquery-latest.min.js\"></script>\r\n");
      out.write("    <link href=\"/CSS/styleIndex.css\" rel=\"stylesheet\">\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("<ul class=\"navPad\">\r\n");
  String name = (String)request.getAttribute("name");
    String surname = (String)request.getAttribute("surname");
      out.write("\r\n");
      out.write("    <i class=\"navTitle\"><h2>");
      out.print( name );
      out.write(' ');
      out.print( surname );
      out.write("</h2></i>\r\n");
      out.write("    <li><form action=\"/login\" method=\"post\"><input type=\"submit\" value=\"Retour à l'accueil\"></form></li>\r\n");
      out.write("    <li><form action=\"/showprofile\" method=\"post\"><input type=\"submit\" value=\"Modifier mon profil\" disabled></form></li>\r\n");
      out.write("    <li><form action=\"/getfriends\" method=\"post\"><input type=\"submit\" value=\"Liste d'amis\"></form></li>\r\n");
      out.write("    <li><form action=\"/getnotifs\" method=\"post\"><input type=\"submit\" value=\"Notifications\"></form></li>\r\n");
      out.write("    <li><form action=\"/searchuser\" method=\"post\"><input type=\"submit\" value=\"Rechercher un utilisateur\"></form></li>\r\n");
      out.write("    <li><form action=\"/getactivities\" method=\"post\"><input type=\"submit\" value=\"Accéder aux activités\"></form></li>\r\n");
      out.write("    <li><form action=\"/logout\" method=\"post\"><input type=\"submit\" value=\"Déconnexion\"></form></li>\r\n");
      out.write("</ul>\r\n");
      out.write("<div class=\"paddedNav\">\r\n");
  String login = (String)request.getAttribute("login");
    String password = (String)request.getAttribute("password");
    Date birthday = (Date)request.getAttribute("birthday");
      out.write("\r\n");
      out.write("    <form action=\"/updateprofile\" method=\"post\">\r\n");
      out.write("        <label>Nom d'utilisateur\r\n");
      out.write("            <input type=\"text\" id=\"login\" name=\"login\" value=\"");
      out.print( login );
      out.write("\" required minlength=\"8\" maxlength=\"32\">\r\n");
      out.write("        </label><br/>\r\n");
      out.write("        <label>Mot de passe<br/>\r\n");
      out.write("            <input type=\"password\" id=\"password\" name=\"password\" value=\"");
      out.print( password );
      out.write("\" required minlength=\"8\" maxlength=\"64\">\r\n");
      out.write("        </label><br/>\r\n");
      out.write("        <label>Nom\r\n");
      out.write("            <input type=\"text\" id=\"name\" name=\"name\" value=\"");
      out.print( name );
      out.write("\" required maxlength=\"64\">\r\n");
      out.write("        </label><br/>\r\n");
      out.write("        <label>Prénom\r\n");
      out.write("            <input type=\"text\" id=\"surname\" name=\"surname\" value=\"");
      out.print( surname );
      out.write("\" required maxlength=\"64\">\r\n");
      out.write("        </label><br/>\r\n");
      out.write("        <label>Date de naissance\r\n");
      out.write("            <input type=\"date\" id=\"birthday\" name=\"birthday\" value=\"");
      out.print( birthday );
      out.write("\" required>\r\n");
      out.write("        </label><br/>\r\n");
      out.write("        <input type=\"submit\" value=\"Mettre à jour mes informations\">\r\n");
      out.write("    </form>\r\n");
      out.write("</div>\r\n");
      out.write("</body>\r\n");
      out.write("</html>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
