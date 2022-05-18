<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <title>My moderated posts</title>
    <script>
        function deleteRequest(postId) {
            const deleteUrl = '/posts/personal/delete?id=' + postId
            $.ajax({
                url: deleteUrl.toString(),
                type: 'DELETE',
                success: function (result) {
                    $('#' + postId).remove()
                    alert(result.toString())
                },
                failure: function (result) {
                    alert('Something wrong happened(')
                }
            })
        }
    </script>
</head>
<body>
<h1>Approved posts</h1>
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
        <table>
            <tr>
                <th>Удалить пост</th>
                <th>Изменить пост</th>
            </tr>
            <tr>
                <td>
                    <button class="postButton" onclick="deleteRequest(${post.getId()})" value="Delete"/>
                </td>
                <td>
                    <form method="get" action="/posts/personal/edit?id=${post.getId()}">
                        <input type="submit" value="Edit">
                    </form>
                </td>
            </tr>
        </table>
    </div>
</#list>
</body>
</html>