import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

// Servlet Name
@WebServlet("/handlenotif")
public class HandleNotif extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            int id = (int) session.getAttribute("id");
            Connection con = DBConnect.initializeDatabase();
            PreparedStatement st;
            ResultSet rs;
            int idNotif;
            if (request.getParameter("changeRead") != null){
                idNotif = Integer.parseInt(request.getParameter("changeRead"));
                st = con.prepareStatement("select notifRead from notifications where id = ?");
                st.setInt(1, idNotif);
                rs = st.executeQuery();
                rs.next();
                boolean read = rs.getBoolean(1);
                st.close();
                st = con.prepareStatement("update notifications set notifRead = ? where id = ?");
                st.setBoolean(1, !read);
                st.setInt(2, idNotif);
                st.executeUpdate();
                st.close();
            } else {
                idNotif = Integer.parseInt(request.getParameter("delete"));
                st = con.prepareStatement("delete from notifications where id = ?");
                st.setInt(1, idNotif);
                st.executeUpdate();
                st.close();
            }
            con.close();
            RequestDispatcher view = getServletContext().getRequestDispatcher("/getnotifs");
            view.forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ignored) {
        }
    }
}