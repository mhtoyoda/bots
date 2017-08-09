package com.fiveware.validate;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component("inputDictionaryValidate")
public class InputDictionaryValidate implements ValidateFile {

	@Override
	public void validate(List<String> lines, String[] fields, String separator) throws ValidationFileErrorException {
		lines.forEach(record -> {
			String[] recordArray = StringUtils.split(record, separator);
			if (recordArray.length != fields.length) {				
				throw new ValidationFileErrorException("Invalid file structure");
			}
		});
	}
}