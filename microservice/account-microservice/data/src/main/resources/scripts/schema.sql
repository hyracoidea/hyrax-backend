DROP TABLE IF EXISTS account;

CREATE TABLE IF NOT EXISTS account (
  account_id BIGINT NOT NULL AUTO_INCREMENT,
  first_name VARCHAR(255) NOT NULL,
  last_name VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  password_hash VARCHAR(255) NOT NULL,

  PRIMARY KEY (account_id),
  UNIQUE(email)
);