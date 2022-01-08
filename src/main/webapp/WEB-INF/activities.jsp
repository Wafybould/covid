<%@page import="com.example.web.*"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Collections"%>
<%@page import="java.sql.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Accueil</title>
    <link href="/CSS/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <script src="/CSS/bootstrap/js/bootstrap.min.js"></script>
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
    <link href="/CSS/styleIndex.css" rel="stylesheet">
</head>
<body>
    <ul class="navPad">
        <i class="navTitle"><h2><%= request.getAttribute("name") %> <%= request.getAttribute("surname") %></h2></i>
        <li><form action="/login" method="post"><input type="submit" value="Retour à l'accueil"></form></li>
        <li><form action="/getfriends" method="post"><input type="submit" value="Liste d'amis"></form></li>
        <li><form action="/getnotifs" method="post"><input type="submit" value="Notifications"></form></li>
        <li><form action="/searchuser" method="post"><input type="submit" value="Rechercher un utilisateur"></form></li>
        <li><form action="/getactivities" method="post"><input type="submit" value="Accéder aux activités"></form></li>
        <li><form action="/logout" method="post"><input type="submit" value="Déconnexion"></form></li>
    </ul>
    <div class="paddedNav">
        <h3>Créer une activité</h3>
        <%  int numArea = (int) request.getAttribute("numArea");
            ArrayList<Integer> idArea = (ArrayList<Integer>)request.getAttribute("idArea");
            ArrayList<String> nameArea = (ArrayList<String>)request.getAttribute("nameArea");
            ArrayList<String> addressArea = (ArrayList<String>)request.getAttribute("addressArea");
            ArrayList<String> gpsArea = (ArrayList<String>)request.getAttribute("gpsArea");
            String desc;
            String gps;
            String options = "<option value="0" selected></option>";
            if (numArea != 0) {
                for ( int i = 0 ; i < numArea; i++ ) {
                    gps = ""
                    if( !gpsArea.get(i).equals("null") ){
                        gps = " (" + gpsArea.get(i) + ")";
                    }
                    desc = nameArea.get(i) + " : " + addressArea.get(i) + gps;
                    options += "<option value=\""+ idArea.get(i) +"\">" + desc + "</option>";
                }
            }%>
        <form action="/createactivity" method="post">
            <label>Création de l'activité
                <input type="text" id="nameAct" name="nameeAct" placeholder="Nom de l'activité" required="required">
                <input type="date" id="dateAct" name="dateAct" required="required">
                <input type="time" id="startTime" name="startTime" required="required">
                <input type="time" id="endTime" name="endTime" required="required">
                <select id="area">
                    <%= options %>
                </select>
            </label>
            <label>Création d'un lieu si celui que vous recherchez n'existe pas
                <input type="text" id="nameArea" name="nameArea" placeholder="Nom du lieu">
                <input type="text" id="addressArea" name="addressArea" placeholder="Adresse du lieu">
                <input type="text" id="gpsArea" name="gpsArea" placeholder="Emplacement GPS du lieu (optionnel)">
            </label>
        </form>
        <h3>Mes activités</h3>
        <%  int numAct = (int)request.getAttribute("numAct");
            if (numAct == 0){%>
                Aucune activité n'est disponible.
        <%  } else {
                ArrayList<Integer> idAct = (ArrayList<Integer>) request.getAttribute("idAct");
                ArrayList<String> nameAct = (ArrayList<String>) request.getAttribute("nameAct");
                ArrayList<Integer> idCreator = (ArrayList<Integer>) request.getAttribute("idCreator");
                ArrayList<Boolean> isMe = (ArrayList<Boolean>) request.getAttribute("isMe");
                ArrayList<String> loginCreator = (ArrayList<String>) request.getAttribute("loginCreator");
                ArrayList<String> nameCreator = (ArrayList<String>) request.getAttribute("nameCreator");
                ArrayList<Date> date = (ArrayList<Date>) request.getAttribute("date");
                ArrayList<Time> startTime = (ArrayList<Time>) request.getAttribute("startTime");
                ArrayList<Time> endTime = (ArrayList<Time>) request.getAttribute("endTime");
                ArrayList<Integer> idWhere = (ArrayList<Integer>) request.getAttribute("idWhere");
                ArrayList<String> nameWhere = (ArrayList<String>) request.getAttribute("nameWhere");
                ArrayList<String> addressWhere = (ArrayList<String>) request.getAttribute("addressWhere");
                ArrayList<String> gpsWhere = (ArrayList<String>) request.getAttribute("gpsWhere");
                String actionUser;
                String gpsText;%>
                <table>
                    <tr>
                        <th>Nom de l'activité</th>
                        <th>Créateur de l'activité</th>
                        <th>Date de l'activité</th>
                        <th>Heure de début</th>
                        <th>Heure de fin</th>
                        <th>Nom de l'emplacement</th>
                        <th>Adresse de l'emplacement</th>
                        <th>Emplacement GPS</th>
                        <th></th>
                    </tr>
                <%  for (int i = 0; i < numAct; i++) {
                        actionUser = "Créé par vous" +
                                        "<form action=\"/login\" method=\"post\">" +
                                        "<input type=\"submit\" value=\"Accéder à mon compte\"></form>";
                        if ( !isMe.get(i) ) {
                            actionUser = "Créé par @" + loginCreator + " (" + nameCreator + ")" +
                                            "<form action=\"/showuser\" method=\"post\">" +
                                            "<input type=\"hidden\" id=\"idUser\" name=\"idUser\" value=\"" + idCreator.get(i) +"\">" +
                                            "<input type=\"submit\" value=\"Voir ce profil\"></form>";
                        }
                        if ( gpsWhere.get(i).equals("null") ){
                            gpsText = "Pas d'emplacement GPS disponible";
                        } else {
                            gpsText = gpsWhere.get(i);
                        }%>
                        <tr>
                            <td><%= nameAct.get(i) %></td>
                            <td><%= actionUser %></td>
                            <td><%= date.get(i) %></td>
                            <td><%= startTime.get(i) %></td>
                            <td><%= endTime.get(i) %></td>
                            <td><%= nameWhere.get(i) %></td>
                            <td><%= addressWhere.get(i) %></td>
                            <td><%= gpsText %></td>
                        </tr>
                <%  } %>
                </table>
        <%  } %>
    </div>
</body>
</html>