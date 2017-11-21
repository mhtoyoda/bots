CREATE TABLE item_task_file (
  id BIGINT(20) auto_increment NOT NULL,
  id_item_task  BIGINT(20) NOT NULL,
  item_file	LONGBLOB NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (id_item_task) REFERENCES item_task(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;