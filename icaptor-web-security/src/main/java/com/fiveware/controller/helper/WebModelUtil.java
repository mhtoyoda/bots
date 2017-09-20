package com.fiveware.controller.helper;

import com.fiveware.model.Bot;
import com.fiveware.model.user.IcaptorUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;
import static org.apache.commons.lang3.StringUtils.startsWith;

@Component
public class WebModelUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(WebModelUtil.class);

	public List<Map<String, Object>> convertUsersToMap(List<IcaptorUser> users, String... attributesToShow) {
		List<Map<String, Object>> botsMapped = new ArrayList<Map<String, Object>>(users.size());
		users.forEach(user -> botsMapped.add(convertToMap(user, attributesToShow)));
		return botsMapped;
	}

	public List<Map<String, Object>> convertBotsToMap(List<Bot> bots, String... attributesToShow) {
		List<Map<String, Object>> botsMapped = new ArrayList<Map<String, Object>>(bots.size());
		bots.forEach(bot -> botsMapped.add(convertToMap(bot, attributesToShow)));
		return botsMapped;
	}

	public Map<String, Object> convertToMap(Object obj, String... attributesToShow) {
		List<Field> fields = listObjectFields(obj);
		List<Method> methods = listObjectMethods(obj);

		Map<String, Object> objMapped = new HashMap<>(fields.size());
		for (Field field : fields) {
			if (shouldPutField(field.getName(), Arrays.asList(attributesToShow))) {
				objMapped.put(field.getName(), getFieldValue(field, methods, obj));
			}
		}

		return objMapped;
	}

	protected Object getFieldValue(Field field, List<Method> methods, Object obj) {
		try {
			Optional<Method> methodOptional = findGetMethodForField(field, methods);
			return methodOptional.get().invoke(obj, new Object[0]);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// Nao deve acontecer.
			LOGGER.error("Error to execute field [{}] get method of type[{}]", field.getName(), obj.getClass());
		}

		return null;
	}

	protected Optional<Method> findGetMethodForField(Field field, List<Method> methods) {
		Optional<Method> getMethod = methods.stream().filter(m -> startsWith(m.getName(), "get")
				&& equalsIgnoreCase(m.getName().substring(3), field.getName())).findAny();
		return getMethod;
	}

	protected boolean shouldPutField(String objField, List<String> attributesToShow) {
		if (attributesToShow.contains(objField)) {
			return true;
		}

		return false;
	}

	protected List<Field> listObjectFields(Object obj) {
		Field[] declaredFields = obj.getClass().getDeclaredFields();
		return Arrays.asList(declaredFields);
	}

	protected List<Method> listObjectMethods(Object obj) {
		Method[] declaredMethods = obj.getClass().getDeclaredMethods();
		return Arrays.asList(declaredMethods);
	}
}
