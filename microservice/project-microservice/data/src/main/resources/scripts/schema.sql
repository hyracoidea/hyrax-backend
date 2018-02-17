DROP TABLE IF EXISTS team;

CREATE TABLE IF NOT EXISTS team (
  team_id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  description VARCHAR(255) NULL,
  owner_username VARCHAR(255) NOT NULL,

  PRIMARY KEY (team_id),
  UNIQUE KEY (name)
);