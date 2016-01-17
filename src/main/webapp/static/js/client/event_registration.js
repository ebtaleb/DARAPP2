function geocodeAddress(address, callback) {
    geocoder = new google.maps.Geocoder();
    geocoder.geocode( { 'address': address}, function(results, status) {
        if (status == google.maps.GeocoderStatus.OK) {
            lat = results[0].geometry.location.lat();
            lng = results[0].geometry.location.lng();
            callback(address, lat, lng);
        } else {
            alert("Geocode was not successful for the following reason: " + status);
        }
    });
}

function validateForm() {

    var owner = $("a.navbar-brand strong").text();
    var title = $("#title").val();
    var descr = $("#descr").val();

    var addr = $("#addr").val();
    var zip = $("#zipcode").val();
    var city = $("#city").val();

    var date = $("#datepicker").datepicker().val();
    var time = $("#timepicker").val();

    if (owner == "") {
        noty({layout: 'bottomLeft', type: 'error', text: "Erreur : champ owner vide", timeout : 2000});
        return false;
    }

    if (title == "") {
        noty({layout: 'bottomLeft', type: 'error', text: "Erreur : champ title vide", timeout : 2000});
        return false;
    }

    if (descr == "") {
        noty({layout: 'bottomLeft', type: 'error', text: "Erreur : champ descr vide", timeout : 2000});
        return false;
    }

    if (addr == "") {
        noty({layout: 'bottomLeft', type: 'error', text: "Erreur : champ addr vide", timeout : 2000});
        return false;
    }

    if (zip == "") {
        noty({layout: 'bottomLeft', type: 'error', text: "Erreur : champ zip vide", timeout : 2000});
        return false;
    }

    if (city == "") {
        noty({layout: 'bottomLeft', type: 'error', text: "Erreur : champ city vide", timeout : 2000});
        return false;
    }

    if (date == "") {
        noty({layout: 'bottomLeft', type: 'error', text: "Erreur : champ date vide", timeout : 2000});
        return false;
    }

    if (time == "") {
        noty({layout: 'bottomLeft', type: 'error', text: "Erreur : champ heure vide", timeout : 2000});
        return false;
    }

    if ($('input:radio[name=eventtype]:checked').val() == undefined) {
        noty({layout: 'bottomLeft', type: 'error', text: "Erreur : champ event type vide", timeout : 2000});
        return false;
    }

    return true;

}

var buildEventJson = function(addr, lat, lng) {

    owner = $("a.navbar-brand strong").text();
    title = $("#title").val();
    descr = $("#descr").val();

    date = $("#datepicker").datepicker().val();
    time = $("#timepicker").val();
    path = createPointListForRoute(gLatLngArray);

    var event_type = "";

    if ($('input:radio[name=eventtype]:checked').val() == 'r') {
        event_type = "RANDO";
    }

    if ($('input:radio[name=eventtype]:checked').val() == 'v') {
        event_type = "VELO";
    }

    return { "owner" : owner, "title" : title, "descr" : descr, "event_type" : event_type, "address" : addr, "path" : path, "start_date" : date, "start_time" : time, "lat" : lat, "lng" : lng };
};

function sendNewEventByAJAX(addr, la, ln) {
    event_data = buildEventJson(addr, la, ln);
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: "POST",
        url: "/api/events/post",
        data: JSON.stringify(event_data),
        dataType: "json",
        success: function(data){
            noty({layout: 'bottomLeft', type: 'success', text: "Evènement crée", timeout : 2000});
            window.setTimeout( function() {window.location.href = location.protocol + "//" + location.host + "/app/event/" +data.id;}, 3000 );
        },
        error: function(data) {
            noty({layout: 'bottomLeft', type: 'error', text: "Erreur lors de l'envoi des données : " + data, timeout : 2000});
        }
    });
}

$(document).ready(function () {

    $( "#datepicker" ).datepicker( { dateFormat: 'yy-mm-dd' } );
    $( "#timepicker" ).timeEntry( {show24Hours: true, showSeconds: true} );

    $(".send-event").on('click', function() {
        if (validateForm()) {
            addr = $("#addr").val() + ", " + $("#zipcode").val() + " " + $("#city").val() + ", " + "France";
            geocodeAddress(addr, sendNewEventByAJAX);
        }
    });

});
