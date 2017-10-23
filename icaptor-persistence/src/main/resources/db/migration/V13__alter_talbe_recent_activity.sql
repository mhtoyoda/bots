ALTER  TABLE recent_activity ADD task_id BIGINT(20) NULL;
ALTER  TABLE recent_activity ADD bot_id BIGINT(20) NULL;
ALTER  TABLE recent_activity ADD visualized BOOLEAN DEFAULT false;
    
ALTER TABLE recent_activity ADD FOREIGN KEY (task_id) REFERENCES task(id);
ALTER TABLE recent_activity ADD FOREIGN KEY (bot_id) REFERENCES bot(id);
