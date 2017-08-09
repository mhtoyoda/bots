package com.fiveware.validate;

import java.util.List;

public interface ValidateFile {

	public void validate(List<String> lines, String[] fields, String separator) throws ValidationFileErrorException;
}
