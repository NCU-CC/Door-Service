CREATE TABLE IF NOT EXISTS entity_authorization
(
  id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  version INT DEFAULT 0,
  created_at DATETIME,
  authorizee_id INT NOT NULL,
  authorizer_id INT NOT NULL,
  entity_id INT NOT NULL
);

CREATE TABLE IF NOT EXISTS authorization_token
(
  id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  version INT DEFAULT 0,
  created_at DATETIME,
  expired_at DATETIME,
  creator_id INT NOT NULL,
  entity_id INT NOT NULL,
  token VARCHAR(64)
);

CREATE TABLE IF NOT EXISTS internet_entity
(
  id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  version INT DEFAULT 0,
  created_at DATETIME,
  updated_at DATETIME,
  creator_id INT NOT NULL,
  ip VARCHAR(255),
  name VARCHAR(255),
  uuid VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS user
(
  id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  version INT DEFAULT 0,
  created_at DATETIME,
  updated_at DATETIME,
  type INT DEFAULT 0,
  description VARCHAR(255),
  uid VARCHAR(255)
);