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
    newlink.setAttribute("href", "events/"+item.id);
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

$(document).ready(function () {
    $.getJSON("../api/events/get")
        .done(function (data) {
            $.each(data, function (key, item) {
                $(".event-list").append(createEventItem(item));
                $(".event-list").append(document.createElement("hr"));
            });
        });
});