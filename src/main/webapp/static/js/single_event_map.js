var gLatLngs;
var map;

$(document).ready(function(){
    google.maps.event.addDomListener(window, 'load', initialize);

    function initialize() {
        var latlng = new google.maps.LatLng($(".lat").text(), $(".lng").text());

        var options = {
            center: latlng,
            zoom: 14,
            mapTypeId: google.maps.MapTypeId.ROADMAP
        };

        map = new google.maps.Map(document.getElementById("map-canvas"), options);

        var marker = new google.maps.Marker({
            position: latlng,
            map: map,
            url: '/',
            animation: google.maps.Animation.DROP
        });

        marker.setMap(map);

        google.maps.event.addListener(map, "move", function() {
            redrawLinesAndMarkers(gLatLngArray)
        });

        google.maps.event.addListener(map, "zoomend", function(h, g) {
            prepMarkerArray();
            redrawLinesAndMarkers(gLatLngArray)
        });

        gLatLngs = JSON.parse($(".path").text());
        bRecordPoints = true;
        var i = 0;
        if (gLatLngs.length > 0) {
            gLatLngs.forEach(function(entry) {
                addLeg(entry.lon, entry.lat, LOADING_FROM_QUERY);
                pointTypeArray.push(entry.pt);
            });

            drawPolyLine(gLatLngArray);
            drawMarkers(gLatLngArray);
        }

    };

});
