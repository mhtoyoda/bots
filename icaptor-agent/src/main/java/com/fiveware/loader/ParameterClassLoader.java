package com.fiveware.loader;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.fiveware.model.BotClassLoaderContext;
import com.fiveware.model.InputDictionaryContext;
import com.fiveware.model.Record;
import com.fiveware.validate.Validate;
import com.google.common.collect.Lists;

@Component
public class ParameterClassLoader {
	
	@Autowired
	@Qualifier("fieldValidate")
	private Validate validate;
	
	@Autowired
	@Qualifier("objectValidate")
	private Validate objectValidate;
	
	public Object loaderParameter(BotClassLoaderContext botClassLoaderContext, Record record) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, InstantiationException{
		Map<String, Object> recordMap = record.getRecordMap();
		Class<?> typeParameter = botClassLoaderContext.getTypeParameter();
		Object instance = typeParameter.newInstance();
		InputDictionaryContext inputDictionary = botClassLoaderContext.getInputDictionary();
		String[] fields = inputDictionary.getFields();
		
		if (instance instanceof String || instance instanceof Integer || instance instanceof Double
			|| instance instanceof Long){
			return recordMap.get(fields[0]);			
		}

		for(String field : fields){
			Field declaredField = instance.getClass().getDeclaredField(field);
			boolean accessible = declaredField.isAccessible();
			declaredField.setAccessible(true);
			declaredField.set(instance, recordMap.get(field));
			declaredField.setAccessible(accessible);
		}
		return instance;
	}
	
	public Validate getValidateByType(BotClassLoaderContext botClassLoaderContext){
		Class<?> typeParameter = botClassLoaderContext.getTypeParameter();
		List<String> typesJava = Lists.newArrayList(String.class.getName(), Integer.class.getName(), 
							  Double.class.getName(), Long.class.getName(), Boolean.class.getName());
		Optional<String> optional = typesJava.stream().filter(t -> t.equals(typeParameter.getName())).findAny();
		if(optional.isPresent()){
			return validate;
		}
		return objectValidate;
	}
	
	private String[] getFields(String inputFields){
		String[] values = inputFields.split(",");
		List<String> list = Lists.newArrayList(values);
		String[] fields = new String[list.size()];
		
		IntStream.range(0, list.size()).forEach(index -> {
			fields[index] = StringUtils.trim(list.get(index));
		});				
				
		return fields;
	}
}