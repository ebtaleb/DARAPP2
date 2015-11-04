<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <!-- begin template -->
        <div class="navbar navbar-custom navbar-fixed-top">
            <div class="navbar-header"><a class="navbar-brand" href="#">IDF Eventer</a>
                <a class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </a>
            </div>
            <div class="navbar-collapse collapse">
                <ul class="nav navbar-nav">
                    <li class="active"><a href="#">Accueil</a></li>
                    <li><a href="/app/newevent">Créer nouveau évènement</a></li>
                    <li><a href="#">Mes évènements</a></li>
                    <li>&nbsp;</li>
                </ul>
            </div>
        </div>

        <div class="container">
            <h2>Nouvel évènement</h2>
            <br>
            <form role="form">

                <div class="form-group">
                    <label for="title">Titre</label>
                    <input type="text" class="form-control" id="title" placeholder="Titre de l'évènement">
                </div>

                <div class="form-group">
                    <label for="descr">Description </label>
                    <textarea class="form-control" rows="5" id="descr" placeholder="Descriptif de l'évènement"></textarea>
                </div>

                <div class="form-group">
                    <label for="addr">Addresse </label>
                    <input type="text" class="form-control" id="addr" placeholder="Addresse">
                </div>
                <div class="row">
                    <div class="col-xs-6 col-sm-6 col-md-6">
                        <div class="form-group">
                            <label for="zip">Code postal </label>
                            <input type="text" class="form-control" id="zipcode" placeholder="75000">
                        </div>
                    </div>
                    <div class="col-xs-6 col-sm-6 col-md-6">
                        <div class="form-group">
                            <label for="city">Ville </label>
                            <input type="text" class="form-control" id="city" placeholder="Paris">
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-xs-6 col-sm-6 col-md-6">
                        <div class="form-group">
                            <li> <label for="eventtype">Type d'évenement </label>
                                <ul>
                                    <li><label for="rando">Randonnée</label> <input type="radio" name="eventtype" value=""></li>
                                    <li><label for="velo">Cyclisme</label> <input type="radio" name="eventtype" value=""></li>
                                </ul>
                            </li>
                        </div>
                    </div>
                    <div class="col-xs-6 col-sm-6 col-md-6">
                        <div class="form-group">
                            <button type="submit" class="btn btn-default">Soumettre</button>
                        </div>
                    </div>
                </div>

            </form>
        </div>

        <script src="<c:url value="/static/js/jquery-1.11.2.js" />"></script>
        <script src="<c:url value="/static/js/bootstrap.min.js" />"></script>
    </body>
</html>
