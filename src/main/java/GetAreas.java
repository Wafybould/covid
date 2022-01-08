import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

// Servlet Name
@WebServlet("/getareas")
public class GetAreas extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();

            int id = (int)session.getAttribute("id");

            Connection con = DBConnect.initializeDatabase();
            PreparedStatement st = con
                    .prepareStatement("select * from area");
            ResultSet rs = st.executeQuery();
            int numAreas = 0;
            ArrayList<Integer> idArea = new ArrayList<>();
            ArrayList<String> nameArea = new ArrayList<>();
            ArrayList<String> addressArea = new ArrayList<>();
            ArrayList<String> gpsArea = new ArrayList<>();
            while(rs.next()){
                idArea.add(rs.getInt(1));
                nameArea.add(rs.getString(2));
                addressArea.add(rs.getString(3));
                gpsArea.add(rs.getString(4));
                numAreas++;
            }
            st = con.prepareStatement("select name, surname, admin from users where id = ?");
            st.setInt(1, id);
            rs = st.executeQuery();
            rs.next();
            String name = rs.getString(1);
            String surname = rs.getString(2);
            Boolean admin = rs.getBoolean(3);
            st.close();
            con.close();
            request.setAttribute("numAreas", numAreas);
            request.setAttribute("idArea", idArea);
            request.setAttribute("nameArea", nameArea);
            request.setAttribute("addressArea", addressArea);
            request.setAttribute("gpsArea", gpsArea);
            request.setAttribute("name", name);
            request.setAttribute("surname", surname);
            request.setAttribute("admin", admin);
            RequestDispatcher view = getServletContext().getRequestDispatcher("/WEB-INF/showAreas.jsp");
            view.forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ignored) {
        }
    }
}