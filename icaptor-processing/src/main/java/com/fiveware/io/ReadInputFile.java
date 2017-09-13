package com.fiveware.io;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import com.fiveware.model.Bot;
import com.fiveware.model.StatusProcessTaskEnum;
import com.fiveware.model.Task;
import com.fiveware.task.TaskManager;
import com.fiveware.util.FileUtil;
import com.fiveware.validate.RuleValidations;
import com.fiveware.validate.ValidationFileErrorException;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;

/**
 * Created by valdisnei on 13/06/17.
 */
@Component
public class ReadInputFile {
	
	private static Logger logger = LoggerFactory.getLogger(ReadInputFile.class);
	
    @Autowired
    private FileUtil fileUtil;
    
    @Autowired
    private TaskManager taskManager;

    @Autowired
    private RuleValidations ruleValidations;
    
    private Long userId = 1L;
    
    public void readFile(final String nameBot, final String path, InputStream file,
                         DeferredResult<ResponseEntity<String>> resultado) throws IOException {
        Task task = createTask(nameBot, userId);
        Bot bot = task.getBot();
        String separatorFile = bot.getSeparatorFile();
        Iterable<String> split = Splitter.on(",").split(bot.getFieldsInput());
        String[] fields = Iterables.toArray(split, String.class);
        List<String> lines = fileUtil.linesFrom(file);

        // Devolve a thread do Browser
        resultado.setResult(ResponseEntity.ok().body("OK"));

        try {
            taskManager.updateTask(task.getId(), StatusProcessTaskEnum.VALIDATING);
            ruleValidations.executeValidations(lines, fields, separatorFile);
            taskManager.updateTask(task.getId(), StatusProcessTaskEnum.PROCESSING);
            createItemTask(task, lines);                
        } catch (ValidationFileErrorException e) {
            taskManager.updateTask(task.getId(), StatusProcessTaskEnum.REJECTED);
        } catch (Exception e){
        	logger.error("Error {}", e.getMessage());
        }
    }

	private void createItemTask(Task task, List<String> lines) {
		if(CollectionUtils.isEmpty(lines)){
			taskManager.createItemTask(task, "");            	
		}else{
			lines.stream().forEach(line -> {
				taskManager.createItemTask(task, line);
			});            		
		}
	}

    private Task createTask(String nameBot, Long userId){
        return taskManager.createTask(nameBot, userId);
    }
}