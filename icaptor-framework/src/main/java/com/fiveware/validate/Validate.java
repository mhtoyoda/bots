package com.fiveware.validate;

import com.fiveware.exception.AttributeLoadException;
import com.fiveware.exception.ValidationFieldException;

public interface Validate {

	@SuppressWarnings("rawtypes")
	<T> void validate(T value, Class clazz) throws ValidationFieldException, AttributeLoadException;
}
