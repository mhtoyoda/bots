create table agent
(
  id bigint auto_increment primary key,
  ip_agent varchar(255) null,
  name_agent varchar(255) null,
  port_agent int null,
  server_id bigint null,
  constraint idx_agent
  foreign key (server_id) references server (id)
);

create index idx_agent on agent (server_id);