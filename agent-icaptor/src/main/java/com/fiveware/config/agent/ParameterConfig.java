package com.fiveware.config.agent;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;

import com.fiveware.exception.AttributeLoadException;
import com.fiveware.exception.ValidationFieldException;
import com.fiveware.validate.Endereco;

public class ParameterConfig {

	public void process(Endereco endereco) {
		Class<? extends Endereco> clazz = endereco.getClass();
		Field[] fields = clazz.getDeclaredFields();
		Lists.newArrayList(fields).forEach(field -> {
			field.setAccessible(true);
			Annotation[] annotations = field.getDeclaredAnnotations();
			for (Annotation annotation : annotations) {
				Class<? extends Annotation> annotationType = annotation.annotationType();
				if (annotationType.getSimpleName().equals("Field")) {
					Method methodTarget;
					try {
						methodTarget = annotationType.getMethod("regexValidate");
						Object regexValidate = methodTarget.invoke(annotation);
						String regex = regexValidate.toString();
						if (StringUtils.isNotBlank(regex)) {
							Pattern pattern = Pattern.compile(regex);
							Matcher matcher = pattern.matcher(String.valueOf(field.get(endereco)));
							if (!matcher.matches()) {
								throw new ValidationFieldException(
										"Value [" + field.get(endereco) + "] does not match validation.");
							}
						}
						methodTarget = annotationType.getMethod("length");
						Object lengthValue = methodTarget.invoke(annotation);
						Integer length = Integer.parseInt(lengthValue.toString());
						if (length != 0 && String.valueOf(field.get(endereco)).length() > length) {
							throw new ValidationFieldException(
									"Value [" + field.get(endereco) + "] should be less than " + lengthValue);
						}
					} catch (NoSuchMethodException | SecurityException | IllegalAccessException
							| IllegalArgumentException | InvocationTargetException e) {
						try {
							throw new AttributeLoadException(e.getMessage());
						} catch (AttributeLoadException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} catch (ValidationFieldException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			;
		});
	}
}
