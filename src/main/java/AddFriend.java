import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

// Servlet Name
@WebServlet("/addfriend")
public class AddFriend extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            int id = (int) session.getAttribute("id");
            int idFriend = Integer.parseInt(request.getParameter("userId"));
            Connection con = DBConnect.initializeDatabase();
            PreparedStatement st = con.prepareStatement("select login from users where id=?");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            rs.next();
            String login = rs.getString(1);
            st.close();
            String sql = "insert into notifications (idUser, idOriginUser, notifType, notifDate, text, notifRead) values ("
                    + idFriend + "," + id + ",0,'" + new Timestamp(System.currentTimeMillis()) + "','"
                    + login + " vous a envoyé une demande en ami !" + "',0)";
            st = con.prepareStatement(sql);
            st.executeUpdate();
            st.close();
            st = con.prepareStatement("insert into friendrequests values (?,?)");
            st.setInt(1, id);
            st.setInt(2, idFriend);
            st.executeUpdate();
            st.close();
            con.close();
            request.setAttribute("redirect", true);
            request.setAttribute("idUser", idFriend);
            RequestDispatcher view = getServletContext().getRequestDispatcher("/showuser");
            view.forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ignored) {
        }
    }
}