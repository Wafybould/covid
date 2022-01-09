import tools.DBConnect;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

// Servlet Name
@WebServlet("/showhuser")
public class ShowUser extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            int id = (int) session.getAttribute("id");
            int userId;
            if (request.getAttribute("redirect") == null){
                userId = Integer.parseInt(request.getParameter("idUser"));
            } else {
                userId = (int)request.getAttribute("idUser");
            }
            Connection con = DBConnect.initializeDatabase();
            PreparedStatement st = con.prepareStatement("select idFriend from friendslink where idList=(select id from friendslist where idUser=?)");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            ArrayList<Integer> friendsIds = new ArrayList<>();
            boolean isFriend = false;
            while(rs.next()){
                friendsIds.add(rs.getInt(1));
            }
            st.close();
            boolean reqSent = false;
            boolean hasSent = false;
            if (friendsIds.contains(userId)){
                isFriend = true;
            } else {
                st = con.prepareStatement("select count(*) from friendrequests where idFrom = ? and idTo = ?");
                st.setInt(1, id);
                st.setInt(2, userId);
                rs = st.executeQuery();
                rs.next();
                if (rs.getInt(1) != 0){
                    reqSent = true;
                } else {
                    st = con.prepareStatement("select count(*) from friendrequests where idFrom = ? and idTo = ?");
                    st.setInt(1, userId);
                    st.setInt(2, id);
                    rs = st.executeQuery();
                    rs.next();
                    if (rs.getInt(1) != 0){
                        hasSent = true;
                    }
                }
            }
            st.close();
            st = con.prepareStatement("select login, name, surname, birthday, admin from users where id=?");
            st.setInt(1, userId);
            rs = st.executeQuery();
            rs.next();
            String userLogin = rs.getString(1);
            String userName = rs.getString(2);
            String userSurname = rs.getString(3);
            String userBirthday = rs.getString(4);
            Boolean isAdmin = rs.getBoolean(5);
            st.close();

            st = con.prepareStatement("select activity.*, area.name, area.address, area.GPS from activity " +
                    "inner join area on activity.idArea = area.id " +
                    "where (idCreator = ? or exists (select * from activitylink where activitylink.idActivity = activity.id and activitylink.idUser = ?))" +
                    "order by activity.date desc");
            st.setInt(1, userId);
            st.setInt(2, userId);
            rs = st.executeQuery();
            int numAct = 0;
            ArrayList<Integer> idAct = new ArrayList<>();
            ArrayList<String> nameAct = new ArrayList<>();
            ArrayList<Integer> idCreator = new ArrayList<>();
            ArrayList<Boolean> isMe = new ArrayList<>();
            ArrayList<String> loginCreator = new ArrayList<>();
            ArrayList<String> nameCreator = new ArrayList<>();
            ArrayList<java.sql.Date> date = new ArrayList<>();
            ArrayList<Boolean> participates = new ArrayList<>();
            int newCreator;
            int idActivity;
            PreparedStatement st2;
            ResultSet rs2;
            PreparedStatement st3;
            ResultSet rs3;
            while(rs.next()){
                idActivity = rs.getInt(1);
                idAct.add(idActivity);
                nameAct.add(rs.getString(2));
                newCreator = rs.getInt(3);
                idCreator.add(newCreator);
                isMe.add(newCreator==id);
                st2 = con.prepareStatement("select login, name, surname from users where id=?");
                st2.setInt(1, newCreator);
                rs2 = st2.executeQuery();
                rs2.next();
                loginCreator.add(rs2.getString(1));
                nameCreator.add(rs2.getString(2) + " " + rs2.getString(3));
                st2.close();
                date.add(rs.getDate(4));
                st3 = con.prepareStatement("select count(1) from activitylink where idActivity=? and idUser=?");
                st3.setInt(1, idActivity);
                st3.setInt(2, id);
                rs3 = st3.executeQuery();
                rs3.next();
                participates.add(rs3.getInt(1) != 0);
                st3.close();
                numAct++;
            }
            st.close();

            st = con.prepareStatement("select name, surname, admin from users where id = ?");
            st.setInt(1, id);
            rs = st.executeQuery();
            rs.next();
            String name = rs.getString(1);
            String surname = rs.getString(2);
            Boolean admin = rs.getBoolean(3);
            st.close();
            con.close();
            request.setAttribute("userId", userId);
            request.setAttribute("userLogin", userLogin);
            request.setAttribute("userName", userName);
            request.setAttribute("userSurname", userSurname);
            request.setAttribute("userBirthday", userBirthday);
            request.setAttribute("isAdmin", isAdmin);
            request.setAttribute("isFriend", isFriend);
            request.setAttribute("reqSent", reqSent);
            request.setAttribute("hasSent", hasSent);
            request.setAttribute("numAct", numAct);
            request.setAttribute("idAct", idAct);
            request.setAttribute("nameAct", nameAct);
            request.setAttribute("idCreator", idCreator);
            request.setAttribute("isMe",isMe);
            request.setAttribute("loginCreator", loginCreator);
            request.setAttribute("nameCreator", nameCreator);
            request.setAttribute("date", date);
            request.setAttribute("participates", participates);
            request.setAttribute("name", name);
            request.setAttribute("surname", surname);
            request.setAttribute("admin", admin);
            RequestDispatcher view = getServletContext().getRequestDispatcher("/WEB-INF/showUser.jsp");
            view.forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ignored) {
        }
    }
}