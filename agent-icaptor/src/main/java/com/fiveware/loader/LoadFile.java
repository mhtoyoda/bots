package com.fiveware.loader;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fiveware.Automation;
import com.fiveware.file.FileUtil;
import com.fiveware.file.RecordLine;
import com.fiveware.metadata.Dictionary;

@Component
public class LoadFile {

	Logger logger = LoggerFactory.getLogger(LoadFile.class);
	
	@Autowired
	private Automation automation;
	
	@Autowired
	private FileUtil fileUtil;
	
	@Autowired
	private Dictionary dictionary;
	
	public void executeLoad(){
		File file = fileUtil.getFile("cep.txt");
		String[] fieldsInput = dictionary.getFieldsInput(automation);
		List<RecordLine> recordLine = fileUtil.getLines(file, fieldsInput);
		for (RecordLine line : recordLine) {
			try{
				//TODO validar line
				RecordLine result = automation.execute(line);
				//TODO result joga para o buffer
			}catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
		//TODO gera o arquivo de saida
	}
}