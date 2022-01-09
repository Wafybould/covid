import tools.DBConnect;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

// Servlet Name
@WebServlet("/searchuser")
public class SearchUser extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        try {
            if(request.getParameter("type") == null){
                int id = (int) session.getAttribute("id");
                Connection con = DBConnect.initializeDatabase();
                PreparedStatement st = con.prepareStatement("select name, surname, admin from users where id = ?");
                st.setInt(1, id);
                ResultSet rs = st.executeQuery();
                rs.next();
                String name = rs.getString(1);
                String surname = rs.getString(2);
                Boolean admin = rs.getBoolean(3);
                st.close();
                con.close();
                request.setAttribute("name", name);
                request.setAttribute("surname", surname);
                request.setAttribute("admin", admin);
                RequestDispatcher view = getServletContext().getRequestDispatcher("/WEB-INF/searchUser.jsp");
                view.forward(request, response);
            } else {
                int id = (int) session.getAttribute("id");
                String type = request.getParameter("type");
                String search = request.getParameter("search");
                Connection con = DBConnect.initializeDatabase();
                String sqlReq = "select id, login, name, surname from users where " + type + " like '%" + search + "%'";
                PreparedStatement st = con.prepareStatement(sqlReq);
                ResultSet rs = st.executeQuery();
                int numResults = 0;
                ArrayList<Integer> ids = new ArrayList<>();
                ArrayList<String> logins = new ArrayList<>();
                ArrayList<String> names = new ArrayList<>();
                ArrayList<String> surnames = new ArrayList<>();
                int userId;
                while (rs.next()) {
                    userId = rs.getInt(1);
                    if (id != userId) {
                        ids.add(rs.getInt(1));
                        logins.add(rs.getString(2));
                        names.add(rs.getString(3));
                        surnames.add(rs.getString(4));
                        numResults++;
                    }
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
                request.setAttribute("numResults", numResults);
                request.setAttribute("ids", ids);
                request.setAttribute("logins", logins);
                request.setAttribute("names", names);
                request.setAttribute("surnames", surnames);
                request.setAttribute("name", name);
                request.setAttribute("surname", surname);
                request.setAttribute("admin", admin);
                RequestDispatcher view = getServletContext().getRequestDispatcher("/WEB-INF/searchUser.jsp");
                view.forward(request, response);
            }
    } catch (SQLException e) {
        e.printStackTrace();
    } catch (ClassNotFoundException ignored) {
    }
    }
}