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
        <link href="<c:url value="/static/css/jquery-ui.css" />" rel="stylesheet">
        <link href="<c:url value="/static/css/jquery.timeentry.css" />" rel="stylesheet">
    </head>
    <body>

        <div class="navbar navbar-custom navbar-fixed-top">
            <div class="navbar-header"><a class="navbar-brand">IDF Eventer</a>
                <a class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </a>
            </div>
            <div class="navbar-collapse collapse">
                <ul class="nav navbar-nav">
                    <li class="active"><a href="<c:url value='/app/main' />">Accueil</a></li>
                    <li><a href="<c:url value='/app/newevent' />">Créer nouveau évènement</a></li>
                    <li><a href="<c:url value='/app/profile' />">Mon profil</a></li>
                    <li class="navbar-right"></li>
                </ul>
                <div class="navbar-right">
                    <a class="navbar-brand">Vous êtes connecté en tant que <c:if test="${pageContext.request.userPrincipal.name != null}"><strong>${pageContext.request.userPrincipal.name}</strong></c:if></a>
                    <a class="navbar-brand" href="javascript:formSubmit()"> Logout</a>
                </div>
            </div>
        </div>

        <div id="map-canvas"></div>

        <div class="container-fluid" id="main">
            <div class="row">
                <div class="col-xs-8" id="right">
                        <div class="row">
                            <div class="col-xs-6 col-sm-6 col-md-6">
                                <h2>Nouvel évènement</h2>
                            </div>
                            <div class="col-xs-6 col-sm-6 col-md-6">

                                <form name="controlPanel" id="controlPanel">

                                    <div id="noDefaultLocButtons">
                                        <input type="button" class="btn btn-info" name="startRecording" id="startRecording" value="Activer trace de la route">
                                        <input type="button" class="btn btn-info" name="removeLastLegButton" id="removeLastLegButton" value="Enlever dernier point" onclick="removeLastLeg()">
                                    </div>
                                    
                                    <table class="plain">
                                        <tr class="distanceRow">
                                            <td class="col1">Distance : </td>
                                            <td><span id="mileage">0</span> <span id="dstUnits1">km</span></td>
                                        </tr>
                                    </table>

                                    <div class="para"><a href="javascript:clearLinkHandler();">Tout effacer</a></div>
                                </form>
                            </div>
                        </div>
                    

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
                                    <label for="date">Date </label>
                                    <input type="text" class="form-control" id="datepicker">
                                </div>
                            </div>
                            <div class="col-xs-6 col-sm-6 col-md-6">
                                <div class="form-group">
                                    <label for="time">Heure </label>
                                    <input type="text" class="form-control" id="timepicker">
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-xs-6 col-sm-6 col-md-6">
                                <div class="form-group">
                                    <li> <label for="eventtype">Type d'évenement </label>
                                        <ul>
                                            <li><label for="rando">Randonnée</label> <input type="radio" name="eventtype" value="r"></li>
                                            <li><label for="velo">Cyclisme</label> <input type="radio" name="eventtype" value="v"></li>
                                        </ul>
                                    </li>
                                </div>
                            </div>
                            <div class="col-xs-6 col-sm-6 col-md-6">
                                <div class="form-group">
                                    <button type="button" class="btn btn-default send-event">Soumettre</button>
                                </div>
                            </div>
                        </div>

                    </form>

                </div>

                <div class="col-xs-4"></div>
            </div>
        </div>



        <script src="<c:url value="/static/js/jquery-1.11.2.js" />"></script>
        <script src="<c:url value="/static/js/bootstrap.min.js" />"></script>
        <script src="<c:url value="/static/js/noty/packaged/jquery.noty.packaged.min.js" />"></script>
        <script src="<c:url value="/static/js/jquery-ui.js" />"></script>
        <script src="<c:url value="/static/js/jquery.plugin.js" />"></script>
        <script src="<c:url value="/static/js/jquery.timeentry.js" />"></script>
        <script src="<c:url value="/static/js/gmp.js" />"></script>
        <script src="<c:url value="/static/js/event_registration.js" />"></script>
    </body>
</html>
