package com.fiveware.scheduler;

import com.fiveware.config.agent.AgentListener;
import com.fiveware.model.Agent;
import com.fiveware.service.ServiceAgent;
import com.fiveware.service.infra.ServiceAgentInfra;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.google.common.collect.Lists.newArrayList;

@Component
public class MetricScheduler {

	private static Logger log = LoggerFactory.getLogger(MetricScheduler.class);

	@Autowired
	private ServiceAgentInfra serviceAgentInfra;

	@Autowired
	private ServiceAgent serviceAgent;

	@Autowired
	Environment environment;

	@Autowired
	private AgentListener agentListener;

	//@Scheduled(fixedDelayString = "${icaptor.metrics-schedulle-time}")
	public void process() {
		Optional<List<Agent>> all = Optional.ofNullable(serviceAgent.findAll());
		all.orElse(newArrayList())
				.stream()
				.filter(this::test)
				.forEach((this::accept));
	}

	private void accept(Agent agent) {
		serviceAgentInfra.metrics(agent);
	}

	private boolean test(Agent agent) {
		return agent.getPort() == agentListener.getAgentPort();
	}
}