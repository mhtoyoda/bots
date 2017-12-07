CREATE TABLE input_field (
  id BIGINT(20) auto_increment NOT NULL,
  name  VARCHAR(255) NOT NULL UNIQUE ,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


create table bot_has_inputFields (
  bot_id BIGINT(20) NOT NULL,
  inputField_id BIGINT(20)  NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
