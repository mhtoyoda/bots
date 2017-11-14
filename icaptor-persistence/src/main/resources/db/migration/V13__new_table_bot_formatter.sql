CREATE TABLE bot_formatter (
  id BIGINT(20) auto_increment NOT NULL,
  id_bot BIGINT(20) NOT NULL,
  type_file 	VARCHAR(6) NOT NULL,
  field_index	VARCHAR(30) NOT NULL,
  field_name	VARCHAR(30) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (id_bot) REFERENCES bot(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;