insert into type_parameter(name, exclusive, credential) values('credential', 0, 1);
insert into type_parameter(name, exclusive, credential) values('retry', 0, 0);
insert into type_parameter(name, exclusive, credential) values('timeout', 0, 0);
insert into type_parameter(name, exclusive, credential) values('login', 0, 1);
insert into type_parameter(name, exclusive, credential) values('url', 0, 0);

insert into type_parameter(name, exclusive, credential) values('email-from', 0, 0);
insert into type_parameter(name, exclusive, credential) values('email-to', 0, 0);
insert into type_parameter(name, exclusive, credential) values('email-cc', 0, 0);
insert into type_parameter(name, exclusive, credential) values('email-body', 0, 0);
insert into type_parameter(name, exclusive, credential) values('smtp-server', 0, 0);
insert into type_parameter(name, exclusive, credential) values('smtp-user', 0, 0);
insert into type_parameter(name, exclusive, credential) values('smtp-password', 0, 1);
insert into type_parameter(name, exclusive, credential) values('smtp-subject', 0, 0);

insert into type_parameter(name, exclusive, credential) values('day_execution',0,0);
insert into type_parameter(name, exclusive, credential) values('time_execution',0,0);
insert into type_parameter(name, exclusive, credential) values('pathDriverChrome',0,0);
insert into type_parameter(name, exclusive, credential) values('urlpath',0,0);

# Todo remover esta linha apos apresentacao da votorantim
insert into type_parameter(name, exclusive, credential) values('pathFaturas',0,0);

insert into scope_parameter(id, name, priority) values(1, 'cloud', 0);
insert into scope_parameter(id, name, priority) values(2, 'bot', 1);
insert into scope_parameter(id, name, priority) values(3, 'cloud_bot', 2);
insert into scope_parameter(id, name, priority) values(4, 'session', 3);

insert into parameter(field_value, active, id_type_parameter, id_scope_parameter) values(3, 1, 2, 1);
insert into parameter(field_value, active, id_type_parameter, id_scope_parameter) values(300, 1, 3, 1);

insert into status_process_task(id, name) values(1, 'Created');
insert into status_process_task(id, name) values(2, 'Validating');
insert into status_process_task(id, name) values(3, 'Rejected');
insert into status_process_task(id, name) values(4, 'Scheduled');
insert into status_process_task(id, name) values(5, 'Processing');
insert into status_process_task(id, name) values(6, 'Processed');
insert into status_process_task(id, name) values(7, 'Suspending');
insert into status_process_task(id, name) values(8, 'Suspended');
insert into status_process_task(id, name) values(9, 'Success');
insert into status_process_task(id, name) values(10, 'Error');
insert into status_process_task(id, name) values(11, 'Canceled');

insert into status_process_item_task(id, name) values(1, 'Available');
insert into status_process_item_task(id, name) values(2, 'InLine');
insert into status_process_item_task(id, name) values(3, 'Processing');
insert into status_process_item_task(id, name) values(4, 'Success');
insert into status_process_item_task(id, name) values(5, 'Error');
insert into status_process_item_task(id, name) values(6, 'Canceled');
