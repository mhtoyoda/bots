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

CREATE TABLE `group` (
  id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE permission (
  id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE company_group (
	id_company BIGINT(20) NOT NULL,
	id_group BIGINT(20) NOT NULL,
    PRIMARY KEY (id_company, id_group),
	FOREIGN KEY (id_company) REFERENCES company(id),
	FOREIGN KEY (id_group) REFERENCES `group`(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE group_user (
  id_user BIGINT(20) NOT NULL,
  id_group BIGINT(20) NOT NULL,
  PRIMARY KEY (id_user, id_group),
  FOREIGN KEY (id_user) REFERENCES user(id),
  FOREIGN KEY (id_group) REFERENCES `group`(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE group_permission (
  id_group BIGINT(20) NOT NULL,
  id_permission BIGINT(20) NOT NULL,
  PRIMARY KEY (id_group, id_permission),
  FOREIGN KEY (id_group) REFERENCES `group`(id),
  FOREIGN KEY (id_permission) REFERENCES permission(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO user (display_name, email, password, active) VALUES ("admin","admin@icaptor.com",'$2a$10$0ympKfE9MW6ERWelQBvPNOO2GNI2AZ6isfRCayMUsb6k3rnrFX/Yq',1);

INSERT INTO company (name) VALUES ('Fiveware');

INSERT INTO `group` (name) VALUES ('administrator');
INSERT INTO `group` (name) VALUES ('developer');

INSERT INTO permission (name) VALUES ('ALL');
INSERT INTO permission (name) VALUES ('DEV');

INSERT INTO company_group (id_company, id_group) VALUES (1,1);
INSERT INTO company_group (id_company, id_group) VALUES (1,2);

INSERT INTO group_user (id_user, id_group) VALUES (1,2);

INSERT INTO group_permission (id_group, id_permission) VALUES (1,1);
INSERT INTO group_permission (id_group, id_permission) VALUES (1,2);
