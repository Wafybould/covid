<%@page import="com.example.web.*"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Collections"%>
<%@page import="java.sql.*"%>
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
    <%  if ( (Boolean)request.getAttribute("admin") ) { %>
    <li><form action="/getareas" method="post"><input type="submit" value="Accéder aux lieux"></form></li>
    <%  } %>
    <li><form action="/logout" method="post"><input type="submit" value="Déconnexion"></form></li>
</ul>
<div class="paddedNav">
<%  Boolean admin = (Boolean) request.getAttribute("admin");
    Boolean isAdmin = (Boolean) request.getAttribute("isAdmin");
    String adminButton = "Ajouter cet administrateur";
    if ( isAdmin ){
        adminButton = "Supprimer cet administrateur";
    }
    int userId = (int) request.getAttribute("userId"); %>
    <table>
        <tr>
            <th>Informations personnelles de cet utilisateur</th>
            <th>Activités récentes à laquelle cet utilisateur participe ou a participé</th>
        <%  if (admin) { %>
            <th>Actions relatives à cet utilisateur</th>
        <%  } %>
        </tr>
    <%  int numAct = (int)request.getAttribute("numAct");
        Boolean noAct = false;
        int rep = 4;
        if (numAct > rep){
            rep = numAct;
        }
        ArrayList<Integer> idAct = (ArrayList<Integer>) request.getAttribute("idAct");
        ArrayList<String> nameAct = (ArrayList<String>) request.getAttribute("nameAct");
        ArrayList<Integer> idCreator = (ArrayList<Integer>) request.getAttribute("idCreator");
        ArrayList<Boolean> isMe = (ArrayList<Boolean>) request.getAttribute("isMe");
        ArrayList<String> loginCreator = (ArrayList<String>) request.getAttribute("loginCreator");
        ArrayList<String> nameCreator = (ArrayList<String>) request.getAttribute("nameCreator");
        ArrayList<Date> date = (ArrayList<Date>) request.getAttribute("date");
        ArrayList<Boolean> participates = (ArrayList<Boolean>) request.getAttribute("participates");
        String actionUser;
        String gpsText;
        String actionActivity;
        String delActivity;
        for ( int i = 0 ; i < rep ; i++ ) {%>
            <tr>
            <%  if ( i==0 ) { %>
                    <td>@<%= loginUser %></td>
            <%  }else if ( i == 1 ) { %>
                    <td><%= request.getAttribute("userName")%> <%= request.getAttribute("userSurname") %></td>
            <%  } else if ( i == 2 ) { %>
                    <td>Né(e) le : <%= request.getAttribute("userBirthday") %></td>
            <%  } else if ( i == 3 ) {
                    boolean isFriend = (boolean)request.getAttribute("isFriend");
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
                    <%  if (hasSent) { %>
                            <table>
                                <tr>
                                    <td colspan="2">Cette personne vous a déjà envoyé une demande en ami, l'accepter ?</td>
                                </tr>
                                <tr>
                                    <td>
                                        <form action="/handlerequest" method="post">
                                            <input type="hidden" id="from1" name="from1" value="user">
                                            <input type="hidden" id="idAccept" name="idAccept" value="<%= userId %>">
                                            <input type="submit" value="Oui">
                                        </form>
                                    </td>
                                    <td>
                                        <form action="/handlerequest" method="post">
                                            <input type="hidden" id="from2" name="from2" value="user">
                                            <input type="hidden" id="idRefuse" name="idRefuse" value="<%= userId %>">
                                            <input type="submit" value="Non">
                                        </form>
                                    </td>
                                </tr>
                            </table>
                    <%  } else { %>
                            <form action="<%= method %>" method="post">
                                <input type="hidden" id="userId" name="userId" value="<%= userId %>">
                            <%  if (!isFriend) {
                                    if (!reqSent) {%>
                                        <input type="submit" value="Ajouter cet utilisateur en ami">
                                <%  } else { %>
                                        <input type="submit" value="Demandé en ami" disabled>
                                <%  }
                                } else { %>
                                    <input type="submit" value="Supprimer cet utilisateur de mes amis">
                            <%  } %>
                            </form>
                    <%  } %>
                    </td>
            <%  } else { %>
                    <td></td>
            <%  }
                if ( i < numAct ) {
                    actionUser = "Créé par vous" +
                                    "<form action=\"/login\" method=\"post\">" +
                                    "<input type=\"submit\" value=\"Accéder à mon compte\"></form>";
                    if ( !isMe.get(i) ) {
                        actionUser = "Créé par @" + loginCreator.get(i) + " (" + nameCreator.get(i) + ")" +
                                        "<form action=\"/showuser\" method=\"post\">" +
                                        "<input type=\"hidden\" id=\"idUser\" name=\"idUser\" value=\"" + idCreator.get(i) +"\">" +
                                        "<input type=\"submit\" value=\"Voir ce profil\"></form>";
                        if ( !participates.get(i) ){
                            actionActivity = "<form action=\"/joinactivity\" method=\"post\">" +
                                                "<input type=\"hidden\" id=\"joined\" name=\"joined\" value=\"0\">" +
                                                "<input type=\"hidden\" id=\"idAct\" name=\"idAct\" value=\"" + idAct.get(i) + "\">" +
                                                "<input type=\"submit\" value=\"Joindre cette activité\"></form>";
                        } else {
                            actionActivity = "<form action=\"/joinactivity\" method=\"post\">" +
                                                "<input type=\"hidden\" id=\"joined\" name=\"joined\" value=\"1\">" +
                                                "<input type=\"hidden\" id=\"idAct\" name=\"idAct\" value=\"" + idAct.get(i) + "\">" +
                                                "<input type=\"submit\" value=\"Quitter cette activité\"></form>";
                        }
                    } else {
                        actionActivity = "<form action=\"/delactivity\" method=\"post\">" +
                                            "<input type=\"hidden\" id=\"idAct\" name=\"idAct\" value=\"" + idAct.get(i) + "\">" +
                                            "<input type=\"submit\" value=\"Supprimer cette activité\"></form>";
                    } %>
                    <td>
                        <table>
                            <tr>
                                <td><%= nameAct.get(i) %></td>
                                <td><%= actionUser %></td>
                                <td><%= date.get(i) %></td>
                                <td><%= actionActivity %></td>
                            <%  if ( !isMe.get(i) && admin) {%>
                                    <td>
                                        <form action="/delactivit\" method="post">
                                            <input type=hidden id="idAct" name="idAct" value=<%= idAct.get(i) %>">
                                            <input type="submit" value="Supprimer cette activité">
                                        </form>
                                    </td>
                            <%  } %>
                            </tr>
                        </table>
                    </td>
            <%  } else { %>
                    <td></td>
            <%  }
                if ( i == 0 ) {
                    if (admin) { %>
                    <td rowspan="4">
                        <form action="/deluser" method="post">
                            <input type="hidden" id="userId" name="userId" value="<%= userId %>">
                            <input type="submit" value="Supprimer cet utilisateur">
                        </form>
                        <form action="/manageadmin" method="post">
                            <input type="hidden" id="userId" name="userId" value="<%= userId %>">
                            <input type="submit" value="<%= adminButton %>">
                        </form>
                    </td>
                <%  }
                } %>
            </tr>
    <%  } %>
    </table>
</div>
</body>
</html>