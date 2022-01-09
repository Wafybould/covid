import tools.DBConnect;

import java.io.IOException;
import java.sql.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

// Servlet Name
@WebServlet("/deluser")
public class DelUser extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            int id = (int) session.getAttribute("id");
            int userId = Integer.parseInt(request.getParameter("userId"));
            Connection con = DBConnect.initializeDatabase();

            PreparedStatement st = con.prepareStatement("select id from activity where idCreator=?");
            st.setInt(1,userId);
            ResultSet rs = st.executeQuery();
            PreparedStatement st2;
            while (rs.next()) {
                st2 = con.prepareStatement("delete from activitylink where idActivity=?");
                st2.setInt(1, rs.getInt(1));
                st2.executeUpdate();
                st2.close();
                st2 = con.prepareStatement("delete from activity where id = ?");
                st2.setInt(1, rs.getInt(1));
                st2.executeUpdate();
            }
            st.close();
            st = con.prepareStatement("delete from activitylink where idUser=?");
            st.setInt(1, userId);
            st.executeUpdate();
            st.close();

            st = con.prepareStatement("select id from friendslist where idUser = ?");
            st.setInt(1, userId);
            rs = st.executeQuery();
            while (rs.next()){
                st2 = con.prepareStatement("delete from friendslink where idList = ?");
                st2.setInt(1, rs.getInt(1));
                st2.executeUpdate();
                st2.close();
            }
            st.close();
            st = con.prepareStatement("delete from friendslink where idFriend = ?");
            st.setInt(1, userId);
            st.executeUpdate();
            st.close();
            st = con.prepareStatement("delete from friendrequests where (idFrom = ? or idTo = ?)");
            st.setInt(1, userId);
            st.setInt(2, userId);
            st.executeUpdate();
            st.close();
            st = con.prepareStatement("delete from friendslist where idUser = ?");
            st.setInt(1, userId);
            st.executeUpdate();
            st.close();

            st = con.prepareStatement("delete from notifications where (idUser = ? or idOriginUser = ?)");
            st.setInt(1, userId);
            st.setInt(2, userId);
            st.executeUpdate();
            st.close();

            st = con.prepareStatement("delete from users where id = ?");
            st.setInt(1, userId);
            st.executeUpdate();
            st.close();
            con.close();

            RequestDispatcher view = getServletContext().getRequestDispatcher("/searchuser");
            view.forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ignored) {
        }
    }
}