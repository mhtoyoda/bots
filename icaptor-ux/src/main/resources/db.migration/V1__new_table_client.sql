CREATE TABLE role (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(45) DEFAULT NULL,
  PRIMARY KEY ('id')
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;


insert into role values(1,'ROLE_USER');
insert into role values(2,'ROLE_ADMIN');
