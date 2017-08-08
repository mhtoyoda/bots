insert into type_parameter(name, exclusive) values('credential', 0);
insert into type_parameter(name, exclusive) values('retry', 0);
insert into type_parameter(name, exclusive) values('timeout', 0);

insert into scope_parameter(name, priority) values('cloud', 0);
insert into scope_parameter(name, priority) values('bot', 1);
insert into scope_parameter(name, priority) values('cloud_bot', 2);
insert into scope_parameter(name, priority) values('session', 3);

insert into status_process(name) values('Created');
insert into status_process(name) values('Validate');
insert into status_process(name) values('Processing');
insert into status_process(name) values('Proccessed');
insert into status_process(name) values('Suspended');
insert into status_process(name) values('Available');
insert into status_process(name) values('InLine');
insert into status_process(name) values('Success');
insert into status_process(name) values('Error');
