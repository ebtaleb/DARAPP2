function geocodeAddress(address, callback) {
    geocoder = new google.maps.Geocoder();
    geocoder.geocode( { 'address': address}, function(results, status) {
        if (status == google.maps.GeocoderStatus.OK) {
            lat = results[0].geometry.location.lat();
            lng = results[0].geometry.location.lng();
            callback(lat, lng);
        } else {
            alert("Geocode was not successful for the following reason: " + status);
        }
    });
}

var buildEventJson = function() {
    title = $("#title").val();
    descr = $("#descr").val();
    addr = $("#addr").val() + ", " + $("#zipcode").val() + " " + $("#city").val() + ", " + "France";
    date = "2010-10-10";
    var lat;
    var lng;
    geocodeAddress(addr, function(la, ln) { lat = la; lng = ln; });
    alert($("input[name='eventtype']").filter(':selected').val());
    return { "name" : title, "descr" : descr, "address" : addr, "ev_date" : date, "lat" : lat, "lng" : lng };
};

$(document).ready(function () {

    $(".send-event").on('click', function() {
        event_data = buildEventJson();
        console.log(JSON.stringify(event_data));
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
                noty({layout: 'bottom', type: 'success', text: "Evenement crée", timeout : 2000});
                //window.setTimeout( function() {window.location.href = data.url;}, 3000 );
            },
            error: function(data) {
                noty({layout: 'bottom', type: 'error', text: "Erreur lors de l'envoi des données : " + data, timeout : 2000});
            }
        });
    });
});
