<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <title>Admin page</title>

    <script>
        const requestUrl = '/admin/'

        function createModerator() {

            const requestData = {
                "email": $('#email').val(),
                "password": $('#password').val(),
                "nickname": $('#nickname').val()
            }

            $.ajax({
                url: requestUrl,
                method: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(requestData),
                success: function (res) {
                    const id = res.id
                    const email = res.email
                    const html = '<tr id=' + id + '><th>' + id + '</th><th>' + email + '</th><th>' + '<button onclick="deleteModerator(' + id + ')">Delete</button>' + '</th></tr>'

                    $('#table').append(html);
                },
                error: function (res) {
                    alert(res);
                },
            })
        }

        function deleteModerator(id) {
            $.ajax({
                url: '/admin/' + id,
                contentType: 'application/json',
                method: 'DELETE',
                success: function (res) {
                    $('#' + id).remove()
                },
                error: function (res) {
                    alert(res)
                }
            })
        }
    </script>

</head>
<body>
<h2>Create Moderator</h2>
<label for="email">Moderator email</label>
<input type="email" id="email">
<br>
<label for="nickname">Moderator nickname</label>
<input type="text" id="nickname">
<br>
<label for="password">Moderator password</label>
<input type="text" id="password">
<br>
<button onclick="createModerator()">Create!</button>
<h2>Moderators</h2>
<table id="table">
    <tr>
        <th>Id</th>
        <th>Email</th>
        <th>Delete</th>
    </tr>
    <#list moderators as moder>
        <tr id="${moder.getId()}">
            <th>${moder.getId()}</th>
            <th>${moder.getEmail()}</th>
            <th>
                <button onclick="deleteModerator(${moder.getId()})">Delete</button>
            </th>
        </tr>
    </#list>
</table>

</body>
</html>