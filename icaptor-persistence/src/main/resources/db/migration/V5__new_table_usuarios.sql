CREATE TABLE company (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE user (
  id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
  display_name VARCHAR(50) NOT NULL,
  email VARCHAR(50) NOT NULL,
  password VARCHAR(150) NOT NULL,
  active BOOLEAN DEFAULT true,
  profile_image_path VARCHAR(50)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE grupo (
  id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE permission (
  id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE company_grupo (
	id_company BIGINT(20) NOT NULL,
	id_grupo BIGINT(20) NOT NULL,
    PRIMARY KEY (id_company, id_grupo),
	FOREIGN KEY (id_company) REFERENCES company(id),
	FOREIGN KEY (id_grupo) REFERENCES grupo(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE grupo_user (
  id_user BIGINT(20) NOT NULL,
  id_grupo BIGINT(20) NOT NULL,
  PRIMARY KEY (id_user, id_grupo),
  FOREIGN KEY (id_user) REFERENCES user(id),
  FOREIGN KEY (id_grupo) REFERENCES grupo(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE grupo_permission (
  id_grupo BIGINT(20) NOT NULL,
  id_permission BIGINT(20) NOT NULL,
  PRIMARY KEY (id_grupo, id_permission),
  FOREIGN KEY (id_grupo) REFERENCES grupo(id),
  FOREIGN KEY (id_permission) REFERENCES permission(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO user (display_name, email, password, active) VALUES ("admin","admin@icaptor.com",'$2a$10$0ympKfE9MW6ERWelQBvPNOO2GNI2AZ6isfRCayMUsb6k3rnrFX/Yq',1);
INSERT INTO user (display_name, email, password, active) VALUES ("developer","dev@icaptor.com",'$2a$10$0ympKfE9MW6ERWelQBvPNOO2GNI2AZ6isfRCayMUsb6k3rnrFX/Yq',1);

INSERT INTO company (name) VALUES ('Fiveware');

INSERT INTO grupo (name) VALUES ('administrator');
INSERT INTO grupo (name) VALUES ('developer');

INSERT INTO permission (name) VALUES ('ROLE_TASK_ADD');
INSERT INTO permission (name) VALUES ('ROLE_TASK_LIST');
INSERT INTO permission (name) VALUES ('ROLE_TASK_PAUSE');
INSERT INTO permission (name) VALUES ('ROLE_TASK_CANCEL');
INSERT INTO permission (name) VALUES ('ROLE_TASK_RESUME');


INSERT INTO permission (name) VALUES ('ROLE_AGENT_ADD');
INSERT INTO permission (name) VALUES ('ROLE_AGENT_LIST');
INSERT INTO permission (name) VALUES ('ROLE_AGENT_UPDATE');
INSERT INTO permission (name) VALUES ('ROLE_AGENT_DELETE');

INSERT INTO permission (name) VALUES ('ROLE_USER_ADD');
INSERT INTO permission (name) VALUES ('ROLE_USER_LIST');
INSERT INTO permission (name) VALUES ('ROLE_USER_UPDATE');
INSERT INTO permission (name) VALUES ('ROLE_USER_DELETE');

INSERT INTO permission (name) VALUES ('ROLE_PARAMETER_ADD');
INSERT INTO permission (name) VALUES ('ROLE_PARAMETER_LIST');
INSERT INTO permission (name) VALUES ('ROLE_PARAMETER_UPDATE');
INSERT INTO permission (name) VALUES ('ROLE_PARAMETER_DELETE');

INSERT INTO company_grupo (id_company, id_grupo) VALUES (1,1);
INSERT INTO company_grupo (id_company, id_grupo) VALUES (1,2);

INSERT INTO grupo_user (id_user, id_grupo) VALUES (1,1);
INSERT INTO grupo_user (id_user, id_grupo) VALUES (2,2);

# User Admin
INSERT INTO grupo_permission (id_grupo, id_permission) VALUES (1,1);
INSERT INTO grupo_permission (id_grupo, id_permission) VALUES (1,2);
INSERT INTO grupo_permission (id_grupo, id_permission) VALUES (1,3);
INSERT INTO grupo_permission (id_grupo, id_permission) VALUES (1,4);
INSERT INTO grupo_permission (id_grupo, id_permission) VALUES (1,5);
INSERT INTO grupo_permission (id_grupo, id_permission) VALUES (1,6);
INSERT INTO grupo_permission (id_grupo, id_permission) VALUES (1,7);
INSERT INTO grupo_permission (id_grupo, id_permission) VALUES (1,8);
INSERT INTO grupo_permission (id_grupo, id_permission) VALUES (1,9);
INSERT INTO grupo_permission (id_grupo, id_permission) VALUES (1,10);
INSERT INTO grupo_permission (id_grupo, id_permission) VALUES (1,11);
INSERT INTO grupo_permission (id_grupo, id_permission) VALUES (1,12);
INSERT INTO grupo_permission (id_grupo, id_permission) VALUES (1,13);
INSERT INTO grupo_permission (id_grupo, id_permission) VALUES (1,14);
INSERT INTO grupo_permission (id_grupo, id_permission) VALUES (1,15);
INSERT INTO grupo_permission (id_grupo, id_permission) VALUES (1,16);
INSERT INTO grupo_permission (id_grupo, id_permission) VALUES (1,17);

# User Dev
INSERT INTO grupo_permission (id_grupo, id_permission) VALUES (2,2);
INSERT INTO grupo_permission (id_grupo, id_permission) VALUES (2,7);
