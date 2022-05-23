<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Posts feed</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script>
        function like(postId) {
            const likeCounterId = '#likeCounter' + postId;
            const dislikeCounterId = '#dislikeCounter' + postId;

            $.ajax({
                url: "/posts/like/" + postId,
                method: "POST",
                success: function (res) {
                    $(likeCounterId).text(res.likesCount);
                    $(dislikeCounterId).text(res.dislikesCount);
                }
            })
        }

        function unsubscribe(postId) {
            const subscribeId = '#subscribe' + postId

            $.ajax({
                url: "/subscribe/" + postId,
                method: "POST",
                success: function () {
                    $(subscribeId).text("Подписаться на автора")
                    $(subscribeId).click(subscribe(postId))
                }
            })
        }

        function subscribe(postId) {
            const subscribeId = '#subscribe' + postId

            $.ajax({
                url: "/subscribe/" + postId,
                method: "POST",
                success: function () {
                    $(subscribeId).text("Отписаться от автора")
                    $(subscribeId).click(unsubscribe(postId))
                }
            })
        }

        function dislike(postId) {
            const likeCounterId = '#likeCounter' + postId;
            const dislikeCounterId = '#dislikeCounter' + postId;

            $.ajax({
                url: "/posts/dislike/" + postId,
                method: "POST",
                success: function (res) {
                    $(likeCounterId).text(res.likesCount);
                    $(dislikeCounterId).text(res.dislikesCount);
                }
            })
        }
    </script>
</head>
<body>
<h1>Posts</h1>

<#list postPage.getPosts() as post>
    <div class="post">
        <h3>${post.getPostTitle()}</h3>
        <span class="sideInformation">
            Book:
            <a href="/posts/byBookName?name=${post.getBookName()}">
                ${post.getBookName()}
            </a>
        </span>
        <span class="sideInformation">
            Author:
            <a href="/posts/byAuthor?id=${post.getBookAuthor().getId()}">
                ${post.getBookAuthor().getValue()}
            </a>
        </span>
        <div class="postText">${post.getPostText()}</div>
        <div class="postRating">
            <button id="like${post.getId()}" onclick="like(${post.getId()})">Like!</button>
            <span id="likeCounter${post.getId()}">${post.getLikes()}</span>
            <br>
            <button id="dislike${post.getId()}" onclick="dislike(${post.getId()})">Dislike!</button>
            <span id="dislikeCounter${post.getId()}">${post.getDislikes()}</span>
        </div>
        <div class="postTags">
            <#list post.getTags() as tag>
                <a href="/posts/byTag?id=${tag.getId()}">${tag.getValue()}</a>
            </#list>
        </div>
        <button id="subscribe${post.getId()}" onclick="subscribe(${post.getId()})">Подписаться на автора</button>
    </div>
</#list>

<#if postPage.hasPrevious()>
    <a href="/feed?page=${currentPage-1}">Previous page</a>
</#if>
<#if postPage.hasNext()>
    <a href="/feed?page=${currentPage+1}">Next page</a>
</#if>
</body>
</html>