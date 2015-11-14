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
                    <li><a href="<c:url value='/app/profile' />">Mon profil</a></li>
                    <li class="navbar-right"></li>
                </ul>
                <div class="navbar-right">
                    <a class="navbar-brand">Vous êtes connecté en tant que <c:if test="${pageContext.request.userPrincipal.name != null}"><strong>${pageContext.request.userPrincipal.name}</strong></c:if></a>
                    <a class="navbar-brand" href="javascript:formSubmit()"> Déconnexion</a>
                </div>
            </div>
        </div>

        <div class="my-events">
            <h4>Mes évènements</h4>
            <div class="table-responsive">
                <table class="table table-bordered table-hover me">
                    <thead>
                        <tr>
                            <th>Evènement</th>
                            <th>Adresse</th>
                            <th>Date</th>
                            <th>Heure</th>
                        </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>

        <hr>

        <div class="my-subscriptions">
            <h4>Mes inscriptions</h4>
            <div class="table-responsive">
                <table class="table table-bordered table-hover ms">
                    <thead>
                        <tr>
                            <th>Evènement</th>
                            <th>Adresse</th>
                            <th>Date</th>
                            <th>Heure</th>
                        </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>


        <script src="<c:url value="/static/js/jquery/jquery-1.11.2.js" />"></script>
        <script src="<c:url value="/static/js/bootstrap.min.js" />"></script>
        <script src="<c:url value="/static/js/client/profile.js" />"></script>
    </body>
</html>
