create table server_agent
(
  server_id bigint not null,
  agents_id bigint not null,
  constraint UK_agents_id
  unique (agents_id),
  constraint FK_server_id_01
  foreign key (server_id) references server (id),
  constraint FK_agents_id_02
  foreign key (agents_id) references agent (id)
);

create index FK_server_id_01 on server_agent (server_id);

