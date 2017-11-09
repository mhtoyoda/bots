package com.fiveware.validate;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RuleValidations {

    private List<ValidateFile> validations;

    @Autowired
    @Qualifier("inputDictionaryValidate")
    private ValidateFile inputDictionaryValidate;

    public void executeValidations(List<String> lines, String[] fields, String separator) {
        validations = Lists.newArrayList(inputDictionaryValidate);
        validations.stream().forEach(validation -> {
            validation.validate(lines, fields, separator);
        });
    }
}
