import sun.security.pkcs.ParsingException;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

// Servlet Name
@WebServlet("/Register")
public class Register extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("login") == null){
            RequestDispatcher view = getServletContext().getRequestDispatcher("/WEB-INF/register.jsp");
            view.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

            String login = request.getParameter("login");
            String pass = request.getParameter("pwd");
            String name = request.getParameter("name");
            String surname = request.getParameter("surname");
            String birthdayText = request.getParameter("birthday");
            Date birthday = new SimpleDateFormat("yyyy-MM-dd").parse(birthdayText);
            java.sql.Date birthdaySQL = new java.sql.Date(birthday.getTime());

            Connection con = DBConnect.initializeDatabase();

            PreparedStatement st = con.prepareStatement("insert into users (login, password, name, surname, birthday, admin) values(?,?,?,?,?,0)");

            st.setString(1, login);
            st.setString(2, pass);
            st.setString(3, name);
            st.setString(4, surname);
            st.setDate(5, birthdaySQL);

            st.executeUpdate();
            st.close();

            st = con.prepareStatement("select id from users where login=? and password=?");
            st.setString(1, login);
            st.setString(2, pass);
            ResultSet rs = st.executeQuery();
            rs.next();
            int id = rs.getInt(1);
            st.close();

            st = con.prepareStatement("insert into friendslist (idUser) values (?)");
            st.setInt(1, id);
            st.executeUpdate();
            st.close();

            con.close();
            request.setAttribute("registered", true);
            RequestDispatcher view = getServletContext().getRequestDispatcher("/WEB-INF/index.jsp");
            view.forward(request, response);

        } catch (SQLException e) {
            try {
                request.setAttribute("error", true);
                RequestDispatcher view = getServletContext().getRequestDispatcher("/WEB-INF/register.jsp");
                view.forward(request, response);
            } catch (ParsingException ex) {
                e.printStackTrace();
            }
        } catch (  ClassNotFoundException | ParseException ignored){}
    }
}
