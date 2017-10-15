CREATE TABLE recent_activity (
	id BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    message VARCHAR(50) NOT NULL,
	creation_time datetime NOT NULL,
    user_id BIGINT(20) NOT NULL,

    FOREIGN KEY (user_id) REFERENCES user(id)
)ENGINE=InnoDB DEFAULT charset=utf8;