CREATE TABLE recent_activity (
	id BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    message VARCHAR(50) NOT NULL,
	creation_time datetime NOT NULL,
    user_id BIGINT(20) NOT NULL,
    task_id BIGINT(20) NULL,
    bot_id BIGINT(20) NULL,
    visualized BOOLEAN DEFAULT false,

    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (task_id) REFERENCES task(id),
    FOREIGN KEY (bot_id) REFERENCES bot(id)
)ENGINE=InnoDB DEFAULT charset=utf8;