package com.fiveware.validate;

import java.util.List;

import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

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
