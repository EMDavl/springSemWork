<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Posts</title>
</head>
<body>
<h1>Posts by...</h1>
<#list posts as post>
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
        <span class="postRating">${post.getLikes()}</span>
        <span class="postRating">${post.getDislikes()}</span>
        <div class="postTags">
            <#list post.getTags() as tag>
                <a href="/posts/byTag?id=${tag.getId()}">${tag.getValue()}</a>
            </#list>
        </div>
    </div>
</#list>
</body>
</html>