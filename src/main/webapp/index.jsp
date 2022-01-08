<%@page import="com.example.web.*"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Collections"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Se connecter</title>
    <link href="/CSS/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <script src="/CSS/bootstrap/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <link rel="stylesheet" href="/CSS/styleIndex.css">
</head>
<body>

    <div class="wrapper fadeInDown">
        <div id="formContent">
            <%
            boolean error = false;
            boolean registered = false;
            ArrayList<String> names = Collections.list(request.getAttributeNames());
            for (String s : names){
                if(s.equals("error")){
                    error = (boolean)request.getAttribute("error");
                }
                if(s.equals("registered")){
                    registered = (boolean)request.getAttribute("registered");
                }
            }
            if (error){%>
                <text class="error">Erreur, veuillez vérifier votre login ou mot de passe.</text>
            <%}
            if (registered) {%>
                <text class="success">Inscription réussie, vous pouvez vous connecter.</text>
            <%}%>
            <form action="/login" method="post">
                <input type="text" id="login" class="fadeIn first" name="login" placeholder="Nom d'utilisateur" required="required" minlength="8" maxlength="64">
                <input type="password" id="pwd" class="fadeIn second" name="pwd" placeholder="Mot de passe" required="required" minlength="8" maxlength="64">
                <input type="submit" class="fadeIn third" value="Connexion">
            </form>
            <text>Pas encore de compte ? <a class="underlineHover" href="/register">Créez en un ici</a>.</text><br/>
            <div id="formFooter">
                <a class="underlineHover" href="#">Forgot Password?</a>
            </div>

        </div>
    </div>
</body>
</html>
