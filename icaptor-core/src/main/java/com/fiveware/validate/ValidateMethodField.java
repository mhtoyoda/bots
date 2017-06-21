package com.fiveware.validate;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.fiveware.exception.AttributeLoadException;
import com.fiveware.exception.ValidationFieldException;

@Component
public class ValidateMethodField {

	public void validateRegex(Annotation annotation, Class<? extends Annotation> type, Object value)
			throws ValidationFieldException, AttributeLoadException {
		try {
			Method methodTarget = type.getMethod("regexValidate");
			Object regexValidate = methodTarget.invoke(annotation);
			String regex = regexValidate.toString();
			if (StringUtils.isNotBlank(regex)) {
				Pattern pattern = Pattern.compile(regex);
				Matcher matcher = pattern.matcher(String.valueOf(value));
				if (!matcher.matches()) {
					throw new ValidationFieldException("Value [" + value + "] does not match validation.");
				}
			}
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new AttributeLoadException(e.getMessage());
		}
	}

	public void validateLength(Annotation annotation, Class<? extends Annotation> type, Object value)
			throws ValidationFieldException, AttributeLoadException {
		try {
			Method methodTarget = type.getMethod("length");
			Object lengthValue = methodTarget.invoke(annotation);
			Integer length = Integer.parseInt(lengthValue.toString());
			if (length != 0 && String.valueOf(value).length() > length) {
				throw new ValidationFieldException("Value [" + value + "] should be less than " + lengthValue);
			}
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new AttributeLoadException(e.getMessage());
		}
	}
}
