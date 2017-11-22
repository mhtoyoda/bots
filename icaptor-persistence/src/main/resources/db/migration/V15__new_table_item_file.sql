CREATE TABLE task_file (
  id BIGINT(20) auto_increment NOT NULL,
  id_task  BIGINT(20) NOT NULL,
  task_file	LONGBLOB NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (id_task) REFERENCES task(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;