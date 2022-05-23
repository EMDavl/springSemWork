<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Create post</title>
</head>
<body>
<h1>Posts creation</h1>
<form action="/posts/create" method="post">
    <label for="postTitle">Title: </label>
    <input type="text" id="postTitle" name="postTitle" placeholder="Post title">
    <br>
    <label for="postText">Text: </label>
    <textarea id="postText" name="postText" placeholder="Post text">

    </textarea>
    <br>
    <label for="bookName">Book name: </label>
    <input type="text" id="bookName" name="bookName" placeholder="Book name">
    <br>
    <label for="bookAuthor">Book author: </label>
    <input type="text" id="bookAuthor" name="bookAuthor" placeholder="Book author">
    <br>
    <label for="tags">Tags ( если больше одного, то указывать через ; ): </label>
    <input type="text" id="tags" name="tagsString" placeholder="Tags" multiple>
    <br>
    <input type="submit" value="Create!">
</form>
</body>
</html>