import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

// Servlet Name
@WebServlet("/handlerequest")
public class HandleRequest extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            int id = (int) session.getAttribute("id");
            Connection con = DBConnect.initializeDatabase();
            PreparedStatement st;
            ResultSet rs;
            int idUser;
            String from;
            if (request.getParameter("idAccept") != null){
                idUser = Integer.parseInt(request.getParameter("idAccept"));
                from = request.getParameter("from1");
                st = con.prepareStatement("select id from friendslist where idUser = ?");
                st.setInt(1, id);
                rs = st.executeQuery();
                rs.next();
                int idList = rs.getInt(1);
                st.close();
                st = con.prepareStatement("insert into friendslink values (?,?)");
                st.setInt(1, idList);
                st.setInt(2, idUser);
                st.executeUpdate();
                st.close();

                st = con.prepareStatement("select id from friendslist where idUser = ?");
                st.setInt(1, idUser);
                rs = st.executeQuery();
                rs.next();
                idList = rs.getInt(1);
                st.close();
                st = con.prepareStatement("insert into friendslink values (?,?)");
                st.setInt(1, idList);
                st.setInt(2, id);
                st.executeUpdate();
                st.close();
            } else {
                idUser = Integer.parseInt(request.getParameter("idRefuse"));
                from = request.getParameter("from2");
            }

            st = con.prepareStatement("delete from friendrequests where idFrom = ? and idTo = ?");
            st.setInt(1, idUser);
            st.setInt(2, id);
            st.executeUpdate();
            st.close();
            st = con.prepareStatement("delete from notifications where idUser = ? and idOriginUser = ? and notifType = 0");
            st.setInt(1, id);
            st.setInt(2, idUser);
            st.executeUpdate();
            st.close();
            con.close();
            if (from.equals("notifs")){
                RequestDispatcher view = getServletContext().getRequestDispatcher("/getnotifs");
                view.forward(request, response);
            } else {
                request.setAttribute("redirect", true);
                request.setAttribute("idUser", idUser);
                RequestDispatcher view = getServletContext().getRequestDispatcher("/showuser");
                view.forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ignored) {
        }
    }
}