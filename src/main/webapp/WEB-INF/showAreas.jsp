<%@page import="com.example.web.*"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Collections"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Liste des lieux</title>
    <link href="/CSS/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <script src="/CSS/bootstrap/js/bootstrap.min.js"></script>
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
    <link href="/CSS/styleIndex.css" rel="stylesheet">
</head>
<body>
<ul class="navPad">
    <i class="navTitle"><h2><%= request.getAttribute("name") %> <%= request.getAttribute("surname") %></h2></i>
    <li><form action="/login" method="post"><input type="submit" value="Retour à l'accueil" disabled></form></li>
    <li><form action="/showprofile" method="post"><input type="submit" value="Modifier mon profil"></form></li>
    <li><form action="/getfriends" method="post"><input type="submit" value="Liste d'amis"></form></li>
    <li><form action="/getnotifs" method="post"><input type="submit" value="Notifications"></form></li>
    <li><form action="/searchuser" method="post"><input type="submit" value="Rechercher un utilisateur"></form></li>
    <li><form action="/getactivities" method="post"><input type="submit" value="Accéder aux activités"></form></li>
    <%  if ( (Boolean)request.getAttribute("admin") ) { %>
    <li><form action="/getareas" method="post"><input type="submit" value="Accéder aux lieux"></form></li>
    <%  } %>
    <li><form action="/logout" method="post"><input type="submit" value="Déconnexion"></form></li>
</ul>
<div class="paddedNav">
<%  int numAreas = (int) request.getAttribute("numAreas");
    if ( numAreas == 0 ) { %>
    Aucun lieu n'a été trouvé.
<%  } else {
        ArrayList<Integer> idArea = (ArrayList<Integer>)request.getAttribute("idArea");
        ArrayList<String> nameArea = (ArrayList<String>)request.getAttribute("nameArea");
        ArrayList<String> addressArea = (ArrayList<String>)request.getAttribute("addressArea");
        ArrayList<String> gpsArea = (ArrayList<String>)request.getAttribute("gpsArea");
        String gpsText;%>
        <table>
            <tr>
                <th>Nom</th>
                <th>Adresse</th>
                <th>Localisation GPS</th>
                <th></th>
            </tr>
        <%  for ( int i = 0; i < numAreas ; i++ ) {
                if ( gpsArea.get(i).equals("") ) {
                    gpsText = "Pas de localisation disponible";
                } else {
                    gpsText = gpsArea.get(i);
                }%>
                <tr>
                    <td><%= nameArea.get(i) %></td>
                    <td><%= addressArea.get(i) %></td>
                    <td><%= gpsText %></td>
                    <td>
                        <form action="/delarea" method="post">
                            <input type="hidden" id="idArea" name="idArea" value="<%= idArea.get(i) %>">
                            <input type="submit" value="Supprimer ce lieu">
                        </form>
                    </td>
                </tr>
        <%  }   %>
    </table>
<%  } %>
</div>
</body>
</html>