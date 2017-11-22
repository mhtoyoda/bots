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
import com.fiveware.model.ItemTaskFile;
import com.fiveware.model.message.MessageBot;
import com.fiveware.service.ServiceBot;
import com.fiveware.service.ServiceItemTaskFile;

@Component
public class ItemTaskFileManager {

	@Autowired
	private ServiceItemTaskFile itemServiceTaskFile;
		
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
	
	public void createItemTaskFile(ItemTask itemTask, BotFormatter botFormatter) {
		byte[] file = generateFile(botFormatter, itemTask.getDataOut());
		if( null != file){
			ItemTaskFile itemTaskFile = new ItemTaskFile();
			itemTaskFile.setItemTask(itemTask);	
			itemTaskFile.setFile(file);
			itemTaskFile = itemServiceTaskFile.save(itemTaskFile);			
		}
	}
	
	public void saveItemTaskFile(MessageBot messageBot, ItemTask itemTask) {
		List<BotFormatter> botFormatters = getBotFormatters(messageBot.getBotName());
		if(CollectionUtils.isNotEmpty(botFormatters)){		
			for(BotFormatter botFormatter : botFormatters){
				createItemTaskFile(itemTask, botFormatter);				
			}
		}
	}
}