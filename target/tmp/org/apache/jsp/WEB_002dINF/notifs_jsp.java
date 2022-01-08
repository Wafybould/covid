/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: jetty/9.4.28.v20200408
 * Generated at: 2022-01-08 20:47:48 UTC
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
import java.sql.*;
import java.text.SimpleDateFormat;

public final class notifs_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_classes.add("java.text.SimpleDateFormat");
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
      out.write("    <title>Notifications</title>\r\n");
      out.write("    <link href=\"/CSS/bootstrap/css/bootstrap.min.css\" rel=\"stylesheet\">\r\n");
      out.write("    <script src=\"/CSS/bootstrap/js/bootstrap.min.js\"></script>\r\n");
      out.write("    <script src=\"http://code.jquery.com/jquery-latest.min.js\"></script>\r\n");
      out.write("    <link href=\"/CSS/styleIndex.css\" rel=\"stylesheet\">\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("<ul class=\"navPad\">\r\n");
      out.write("    <i class=\"navTitle\"><h2>");
      out.print( request.getAttribute("name") );
      out.write(' ');
      out.print( request.getAttribute("surname") );
      out.write("</h2></i>\r\n");
      out.write("    <li><form action=\"/login\" method=\"post\"><input type=\"submit\" value=\"Retour à l'accueil\"></form></li>\r\n");
      out.write("    <li><form action=\"/showprofile\" method=\"post\"><input type=\"submit\" value=\"Modifier mon profil\"></form></li>\r\n");
      out.write("    <li><form action=\"/getfriends\" method=\"post\"><input type=\"submit\" value=\"Liste d'amis\"></form></li>\r\n");
      out.write("    <li><form action=\"/getnotifs\" method=\"post\"><input type=\"submit\" value=\"Notifications\" disabled></form></li>\r\n");
      out.write("    <li><form action=\"/searchuser\" method=\"post\"><input type=\"submit\" value=\"Rechercher un utilisateur\"></form></li>\r\n");
      out.write("    <li><form action=\"/getactivities\" method=\"post\"><input type=\"submit\" value=\"Accéder aux activités\"></form></li>\r\n");
      out.write("    <li><form action=\"/logout\" method=\"post\"><input type=\"submit\" value=\"Déconnexion\"></form></li>\r\n");
      out.write("</ul>\r\n");
      out.write("<div class=\"paddedNav\">\r\n");
      out.write("    ");
  int numNotifs = (int)request.getAttribute("numNotifs");
        if (numNotifs > 0) {
            ArrayList<Integer> type = (ArrayList<Integer>)request.getAttribute("type");
            ArrayList<Timestamp> date = (ArrayList<Timestamp>)request.getAttribute("date");
            ArrayList<String> text = (ArrayList<String>)request.getAttribute("text");
            ArrayList<Boolean> read = (ArrayList<Boolean>)request.getAttribute("read");
            ArrayList<String> who = (ArrayList<String>)request.getAttribute("who");
            ArrayList<Integer> idWho = (ArrayList<Integer>)request.getAttribute("idWho");
            ArrayList<Integer> idNotif = (ArrayList<Integer>)request.getAttribute("idNotif");
            String color;
            boolean accept = false;
            String shadow;
            String markAs;
            
      out.write("\r\n");
      out.write("            <table>\r\n");
      out.write("                <tr>\r\n");
      out.write("                    <th>Date</th>\r\n");
      out.write("                    <th>Texte</th>\r\n");
      out.write("                    <th>Envoyé par</th>\r\n");
      out.write("                    <th></th>\r\n");
      out.write("                    <th></th>\r\n");
      out.write("                    <th></th>\r\n");
      out.write("                </tr>\r\n");
      out.write("                ");
  for (int i = 0; i < numNotifs; i++){
                        accept = false;
                        if( type.get(i) == 0 ){
                            color = "green";
                            accept = true;
                        } else if (type.get(i) == 1) {
                            color = "red";
                        } else {
                            color="orange";
                        }
                        if ( read.get(i) ){
                            shadow = "\">";
                            markAs = "Marquer comme non lu";
                        } else {
                            shadow = ";box-shadow: 2px 2px 2px 1px rgba(0, 0, 0, 0.2);\">";
                            markAs = "Marquer comme lu";
                        }
      out.write("\r\n");
      out.write("                        <tr style=\"color:");
      out.print( color );
      out.print( shadow );
      out.write("\r\n");
      out.write("                            <td>\r\n");
      out.write("                                ");
      out.print( new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(date.get(i)).toString() );
      out.write("\r\n");
      out.write("                            </td>\r\n");
      out.write("                            <td>\r\n");
      out.write("                                ");
      out.print( text.get(i) );
      out.write("\r\n");
      out.write("                            </td>\r\n");
      out.write("                            <td>\r\n");
      out.write("                                ");
      out.print( who.get(i) );
      out.write("\r\n");
      out.write("                            </td>\r\n");
      out.write("                            <td>\r\n");
      out.write("                                ");
 if (accept) { 
      out.write("\r\n");
      out.write("                                    <table>\r\n");
      out.write("                                        <tr>\r\n");
      out.write("                                            <td colspan=\"2\">Accepter ?</td>\r\n");
      out.write("                                        </tr>\r\n");
      out.write("                                        <tr>\r\n");
      out.write("                                            <td>\r\n");
      out.write("                                                <form action=\"/handlerequest\" method=\"post\">\r\n");
      out.write("                                                    <input type=\"hidden\" id=\"from1\" name=\"from1\" value=\"notifs\">\r\n");
      out.write("                                                    <input type=\"hidden\" id=\"idAccept\" name=\"idAccept\" value=\"");
      out.print( idWho.get(i) );
      out.write("\">\r\n");
      out.write("                                                    <input type=\"submit\" value=\"Oui\">\r\n");
      out.write("                                                </form>\r\n");
      out.write("                                            </td>\r\n");
      out.write("                                            <td>\r\n");
      out.write("                                                <form action=\"/handlerequest\" method=\"post\">\r\n");
      out.write("                                                    <input type=\"hidden\" id=\"from2\" name=\"from2\" value=\"notifs\">\r\n");
      out.write("                                                    <input type=\"hidden\" id=\"idRefuse\" name=\"idRefuse\" value=\"");
      out.print( idWho.get(i) );
      out.write("\">\r\n");
      out.write("                                                    <input type=\"submit\" value=\"Non\">\r\n");
      out.write("                                                </form>\r\n");
      out.write("                                            </td>\r\n");
      out.write("                                        </tr>\r\n");
      out.write("                                    </table>\r\n");
      out.write("                                ");
 } 
      out.write("\r\n");
      out.write("                            </td>\r\n");
      out.write("                            <td>\r\n");
      out.write("                                <form action=\"/handlenotif\" method=\"post\">\r\n");
      out.write("                                    <input type=\"hidden\" id=\"changeRead\" name=\"changeRead\" value=\"");
      out.print( idNotif.get(i) );
      out.write("\">\r\n");
      out.write("                                    <input type=\"submit\" value=\"");
      out.print( markAs );
      out.write("\">\r\n");
      out.write("                                </form>\r\n");
      out.write("                            </td>\r\n");
      out.write("                            <td>\r\n");
      out.write("                                <form action=\"/handlenotif\" method=\"post\">\r\n");
      out.write("                                    <input type=\"hidden\" id=\"delete\" name=\"delete\" value=\"");
      out.print( idNotif.get(i) );
      out.write("\">\r\n");
      out.write("                                    <input type=\"submit\" value=\"Supprimer\">\r\n");
      out.write("                                </form>\r\n");
      out.write("                            </td>\r\n");
      out.write("                        </tr>\r\n");
      out.write("                ");
  } 
      out.write("\r\n");
      out.write("            </table>\r\n");
      out.write("    ");
  } else { 
      out.write("\r\n");
      out.write("            <h1>Aucune notification</h1>\r\n");
      out.write("    ");
  } 
      out.write("\r\n");
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
