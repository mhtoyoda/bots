insert into parameter(name) values('credential');
insert into parameter(name) values('retry');
insert into parameter(name) values('timeToWait');

insert into parameter_key(name, id_parameter) values('login', 1);
insert into parameter_key(name, id_parameter) values('senha', 1);
insert into parameter_key(name, id_parameter) values('retry', 2);
insert into parameter_key(name, id_parameter) values('timeWait', 3);

INSERT INTO type_parameter(type_parameter) values('parâmetro cloud');
INSERT INTO type_parameter(type_parameter) values('parâmetro cloud bot');
INSERT INTO type_parameter(type_parameter) values('parâmetro sessão');

insert into status_process(name) values('Created');
insert into status_process(name) values('Validate');
insert into status_process(name) values('Processing');
insert into status_process(name) values('Proccessed');
insert into status_process(name) values('Suspended');
insert into status_process(name) values('Available');
insert into status_process(name) values('InLine');
insert into status_process(name) values('Success');
insert into status_process(name) values('Error');
