<!DOCTYPE html>
<html>
<style>
    table, th, td {
        border: 1px solid black;
    }
</style>
<head>
    <title>Watched task watch email</title>
</head>
<body>

<p>Dear <b>${username}</b>,</p>
<br>
<p>A new user with <b>${requestedBy}</b> username has started to watch the following task.</p>
<table>
    <tr>
        <th>Task Id</th>
        <th>Task title</th>
        <th>Board name</th>
    </tr>
    <tr>
        <td>${taskId}</td>
        <td>${taskTitle}</td>
        <td>${boardName}</td>
    </tr>
</table>
<br>
</body>
</html>