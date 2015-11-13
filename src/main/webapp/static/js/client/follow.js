$(document).ready(function () {

    $('body').on('click', 'button.followUser', function() {
        username = $("a.navbar-brand strong").text();
        event_id = $(this).attr("id");

        $.ajax({
            type: "POST",
            url: "../../api/events/"+event_id+"/"+username+"/subscribe",
        });

        $(this).fadeOut(300);
        $(this).toggleClass("followUser", false);
        $(this).toggleClass("btn-default", false);
        $(this).toggleClass("btn-info", true);
        $(this).toggleClass("unfollowUser", true);
        $(this).html("Inscrit");
        $(this).fadeIn(300);
    });

    $('body').on('click', 'button.unfollowUser', function() {
        username = $("a.navbar-brand strong").text();
        event_id = $(this).attr("id");

        $.ajax({
            type: "DELETE",
            url: "../../api/events/"+event_id+"/"+username+"/unsubscribe",
        });
        $(this).fadeOut(300);
        $(this).toggleClass("unfollowUser", false);
        $(this).toggleClass("btn-danger", false);
        $(this).toggleClass("btn-default", true);
        $(this).toggleClass("followUser", true);
        $(this).html("S\'inscrire");
        $(this).fadeIn(300);
    });

    $('body').on('mouseenter', 'button.unfollowUser', function() {
        $(this).toggleClass("btn-info", false);
        $(this).toggleClass("btn-danger", true);
        $(this).html("Se désinscrire");
    });

    $('body').on('mouseleave', 'button.unfollowUser', function() {
        $(this).toggleClass("btn-danger", false);
        $(this).toggleClass("btn-info", true);
        $(this).html("Inscrit");;
    });

});
