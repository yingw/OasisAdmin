DROP TABLE IF EXISTS persistent_logins;

CREATE TABLE persistent_logins (
  username  VARCHAR(64) NOT NULL,
  series    VARCHAR(64) PRIMARY KEY,
  token     VARCHAR(64) NOT NULL,
  last_used TIMESTAMP   NOT NULL
);
/*CREATE TABLE persistent_logins (
  series    VARCHAR(255) NOT NULL,
  last_used DATETIME     NOT NULL,
  token     VARCHAR(255) NOT NULL,
  username  VARCHAR(255) NOT NULL,
  PRIMARY KEY (series)
)
  ENGINE = MyISAM*/