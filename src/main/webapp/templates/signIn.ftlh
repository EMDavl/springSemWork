<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Sign In!</title>
</head>
<body>

<form action="/signIn" method="post">
    <label for="email">Email: </label>
    <input type="email" name="email" id="email">
    <br>
    <label for="password">Password: </label>
    <input type="password" name="password" id="password">
    <br>
    <input type="submit" value="Submit!">
    <br>
</form>

</body>
</html>