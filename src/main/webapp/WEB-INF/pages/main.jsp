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

        <c:url value="/logout" var="logoutUrl" />
        <form action="${logoutUrl}" method="post" id="logoutForm">
            <input type="hidden" name="${_csrf.parameterName}"
                                 value="${_csrf.token}" />
        </form>
        <script>
            function formSubmit() {
                document.getElementById("logoutForm").submit();
            }
        </script>

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
                    <li><a href="<c:url value='/app/myevents' />">Mes évènements</a></li>
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
                <div class="col-xs-2 sidebar-outer">
                    <hr>
                    <div class="sidebar">
                        <h4>Par type d'évènement</h4>
                        <ul>
                            <li><input type="checkbox" id="rando" onclick="boxclick(this,'rando')" /> Randonnées</li>
                            <li><input type="checkbox" id="velo" onclick="boxclick(this,'velo')" /> Cyclisme</li>
                        </ul>

                        <h4>Par date</h4>
                    </div>
                </div>
                <div class="col-xs-6" id="right">

                    <h2>Nouveaux évènements</h2>

                    <hr>

                    <div class="event-list">
                    </div>

                </div>

                <div class="col-xs-4"><!--map-canvas will be postioned here--></div>

            </div>
        </div>

        <script src="<c:url value="/static/js/jquery-1.11.2.js" />"></script>
        <script src="<c:url value="/static/js/bootstrap.min.js" />"></script>
        <script src="http://maps.google.com/maps/api/js?output=embed"></script>
        <script src="<c:url value="/static/js/events.js" />"></script>
        <script src="<c:url value="/static/js/follow.js" />"></script>
    </body>
</html>
