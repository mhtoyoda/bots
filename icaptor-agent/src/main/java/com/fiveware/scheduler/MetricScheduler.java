package com.fiveware.scheduler;

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
import java.util.function.Consumer;

import static com.google.common.collect.Lists.newArrayList;

@Component
public class MetricScheduler {

	private static Logger log = LoggerFactory.getLogger(MetricScheduler.class);

	@Autowired
	private ServiceAgentInfra serviceAgentInfra;

	@Autowired
	private ServiceAgent serviceAgent;

	@Value("${server.port}")
	private int port;

	@Autowired
	Environment environment;

	@Scheduled(fixedDelayString = "${icaptor.metrics-schedulle-time}")
	public void process() {
		Optional<List<Agent>> all = Optional.ofNullable(serviceAgent.findAll());
		all.orElse(Lists.newArrayList())
				.forEach((agent -> {
					Map<String, Object> metrics = serviceAgentInfra.metrics(agent);
					log.debug("{}", metrics);
				}));
	}

}