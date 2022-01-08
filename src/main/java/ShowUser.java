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
            st = con.prepareStatement("select login, name, surname, birthday from users where id=?");
            st.setInt(1, userId);
            rs = st.executeQuery();
            rs.next();
            String userLogin = rs.getString(1);
            String userName = rs.getString(2);
            String userSurname = rs.getString(3);
            String userBirthday = rs.getString(4);
            st.close();
            st = con.prepareStatement("select name, surname from users where id = ?");
            st.setInt(1, id);
            rs = st.executeQuery();
            rs.next();
            String name = rs.getString(1);
            String surname = rs.getString(2);
            st.close();
            con.close();
            request.setAttribute("userId", userId);
            request.setAttribute("userLogin", userLogin);
            request.setAttribute("userName", userName);
            request.setAttribute("userSurname", userSurname);
            request.setAttribute("userBirthday", userBirthday);
            request.setAttribute("isFriend", isFriend);
            request.setAttribute("reqSent", reqSent);
            request.setAttribute("hasSent", hasSent);
            request.setAttribute("name", name);
            request.setAttribute("surname", surname);
            RequestDispatcher view = getServletContext().getRequestDispatcher("/WEB-INF/showUser.jsp");
            view.forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ignored) {
        }
    }
}