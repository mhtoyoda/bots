package com.fiveware.loader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fiveware.Automation;
import com.fiveware.file.FileUtil;
import com.fiveware.metadata.Dictionary;
import com.fiveware.model.Record;

@Component
public class LoadFile {

	Logger logger = LoggerFactory.getLogger(LoadFile.class);
	
	@Autowired
	private Automation automation;
	
	@Autowired
	private FileUtil fileUtil;
	
	@Autowired
	private Dictionary dictionary;
	
	public void executeLoad(File file) throws FileNotFoundException{
		String separatorInput = dictionary.getSeparatorInput(automation);
		String[] fieldsInput = dictionary.getFieldsInput(automation);
		List<Record> recordLines = fileUtil.linesFrom(file, fieldsInput, separatorInput);
		for (Record line : recordLines) {
			try{
				//TODO validar line
				Record result = automation.execute(line);
				//TODO result joga para o buffer
			}catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
		//TODO gera o arquivo de saida
	}
}