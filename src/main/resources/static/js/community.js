function postComment() {
    var topicId = $("#topic_id").val();
    var content = $("#comment_content").val();
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "parentId": topicId,
            "content": content,
            "type": 1
        }),
        success: function(response) {
            if (response.code == 200) {
                window.location.reload();
            }else {
                alert(response.message);
            }
            console.log(response);
        },
        dataType: "json"
    });
}

function savePath() {
    var path = window.location.pathname;
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

