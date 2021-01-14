function sendComment(btn) {
    var topicId = btn.getAttribute("data-id");
    var content = $("#comment_content").val();
    postCom(topicId, content, 1);
}

function sendSubComment(btn) {
    var commentId = btn.getAttribute("data-id");
    var content = $("#subcomment-input-" + commentId).val();
    postCom(commentId, content, 2);
}


function postCom(parentId, content, type) {
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "parentId": parentId,
            "content": content,
            "type": type
        }),
        success: function(response) {
            if (response.code == 200) {
                location.reload();
            }else {
                alert(response.message);
            }
            console.log(response);
        },
        dataType: "json"
    });
}

function savePath() {
    var path = location.pathname;
    $.ajax({
        type: "POST",
        url: "/last_status",
        contentType: "application/json",
        data: JSON.stringify({
            "preUrl": path
        }),
        success: function(response) {
            if (response.code != 200) {
                alert(response.message);
            }
        },
        dataType: "json"
    });
}

function subComments(btn) {
    var parentId = btn.getAttribute("data-parentId");
    var subComments = $("#subcomment-" + parentId);
    var collapse = subComments.hasClass("in");
    if (!collapse) {
        if (subComments.children().length == 2) {
            $.getJSON("/comment/" + parentId, function (data) {
                $.each(data.reverse(), function (index, comment) {
                    var mediaLeft = $("<div/>", {
                        "class": "media-left"
                    }).append($("<img/>", {
                        "class": "avatar-min img-rounded",
                        "src": comment.user.avatarUrl
                    }));

                    var mediaBody = $("<div/>", {
                        "class": "media-body"
                    }).append($("<h5/>", {
                        "class": "media-heading",
                        "html": comment.user.name
                    })).append($("<div/>", {
                        "html": comment.content
                    })).append($("<div/>", {
                        "class": "comment"
                    }).append($("<span/>", {
                        "class": "pull-right time",
                        "html": moment(comment.gmtCreate).format('YYYY-MM-DD')
                    })));

                    var media = $("<div/>", {
                        "class": "media"
                    }).append(mediaLeft).append(mediaBody).append($("<hr>", {
                        "class": "hr-size"
                    }));

                    subComments.prepend(media);
                })
            })
        }
    }
    subComments.toggleClass("in");

}

function showTags() {
    $("#tagLibrary").show();
}

function removeTags() {
    $("#tagLibrary").hide();
}

function addTag(btn) {
    var tag = btn.getAttribute("data-tag");
    var textStr = $("#tag").val();
    if (textStr.indexOf(tag) == -1) {
        if (textStr) {
            $("#tag").val(textStr + ',' + tag);
        }else {
            $("#tag").val(tag);
        }
    }
}


