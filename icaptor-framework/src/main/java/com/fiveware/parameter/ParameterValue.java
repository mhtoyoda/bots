package com.fiveware.parameter;

import com.google.common.collect.Lists;
import org.apache.commons.codec.binary.Base64;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@AutoProperty
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

	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}
}
