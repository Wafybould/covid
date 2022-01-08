<%@page import="com.example.web.*"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Notifications</title>
    <link href="/CSS/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <script src="/CSS/bootstrap/js/bootstrap.min.js"></script>
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
    <link href="/CSS/styleIndex.css" rel="stylesheet">
</head>
<body>
<ul class="navPad">
    <i class="navTitle"><h2><%= request.getAttribute("name") %> <%= request.getAttribute("surname") %></h2></i>
    <li><form action="/login" method="post"><input type="submit" value="Retour à l'accueil"></form></li>
    <li><form action="/showprofile" method="post"><input type="submit" value="Modifier mon profil"></form></li>
    <li><form action="/getfriends" method="post"><input type="submit" value="Liste d'amis"></form></li>
    <li><form action="/getnotifs" method="post"><input type="submit" value="Notifications" disabled></form></li>
    <li><form action="/searchuser" method="post"><input type="submit" value="Rechercher un utilisateur"></form></li>
    <li><form action="/getactivities" method="post"><input type="submit" value="Accéder aux activités"></form></li>
    <%  if ( (Boolean)request.getAttribute("admin") ) { %>
    <li><form action="/getareas" method="post"><input type="submit" value="Accéder aux lieux"></form></li>
    <%  } %>
    <li><form action="/logout" method="post"><input type="submit" value="Déconnexion"></form></li>
</ul>
<div class="paddedNav">
    <%  int numNotifs = (int)request.getAttribute("numNotifs");
        if (numNotifs > 0) {
            ArrayList<Integer> type = (ArrayList<Integer>)request.getAttribute("type");
            ArrayList<Timestamp> date = (ArrayList<Timestamp>)request.getAttribute("date");
            ArrayList<String> text = (ArrayList<String>)request.getAttribute("text");
            ArrayList<Boolean> read = (ArrayList<Boolean>)request.getAttribute("read");
            ArrayList<String> who = (ArrayList<String>)request.getAttribute("who");
            ArrayList<Integer> idWho = (ArrayList<Integer>)request.getAttribute("idWho");
            ArrayList<Integer> idNotif = (ArrayList<Integer>)request.getAttribute("idNotif");
            String color;
            boolean accept = false;
            String shadow;
            String markAs;
            %>
            <table>
                <tr>
                    <th>Date</th>
                    <th>Texte</th>
                    <th>Envoyé par</th>
                    <th></th>
                    <th></th>
                    <th></th>
                </tr>
                <%  for (int i = 0; i < numNotifs; i++){
                        accept = false;
                        if( type.get(i) == 0 ){
                            color = "green";
                            accept = true;
                        } else if (type.get(i) == 1) {
                            color = "red";
                        } else {
                            color="orange";
                        }
                        if ( read.get(i) ){
                            shadow = "\">";
                            markAs = "Marquer comme non lu";
                        } else {
                            shadow = ";box-shadow: 2px 2px 2px 1px rgba(0, 0, 0, 0.2);\">";
                            markAs = "Marquer comme lu";
                        }%>
                        <tr style="color:<%= color %><%= shadow %>
                            <td>
                                <%= new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(date.get(i)).toString() %>
                            </td>
                            <td>
                                <%= text.get(i) %>
                            </td>
                            <td>
                                <%= who.get(i) %>
                            </td>
                            <td>
                                <% if (accept) { %>
                                    <table>
                                        <tr>
                                            <td colspan="2">Accepter ?</td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <form action="/handlerequest" method="post">
                                                    <input type="hidden" id="from1" name="from1" value="notifs">
                                                    <input type="hidden" id="idAccept" name="idAccept" value="<%= idWho.get(i) %>">
                                                    <input type="submit" value="Oui">
                                                </form>
                                            </td>
                                            <td>
                                                <form action="/handlerequest" method="post">
                                                    <input type="hidden" id="from2" name="from2" value="notifs">
                                                    <input type="hidden" id="idRefuse" name="idRefuse" value="<%= idWho.get(i) %>">
                                                    <input type="submit" value="Non">
                                                </form>
                                            </td>
                                        </tr>
                                    </table>
                                <% } %>
                            </td>
                            <td>
                                <form action="/handlenotif" method="post">
                                    <input type="hidden" id="changeRead" name="changeRead" value="<%= idNotif.get(i) %>">
                                    <input type="submit" value="<%= markAs %>">
                                </form>
                            </td>
                            <td>
                                <form action="/handlenotif" method="post">
                                    <input type="hidden" id="delete" name="delete" value="<%= idNotif.get(i) %>">
                                    <input type="submit" value="Supprimer">
                                </form>
                            </td>
                        </tr>
                <%  } %>
            </table>
    <%  } else { %>
            <h1>Aucune notification</h1>
    <%  } %>
</div>
</body>
</html>