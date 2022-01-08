<%@page import="com.example.web.*"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Collections"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Recherche d'utilisateur</title>
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
    <li><form action="/getnotifs" method="post"><input type="submit" value="Notifications"></form></li>
    <li><form action="/searchuser" method="post"><input type="submit" value="Rechercher un utilisateur" disabled></form></li>
    <li><form action="/getactivities" method="post"><input type="submit" value="Accéder aux activités"></form></li>
    <%  if ( (Boolean)request.getAttribute("admin") ) { %>
    <li><form action="/getareas" method="post"><input type="submit" value="Accéder aux lieux"></form></li>
    <%  } %>
    <li><form action="/logout" method="post"><input type="submit" value="Déconnexion"></form></li>
</ul>
<div class="paddedNav">
    <form action="/searchuser" method="post">
        <label for="type">Rechercher par :
            <select id="type" name="type">
                <option value="login">Pseudo</option>
                <option value="name">Nom de famille</option>
            </select>
        </label>
        <input type="text" id="search" name="search" placeholder="Votre recherche ici" required="true">
        <input type="submit" value="Rechercher">
    </form>
    <% ArrayList<String> namesAttributes = Collections.list(request.getAttributeNames());
    if (namesAttributes.contains("numResults")){
        int numResults = (int)request.getAttribute("numResults");
        ArrayList<Integer> ids = (ArrayList<Integer>)request.getAttribute("ids");
        ArrayList<String> logins = (ArrayList<String>)request.getAttribute("logins");
        ArrayList<String> names = (ArrayList<String>)request.getAttribute("names");
        ArrayList<String> surnames = (ArrayList<String>)request.getAttribute("surnames");%>
        <table>
            <tr>
                <th>Login</th>
                <th>Nom</th>
                <th>Prénom</th>
                <th></th>
            </tr>
            <% for (int i = 0; i < numResults; i++){%>
                <tr>
                    <td>
                        <%= logins.get(i) %>
                    </td>
                    <td>
                        <%= names.get(i) %>
                    </td>
                    <td>
                        <%= surnames.get(i) %>
                    </td>
                    <td>
                        <form action="/showuser" method="post">
                            <input type="hidden" id="idUser" name="idUser" value="<%= ids.get(i) %>">
                            <input type="submit" value="Voir ce profil">
                        </form>
                    </td>
                </tr>
            <% } %>
        </table>
    <% } %>
</div>
</body>
</html>