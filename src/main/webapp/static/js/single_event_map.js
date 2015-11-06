$(document).ready(function(){
    google.maps.event.addDomListener(window, 'load', initialize);

    function initialize() {
        var latlng = new google.maps.LatLng($(".lat").text(), $(".lng").text());

        var options = {
            center: latlng,
            zoom: 14,
            mapTypeId: google.maps.MapTypeId.ROADMAP
        };

        var map = new google.maps.Map(document.getElementById("map-canvas"), options);

        var marker = new google.maps.Marker({
            position: latlng,
            map: map,
            url: '/',
            animation: google.maps.Animation.DROP
        });

        marker.setMap(map);
    };

});
