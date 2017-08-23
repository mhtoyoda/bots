package com.fiveware.parameter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fiveware.model.Parameter;
import com.fiveware.service.ServiceParameter;
import com.google.common.collect.Lists;

@Component
public class ParameterResolver {

	@Autowired
	private ServiceParameter serviceParameter;
	
	public ParameterInfo getParameterByBot(String botName){
		List<Parameter> credentials = Lists.newArrayList();
		ParameterInfo parameterInfo = new ParameterInfo();
		List<Parameter> list = serviceParameter.getParameterByBot(botName);
		list.sort((Parameter p1, Parameter p2) -> -p1.getScopeParameter().getPriority().compareTo(p2.getScopeParameter().getPriority()));
		list.forEach(param -> {
			if(param.getActive() && !param.getTypeParameter().getCredential()){
				parameterInfo.addParameters(param.getTypeParameter().getName(), param);
			}
			if(param.getActive() && param.getTypeParameter().getCredential()){
				credentials.add(param);
				parameterInfo.addCredentials(param.getTypeParameter().getName(), credentials);
			}
		});
		
		return parameterInfo;
	}
	
}
