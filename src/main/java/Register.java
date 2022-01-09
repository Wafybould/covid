import tools.DBConnect;
import tools.SHA512;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

// Servlet Name
@WebServlet("/Register")
public class Register extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("login") == null){
            RequestDispatcher view = getServletContext().getRequestDispatcher("/WEB-INF/register.jsp");
            view.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

            String login = request.getParameter("login");
            String pass = request.getParameter("pwd");
            String name = request.getParameter("name");
            String surname = request.getParameter("surname");
            String birthdayText = request.getParameter("birthday");
            Date birthday = new SimpleDateFormat("yyyy-MM-dd").parse(birthdayText);
            Date now = new Date(System.currentTimeMillis());
            Calendar c = Calendar.getInstance();
            c.setTime(new Date(System.currentTimeMillis()));
            c.add(Calendar.YEAR, -125);
            if ( !(birthday.getTime() < now.getTime() && birthday.getTime() > c.getTimeInMillis()) ){
                request.setAttribute("error", true);
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/register.jsp");
                rd.forward(request, response);
            } else {
                java.sql.Date birthdaySQL = new java.sql.Date(birthday.getTime());

                Pattern pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&€])[A-Za-z\\d@$!%*?&€]{8,}$");
                Matcher matcher = pattern.matcher(pass);
                Pattern pattern1 = Pattern.compile("^[a-zA-Z]+$");
                Matcher matcher1 = pattern1.matcher(name);
                Matcher matcher2 = pattern1.matcher(surname);
                if (!matcher.find() || !matcher1.find() || matcher2.find()) {
                    request.setAttribute("error", true);
                    RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/register.jsp");
                    rd.forward(request, response);
                } else {

                    byte[] salt = SHA512.getSalt();
                    String hashedPass = SHA512.SHA512Hash(pass, salt);
                    StringBuilder saltString = new StringBuilder();
                    for (byte aByte : salt) {
                        saltString.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
                    }

                    Connection con = DBConnect.initializeDatabase();

                    PreparedStatement st = con.prepareStatement("insert into users (login, password, name, surname, birthday, admin, salt) values(?,?,?,?,?,0,?)");

                    st.setString(1, login);
                    st.setString(2, hashedPass);
                    st.setString(3, name);
                    st.setString(4, surname);
                    st.setDate(5, birthdaySQL);
                    st.setString(6, saltString.toString());

                    st.executeUpdate();
                    st.close();

                    st = con.prepareStatement("select id from users where login=? and password=?");
                    st.setString(1, login);
                    st.setString(2, hashedPass);
                    ResultSet rs = st.executeQuery();
                    rs.next();
                    int id = rs.getInt(1);
                    st.close();

                    st = con.prepareStatement("insert into friendslist (idUser) values (?)");
                    st.setInt(1, id);
                    st.executeUpdate();
                    st.close();

                    con.close();
                    request.setAttribute("registered", true);
                    RequestDispatcher view = getServletContext().getRequestDispatcher("/index.jsp");
                    view.forward(request, response);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", true);
            RequestDispatcher view = getServletContext().getRequestDispatcher("/WEB-INF/register.jsp");
            view.forward(request, response);
        } catch (  ClassNotFoundException | ParseException e){
            e.printStackTrace();
        }
    }
}
