package com.fiveware.task;

import java.io.IOException;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fiveware.dsl.file.FileConvert;
import com.fiveware.model.BotFormatter;
import com.fiveware.model.ItemTask;
import com.fiveware.model.Task;
import com.fiveware.model.TaskFile;
import com.fiveware.service.ServiceBot;
import com.fiveware.service.ServiceTaskFile;

@Component
public class TaskFileManager {

	private static Logger log = LoggerFactory.getLogger(TaskFileManager.class);
	
	@Autowired
	private ServiceTaskFile serviceTaskFile;
		
	@Autowired
	private ServiceBot serviceBot;
	
	@Autowired
	private FileConvert fileConvert;
	
	public List<TaskFile> getFileTaskById(Long taskId){
		List<TaskFile> taskList = serviceTaskFile.getFileTaskById(taskId);
		return taskList;
	}
	
	private byte[] getFileByte(BotFormatter botFormatter, String dataOut){
		try {
			JSONObject jsonObject = new JSONObject(dataOut);
			String value = (String) jsonObject.get(botFormatter.getFieldName());
			return value.getBytes();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private String getIndexField(BotFormatter botFormatter, String dataOut){
		try {
			JSONObject jsonObject = new JSONObject(dataOut);
			String value = (String) jsonObject.get(botFormatter.getFieldIndex());
			return value;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	private byte[] generateFile(BotFormatter botFormatter, String dataOut){
		byte[] fileByte = getFileByte(botFormatter, dataOut);
		if( null != fileByte){
			String indexField = getIndexField(botFormatter, dataOut);
			try {
				byte[] readByte = fileConvert.readByte(fileByte, indexField, botFormatter.getTypeFile());
				return readByte;
			} catch (IOException e) {
				e.printStackTrace();
			}						
		}
		return null;
	}
	
	private void saveTaskFile(Task task, byte[] zip) {			
		TaskFile taskFile = new TaskFile();			
		taskFile.setTask(task);				
		taskFile.setFile(zip);			
		taskFile = serviceTaskFile.save(taskFile);
	}
	
	public void processTaskFile(Task task, List<ItemTask> itemTaskList) {
		List<BotFormatter> botFormatters = getBotFormatters(task.getBot().getNameBot());
		if(CollectionUtils.isNotEmpty(botFormatters)){
			itemTaskList.forEach(itemTask ->{
				for(BotFormatter botFormatter: botFormatters){
					log.info("Generate file to task id: {} - field: {}", task.getId(), botFormatter.getFieldName());
					//TODO gerar zip
					//byte[] file = generateFile(botFormatter, itemTask.getDataOut());
//					if( null != file){
//						
//					}					
				}
			});
			byte[] zip = null;
			saveTaskFile(task, zip);
		}
	}
	
	private List<BotFormatter> getBotFormatters(String nameBot){
		List<BotFormatter> botFormatters = serviceBot.findBotFormatter(nameBot);
		return botFormatters;
	}
	
	public void deleteTaskFileManager(TaskFile taskFile){
		serviceTaskFile.delete(taskFile);
	}
}