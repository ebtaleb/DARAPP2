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
        <li><a href="#">Mes évènements</a></li>
        <li>&nbsp;</li>
      </ul>
    </div>
</div>

<div id="map-canvas"></div>
<div class="container-fluid" id="main">
  <div class="row">
    <div class="col-xs-2 sidebar-outer">
       <div class="sidebar">
                <ul>
                <li><input type="checkbox" id="attractionbox" onclick="boxclick(this,'attraction')" /> Attractions</li>
                <li><input type="checkbox" id="foodbox" onclick="boxclick(this,'food')" /> Food and Drink</li>
                <li><input type="checkbox" id="hotelbox" onclick="boxclick(this,'hotel')" /> Hotels</li>
                <li><input type="checkbox" id="citybox" onclick="boxclick(this,'city')" /> Towns/Cities</li>
              </ul>
        </div>
    </div>
  	<div class="col-xs-6" id="right">

      <h2>Nouveaux évenements</h2>

      <hr>

      <!-- item list -->
      <div class="panel panel-default">
        <div class="panel-heading"><a href="">Item heading</a></div>
      </div>
      <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis pharetra varius quam sit amet vulputate.
        Quisque mauris augue, molestie tincidunt condimentum vitae, gravida a libero. Aenean sit amet felis
        dolor, in sagittis nisi. Sed ac orci quis tortor imperdiet venenatis. Duis elementum auctor accumsan.
        Aliquam in felis sit amet augue.</p>

      <hr>

      <div class="panel panel-default">
        <div class="panel-heading"><a href="">Item heading</a></div>
      </div>
      <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis pharetra varius quam sit amet vulputate.
        Quisque mauris augue, molestie tincidunt condimentum vitae, gravida a libero. Aenean sit amet felis
        dolor, in sagittis nisi. Sed ac orci quis tortor imperdiet venenatis. Duis elementum auctor accumsan.
        Aliquam in felis sit amet augue.</p>

      <hr>

      <div class="panel panel-default">
        <div class="panel-heading"><a href="">Item heading</a></div>
      </div>
      <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis pharetra varius quam sit amet vulputate.
        Quisque mauris augue, molestie tincidunt condimentum vitae, gravida a libero. Aenean sit amet felis
        dolor, in sagittis nisi. Sed ac orci quis tortor imperdiet venenatis. Duis elementum auctor accumsan.
        Aliquam in felis sit amet augue.</p>

      <hr>

      <div class="panel panel-default">
        <div class="panel-heading"><a href="">Item heading</a></div>
      </div>
      <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis pharetra varius quam sit amet vulputate.
        Quisque mauris augue, molestie tincidunt condimentum vitae, gravida a libero. Aenean sit amet felis
        dolor, in sagittis nisi. Sed ac orci quis tortor imperdiet venenatis. Duis elementum auctor accumsan.
        Aliquam in felis sit amet augue.</p>

      <hr>

    </div>
    <div class="col-xs-4"><!--map-canvas will be postioned here--></div>

  </div>
</div>

		<script src="<c:url value="/static/js/jquery-1.11.2.js" />"></script>
		<script src="<c:url value="/static/js/bootstrap.min.js" />"></script>
		<script src="http://maps.google.com/maps/api/js?sensor=false&output=embed"></script>
		<script src="<c:url value="/static/js/scripts.js" />"></script>
	</body>
</html>
