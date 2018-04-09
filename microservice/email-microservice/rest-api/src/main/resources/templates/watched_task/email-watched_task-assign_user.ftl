<!DOCTYPE html>
<html>
<style>
    table, th, td {
        border: 1px solid black;
    }
</style>
<head>
    <title>Watched task assign user email</title>
</head>
<body>

<p>Dear <b>${username}</b>,</p>
<br>
<p>A user with <b>${requestedBy}</b> username has assigned the following task.</p>
<table>
    <tr>
        <th>Task Id</th>
        <th>Task title</th>
        <th>Previously assigned username</th>
        <th>New assigned username</th>
        <th>Board name</th>
    </tr>
    <tr>
        <td>${taskId}</td>
        <td>${taskTitle}</td>
        <td>${previouslyAssignedUsername}</td>
        <td>${newAssignedUsername}</td>
        <td>${boardName}</td>
    </tr>
</table>
<br>
</body>
</html>