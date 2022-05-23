<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Tasks</title>
</head>
<body>
<h1>Tasks</h1>
<#list tasks as task>
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
        <form action="/tasks" method="post">
            <input type="hidden" id="taskId" name="taskId" value="${task.getId()}">
            <input type="submit" value="Take task">
        </form>
    </div>
</#list>
</body>
</html>