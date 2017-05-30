package com.fiveware.validate;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.fiveware.exception.AttributeLoadException;
import com.fiveware.exception.ValidationFieldException;

@Service
public class CepValidate implements Validate<String> {

	@SuppressWarnings("rawtypes")
	@Override
	public void validate(String value, Class clazz) throws ValidationFieldException, AttributeLoadException {
		for (Method method : clazz.getDeclaredMethods()) {
			Annotation[][] parameterAnnotations = method.getParameterAnnotations();
			for (Annotation[] annotations : parameterAnnotations) {
				for (Annotation annotation : annotations) {
					Class<? extends Annotation> type = annotation.annotationType();
					if (type.getSimpleName().equals("Field")) {

						Method methodTarget;
						try {
							methodTarget = type.getMethod("regexValidate");
							Object regexValidate = methodTarget.invoke(annotation);
							String regex = regexValidate.toString();
							if (StringUtils.isNotBlank(regex)) {
								Pattern pattern = Pattern.compile(regex);
								Matcher matcher = pattern.matcher(value);
								if (!matcher.matches()) {
									throw new ValidationFieldException(
											"Value [" + value + "] does not match validation.");
								}
							}
							methodTarget = type.getMethod("length");
							Object lengthValue = methodTarget.invoke(annotation);
							Integer length = Integer.parseInt(lengthValue.toString());
							if (length != 0 && value.length() > length) {
								throw new ValidationFieldException(
										"Value [" + value + "] should be less than " + lengthValue);
							}
						} catch (NoSuchMethodException | SecurityException | IllegalAccessException
								| IllegalArgumentException | InvocationTargetException e) {
							throw new AttributeLoadException(e.getMessage());
						}
					}

				}

			}
		}
	}
}