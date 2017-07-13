CREATE TABLE task (
  id BIGINT(20) NOT NULL,
  create_time	DATETIME NOT NULL,
  last_update_time	DATETIME NOT NULL,
  start_time	DATETIME,
  end_time	    DATETIME,
  count_executions BIGINT(20) NULL,
  status VARCHAR(30) NOT NULL,
  id_bot BIGINT(20) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (id_bot) REFERENCES bot(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;