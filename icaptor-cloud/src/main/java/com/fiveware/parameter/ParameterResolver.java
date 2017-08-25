package com.fiveware.parameter;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fiveware.model.Parameter;
import com.fiveware.service.ServiceParameter;
import com.google.common.collect.Lists;

@Component
public class ParameterResolver {

	private static Logger log = LoggerFactory.getLogger(ParameterResolver.class);
	
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
				log.info("Add Parameter Bot: {} - Type: {} - Scope: {}", botName, param.getTypeParameter().getName(), param.getScopeParameter().getName());
			}
			if(param.getActive() && param.getTypeParameter().getCredential()){
				credentials.add(param);
				parameterInfo.addCredentials(param.getTypeParameter().getName(), credentials);
				log.info("Add Parameter Credential Bot: {} - Type: {} - Scope: {}", botName, param.getTypeParameter().getName(), param.getScopeParameter().getName());
			}
		});
		
		return parameterInfo;
	}
	
	public void disableParameter(Long parameterId){
		Parameter parameter = serviceParameter.getParameterById(parameterId);
		parameter.setActive(false);
		serviceParameter.save(parameter);
	}
	
	public Parameter getParameterCloud(String typeParameter){
		List<Parameter> list = serviceParameter.findParameterByScopeAndType(ScopeParameterEnum.CLOUD.getName(), typeParameter);
		if( null != list ){
			return list.get(0);
		}
		return null;
	}
}
