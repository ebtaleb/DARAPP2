$(document).ready(function(){
    google.maps.event.addDomListener(window, 'load', initialize);

    function initialize() {

        var latlng = new google.maps.LatLng(48.8566667, 2.3509871);

        var options = {
            center: latlng,
            zoom: 12,
            mapTypeId: google.maps.MapTypeId.ROADMAP
        };

        var map = new google.maps.Map(document.getElementById("map-canvas"), options);

        var marker = new google.maps.Marker({
            position: {lat: 48.8464111, lng: 2.3548468},
            map: map,
            url: '/',
            animation: google.maps.Animation.DROP,
            title: 'Click to zoom'
        });

        marker.setMap(map);

        var contentString = '<div id="content">' +
            '<p><b>Uluru</b>, also referred to as <b>Ayers Rock</b>, is a large AIKATSU' +
            '</div>';
        var infowindow = new google.maps.InfoWindow({ content: contentString });

        marker.addListener('click', function() {
            map.setZoom(16);
            map.setCenter(marker.getPosition());
            infowindow.open(map, marker);
        });

    };

});
