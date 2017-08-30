CREATE TABLE agent_parameter (
  id BIGINT(20) auto_increment NOT NULL,
  id_agent BIGINT(20) NOT NULL,
  id_parameter BIGINT(20) NOT NULL,
  use_date	DATETIME NOT NULL,  
  PRIMARY KEY (id),
  UNIQUE KEY un_agent_param (id_agent, id_parameter),
  FOREIGN KEY (id_agent) REFERENCES agent(id),
  FOREIGN KEY (id_parameter) REFERENCES parameter(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;