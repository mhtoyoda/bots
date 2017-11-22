package com.fiveware.task;

import java.io.IOException;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fiveware.dsl.file.FileConvert;
import com.fiveware.model.BotFormatter;
import com.fiveware.model.ItemTask;
import com.fiveware.model.TaskFile;
import com.fiveware.model.message.MessageBot;
import com.fiveware.service.ServiceBot;
import com.fiveware.service.ServiceTaskFile;

@Component
public class TaskFileManager {

	@Autowired
	private ServiceTaskFile serviceTaskFile;
		
	@Autowired
	private ServiceBot serviceBot;
	
	@Autowired
	private FileConvert fileConvert;
	
	private List<BotFormatter> getBotFormatters(String nameBot){
		List<BotFormatter> botFormatters = serviceBot.findBotFormatter(nameBot);
		return botFormatters;
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
	
	public void createTaskFile(ItemTask itemTask, BotFormatter botFormatter) {
		byte[] file = generateFile(botFormatter, itemTask.getDataOut());
		if( null != file){
			TaskFile taskFile = new TaskFile();
			taskFile.setTask(itemTask.getTask());	
			taskFile.setFile(file);
			taskFile = serviceTaskFile.save(taskFile);			
		}
	}
	
	public void saveTaskFile(MessageBot messageBot, ItemTask itemTask) {
		List<BotFormatter> botFormatters = getBotFormatters(messageBot.getBotName());
		if(CollectionUtils.isNotEmpty(botFormatters)){		
			for(BotFormatter botFormatter : botFormatters){
				createTaskFile(itemTask, botFormatter);				
			}
		}
	}
}