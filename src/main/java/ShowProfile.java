import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

// Servlet Name
@WebServlet("/showprofile")
public class ShowProfile extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();

            int id = (int)session.getAttribute("id");

            Connection con = DBConnect.initializeDatabase();
            PreparedStatement st = con.prepareStatement("select * from users where id=?");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            rs.next();
            String login = rs.getString(2);
            String password = rs.getString(3);
            String name = rs.getString(4);
            String surname = rs.getString(5);
            Date birthday = rs.getDate(6);
            st.close();
            con.close();
            request.setAttribute("login", login);
            request.setAttribute("password", password);
            request.setAttribute("name", name);
            request.setAttribute("surname", surname);
            request.setAttribute("birthday", birthday);
            RequestDispatcher view = getServletContext().getRequestDispatcher("/WEB-INF/showProfile.jsp");
            view.forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ignored) {
        }
    }
}