package com.fiveware.task;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fiveware.dsl.file.FileExtractUtil;
import com.fiveware.model.BotFormatter;
import com.fiveware.model.ItemTask;
import com.fiveware.model.Task;
import com.fiveware.model.TaskFile;
import com.fiveware.service.ServiceBot;
import com.fiveware.service.ServiceTaskFile;
import com.google.common.collect.Lists;

@Component
public class TaskFileManager {

	private static Logger log = LoggerFactory.getLogger(TaskFileManager.class);
	
	@Autowired
	private ServiceTaskFile serviceTaskFile;
		
	@Autowired
	private ServiceBot serviceBot;
	
	@Autowired
	private FileExtractUtil fileExtractUtil;
	
	public List<TaskFile> getFileTaskById(Long taskId){
		List<TaskFile> taskList = serviceTaskFile.getFileTaskById(taskId);
		return taskList;
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
	
	private byte[] getFileByteArray(BotFormatter botFormatter, String dataOut){
		try {
			JSONObject jsonObject = new JSONObject(dataOut);
			String value = (String) jsonObject.get(botFormatter.getFieldName());
			return value.getBytes();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;						
	}
	
	private String generateFileName(BotFormatter botFormatter, String dataOut){
		String typeFile = botFormatter.getTypeFile().toLowerCase();
		String indexField = getIndexField(botFormatter, dataOut);
		String fileName = indexField + "." + typeFile;
		return fileName;
	}
	
	private void saveTaskFile(Task task, byte[] zip) {			
		TaskFile taskFile = new TaskFile();			
		taskFile.setTask(task);				
		taskFile.setFile(zip);			
		taskFile = serviceTaskFile.save(taskFile);
	}
	
	public void processTaskFile(Task task, List<ItemTask> itemTaskList) {
		Map<String, List<byte[]>> filesMap = null ; 
		List<BotFormatter> botFormatters = getBotFormatters(task.getBot().getNameBot());
		if(CollectionUtils.isNotEmpty(botFormatters)){
			filesMap = new HashMap<>();
			for(ItemTask itemTask : itemTaskList){
				List<byte[]> list = Lists.newArrayList();
				for(BotFormatter botFormatter: botFormatters){
					String key = generateFileName(botFormatter, itemTask.getDataOut());
					log.info("Generate file to task id: {} - item task id: {} - field: {}", task.getId(), itemTask.getId(), botFormatter.getFieldName());
					byte[] file = getFileByteArray(botFormatter, itemTask.getDataOut());
					if(null != file){
						if(filesMap.containsKey(key)){
							filesMap.get(key).add(file);
						}else{
							list.add(file);
							filesMap.put(key, list);
						}
					}
				}
			}
			
			try {
				byte[] zip = fileExtractUtil.createZipFile(filesMap);
				saveTaskFile(task, zip);
			} catch (IOException e) {
				e.printStackTrace();
			}
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