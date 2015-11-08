var createSubscribeButton = function(id) {
    div = document.createElement("div");
    div.className = "followButton";

    button = document.createElement("button");
    button.className = "btn btn-default followUser";
    button.setAttribute('id',""+id);
    button.setAttribute('name',"follow");
    button.innerHTML = "Suivre";

    div.appendChild(button);
    return div;
}

var createEventItem = function(item) {
    newdiv = document.createElement("div");
    newdiv.className = "event";

    newpanel = document.createElement("div");
    newpanel.className = "panel panel-default";

    panelheading = document.createElement("div");
    panelheading.className = "panel-heading";

    newlink = document.createElement("a");
    newlink.setAttribute("href", "event/"+item.id);
    newlink.innerHTML = item.name;

    p_desc = document.createElement("p");
    p_desc.innerHTML = item.descr;

    p_addr = document.createElement("p");
    p_addr.innerHTML = item.address;

    panelheading.appendChild(newlink);
    newpanel.appendChild(panelheading);

    newdiv.appendChild(newpanel);
    newdiv.appendChild(p_desc);
    newdiv.appendChild(p_addr);
    newdiv.appendChild(createSubscribeButton(item.id));

    return newdiv;
}

var map = null;

function initialize() {

    var latlng = new google.maps.LatLng(48.8566667, 2.3509871);

    var options = {
        center: latlng,
        zoom: 12,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };

    map = new google.maps.Map(document.getElementById("map-canvas"), options);
}

function createMarker(item) {
    var marker = new google.maps.Marker({
        position: {lat: item.lat, lng: item.lng},
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
    return marker;
}

$(document).ready(function () {
    google.maps.event.addDomListener(window, 'load', initialize);

    $.getJSON("../../api/events/get")
        .done(function (data) {
            $.each(data, function (key, item) {
                $(".event-list").append(createEventItem(item));
                $(".event-list").append(document.createElement("hr"));
                createMarker(item);
            });
        });
});
