package com.fiveware.validate;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.fiveware.Automation;
import com.fiveware.annotation.IcaptorMethod;
import com.fiveware.annotation.Field;
import com.fiveware.exception.ValidationFieldException;

@Service
public class CepValidate implements Validate<String> {

	@Override
	public void validate(String value, Automation<?> automation) throws ValidationFieldException {
		Class<? extends Object> clazz = automation.getClass();
		for (Method method : clazz.getDeclaredMethods()) {
			if (method.isAnnotationPresent(IcaptorMethod.class)) {
				Annotation[][] parameterAnnotations = method.getParameterAnnotations();
				for (Annotation[] annotations : parameterAnnotations) {
					for (Annotation annotation : annotations) {
						if (annotation instanceof Field) {
							Field fieldAnnotation = (Field) annotation;
							if(StringUtils.isNotBlank(fieldAnnotation.regexValidate())){
								String regexValidate = fieldAnnotation.regexValidate();
								Pattern pattern = Pattern.compile(regexValidate);
								Matcher matcher = pattern.matcher(value);
								if(!matcher.matches()){
									throw new ValidationFieldException("Value ["+value+"] does not match validation.");
								}
							}
							if(fieldAnnotation.length() != 0 && value.length() > fieldAnnotation.length()){
								throw new ValidationFieldException("Value ["+value+"] should be less than "+fieldAnnotation.length());
							}
						}
					}
				}
			}
		}
	}
}