import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

// Servlet Name
@WebServlet("/delarea")
public class DelArea extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();

            int id = (int)session.getAttribute("id");

            int idArea = Integer.parseInt(request.getParameter("idArea"));

            Connection con = DBConnect.initializeDatabase();
            PreparedStatement st = con.prepareStatement("select id from activity where idArea = ?");
            st.setInt(1, idArea);
            ResultSet rs = st.executeQuery();
            PreparedStatement st2;
            while(rs.next()){
                st2 = con.prepareStatement("delete from activitylink where idActivity = ?");
                st2.setInt(1, rs.getInt(1));
                st2.executeUpdate();
                st2.close();
            }
            st.close();
            st = con.prepareStatement("delete from activity where idArea = ?");
            st.setInt(1, idArea);
            st.executeUpdate();
            st.close();
            st = con.prepareStatement("delete from area where id = ?");
            st.setInt(1, idArea);
            st.executeUpdate();
            st.close();
            con.close();
            RequestDispatcher view = getServletContext().getRequestDispatcher("/getareas");
            view.forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ignored) {
        }
    }
}