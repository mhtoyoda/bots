CREATE TABLE parameter(
	id bigint auto_increment primary key,
	name varchar(60) null
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE parameter_bot (
  id BIGINT(20) auto_increment primary key,
  id_bot BIGINT(20) NOT NULL,
  id_parameter BIGINT(20) NOT NULL,
  exclusive BOOLEAN DEFAULT false,
  FOREIGN KEY (id_bot) REFERENCES bot(id),
  FOREIGN KEY (id_parameter) REFERENCES parameter(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE parameter_key(
    id bigint auto_increment primary key,
    name varchar(60) null,
    id_parameter BIGINT(20) NOT NULL,
    FOREIGN KEY (id_parameter) REFERENCES parameter(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE type_parameter(
	id bigint auto_increment primary key,	
    type_parameter varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE parameter_value(
	id bigint auto_increment primary key,
	value varchar(60) null,
    id_parameter_key BIGINT(20) NOT NULL,
    id_type_parameter BIGINT(20) NOT NULL,	
    FOREIGN KEY (id_parameter_key) REFERENCES parameter_key(id),
    FOREIGN KEY (id_type_parameter) REFERENCES type_parameter(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE parameter_credential(
	id bigint auto_increment primary key,
	value varchar(60) null,
	active BOOLEAN DEFAULT true,
    id_parameter_key BIGINT(20) NOT NULL,
    id_parameter_bot BIGINT(20) NOT NULL,
    FOREIGN KEY (id_parameter_key) REFERENCES parameter_key(id),
    FOREIGN KEY (id_parameter_bot) REFERENCES parameter_bot(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE parameter_session_bot(
	id bigint auto_increment primary key,
    id_parameter_login BIGINT(20) NOT NULL,
    id_parameter_value BIGINT(20) NOT NULL,
    FOREIGN KEY (id_parameter_login) REFERENCES parameter_credential(id),
    FOREIGN KEY (id_parameter_value) REFERENCES parameter_value(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;