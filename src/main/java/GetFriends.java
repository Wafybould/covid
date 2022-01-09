import tools.DBConnect;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

// Servlet Name
@WebServlet("/getfriends")
public class GetFriends extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try {
            HttpSession session = request.getSession();

            int id = (int)session.getAttribute("id");

            Connection con = DBConnect.initializeDatabase();
            PreparedStatement st = con
                    .prepareStatement("select idFriend from friendslink where idList = (select id from friendslist where idUser=?)");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            ArrayList<String> namesList = new ArrayList<>();
            ArrayList<String> surnamesList = new ArrayList<>();
            ArrayList<Integer> idList = new ArrayList<>();
            PreparedStatement st2;
            int numFriends = 0;
            while(rs.next()){
                idList.add(rs.getInt(1));
                st2 = con.prepareStatement("select name, surname from users where id=?");
                st2.setInt(1, rs.getInt(1));
                ResultSet rs2 = st2.executeQuery();
                rs2.next();
                namesList.add(rs2.getString(1));
                surnamesList.add(rs2.getString(2));
                numFriends++;
                st2.close();
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
            request.setAttribute("numFriends", numFriends);
            request.setAttribute("friendsId", idList);
            request.setAttribute("friendsName", namesList);
            request.setAttribute("friendsSurname", surnamesList);
            request.setAttribute("name", name);
            request.setAttribute("surname", surname);
            request.setAttribute("admin", admin);
            RequestDispatcher view = getServletContext().getRequestDispatcher("/WEB-INF/friends.jsp");
            view.forward(request, response);
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}