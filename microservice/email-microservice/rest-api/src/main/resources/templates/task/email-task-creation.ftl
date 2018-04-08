<!DOCTYPE html>
<html>
<style>
    table, th, td {
        border: 1px solid black;
    }
</style>
<head>
    <title>Task creation email</title>
</head>
<body>

<p>Dear <b>${username}</b>,</p>
<br>
<p>A new task has been created by <b>${requestedBy}</b>.</p>
<table>
    <tr>
        <th>Task Id</th>
        <th>Task title</th>
        <th>Column name</th>
        <th>Board name</th>
    </tr>
    <tr>
        <td>${taskId}</td>
        <td>${taskTitle}</td>
        <td>${columnName}</td>
        <td>${boardName}</td>
    </tr>
</table>
<br>
</body>
</html>