DROP TABLE IF EXISTS APP_USER;
CREATE TABLE APP_USER (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  username VARCHAR(250) NOT NULL,
  password VARCHAR(250) NOT NULL,
  fullName VARCHAR(250) NOT NULL,
  country VARCHAR(250) NOT NULL,
  age int NOT NULL,
  email VARCHAR(250) DEFAULT NULL,
  isAuthenticated BOOLEAN
);

DROP TABLE IF EXISTS IMAGE_METADATA;
CREATE TABLE IMAGE_METADATA (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  username VARCHAR(250) NOT NULL,
  fileId VARCHAR(250) NOT NULL,
  fileType VARCHAR(250) NOT NULL,
  deleteHash VARCHAR(250) NOT NULL,
  previewLink VARCHAR(250) NOT NULL
  );