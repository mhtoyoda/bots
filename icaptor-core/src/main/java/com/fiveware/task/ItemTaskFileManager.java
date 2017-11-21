package com.fiveware.task;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fiveware.dsl.file.FileConvert;
import com.fiveware.model.BotFormatter;
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
	
	public List<BotFormatter> getBotFormatters(String nameBot){
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
	
	public void generateFile(BotFormatter botFormatter, String dataOut){
		byte[] fileByte = getFileByte(botFormatter, dataOut);
		String indexField = getIndexField(botFormatter, dataOut);
		try {
			//TODO acertar geracao de file
			fileConvert.transformByteToFile(fileByte, botFormatter.getFieldIndex(), indexField);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}