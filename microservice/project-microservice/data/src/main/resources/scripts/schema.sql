DROP TABLE IF EXISTS team_member;

DROP TABLE IF EXISTS team;

DROP TABLE IF EXISTS board_member;

DROP TABLE IF EXISTS task_label;

DROP TABLE IF EXISTS task;

DROP TABLE IF EXISTS board_column;

DROP TABLE IF EXISTS label;

DROP TABLE IF EXISTS board;


CREATE TABLE IF NOT EXISTS team (
  team_id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  description VARCHAR(255) NULL,
  owner_username VARCHAR(255) NOT NULL,

  PRIMARY KEY (team_id),
  UNIQUE KEY (name)
);

CREATE TABLE IF NOT EXISTS team_member (
  team_name VARCHAR(255) NOT NULL,
  username VARCHAR(255) NOT NULL,

  FOREIGN KEY (team_name) REFERENCES team(name),
  UNIQUE KEY (team_name, username)
);

CREATE TABLE IF NOT EXISTS board (
    board_id BIGINT NOT NULL AUTO_INCREMENT,
    board_name VARCHAR(255) NOT NULL,
    owner_username VARCHAR(255) NOT NULL,

    PRIMARY KEY (board_id),
    UNIQUE KEY (board_name)
);

CREATE TABLE IF NOT EXISTS board_member (
  board_name VARCHAR(255) NOT NULL,
  username VARCHAR(255) NOT NULL,

  FOREIGN KEY (board_name) REFERENCES board(board_name),
  UNIQUE KEY (board_name, username)
);

CREATE TABLE IF NOT EXISTS board_column (
    column_id BIGINT NOT NULL AUTO_INCREMENT,
    board_id BIGINT NOT NULL,
    column_name VARCHAR(255) NOT NULL,
    column_index BIGINT NOT NULL,

    PRIMARY KEY (column_id),
    FOREIGN KEY (board_id) REFERENCES board(board_id),
    UNIQUE KEY (board_id, column_name)
);

CREATE TABLE IF NOT EXISTS task (
    task_id BIGINT NOT NULL AUTO_INCREMENT,
    board_id BIGINT NOT NULL,
    column_id BIGINT NOT NULL,
    task_name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    task_index BIGINT NOT NULL,

    PRIMARY KEY (task_id),
    FOREIGN KEY (board_id) REFERENCES board(board_id),
    FOREIGN KEY (column_id) REFERENCES board_column(column_id)
);

CREATE TABLE IF NOT EXISTS label (
    label_id BIGINT NOT NULL AUTO_INCREMENT,
    board_id BIGINT NOT NULL,
    label_name VARCHAR(255) NOT NULL,
    red TINYINT UNSIGNED NOT NULL,
    green TINYINT UNSIGNED NOT NULL,
    blue TINYINT UNSIGNED NOT NULL,

    PRIMARY KEY (label_id),
    FOREIGN KEY (board_id) REFERENCES board(board_id),
    UNIQUE KEY (board_id, label_name)
);

CREATE TABLE IF NOT EXISTS task_label (
    board_id BIGINT NOT NULL,
    task_id BIGINT NOT NULL,
    label_id BIGINT NOT NULL,

    FOREIGN KEY (board_id) REFERENCES board(board_id),
    FOREIGN KEY (task_id) REFERENCES task(task_id),
    FOREIGN KEY (label_id) REFERENCES label(label_id),
    UNIQUE KEY (board_id, task_id, label_id)
);