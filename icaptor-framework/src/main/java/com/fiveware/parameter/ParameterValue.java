package com.fiveware.parameter;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import com.google.common.collect.Lists;

public class ParameterValue implements Serializable {

	private List<ParameterIcaptor> parameterList;

	public ParameterValue() {
		parameterList = Lists.newArrayList();
	}
	
	public void add(String type, String field, String value) {
		ParameterIcaptor icaptorParameter = new ParameterIcaptor(type, field, value);
		parameterList.add(icaptorParameter);
	}

	public ParameterIcaptor getByType(String type){
		Optional<ParameterIcaptor> optional = parameterList.stream().filter(p -> p.getType().equals(type)).findFirst();
		return optional.get();
	}

	public List<ParameterIcaptor> getParameterList() {
		return parameterList;
	}

	public void setParameterList(List<ParameterIcaptor> parameterList) {
		this.parameterList = parameterList;
	}
}
