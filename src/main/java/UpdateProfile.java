import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

// Servlet Name
@WebServlet("/updateprofile")
public class UpdateProfile extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();

            int id = (int)session.getAttribute("id");

            String login = request.getParameter("login");
            String password = request.getParameter("password");
            String name = request.getParameter("name");
            String surname = request.getParameter("surname");
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Date birthday = new Date(sf.parse(request.getParameter("birthday")).getTime());
            Connection con = DBConnect.initializeDatabase();
            PreparedStatement st = con.prepareStatement("update users set login = ?, password = ?, name = ?, surname = ?, birthday = ? where id = ?");
            st.setString(1, login);
            st.setString(2,password);
            st.setString(3, name);
            st.setString(4, surname);
            st.setDate(5, birthday);
            st.setInt(6, id);
            st.executeUpdate();
            st.close();
            con.close();
            RequestDispatcher view = getServletContext().getRequestDispatcher("/showprofile");
            view.forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException | ParseException ignored) {
        }
    }
}