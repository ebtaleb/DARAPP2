$('body').on('click', 'button.followUser', function() {
        var userid = $(this).attr("id");

        $("#followButton").fadeOut(300);
        $("#followButton").html('<button id="' +userid + '" name="unfollow" class="btn btn-info unfollowUser">Following</button>');
        $("#followButton").fadeIn(300);
});

$('body').on('click', 'button.unfollowUser', function() {
        var userid = $(this).attr("id");

        $("#followButton").fadeOut(300);
        $("#followButton").html('<button id="' +userid + '" name="follow" class="btn btn-default followUser">Follow</button>');
        $("#followButton").fadeIn(300);
});

$('body').on('mouseenter', 'button.unfollowUser', function() {
        $(this).toggleClass("btn-info", false);
        $(this).toggleClass("btn-danger", true);
        $(this).html("Unfollow");
});

$('body').on('mouseleave', 'button.unfollowUser', function() {
        $(this).toggleClass("btn-danger", false);
        $(this).toggleClass("btn-info", true);
        $(this).html("Following");;
});

//$.ajax({
    //type: "PUT",
    //url: "api/events/"+id+"/",
    //data: dataString,
    //cache: false,
    //beforeSend: function() {},
    //success: function(html)
    //{
        //$("#followButton").html('<button id="' +userid '" name="unfollow" class="btn btn-danger unfollowUser">Unfollow</button>');
        //$("#followButton").fadeIn(300);
    //}
//});
