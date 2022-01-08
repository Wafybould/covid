import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

// Servlet Name
@WebServlet("/Login")
public class Login extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher view = getServletContext().getRequestDispatcher("/WEB-INF/index.jsp");
        view.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try {

            HttpSession session = request.getSession();
            if(session.getAttribute("id") == null) {

                String login = request.getParameter("login");
                String pass = request.getParameter("pwd");

                Connection con = DBConnect.initializeDatabase();

                PreparedStatement st = con
                        .prepareStatement("select count(*) from users where login=? and password=?");

                st.setString(1, login);
                st.setString(2, pass);

                ResultSet rs = st.executeQuery();

                rs.next();
                if (rs.getInt(1) != 0) {
                    st = con.prepareStatement("select id, name, surname, admin from users where login=? and password=?");
                    st.setString(1, login);
                    st.setString(2, pass);
                    rs = st.executeQuery();
                    rs.next();
                    int id = rs.getInt(1);
                    String name = rs.getString(2);
                    String surname = rs.getString(3);
                    Boolean admin = rs.getBoolean(4);
                    st.close();
                    con.close();
                    session.setAttribute("id", id);
                    request.setAttribute("name", name);
                    request.setAttribute("surname", surname);
                    request.setAttribute("admin", admin);
                    RequestDispatcher view = getServletContext().getRequestDispatcher("/WEB-INF/home.jsp");
                    view.forward(request, response);
                } else {
                    st.close();
                    con.close();
                    request.setAttribute("error", true);
                    RequestDispatcher view = getServletContext().getRequestDispatcher("/WEB-INF/index.jsp");
                    view.forward(request, response);
                }
            }else{
                int id = (int)session.getAttribute("id");
                Connection con = DBConnect.initializeDatabase();
                PreparedStatement st = con.prepareStatement("select name, surname, admin from users where id=?");
                st.setInt(1, id);
                ResultSet rs = st.executeQuery();
                rs.next();
                String name = rs.getString(1);
                String surname = rs.getString(2);
                Boolean admin = rs.getBoolean(3);
                st.close();
                con.close();
                request.setAttribute("name", name);
                request.setAttribute("surname", surname);
                request.setAttribute("admin", admin);
                RequestDispatcher view = getServletContext().getRequestDispatcher("/WEB-INF/home.jsp");
                view.forward(request, response);
            }
        }catch (SQLException e){
            request.setAttribute("error", true);
            RequestDispatcher view = getServletContext().getRequestDispatcher("/WEB-INF/index.jsp");
            view.forward(request, response);
        }
        catch (ClassNotFoundException ignored) {}
    }
}