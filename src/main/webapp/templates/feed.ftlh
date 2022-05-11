<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Posts feed</title>
</head>
<body>
<h1>Posts</h1>

<#list posts.iterator() as post>

    <h3>${post.getPostText()}</h3>
    <h3>${post.getBookName()}</h3>

</#list>

<#if posts.hasPrevious()>
    <a href="/feed?page=${currentPage-1}">Previous page</a>
</#if>
<#if posts.hasNext()>
    <a href="/feed?page=${currentPage+1}">Next page</a>
</#if>
</body>
</html>