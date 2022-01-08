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
<%  String name = (String)request.getAttribute("name");
    String surname = (String)request.getAttribute("surname");%>
    <i class="navTitle"><h2><%= name %> <%= surname %></h2></i>
    <li><form action="/login" method="post"><input type="submit" value="Retour à l'accueil"></form></li>
    <li><form action="/showprofile" method="post"><input type="submit" value="Modifier mon profil" disabled></form></li>
    <li><form action="/getfriends" method="post"><input type="submit" value="Liste d'amis"></form></li>
    <li><form action="/getnotifs" method="post"><input type="submit" value="Notifications"></form></li>
    <li><form action="/searchuser" method="post"><input type="submit" value="Rechercher un utilisateur"></form></li>
    <li><form action="/getactivities" method="post"><input type="submit" value="Accéder aux activités"></form></li>
    <li><form action="/logout" method="post"><input type="submit" value="Déconnexion"></form></li>
</ul>
<div class="paddedNav">
<%  String login = (String)request.getAttribute("login");
    String password = (String)request.getAttribute("password");
    Date birthday = (Date)request.getAttribute("birthday");%>
    <form action="/updateprofile" method="post">
        <label>Nom d'utilisateur
            <input type="text" id="login" name="login" value="<%= login %>" required minlength="8" maxlength="32">
        </label><br/>
        <label>Mot de passe<br/>
            <input type="password" id="password" name="password" value="<%= password %>" required minlength="8" maxlength="64">
        </label><br/>
        <label>Nom
            <input type="text" id="name" name="name" value="<%= name %>" required maxlength="64">
        </label><br/>
        <label>Prénom
            <input type="text" id="surname" name="surname" value="<%= surname %>" required maxlength="64">
        </label><br/>
        <label>Date de naissance
            <input type="date" id="birthday" name="birthday" value="<%= birthday %>" required>
        </label><br/>
        <input type="submit" value="Mettre à jour mes informations">
    </form>
</div>
</body>
</html>