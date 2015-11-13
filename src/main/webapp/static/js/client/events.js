var createEventItem = function(item) {
    id = item.id

    newdiv = document.createElement("div");
    newdiv.className = "event";
    newdiv.setAttribute("id","event_"+id);

    newpanel = document.createElement("div");
    newpanel.className = "panel panel-default";

    panelheading = document.createElement("div");
    panelheading.className = "panel-heading";

    newlink = document.createElement("a");
    newlink.setAttribute("href", "event/"+id);
    newlink.innerHTML = item.title;

    p_desc = document.createElement("p");
    p_desc.innerHTML = "<strong>" + item.descr + "</strong>";

    p_addr = document.createElement("p");
    p_addr.innerHTML = "au " + item.address;

    p_date = document.createElement("p");
    p_date.innerHTML = "le " + item.start_date + " à " + item.start_time;

    panelheading.appendChild(newlink);
    newpanel.appendChild(panelheading);

    newdiv.appendChild(newpanel);
    newdiv.appendChild(p_desc);
    newdiv.appendChild(p_addr);
    newdiv.appendChild(p_date);

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

var markers = [];

function createMarker(item) {
    var marker = new google.maps.Marker({
        position: {lat: item.lat, lng: item.lng},
        map: map,
        url: '/',
        animation: google.maps.Animation.DROP,
        title: 'Click to zoom',
        customInfo: item
    });

    marker.setMap(map);

    var url = location.protocol + "//" + location.host + "/app/event/" +item.id;
    var contentString = '<div id="content">' + '<a href="'+ url +'">' + item.title + '</a></div>';
    var infowindow = new google.maps.InfoWindow({ content: contentString });

    marker.addListener('click', function() {
        map.setZoom(16);
        map.setCenter(marker.getPosition());
        infowindow.open(map, marker);
    });

    markers.push(marker);

    return marker;
}

function filter() {

    hiking_flag = $('input#rando').is(':checked');
    biking_flag = $('input#velo').is(':checked');

    date_start = $("#datepicker-start").datepicker().val();
    date_end = $("#datepicker-end").datepicker().val();

    date_start_flag = date_start ? true : false;
    date_end_flag = date_end ? true : false;

    date_start_arr = date_start.split('-');
    date_end_arr = date_end.split('-');

    for (var i=0; i<markers.length; i++) {

        if (hiking_flag) {
            if (markers[i].customInfo.event_type.localeCompare('RANDO') == 0) {
                markers[i].setVisible(true);
            }
        } else {
            if (markers[i].customInfo.event_type.localeCompare('RANDO') == 0) {
                markers[i].setVisible(false);
            }
        }

        if (biking_flag) {
            if (markers[i].customInfo.event_type.localeCompare('VELO') == 0) {
                markers[i].setVisible(true);
            }
        } else {
            if (markers[i].customInfo.event_type.localeCompare('VELO') == 0) {
                markers[i].setVisible(false);
            }
        }

        if (date_start_flag && date_end_flag) {
            date_to_match = markers[i].customInfo.start_date.split("-").reverse();
            alert(date_to_match);
            if (date_to_match[0] >= date_start_arr[0] &&  date_to_match[1] >= date_start_arr[1] && date_to_match[2] >= date_start_arr[2] &&
                    date_to_match[0] <= date_end_arr[0] &&  date_to_match[1] <= date_end_arr[1] && date_to_match[2] <= date_end_arr[2]) {
                markers[i].setVisible(true);
            } else {
                markers[i].setVisible(false);
            }
        }
    }
}

$(document).ready(function () {
    google.maps.event.addDomListener(window, 'load', initialize);

    $.getJSON("../../api/events/")
        .done(function (data) {
            $.each(data, function (key, item) {
                $(".event-list").append(createEventItem(item));
                $(".event-list").append(document.createElement("hr"));
                createMarker(item);
            });
        });

    $( "#datepicker-start" ).datepicker( { dateFormat: 'dd-mm-yy' } );
    $( "#datepicker-end" ).datepicker( { dateFormat: 'dd-mm-yy' } );

    $("#filter").on('click', function() {
        filter();
    });
});
