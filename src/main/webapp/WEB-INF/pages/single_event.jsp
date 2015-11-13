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
                    </div>
                </div>
                <div class="col-xs-6" id="right">

                    <hr>

                    <div class="event">
                        <c:if test="${not empty title}">
                        <div class="title">${title}</div>
                        </c:if>
                        <c:if test="${not empty desc}">
                        <div class="desc">${desc}</div>
                        </c:if>
                        <c:if test="${not empty addr}">
                        <div class="addr">${addr}</div>
                        </c:if>
                        <c:if test="${not empty date}">
                        <div class="date">${date}</div>
                        </c:if>
                        <c:if test="${not empty time}">
                        <div class="time">${time}</div>
                        </c:if>
                        <c:if test="${not empty lat}">
                        <div hidden class="lat">${lat}</div>
                        </c:if>
                        <c:if test="${not empty lng}">
                        <div hidden class="lng">${lng}</div>
                        </c:if>
                        <c:if test="${not empty path}">
                        <div hidden class="path">${path}</div>
                        </c:if>
                        <c:if test="${not empty event_id}">
                        <div hidden class="event_id">${event_id}</div>
                        </c:if>
                        <c:if test="${not empty button}">
                        ${button}
                        </c:if>
                    </div>

                    <hr>
                    <h4>Commentaires</h4>
                    <div class="comments bubble-list">

                    </div>

                    <hr>
                    <h4>Ajouter un commentaire</h4>
                    <br>
                    <form role="form" id="comment-form" data-toggle="validator">
                        <div class="row">
                            <div class="form-group col-sm-6">
                                <label for="name" class="h4">Nom</label>
                                <input type="text" class="form-control" id="name" required readonly>
                                <div class="help-block with-errors"></div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="message" class="h4 ">Message</label>
                            <textarea id="message" class="form-control" rows="5" placeholder="Message" required></textarea>
                            <div class="help-block with-errors"></div>
                        </div>
                        <button type="button" id="comment-submit" class="btn btn-success btn-lg pull-right ">Soumettre</button>
                        <div class="clearfix"></div>
                    </form>
                </div>

            </div>

            <div class="col-xs-4"><!--map-canvas will be positioned here--></div>

        </div>
        </div>

        <script src="<c:url value="/static/js/jquery/jquery-1.11.2.js" />"></script>
        <script src="<c:url value="/static/js/bootstrap.min.js" />"></script>
        <script src="<c:url value="/static/js/noty/packaged/jquery.noty.packaged.min.js" />"></script>
        <script src="http://maps.google.com/maps/api/js?libraries=geometry&output=embed"></script>
        <script src="<c:url value="/static/js/client/gmp.js" />"></script>
        <script src="<c:url value="/static/js/client/single_event_map.js" />"></script>
        <script src="<c:url value="/static/js/client/comment.js" />"></script>
        <script src="<c:url value="/static/js/client/follow.js" />"></script>
    </body>
</html>
