package com.fiveware.validate;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component("inputDictionaryValidate")
public class InputDictionaryValidate implements ValidateFile {

    @Autowired
    private MessageSource messageSource;

    @Override
    public void validate(List<String> lines, String[] fields, String separator) throws ValidationFileErrorException {
        lines.forEach(record -> {
            String[] recordArray = StringUtils.split(record, separator);
            if (recordArray.length != fields.length) {
                String message = messageSource.getMessage("validate.fields.inputDictionary",
                        new Object[]{record, fields.length, recordArray.length}, null);
                throw new ValidationFileErrorException(message);
            }
        });
    }
}