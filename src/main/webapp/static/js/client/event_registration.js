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
        addr = $("#addr").val() + ", " + $("#zipcode").val() + " " + $("#city").val() + ", " + "France";
        geocodeAddress(addr, sendNewEventByAJAX);
    });

});
