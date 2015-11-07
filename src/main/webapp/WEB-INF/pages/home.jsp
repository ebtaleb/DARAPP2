<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
    <head>
        <%@ page
        language="java"
        contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8"
        %>
        <title>IDF Eventer</title>
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
        <link href="<c:url value="/static/css/bootstrap.min.css"  />" rel="stylesheet">
        <link href="<c:url value="/static/css/styles.css" />" rel="stylesheet">
    </head>
    <body>
        <div class="navbar navbar-custom navbar-fixed-top">
            <div class="navbar-header"><a class="navbar-brand" href="">IDF Eventer</a>
                <a class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </a>
            </div>
        </div>

        <div class="jumbotron">
            <div class="container">
                <h1>Bienvenue sur IDF Eventer!</h1>
                <p> IDF Eventer vous permet d'assister à des évènements sur toute l'Île-de-France. Vous pouvez aussi organiser vos propres évènements!</p>
            </div>

            <div class="row">
                <div class="col-xs-6 col-md-6">
                    <h3 style="text-align:center;">Se connecter</h3>
                    <form action="<c:url value='/login' />" method="POST" style="text-align:center;">
                        <div class="form-group">
                            <input id="short" name="username" placeholder="Identifiant" type="text">
                        </div>
                        <div class="form-group">
                            <input id="short" name="password" placeholder="Mot de passe" type="password">
                        </div>
                        <div class="form-group">
                            <button type="submit" name="login" class="btn btn-primary">Login</button>
                        </div>
                    </form>
                </div>

                <div class="col-xs-6 col-md-6">
                    <h3 style="text-align:center;">S'inscrire</h3>

                    <form:form modelAttribute="user" action="user/register" method="POST" style="text-align:center;" >

                            <div class="form-group">
                                <form:input path="username" id="short" type="text" placeholder="Identifiant"/>
                            </div>

                            <div class="form-group">
                                <form:input path="password" id="short" type="password" placeholder="Mot de passe"/>
                            </div>

                            <div class="form-group">
                                <button type="submit" name="register" class="btn btn-success">Créer compte</button>
                            </div>

                   </form:form>
                </div>
            </div>

        </div>


        <script src="<c:url value="/static/js/jquery-1.11.2.js" />"></script>
        <script src="<c:url value="/static/js/bootstrap.min.js" />"></script>
        <script src="http://maps.google.com/maps/api/js?sensor=false&output=embed"></script>
        <script src="<c:url value="/static/js/scripts.js" />"></script>
        <script src="<c:url value="/static/js/follow.js" />"></script>
    </body>
</html>
