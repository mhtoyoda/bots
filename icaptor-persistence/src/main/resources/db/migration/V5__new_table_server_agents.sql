create table server_agent
(
  server_id bigint not null,
  agents_id bigint not null,
  PRIMARY KEY (`server_id`,`agents_id`),

  KEY FK_SERVERAGENT_idx (server_id),
  constraint FK_SERVERAGENT_server
  foreign key (server_id) references server (id) ON DELETE CASCADE ON UPDATE CASCADE,
  constraint FK_SERVERAGENT_agent
  foreign key (agents_id) references agent (id) ON DELETE CASCADE ON UPDATE CASCADE
);

