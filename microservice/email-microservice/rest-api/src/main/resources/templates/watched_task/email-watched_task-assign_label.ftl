<!DOCTYPE html>
<html>
<style>
    table, th, td {
        border: 1px solid black;
    }
</style>
<head>
    <title>Watched task assign label email</title>
</head>
<body>

<p>Dear <b>${username}</b>,</p>
<br>
<p>A user with <b>${requestedBy}</b> username has assigned a label to the following task.</p>
<table>
    <tr>
        <th>Task Id</th>
        <th>Task title</th>
        <th>Assigned label name</th>
        <th>Column name</th>
        <th>Board name</th>
    </tr>
    <tr>
        <td>${taskId}</td>
        <td>${taskTitle}</td>
        <td>${assignedLabelName}</td>
        <td>${columnName}</td>
        <td>${boardName}</td>
    </tr>
</table>
<br>
</body>
</html>