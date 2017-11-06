package com.fiveware.controller.infra;

import com.fiveware.model.Agent;
import com.fiveware.service.ServiceAgent;
import com.fiveware.service.infra.ServiceAgentInfra;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.applet.Main;

import java.awt.*;
import java.io.Console;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@EnableCaching
@RestController
@RequestMapping("/api/infra/agent")
public class AgentInfraResource {

	static final Logger logger = LoggerFactory.getLogger(AgentInfraResource.class);

	@Autowired
	private ServiceAgent serviceAgent;

	@Autowired
	private ServiceAgentInfra serviceAgentInfra;

	@GetMapping("/metrics")
	public List<Map<String,Object>> metrics(){
		List<Agent> all = serviceAgent.findAll();

		List<Map<String, Object>> mapList = all.stream().map((s) -> serviceAgentInfra.getRedis(s)).collect(Collectors.toList());

		return mapList;
	}


}
