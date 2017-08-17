CREATE TABLE type_parameter(
	id bigint auto_increment primary key,
	name varchar(60) null,
    exclusive boolean default false
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE scope_parameter(
	id bigint primary key,
	name varchar(60) not null,
    priority tinyint(20) not null
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE parameter(
	id bigint auto_increment primary key,
    field_value varchar(200) not null,
	active boolean default true,
    id_type_parameter bigint(20) not null,
    id_scope_parameter bigint(20) not null,
    id_cloud bigint(20) not null,
    id_bot bigint(20) not null,
    id_user bigint(20) not null,
    FOREIGN KEY (id_type_parameter) REFERENCES type_parameter(id),
    FOREIGN KEY (id_scope_parameter) REFERENCES scope_parameter(id),
    FOREIGN KEY (id_cloud) REFERENCES server(id),
    FOREIGN KEY (id_bot) REFERENCES bot(id),
    FOREIGN KEY (id_user) REFERENCES user(id)    
) ENGINE=InnoDB DEFAULT CHARSET=utf8;