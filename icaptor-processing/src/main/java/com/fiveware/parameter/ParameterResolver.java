package com.fiveware.parameter;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fiveware.model.AgentParameter;
import com.fiveware.model.Parameter;
import com.fiveware.service.ServiceAgent;
import com.fiveware.service.ServiceParameter;
import com.google.common.collect.Lists;

@Component
public class ParameterResolver {

	private static Logger log = LoggerFactory.getLogger(ParameterResolver.class);
	
	@Autowired
	private ServiceParameter serviceParameter;
	
	@Autowired
	private ServiceAgent serviceAgent;
	
	public Boolean hasNecessaryParameterFromBot(String botName){		
		List<Parameter> list = serviceParameter.getParameterByBot(botName);
		return CollectionUtils.isNotEmpty(list);		
	}
	
	public int countParameterCredential(String botName){		
		List<Parameter> list = serviceParameter.getParameterByBot(botName);
		int count = (int) list.stream().filter(p -> p.getTypeParameter().getCredential()).count();		
		return count;		
	}
	
	public boolean exclusiveParameterCredential(String botName){		
		List<Parameter> list = serviceParameter.getParameterByBot(botName);
		boolean exclusive = list.stream().filter(p -> p.getTypeParameter().getCredential()).anyMatch( p -> p.getTypeParameter().getExclusive());		
		return exclusive;		
	}
	
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
	
	public Parameter getParameterById(Long parameterId){
		Parameter parameter = serviceParameter.getParameterById(parameterId);
		return parameter;
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
	
	public Parameter getParameterCredential(String nameAgent, String nameBot){
		ParameterInfo parameterByBot = getParameterByBot(nameBot);
		Map<String, List<Parameter>> credentials = parameterByBot.getCredentials();
		List<Parameter> parameters = credentials.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
		Iterator<Parameter> iterator = parameters.iterator();
		AgentParameter agentParameter = null;
		if(exclusiveParameterCredential(nameBot)){
			while(iterator.hasNext()){
				Parameter param = iterator.next();
				agentParameter = saveAgentParameter(nameAgent, param);			
				agentParameter = equalsAgentParameter(agentParameter, nameAgent);
				if( null != agentParameter){
					break;				
				}
			}
			return agentParameter == null ? null : agentParameter.getParameter();
		}else{
			return iterator.hasNext() ? iterator.next() : null;
		}
	}
	
	private AgentParameter equalsAgentParameter(AgentParameter agentParameter, String nameAgent){
		if( null != agentParameter && agentParameter.getAgent().getNameAgent().equals(nameAgent)){
			return agentParameter;
		}
		return null;
	}
	
	private synchronized AgentParameter saveAgentParameter(String nameAgent, Parameter param) {
		AgentParameter agentParameter = findAgentParameterByParameter(param);
		if(null == agentParameter){
			agentParameter = new AgentParameter();
			agentParameter.setAgent(serviceAgent.findByNameAgent(nameAgent));
			agentParameter.setParameter(serviceParameter.getParameterById(param.getId()));
			agentParameter = serviceAgent.save(agentParameter);
			return agentParameter;
		}
		return agentParameter;
	}

	public AgentParameter findAgentParameterByParameter(Parameter param) {
		AgentParameter agentParameter = serviceAgent.findByAgentParameterId(param.getId());
		return agentParameter;
	}
	
	public AgentParameter findAgentParameterByNameAgent(String nameAgent) {
		AgentParameter agentParameter = serviceAgent.findByAgentName(nameAgent);
		return agentParameter;
	}
	
	public void removeAgentParameter(AgentParameter agentParameter){
		serviceAgent.remove(agentParameter);
	}
}