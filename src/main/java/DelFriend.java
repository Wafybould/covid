import tools.DBConnect;

import java.io.IOException;
import java.sql.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

// Servlet Name
@WebServlet("/delfriend")
public class DelFriend extends HttpServlet {

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
                    + idFriend + "," + id + ",2,'" + new Timestamp(System.currentTimeMillis()) + "','"
                    + login + " vous a supprimé de ses amis" + "',0)";
            st = con.prepareStatement(sql);
            st.executeUpdate();
            st.close();
            st = con.prepareStatement("select id from friendslist where idUser = ?");
            st.setInt(1, id);
            rs = st.executeQuery();
            rs.next();
            int idList = rs.getInt(1);
            st.close();
            st = con.prepareStatement("delete from friendslink where idList = ? and idFriend = ?");
            st.setInt(1, idList);
            st.setInt(2, idFriend);
            st.executeUpdate();
            st.close();

            st = con.prepareStatement("select id from friendslist where idUser = ?");
            st.setInt(1, idFriend);
            rs = st.executeQuery();
            rs.next();
            idList = rs.getInt(1);
            st.close();
            st = con.prepareStatement("delete from friendslink where idList = ? and idFriend = ?");
            st.setInt(1, idList);
            st.setInt(2, id);
            st.executeUpdate();
            st.close();
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