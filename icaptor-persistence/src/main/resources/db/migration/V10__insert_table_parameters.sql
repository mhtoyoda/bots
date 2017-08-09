insert into type_parameter(name, exclusive) values('credential', 0);
insert into type_parameter(name, exclusive) values('retry', 0);
insert into type_parameter(name, exclusive) values('timeout', 0);

insert into scope_parameter(name, priority) values('cloud', 0);
insert into scope_parameter(name, priority) values('bot', 1);
insert into scope_parameter(name, priority) values('cloud_bot', 2);
insert into scope_parameter(name, priority) values('session', 3);

insert into status_process(id, name) values(1, 'Created');
insert into status_process(id, name) values(2, 'Validating');
insert into status_process(id, name) values(3, 'Rejected');
insert into status_process(id, name) values(4, 'Scheduled');
insert into status_process(id, name) values(5, 'Processing');
insert into status_process(id, name) values(6, 'Proccessed');
insert into status_process(id, name) values(7, 'Suspended');
insert into status_process(id, name) values(8, 'Available');
insert into status_process(id, name) values(9, 'InLine');
insert into status_process(id, name) values(10, 'Success');
insert into status_process(id, name) values(11, 'Error');
insert into status_process(id, name) values(12, 'Canceled');
