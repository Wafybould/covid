import tools.DBConnect;
import tools.SHA512;

import java.io.IOException;
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
        RequestDispatcher view = getServletContext().getRequestDispatcher("/index.jsp");
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

                PreparedStatement st = con.prepareStatement("select salt, password from users where login = ?");
                st.setString(1, login);
                ResultSet rs = st.executeQuery();
                rs.next();
                String salt = rs.getString(1);
                String hashedPwd = rs.getString(2);
                st.close();
                int len = salt.length();
                byte[] saltBytes = new byte[len / 2];
                for (int i = 0; i < len; i += 2) {
                    saltBytes[i / 2] = (byte) ((Character.digit(salt.charAt(i), 16) << 4)
                            + Character.digit(salt.charAt(i+1), 16));
                }
                String hashPass = SHA512.SHA512Hash(pass, saltBytes);

                st = con.prepareStatement("select count(*) from users where login=? and password=?");

                st.setString(1, login);
                st.setString(2, hashPass);

                rs = st.executeQuery();

                rs.next();
                if (rs.getInt(1) != 0) {
                    st = con.prepareStatement("select id, name, surname, admin from users where login=? and password=?");
                    st.setString(1, login);
                    st.setString(2, hashPass);
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
                    RequestDispatcher view = getServletContext().getRequestDispatcher("/index.jsp");
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
            e.printStackTrace();
            request.setAttribute("error", true);
            RequestDispatcher view = getServletContext().getRequestDispatcher("/index.jsp");
            view.forward(request, response);
        }
        catch (ClassNotFoundException ignored) {}
    }
}