<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Task</title>
</head>
<body>
<h3>${task.getId()}</h3>
<div class="post">
    <h3>${task.getPost().getPostTitle()}</h3>
    <span class="sideInformation">
            Book: ${task.getPost().getBookName()}
        </span>
    <span class="sideInformation">
            Author: ${task.getPost().getBookAuthor().getValue()}
        </span>
    <div class="postText">${task.getPost().getPostText()}</div>
    <div class="postTags">
        <#list task.getPost().getTags() as tag>
            ${tag.getValue()} ;
        </#list>
    </div>
    <form action="/tasks/${task.getId()}" method="post">
        <label> Approve
            <input type="radio" name="verdict" value="approved" checked>
        </label>
        <br>
        <label> Decline
            <input type="radio" name="verdict" value="declined">
        </label>
        <br>
        <input type="submit" value="Send verdict">
    </form>
</div>
</body>
</html>