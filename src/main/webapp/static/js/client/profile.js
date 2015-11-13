function createRow(event) {
    url = location.protocol + "//" + location.host + "/app/event/" +event.id;
    link = "<a href='" + url + "'>" + event.title + "</a>";
    row = "<tr><td>" + link + "</td><td>" + event.address + "</td><td>" + event.start_date + "</td><td>" + event.start_time + "</td></tr>";
    return row;
}

$(document).ready(function () {
    user = $("a.navbar-brand strong").text();

    $.getJSON("../../api/events/" + user + "/get_user_owned_events")
        .done(function (data) {
            $.each(data, function (key, item) {
                $("table.me tbody").append(createRow(item));
            });
        });

    $.getJSON("../../api/events/" + user + "/get_subscriptions")
        .done(function (data) {
            $.each(data, function (key, item) {
                $("table.ms tbody").append(createRow(item));
            });
        });
});