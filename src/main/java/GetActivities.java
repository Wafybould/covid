import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

// Servlet Name
@WebServlet("/getactivities")
public class GetActivities extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();

            int id = (int)session.getAttribute("id");

            Connection con = DBConnect.initializeDatabase();
            PreparedStatement st = con
                    .prepareStatement("select activity.*, area.name, area.address, area.GPS from activity inner join area on activity.idArea = area.id where " +
                            "(idCreator=? or idCreator in (select idFriend from friendslink where idList = (select id from friendslist where idUser = ?)))" +
                            " order by activity.date desc");
            st.setInt(1, id);
            st.setInt(2, id);
            ResultSet rs = st.executeQuery();
            int numAct = 0;
            ArrayList<Integer> idAct = new ArrayList<>();
            ArrayList<String> nameAct = new ArrayList<>();
            ArrayList<Integer> idCreator = new ArrayList<>();
            ArrayList<Boolean> isMe = new ArrayList<>();
            ArrayList<String> loginCreator = new ArrayList<>();
            ArrayList<String> nameCreator = new ArrayList<>();
            ArrayList<java.sql.Date> date = new ArrayList<>();
            ArrayList<Time> startTime = new ArrayList<>();
            ArrayList<Time> endTime = new ArrayList<>();
            ArrayList<Integer> idWhere = new ArrayList<>();
            ArrayList<String> nameWhere = new ArrayList<>();
            ArrayList<String> addressWhere = new ArrayList<>();
            ArrayList<String> gpsWhere = new ArrayList<>();
            int newCreator;
            PreparedStatement st2;
            ResultSet rs2;
            while(rs.next()){
                idAct.add(rs.getInt(1));
                nameAct.add(rs.getString(2));
                newCreator = rs.getInt(3);
                idCreator.add(newCreator);
                isMe.add(newCreator==id);
                st2 = con.prepareStatement("select login, name, surname from users where id=?");
                st2.setInt(1, newCreator);
                rs2 = st2.executeQuery();
                rs2.next();
                loginCreator.add(rs.getString(1));
                nameCreator.add(rs.getString(2) + " " + rs.getString(3));
                date.add(rs.getDate(4));
                startTime.add(rs.getTime(5));
                endTime.add(rs.getTime(6));
                idWhere.add(rs.getInt(7));
                nameWhere.add(rs.getString(8));
                addressWhere.add(rs.getString(9));
                gpsWhere.add(rs.getString(10));
                numAct++;
            }
            st.close();
            st = con.prepareStatement("select * from area");
            rs = st.executeQuery();
            int numArea = 0;
            ArrayList<Integer> idArea = new ArrayList<>();
            ArrayList<String> nameArea = new ArrayList<>();
            ArrayList<String> addressArea = new ArrayList<>();
            ArrayList<String> gpsArea = new ArrayList<>();
            while (rs.next()) {
                idArea.add(rs.getInt(1));
                nameArea.add(rs.getString(2));
                addressArea.add(rs.getString(3));
                gpsArea.add(rs.getString(4));
                numArea++;
            }
            st.close();
            st = con.prepareStatement("select name, surname from users where id = ?");
            st.setInt(1, id);
            rs = st.executeQuery();
            rs.next();
            String name = rs.getString(1);
            String surname = rs.getString(2);
            st.close();
            con.close();
            request.setAttribute("numAct", numAct);
            request.setAttribute("idAct", idAct);
            request.setAttribute("nameAct", nameAct);
            request.setAttribute("idCreator", idCreator);
            request.setAttribute("isMe",isMe);
            request.setAttribute("loginCreator", loginCreator);
            request.setAttribute("nameCreator", nameCreator);
            request.setAttribute("date", date);
            request.setAttribute("startTime", startTime);
            request.setAttribute("endTime", endTime);
            request.setAttribute("idWhere", idWhere);
            request.setAttribute("nameWhere", nameWhere);
            request.setAttribute("addressWhere", addressWhere);
            request.setAttribute("gpsWhere", gpsWhere);
            request.setAttribute("numArea", numArea);
            request.setAttribute("idArea", idArea);
            request.setAttribute("nameArea", nameArea);
            request.setAttribute("addressArea", addressArea);
            request.setAttribute("gpsArea", gpsArea);
            request.setAttribute("name", name);
            request.setAttribute("surname", surname);
            RequestDispatcher view = getServletContext().getRequestDispatcher("/WEB-INF/activities.jsp");
            view.forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ignored) {
        }
    }
}