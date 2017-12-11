CREATE TABLE input_field (
  id BIGINT(20) auto_increment NOT NULL,
  name  VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- auto-generated definition
CREATE TABLE bot_has_inputFields
(
  bot_id        BIGINT NOT NULL,
  inputField_id BIGINT NOT NULL,
  PRIMARY KEY (bot_id, inputField_id),
  CONSTRAINT FK144sbkv64piwqeklvm9vkjx7e
  FOREIGN KEY (bot_id) REFERENCES bot (id),
  CONSTRAINT FKpxa9656ww85pvaoxy8iicpo0q
  FOREIGN KEY (inputField_id) REFERENCES input_field (id)
);
CREATE INDEX FKpxa9656ww85pvaoxy8iicpo0q
  ON bot_has_inputFields (inputField_id);