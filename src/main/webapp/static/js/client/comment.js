function formatDate(s) {
    date = new Date(s);
    hours = date.getHours();
    minutes = "0" + date.getMinutes();

    year = date.getFullYear();
    month = date.getMonth() + 1;
    day = date.getDate();

    return day + '/' + month + '/' + year + ' ' + hours + ':' + minutes.substr(-2);
}

var createComment = function(user, content, date, imgsrc) {
    return "<div class='bubble bclearfix'>" +
        "<img src='" + imgsrc + "'>" +
        "<div class='bubble-content'>" +
            "<div class='point'></div>" +
            "<strong>par " + user + " le " + date + "</strong> <br><br>" +
            "<p>" + content + "</p>" +
        "</div>"
    "</div>";
}

var addCommentToEvent = function(id, username, text) {
    if (text) {
	    $.ajax({
		    type: "POST",
		    url: "../../api/events/"+id+"/post_comment",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
		    data: JSON.stringify({'owner': username, 'content': text, 'event_id' : id }),
		    dataType: "json",
		    success: function(data){
			    $('textarea#message').val('');
                newcomment = createComment(username, text, data.creation_date, "http://lorempixel.com/50/50/people/9");
                $('.comments.bubble-list').append(newcomment);
                noty({layout: 'bottomLeft', type: 'success', text: "Commentaire ajouté", timeout : 2000});
		    }
	    });
    } else {
        noty({layout: 'bottomLeft', type: 'error', text: "Erreur : commentaire vide", timeout : 2000});
    }
}

$(document).ready(function () {
    var user = $("a.navbar-brand strong").text();
    var id = $(".event_id").text();
    $("input#name").val(user);

    $.getJSON("../../api/events/"+id+"/get_comments")
        .done(function (data) {
            $.each(data, function (key, item) {
                newcomment = createComment(item.owner, item.content, formatDate(item.creation_time), "http://lorempixel.com/50/50/people/9");
                $('.comments.bubble-list').append(newcomment);
            });
        });

    $("#comment-submit").on('click', function() {
        text = $("textarea#message").val();
        addCommentToEvent(id, user, text);
    });

});