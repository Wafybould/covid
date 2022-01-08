<%@page import="com.example.web.*"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Collections"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
    <% String loginUser = (String)request.getAttribute("userLogin"); %>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Profil de <%= loginUser %> </title>
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
    <li><form action="/searchuser" method="post"><input type="submit" value="Rechercher un utilisateur"></form></li>
    <li><form action="/getactivities" method="post"><input type="submit" value="Accéder aux activités"></form></li>
    <li><form action="/logout" method="post"><input type="submit" value="Déconnexion"></form></li>
</ul>
<div class="paddedNav">
    <table>
        <tr>
            <th>Informations personnelles de cet utilisateur</th>
            <th>Activités récentes à laquelle cet utilisateur a participé</th>
        </tr>
        <tr>
            <td>@<%= loginUser %></td>
        </tr>
        <tr>
            <td><%= request.getAttribute("userName")%> <%= request.getAttribute("userSurname") %></td>
        </tr>
        <tr>
            <td>Né(e) le : <%= request.getAttribute("userBirthday") %></td>
        </tr>
        <tr><%  boolean isFriend = (boolean)request.getAttribute("isFriend");
            boolean reqSent = (boolean)request.getAttribute("reqSent");
            boolean hasSent = (boolean)request.getAttribute("hasSent");
            String method;
            if(!isFriend){
                if(!reqSent){
                    method = "/addfriend";
                } else {
                    method = "";
                }
            } else {
                method = "/delfriend";
            } %>
            <td>
            <%   if (hasSent) { %>
                    <table>
                        <tr>
                            <td colspan="2">Cette personne vous a déjà envoyé une demande en ami, l'accepter ?</td>
                        </tr>
                        <tr>
                            <td>
                                <form action="/handlerequest" method="post">
                                    <input type="hidden" id="from1" name="from1" value="user">
                                    <input type="hidden" id="idAccept" name="idAccept" value="<%= request.getAttribute("userId") %>">
                                    <input type="submit" value="Oui">
                                </form>
                            </td>
                            <td>
                                <form action="/handlerequest" method="post">
                                    <input type="hidden" id="from2" name="from2" value="user">
                                    <input type="hidden" id="idRefuse" name="idRefuse" value="<%= request.getAttribute("userId") %>">
                                    <input type="submit" value="Non">
                                </form>
                            </td>
                        </tr>
                    </table>
            <%  } else { %>
                <form action="<%= method %>" method="post">
                    <input type="hidden" id="userId" name="userId" value="<%= request.getAttribute("userId") %>">
                <%  if (!isFriend) {
                        if (!reqSent) {%>
                            <input type="submit" value="Ajouter cet utilisateur en ami">
                    <%  } else { %>
                            <input type="submit" value="Vous avez déjà envoyé une demande en ami à cet utilisateur" disabled>
                    <%  }
                    } else { %>
                        <input type="submit" value="Supprimer cet utilisateur de mes amis">
                <%  } %>
                </form>
            <%  } %>
            </td>
        </tr>
    </table>
</div>
</body>
</html>