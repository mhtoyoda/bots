package com.fiveware.integration;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fiveware.dao.AgentDAO;
import com.fiveware.dao.BotDAO;
import com.fiveware.dao.ServerDAO;
import com.fiveware.model.Agent;
import com.fiveware.model.Bot;
import com.fiveware.model.MessageAgent;
import com.fiveware.model.Server;
import com.google.common.collect.Lists;

@Component("serverAgentIntegrationInMemory")
public class ServerAgentIntegrationInMemory implements ServerAgentIntegration {

	@Autowired
	private BotDAO botDao;
	
	@Autowired
	private ServerDAO serverDao;
	
	@Autowired
	private AgentDAO agentDao;

	@Override
	public void join(MessageAgent message) {
		List<Bot> bots = Lists.newArrayList();
		Optional<Server> server = serverDao.findByHost(message.getHost());
		if(server.isPresent()){	
			message.getMessageAgentBots().forEach(botMessage -> {
				Bot bot = new Bot();
				bot.setMethod(botMessage.getNameMethod());
				bot.setEndpoint(botMessage.getEndpoint());
				bot.setNameBot(botMessage.getNameBot());
				bots.add(bot);
			});
			
//			Iterable<Bot> botsPersist = botDao.save(bots);
			
			Agent agent = new Agent();
			agent.setNameAgent(message.getAgent());
			agent.setIp(message.getIp());
			agent.setServer(server.get());			
			agent.setBots(bots);
//			agent.setBots(Lists.newArrayList(botsPersist));
			agentDao.save(agent);
		}
	}
}