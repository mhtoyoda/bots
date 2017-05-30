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
import com.fiveware.metadata.DataIcaptorMethod;
import com.fiveware.metadata.DataIcaptorMethod.IcaptorMethodAttribute;
import com.fiveware.metadata.DataInputDictionary;
import com.fiveware.metadata.DataOutputDictionary;
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
	private DataInputDictionary dataInputDictionary;
	
	@Autowired
	private DataOutputDictionary dataOutputDictionary;
	
	@Autowired
	private DataIcaptorMethod dataIcaptorMethod;
	
	@Autowired
	private Validate<String> validate;
	
	@Autowired
	private ServiceBot<String> serviceBot;

	@SuppressWarnings("rawtypes")
	public void executeLoad(File file) throws IOException, AttributeLoadException {
		logger.info("Init Import File "+file.getName());
		Class classLoader = serviceBot.loadClassLoader();
		String nameMethod = (String) dataIcaptorMethod.getValueAtribute(classLoader, IcaptorMethodAttribute.VALUE);
		String separatorInput = (String) dataInputDictionary.getValueAtribute(classLoader, nameMethod, DataInputDictionary.InputDictionaryAttribute.SEPARATOR);
		String[] fieldsInput = (String[]) dataInputDictionary.getValueAtribute(classLoader, nameMethod, DataInputDictionary.InputDictionaryAttribute.FIELDS);
		String fileNameOut = (String) dataOutputDictionary.getValueAtribute(classLoader, nameMethod, DataOutputDictionary.OutputDictionaryAttribute.NAMEFILEOUT);
		List<Record> recordLines = fileUtil.linesFrom(file, fieldsInput, separatorInput );
		for (Record line : recordLines) {
			String cep = line.getValue("cep");
			try {
				validate.validate(cep, classLoader);
				OutTextRecord result = serviceBot.callBot(classLoader, cep);
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