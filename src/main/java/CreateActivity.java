import tools.DBConnect;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

// Servlet Name
@WebServlet("/createactivity")
public class CreateActivity extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            int id = (int)session.getAttribute("id");

            String nameAct = request.getParameter("nameAct");
            Date dateAct = Date.valueOf(request.getParameter("dateAct"));
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            Time startTime = new Time(format.parse(request.getParameter("startTime")).getTime());
            Time endTime = new Time(format.parse(request.getParameter("endTime")).getTime());
            int idArea = Integer.parseInt(request.getParameter("area"));
            Connection con = DBConnect.initializeDatabase();
            PreparedStatement st;
            ResultSet rs;
            if (idArea == 0){
                String nameArea = request.getParameter("nameArea");
                String addressArea = request.getParameter("addressArea");
                String gpsArea = request.getParameter("gpsArea");
                Pattern pattern = Pattern.compile("^[(][0-9]*[.][0-9]*,[0-9]*[.][0-9]*[)]$");
                Matcher matcher = pattern.matcher(gpsArea);
                if(!matcher.find()){
                    request.setAttribute("error", true);
                    RequestDispatcher view = getServletContext().getRequestDispatcher("/getactivities");
                    view.forward(request, response);
                }
                st = con.prepareStatement("insert into area (name, address, GPS) values (?,?,?)");
                st.setString(1, nameArea);
                st.setString(2, addressArea);
                st.setString(3, gpsArea);
                st.executeUpdate();
                st.close();
                st = con.prepareStatement("select id from area order by id desc limit 1");
                rs = st.executeQuery();
                rs.next();
                idArea = rs.getInt(1);
                st.close();
            }
            st = con.prepareStatement("insert into activity (name, idCreator, date, startTime, endTime, idArea) values (?,?,?,?,?,?)");
            st.setString(1, nameAct);
            st.setInt(2, id);
            st.setDate(3, dateAct);
            st.setTime(4, startTime);
            st.setTime(5, endTime);
            st.setInt(6, idArea);
            st.executeUpdate();
            st.close();
            con.close();
            RequestDispatcher view = getServletContext().getRequestDispatcher("/getactivities");
            view.forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ignored) {
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}