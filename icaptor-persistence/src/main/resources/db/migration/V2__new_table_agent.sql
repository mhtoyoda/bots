create table agent
(
  id bigint auto_increment primary key,
  ip_agent varchar(255) null,
  name_agent varchar(255) null,
  port_agent int null
) ENGINE=InnoDB DEFAULT CHARSET=utf8;