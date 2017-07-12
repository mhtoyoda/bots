DROP TABLE parameter_bot;

CREATE TABLE parameter_bot (
  id BIGINT(20) NOT NULL,
  name VARCHAR(60) NOT NULL,  
  id_bot BIGINT(20) NOT NULL,
  ativo BOOLEAN DEFAULT true,
  PRIMARY KEY (id),
  FOREIGN KEY (id_bot) REFERENCES bot(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE parameter_value_bot (
  id BIGINT(20) NOT NULL,
  key VARCHAR(60) NOT NULL,
  value VARCHAR(180) NOT NULL,
  id_parameter_bot BIGINT(20) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (id_parameter_bot) REFERENCES parameter_bot(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;