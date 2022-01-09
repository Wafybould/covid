import tools.DBConnect;

import java.io.IOException;
import java.sql.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

// Servlet Name
@WebServlet("/joinactivity")
public class JoinActivity extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            int id = (int)session.getAttribute("id");
            int idAct = Integer.parseInt(request.getParameter("idAct"));
            boolean joined = request.getParameter("joined").equals("1");
            Connection con = DBConnect.initializeDatabase();

            PreparedStatement st = con.prepareStatement("select login from users where id=?");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            rs.next();
            String login = rs.getString(1);
            st.close();

            st = con.prepareStatement("select name, idCreator from activity where id = ?");
            st.setInt(1, idAct);
            rs = st.executeQuery();
            rs.next();
            String nameAct = rs.getString(1);
            int idCreator = rs.getInt(2);
            st.close();

            PreparedStatement st2;
            if (!joined) {
                st = con.prepareStatement("insert into activitylink values (?,?)");
                st2 = con.prepareStatement("insert into notifications (idUser, idOriginUser, notifType, notifDate, text, notifRead) values (?, ?, 2, ?, ?, 0)");
                st2.setString(4,login + " participe à votre activité " + nameAct);
            } else {
              st = con.prepareStatement("delete from activitylink where idActivity = ? and idUser = ?");
                st2 = con.prepareStatement("insert into notifications (idUser, idOriginUser, notifType, notifDate, text, notifRead) values (?, ?, 2, ?, ?, 0)");
                st2.setString(4,login + " ne participe plus à votre activité " + nameAct);
            }
            st.setInt(1, idAct);
            st.setInt(2, id);
            st.executeUpdate();
            st.close();
            st2.setInt(1, idCreator);
            st2.setInt(2, id);
            st2.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            st2.executeUpdate();
            st2.close();
            con.close();
            RequestDispatcher view = getServletContext().getRequestDispatcher("/getactivities");
            view.forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ignored) {}
    }
}