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
@WebServlet("/delactivity")
public class DelActivity extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            int id = (int)session.getAttribute("id");

            int idAct = Integer.parseInt(request.getParameter("idAct"));

            Connection con = DBConnect.initializeDatabase();

            PreparedStatement st = con.prepareStatement("select login from users where id = ?");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            rs.next();
            String login = rs.getString(1);
            st.close();

            st = con.prepareStatement("select name from activity where id = ?");
            st.setInt(1, idAct);
            rs = st.executeQuery();
            rs.next();
            String actName = rs.getString(1);
            st.close();

            st = con.prepareStatement("select idUser from activitylink where idActivity = ?");
            st.setInt(1, idAct);
            rs = st.executeQuery();
            PreparedStatement st2;
            int idUser;
            while(rs.next()){
                idUser = rs.getInt(1);
                st2 = con.prepareStatement("insert into notifications (idUser, idOriginUser, notifType, notifDate, text, notifRead) values (?, ?, 2, ?, ?, 0)");
                st2.setInt(1, idUser);
                st2.setInt(2, id);
                st2.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
                st2.setString(4, login + " a supprimé l'activité " + actName + " à laquelle vous participiez");
                st2.executeUpdate();
                st2.close();
            }
            st.close();
            st = con.prepareStatement("delete from activitylink where idActivity = ?");
            st.setInt(1, idAct);
            st.executeUpdate();
            st.close();
            st = con.prepareStatement("delete from activity where id = ?");
            st.setInt(1, idAct);
            st.executeUpdate();
            st.close();
            con.close();
            RequestDispatcher view = getServletContext().getRequestDispatcher("/getactivities");
            view.forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ignored) {
        }
    }
}