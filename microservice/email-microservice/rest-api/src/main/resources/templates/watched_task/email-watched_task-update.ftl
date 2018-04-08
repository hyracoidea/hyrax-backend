<!DOCTYPE html>
<html>
<style>
    table, th, td {
        border: 1px solid black;
    }
</style>
<head>
    <title>Watched task update email</title>
</head>
<body>

<p>Dear <b>${username}</b>,</p>
<br>
<p>A user with <b>${requestedBy}</b> username has updated the following task.</p>
<table>
    <tr>
        <th>Task Id</th>
        <th>Previous task title</th>
        <th>New task title</th>
        <th>Previous task description</th>
        <th>New task description</th>
        <th>Column name</th>
        <th>Board name</th>
    </tr>
    <tr>
        <td>${taskId}</td>
        <td>${previousTaskTitle}</td>
        <td>${newTaskTitle}</td>
        <td>${previousTaskDescription}</td>
        <td>${newTaskDescription}</td>
        <td>${columnName}</td>
        <td>${boardName}</td>
    </tr>
</table>
<br>
</body>
</html>