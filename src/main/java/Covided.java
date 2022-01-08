import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

// Servlet Name
@WebServlet("/covided")
public class Covided extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();

            int id = (int)session.getAttribute("id");

            Date dateNow = new Date(System.currentTimeMillis());
            Calendar c = Calendar.getInstance();
            c.setTime(dateNow);
            c.add(Calendar.DATE, -7);

            Connection con = DBConnect.initializeDatabase();
            PreparedStatement st = con.prepareStatement("select idFriend from friendslink where idList = (select id from friendslist where idUser = ?)");
            PreparedStatement st2 = con.prepareStatement("select idUser from activitylink where idActivity = (select id from activity where idCreator = ?)");
            PreparedStatement st3 = con.prepareStatement("select activity.idCreator, activitylink.idUser from activity inner join activitylink on activity.id = activitylink.idActivity " +
                    "where activitylink.idUser = ? and activity.date >= ?");
            st.setInt(1, id);
            st2.setInt(1, id);
            st3.setInt(1, id);
            st3.setDate(2, new Date(c.getTimeInMillis()));
            ResultSet rs = st.executeQuery();
            Set<Integer> set = new HashSet<>();
            while (rs.next()){
                set.add(rs.getInt(1));
            }
            st.close();
            rs = st2.executeQuery();
            while (rs.next()){
                set.add(rs.getInt(1));
            }
            st2.close();
            rs = st3.executeQuery();
            while (rs.next()){
                set.add(rs.getInt(1));
                set.add(rs.getInt(2));
            }
            st3.close();
            st = con.prepareStatement("select login from users where id=?");
            st.setInt(1, id);
            rs = st.executeQuery();
            rs.next();
            String login = rs.getString(1);
            ArrayList<Integer> ids = new ArrayList<>(set);
            for (int i : ids){
                if (i != id) {
                    st = con.prepareStatement("insert into notifications (idUser, idOriginUser, notifType, notifDate, text, notifRead) values (?,?,1,?,?,0)");
                    st.setInt(1, i);
                    st.setInt(2, id);
                    st.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
                    st.setString(4, login + ", avec qui vous avez été en contact récemment, a contracté le COVID, pensez à vous faire tester !");
                    st.executeUpdate();
                    st.close();
                }
            }
            RequestDispatcher view = getServletContext().getRequestDispatcher("/login");
            view.forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ignored) {
        }
    }
}