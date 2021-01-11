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
                window.location.replace("/topic/" + topicId);
            }else {
                alert(response.message);
            }
            console.log(response);
        },
        dataType: "json"
    });
}

// window.localStorage.setItem("closable", true);