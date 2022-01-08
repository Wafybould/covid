<%@page import="com.example.web.*"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Collections"%>
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
        <div><h1 class="pageTitle">Bonjour <%= request.getAttribute("name") %> <%= request.getAttribute("surname") %></h1></div>
        <div style="align:center"><form action="/covided" method="post"><input type="submit" value="J'ai attrapé le COVID, me signaler !"></form></div>
    </div>
</body>
</html>