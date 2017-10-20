CREATE TABLE Workflow(id INTEGER Primary key, name varchar(30) not null, active INTEGER not null);
CREATE TABLE Workflow_Bot_Step(id INTEGER Primary key, bot_source varchar(30) not null, bot_target varchar(30) null, sequence INTEGER not null, field_verify varchar(30) null);
CREATE TABLE Workflow_Bot(id INTEGER auto_increment Primary key, workflow_bot_step_id INTEGER not null,
					task_id INTEGER null, status varchar(30) not null, count_try INTEGER not null,
					date_created date not null, date_updated date null,
					FOREIGN KEY(workflow_bot_step_id) REFERENCES Workflow_Bot_Step(id));
					
INSERT INTO Workflow(id, name, active) VALUES(1, 'Votorantim', 1);
INSERT INTO Workflow_Bot_Step(id, bot_source, bot_target, sequence, field_verify) VALUES(1, 'votorantimFaturas', 'votorantimRC', 1, '"path":"(\S+)"');
INSERT INTO Workflow_Bot_Step(id, bot_source, bot_target, sequence, field_verify) VALUES(2, 'votorantimRC', 'consultaSap', 2, '"numeroRC":"(\d+)"');
INSERT INTO Workflow_Bot_Step(id, bot_source, bot_target, sequence, field_verify) VALUES(3, 'consultaSap', 'votorantimCP', 3, '"aprovadoRC":"(\true)"');
INSERT INTO Workflow_Bot_Step(id, bot_source, bot_target, sequence, field_verify) VALUES(4, 'votorantimCP', 'votorantimCAP', 4, '"numeroPC":"(\d+)"');
INSERT INTO Workflow_Bot_Step(id, bot_source, sequence) VALUES(5, 'votorantimCAP', 5);