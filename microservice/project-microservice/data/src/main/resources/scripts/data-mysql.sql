INSERT INTO team(team_id, name, description, owner_username)
VALUES (1, 'SampleTeam', 'Sample team', 'hyrax_username');

INSERT INTO board(board_id, board_name, owner_username)
VALUES (1, 'board1', 'admin');

INSERT INTO board_column(column_id, board_id, column_name, column_index)
VALUES (1, 1, 'column1', 1);

INSERT INTO board_column(column_id, board_id, column_name, column_index)
VALUES (2, 1, 'column2', 2);

INSERT INTO board_column(column_id, board_id, column_name, column_index)
VALUES (3, 1, 'column3', 3);

INSERT INTO task(task_id, board_id, column_id, task_name, description, task_index)
VALUES (1, 1, 1, 'task1', 'task1 desc', 1);

INSERT INTO task(task_id, board_id, column_id, task_name, description, task_index)
VALUES (2, 1, 1, 'task2', 'task2 desc', 2);

INSERT INTO task(task_id, board_id, column_id, task_name, description, task_index)
VALUES (3, 1, 1, 'task3', 'task3 desc', 3);

INSERT INTO label(label_id, board_id, label_name, red, green, blue)
VALUES (1, 1, 'label1', 0, 0, 0);

INSERT INTO label(label_id, board_id, label_name, red, green, blue)
VALUES (2, 1, 'label2', 0, 0, 0);

INSERT INTO label(label_id, board_id, label_name, red, green, blue)
VALUES (3, 1, 'label3', 0, 0, 0);

INSERT INTO task_label(board_id, task_id, label_id)
VALUES (1, 1, 1);

INSERT INTO task_label(board_id, task_id, label_id)
VALUES (1, 1, 2);

INSERT INTO task_label(board_id, task_id, label_id)
VALUES (1, 1, 3);