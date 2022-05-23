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
<div>
    <a href="/admin">Create/Edit moderators</a>
    <a href="/logout">Logout</a>
</div>
<div>
    <span>Email: ${user.getEmail()}</span>
    <br>
    <span>Nickname: ${user.getNickname()}</span>
    <br>
</div>
</body>
</html>