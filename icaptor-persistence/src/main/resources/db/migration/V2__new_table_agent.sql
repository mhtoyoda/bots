create table agent
(
  id bigint auto_increment primary key,
  ip_agent varchar(255) null,
  name_agent varchar(255) null,
  port_agent int null,
  id_server bigint null,
  FOREIGN KEY (id_server) REFERENCES server(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;