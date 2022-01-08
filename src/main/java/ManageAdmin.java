import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

// Servlet Name
@WebServlet("/manageadmin")
public class ManageAdmin extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            int id = (int) session.getAttribute("id");
            int userId = Integer.parseInt(request.getParameter("userId"));
            Connection con = DBConnect.initializeDatabase();

            PreparedStatement st = con.prepareStatement("select admin from users where id=?");
            st.setInt(1,userId);
            ResultSet rs = st.executeQuery();
            rs.next();
            boolean admin = rs.getBoolean(1);
            st.close();
            st = con.prepareStatement("update users set admin = ? where id = ?");
            st.setBoolean(1, !admin);
            st.setInt(2, userId);
            st.executeUpdate();
            st.close();
            con.close();
            request.setAttribute("redirect", true);
            request.setAttribute("idUser", userId);
            RequestDispatcher view = getServletContext().getRequestDispatcher("/showuser");
            view.forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ignored) {
        }
    }
}