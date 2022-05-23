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
<#list tasks as task>
    <h3><a href="/tasks/${task.getId()}">To this task</a></h3>
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
    </div>
</#list>
</body>
</html>