create table agent_bot
(
  id_agent bigint not null,
  id_bot bigint not null,
  constraint idx_agent_bot_01
  foreign key (id_agent) references agent (id),
  constraint idx_agent_bot_02
  foreign key (id_bot) references bot (id)
)
;

create index idx_agent_bot_01
  on agent_bot (id_agent)
;

create index idx_agent_bot_02
  on agent_bot (id_bot)
;

