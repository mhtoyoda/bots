insert into type_parameter(name, exclusive, credential) values('credential', 0, 1);
insert into type_parameter(name, exclusive, credential) values('retry', 0, 0);
insert into type_parameter(name, exclusive, credential) values('timeout', 0, 0);

insert into scope_parameter(id, name, priority) values(1, 'cloud', 0);
insert into scope_parameter(id, name, priority) values(2, 'bot', 1);
insert into scope_parameter(id, name, priority) values(3, 'cloud_bot', 2);
insert into scope_parameter(id, name, priority) values(4, 'session', 3);

insert into status_process_task(id, name) values(1, 'Created');
insert into status_process_task(id, name) values(2, 'Validating');
insert into status_process_task(id, name) values(3, 'Rejected');
insert into status_process_task(id, name) values(4, 'Scheduled');
insert into status_process_task(id, name) values(5, 'Processing');
insert into status_process_task(id, name) values(6, 'Proccessed');
insert into status_process_task(id, name) values(7, 'Suspended');
insert into status_process_task(id, name) values(8, 'Success');
insert into status_process_task(id, name) values(9, 'Error');
insert into status_process_task(id, name) values(10, 'Canceled');

insert into status_process_item_task(id, name) values(1, 'Available');
insert into status_process_item_task(id, name) values(2, 'InLine');
insert into status_process_item_task(id, name) values(3, 'Processing');
insert into status_process_item_task(id, name) values(4, 'Success');
insert into status_process_item_task(id, name) values(5, 'Error');
