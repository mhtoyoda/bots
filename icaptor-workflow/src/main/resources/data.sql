CREATE TABLE Workflow(id INTEGER Primary key, name varchar(30) not null, active INTEGER not null);
CREATE TABLE Workflow_Bot_Cron(id INTEGER Primary key, workflow_id INTEGER not null, day_execution INTEGER not null,
						  time_execution time not null, FOREIGN KEY(workflow_id) REFERENCES Workflow(id));
CREATE TABLE Workflow_Bot_Step(id INTEGER Primary key, bot_source varchar(30) not null, bot_target varchar(30) null, sequence INTEGER not null);
CREATE TABLE Workflow_Bot(id INTEGER auto_increment Primary key, workflow_bot_step_id INTEGER not null,
					task_id INTEGER null, status varchar(30) not null, count_try INTEGER not null,
					date_created date not null, date_updated date null,
					FOREIGN KEY(workflow_bot_step_id) REFERENCES Workflow_Bot_Step(id));
					
INSERT INTO Workflow(id, name, active) VALUES(1, 'Votorantim', 1);
INSERT INTO Workflow_Bot_Step(id, bot_source, bot_target, sequence) VALUES(1, 'votorantimRC', 'votorantimCP', 1);
INSERT INTO Workflow_Bot_Step(id, bot_source, bot_target, sequence) VALUES(2, 'votorantimCP', 'votorantimSap', 2);
INSERT INTO Workflow_Bot_Step(id, bot_source, bot_target, sequence) VALUES(3, 'votorantimSap', 'votorantimCAP', 3);
INSERT INTO Workflow_Bot_Step(id, bot_source, sequence) VALUES(4, 'votorantimCAP', 4);
INSERT INTO Workflow_Bot_Cron(id, workflow_id, day_execution, time_execution) VALUES(1, 1, 15, PARSEDATETIME('08:00', 'HH:mm'));
INSERT INTO Workflow_Bot_Cron(id, workflow_id, day_execution, time_execution) VALUES(2, 1, 16, PARSEDATETIME('12:00', 'HH:mm'));
INSERT INTO Workflow_Bot_Cron(id, workflow_id, day_execution, time_execution) VALUES(3, 1, 17, PARSEDATETIME('16:00', 'HH:mm'));