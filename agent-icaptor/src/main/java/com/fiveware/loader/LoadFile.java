package com.fiveware.loader;

import com.fiveware.Automation;
import com.fiveware.exception.ValidationFieldException;
import com.fiveware.file.FileUtil;
import com.fiveware.metadata.DataInputDictionary;
import com.fiveware.metadata.DataOutputDictionary;
import com.fiveware.model.OutTextRecord;
import com.fiveware.model.Record;
import com.fiveware.validate.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class LoadFile {

	Logger logger = LoggerFactory.getLogger(LoadFile.class);

	@Autowired
	private Automation<String> automation;

	@Autowired
	private FileUtil fileUtil;

	@Autowired
	private DataInputDictionary dataInputDictionary;
	
	@Autowired
	private DataOutputDictionary dataOutputDictionary;
	
	@Autowired
	private Validate<String> validate;

	public void executeLoad(File file) throws IOException {
		logger.info("Init Import File "+file.getName());
		String separatorInput = (String) dataInputDictionary.getValueAtribute(automation, DataInputDictionary.InputDictionaryAttribute.SEPARATOR);
		String[] fieldsInput = (String[]) dataInputDictionary.getValueAtribute(automation, DataInputDictionary.InputDictionaryAttribute.FIELDS);
		String fileNameOut = (String) dataOutputDictionary.getValueAtribute(automation, DataOutputDictionary.OutputDictionaryAttribute.NAMEFILEOUT);
		List<Record> recordLines = fileUtil.linesFrom(file, fieldsInput, separatorInput);
		for (Record line : recordLines) {
			String cep = line.getValue("cep");
			try {
				validate.validate(cep, automation);
				OutTextRecord result = automation.execute(cep);
				fileUtil.writeFile(fileNameOut, separatorInput, result);
			} catch (ValidationFieldException e) {
				logger.error("Unprocessed Record - Cause: "+e.getMessage());
				Map<String, String> map = new LinkedHashMap<>();
		        map.put("cep", cep);
				fileUtil.writeFile(fileNameOut, separatorInput, new OutTextRecord(map));
			}
		}
		logger.info("End Import File "+file.getName());
	}
}