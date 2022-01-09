<%@page import="com.example.web.*"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Collections"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>S'inscrire</title>
    <link href="/CSS/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <script src="/CSS/bootstrap/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <link rel="stylesheet" href="/CSS/styleIndex.css">
</head>
<body>
<a href="/login" class="fadeIn first"><button type="button" class="btn btn-light">Retour</button></a>
<div class="wrapper fadeInDown">
    <div id="formContent">
        <%
        boolean error = false;
        ArrayList<String> names = Collections.list(request.getAttributeNames());
        for (String s : names){
            if(s.equals("error")){
                error = (boolean)request.getAttribute("error");
            }
        }
        if (error) { %>
            <text class="error">Erreur, des informations sont erronées ou ce nom d'utilisateur est déjà pris</text>
        <%}%>
        <form action="/register" method="post">
            <label> Doit contenir au moins 8 caractères
                <input type="text" id="login" class="fadeIn first" name="login" placeholder="Nom d'utilisateur" required="required" minlength="8" maxlength="32">
            </label>
            <label>Doit contenir au moins 8 caractères, une majuscule, une minuscule et un caractère spécial
                <input type="password" id="pwd" class="fadeIn second" name="pwd" placeholder="Mot de passe" required="required" minlength="8" maxlength="64">
            </label>
            <input type="text" id="name" class="fadeIn third" name="name" placeholder="Nom" required="required" maxlength="64">
            <input type="text" id="surname" class="fadeIn fourth" name="surname" placeholder="Prénom" required="required" maxlength="64">
            <input type="date" id="birthday" class="fadeIn fifth" name="birthday" placeholder="xx/xx/xxxx" required="required">
            <input type="submit" class="fadeIn third" value="S'inscrire">
        </form>
    </div>
</div>
</body>