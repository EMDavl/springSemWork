<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Profile</title>
</head>
<body>
<a href="/feed">Feed</a>
<a href="/posts/personal/moderated">My posts</a>
<a href="/logout">Logout</a>
<a href="/posts/create">Create post</a>
<br>
<p>${user.getEmail()}</p>
<p>${user.getNickname()}</p>
<p>
    Subscriptions:
    <a href="/subscriptions/${user.getId()}">
        ${user.getSubscriptions()}
    </a>
</p>
<p>
    Subscribers:
    <a href="/subscribers/${user.getId()}">
        ${user.getSubscribers()}</a></p>
<p>
    Moderated posts:
    <a href="/posts/personal/moderated">
        ${user.getModeratedPostsAmount()}
    </a>
</p>
<p>
    Posts on moderation:
    <a href="/posts/personal/unmoderated">
        ${user.getUnmoderatedPostsAmount()}
    </a>
</p>
<p>
    Account type: ${user.isPublicAccount()?string("public", "private")}
</p>

<a href="/profile/settings">profile settings</a>
</body>
</html>