CREATE TABLE status_process (
  id BIGINT(20) auto_increment NOT NULL,
  name varchar(20) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE task (
  id BIGINT(20) auto_increment NOT NULL,
  id_bot BIGINT(20) NOT NULL,
  id_user BIGINT(20) NOT NULL,
  load_time	DATETIME NOT NULL,
  start_at	DATETIME NULL,
  end_at	DATETIME NULL,  
  schedule_to DATETIME NULL,
  id_status BIGINT(20) NOT NULL,  
  PRIMARY KEY (id),
  FOREIGN KEY (id_bot) REFERENCES bot(id),
  FOREIGN KEY (id_user) REFERENCES usuario(id),
  FOREIGN KEY (id_status) REFERENCES status_process(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE item_task (
  id BIGINT(20) auto_increment NOT NULL,
  id_task BIGINT(20) NOT NULL,
  id_status BIGINT(20) NOT NULL,
  attempts_count BIGINT(20) NULL,
  start_at	DATETIME NULL,
  end_at	DATETIME NULL,  
  data_in MEDIUMTEXT NULL,
  data_out MEDIUMTEXT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (id_task) REFERENCES task(id),
  FOREIGN KEY (id_status) REFERENCES status_process(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;