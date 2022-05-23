<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Sign Up!</title>
</head>
<body>

<form action="/signUp" method="post" enctype="multipart/form-data">
    <label for="email">Email: </label>
    <input type="email" name="email" id="email">
    <br>
    <label for="password">Password: </label>
    <input type="password" name="password" id="password">
    <br>
    <label for="nickname">Nickname: </label>
    <input type="text" name="nickname" id="nickname">
    <br>
    <input type="file" name="file" id="file">
    <br>
    <input type="submit" value="Submit!">
    <br>
</form>

</body>
</html>