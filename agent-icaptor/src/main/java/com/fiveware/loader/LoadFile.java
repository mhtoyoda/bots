package com.fiveware.loader;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fiveware.exception.AttributeLoadException;
import com.fiveware.file.FileUtil;
import com.fiveware.model.BotClassLoaderContext;
import com.fiveware.model.InputDictionaryContext;
import com.fiveware.model.OutTextRecord;
import com.fiveware.model.Record;
import com.fiveware.service.ServiceBot;
import com.fiveware.validate.Validate;

@Component
public class LoadFile {

	Logger logger = LoggerFactory.getLogger(LoadFile.class);

	@Autowired
	private FileUtil fileUtil;
	
	@Autowired
	private ServiceBot<String> serviceBot;
	
	@Autowired
	private Validate<String> validate;
	
	@Autowired
	private ClassLoaderConfig classLoaderConfig;
	
	@Autowired
	private ClassLoaderRunner classLoaderRunner;
	
	@SuppressWarnings("rawtypes")
	public void executeLoad(String botName, File file) throws IOException, AttributeLoadException, ClassNotFoundException {
		logger.info("Init Import File "+file.getName());		
		BotClassLoaderContext botClassLoaderContext = classLoaderConfig.getPropertiesBot("consultaCEP");
		InputDictionaryContext inputDictionary = botClassLoaderContext.getInputDictionary();
		String separatorInput = inputDictionary.getSeparator(); 
		String[] fieldsInput = inputDictionary.getFields();
		String fileNameOut = botClassLoaderContext.getOutputDictionary().getNameFileOut();
		List<Record> recordLines = fileUtil.linesFrom(file, fieldsInput, separatorInput );
		Class classLoader = classLoaderRunner.loadClassLoader(botName);
		for (Record line : recordLines) {
			String cep = (String) line.getValue("cep");
			try {
				validate.validate(cep, classLoader);
				OutTextRecord result = serviceBot.callBot(botName, cep);
				fileUtil.writeFile(fileNameOut, separatorInput, result);
			} catch (Exception e) {
				logger.error("Unprocessed Record - Cause: "+e.getMessage());
				Map<String, Object> map = new LinkedHashMap<>();
		        map.put("cep", cep);
				fileUtil.writeFile(fileNameOut, separatorInput, new OutTextRecord(map));
			}
		}
		
		logger.info("End Import File "+file.getName());
	}
}