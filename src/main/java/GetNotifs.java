import tools.DBConnect;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

// Servlet Name
@WebServlet("/getnotifs")
public class GetNotifs extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();

            int id = (int)session.getAttribute("id");

            Connection con = DBConnect.initializeDatabase();
            PreparedStatement st = con
                    .prepareStatement("select * from notifications where idUser=?");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            PreparedStatement st2;
            int numNotifs = 0;
            ArrayList<Integer> idNotif = new ArrayList<>();
            ArrayList<Integer> type = new ArrayList<>();
            ArrayList<Timestamp> date = new ArrayList<>();
            ArrayList<String> text = new ArrayList<>();
            ArrayList<Boolean> read = new ArrayList<>();
            ArrayList<String> who = new ArrayList<>();
            ArrayList<Integer> idWho = new ArrayList<>();
            int idSender = -1;
            ResultSet rs2;
            while(rs.next()){
                idNotif.add(rs.getInt(1));
                type.add(rs.getInt(4));
                date.add(rs.getTimestamp(5));
                text.add(rs.getString(6));
                read.add(rs.getBoolean(7));
                idSender = rs.getInt(3);
                idWho.add(idSender);
                st2 = con.prepareStatement("select name, surname from users where id=?");
                st2.setInt(1, idSender);
                rs2 = st2.executeQuery();
                rs2.next();
                who.add(rs2.getString(1) + " " + rs2.getString(2));
                st2.close();
                numNotifs++;
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
            request.setAttribute("numNotifs", numNotifs);
            request.setAttribute("idNotif", idNotif);
            request.setAttribute("type", type);
            request.setAttribute("date", date);
            request.setAttribute("text", text);
            request.setAttribute("read", read);
            request.setAttribute("idWho", idWho);
            request.setAttribute("who", who);
            request.setAttribute("name", name);
            request.setAttribute("surname", surname);
            request.setAttribute("admin", admin);
            RequestDispatcher view = getServletContext().getRequestDispatcher("/WEB-INF/notifs.jsp");
            view.forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ignored) {
        }
    }
}