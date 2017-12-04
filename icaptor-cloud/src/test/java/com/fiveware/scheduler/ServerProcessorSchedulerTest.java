package com.fiveware.scheduler;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;
import static org.powermock.api.mockito.PowerMockito.verifyZeroInteractions;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.fiveware.config.ServerConfig;
import com.fiveware.exception.AuthenticationBotException;
import com.fiveware.exception.RecoverableException;
import com.fiveware.messaging.Producer;
import com.fiveware.messaging.Receiver;
import com.fiveware.model.AgentParameter;
import com.fiveware.model.Parameter;
import com.fiveware.model.StatusProcessItemTaskEnum;
import com.fiveware.model.Task;
import com.fiveware.model.message.MessageBot;
import com.fiveware.parameter.ParameterResolver;
import com.fiveware.service.ServiceServer;
import com.fiveware.task.TaskManager;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ServerProcessorScheduler.class)
public class ServerProcessorSchedulerTest {

	@Mock
	private Receiver<MessageBot> receiver;

	@Mock
	private ServiceServer serviceServer;

	@Mock
	private ServerConfig serverConfig;

	@Mock
	private TaskManager taskManager;

	@Mock
	private Producer<MessageBot> producer;
	
	@Mock
	private ParameterResolver parameterResolver;
	
	@InjectMocks
	private ServerProcessorScheduler serverProcessorScheduler;
	
	private ServerProcessorScheduler serverProcessorSchedulerSpy;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		serverProcessorSchedulerSpy = PowerMockito.spy(serverProcessorScheduler);
	}
	
	@Test
	public void receiveMessageTest(){
		MessageBot message = new MessageBot(1L, 1L, "teste", null);
		when(receiver.receive("BOT.1.IN")).thenReturn(message);	
		assertTrue(serverProcessorScheduler.receiveMessage("BOT.1.IN").isPresent());
	}
	
	@Test
	public void recoverableExceptionRetryTest() throws Exception{
		int parameterRetry = 3;		
		
		Task task = new Task();
		task.setId(1L);
		
		MessageBot messageBot = new MessageBot(1L, 1L, "teste", null);
		String botName = "ConsultaCEP";
		messageBot.setBotName(botName);
		messageBot.setException(new RecoverableException("Lancando exception recuperada"));
		
		doReturn(parameterRetry).when(serverProcessorSchedulerSpy, "getParameter", botName, "retry");
		when(taskManager.getTask(1L)).thenReturn(task );
		
		serverProcessorSchedulerSpy.processMessage(botName, messageBot);
		
		verifyPrivate(serverProcessorSchedulerSpy).invoke("verifyRetry", botName, messageBot, parameterRetry);
		verifyPrivate(serverProcessorSchedulerSpy).invoke("sendMessageBot", messageBot, "ConsultaCEP.1.IN");
	}
	
	@Test
	public void recoverableExceptionWihtoutRetryTest() throws Exception{
		int parameterRetry = 1;		
		
		Task task = new Task();
		task.setId(1L);
		
		MessageBot messageBot = new MessageBot(1L, 1L, "teste", null);
		String botName = "ConsultaCEP";
		messageBot.setBotName(botName);
		messageBot.setException(new RecoverableException("Lancando exception recuperada"));
		
		doReturn(parameterRetry).when(serverProcessorSchedulerSpy, "getParameter", botName, "retry");
		when(taskManager.getTask(1L)).thenReturn(task );
		
		serverProcessorSchedulerSpy.processMessage(botName, messageBot);
		
		verifyPrivate(serverProcessorSchedulerSpy).invoke("verifyRetry", botName, messageBot, parameterRetry);
		verifyZeroInteractions(producer);
	}
	
	@Test
	public void authenticationBotExceptionRetryTest() throws Exception{
		int parameterRetry = 3;		
		
		Task task = new Task();
		task.setId(1L);
		
		AgentParameter agentParameter = new AgentParameter();
		Parameter parameter = new Parameter();
		parameter.setId(1L);
		agentParameter.setParameter(parameter);
		
		MessageBot messageBot = new MessageBot(1L, 1L, "teste", null);
		String botName = "ConsultaCEP";
		messageBot.setBotName(botName);
		messageBot.setNameAgent("agent");
		messageBot.setException(new AuthenticationBotException("Lancando exception authentication"));
		
		doReturn(parameterRetry).when(serverProcessorSchedulerSpy, "getParameter", botName, "retry");
		when(parameterResolver.hasNecessaryParameterFromBot(botName)).thenReturn(true);
		when(parameterResolver.findAgentParameterByNameAgent("agent")).thenReturn(agentParameter);
		when(taskManager.getTask(1L)).thenReturn(task );
		
		serverProcessorSchedulerSpy.processMessage(botName, messageBot);
		
		verifyPrivate(serverProcessorSchedulerSpy).invoke("verifyRetry", botName, messageBot, parameterRetry);
		verifyPrivate(serverProcessorSchedulerSpy).invoke("sendMessageBot", messageBot, "ConsultaCEP.1.IN");
		verifyPrivate(serverProcessorSchedulerSpy).invoke("invalidateParameterCredential", botName, messageBot);
		verify(parameterResolver, times(1)).removeAgentParameter(agentParameter);
		verify(parameterResolver, times(1)).disableParameter(1L);
	}
	
	@Test
	public void authenticationBotExceptionWithoutRetryTest() throws Exception{
		int parameterRetry = 1;		
		
		Task task = new Task();
		task.setId(1L);
		
		AgentParameter agentParameter = new AgentParameter();
		Parameter parameter = new Parameter();
		parameter.setId(1L);
		agentParameter.setParameter(parameter);
		
		MessageBot messageBot = new MessageBot(1L, 1L, "teste", null);
		String botName = "ConsultaCEP";
		messageBot.setBotName(botName);
		messageBot.setNameAgent("agent");
		messageBot.setException(new AuthenticationBotException("Lancando exception authentication"));
		
		doReturn(parameterRetry).when(serverProcessorSchedulerSpy, "getParameter", botName, "retry");
		when(parameterResolver.hasNecessaryParameterFromBot(botName)).thenReturn(true);
		when(parameterResolver.findAgentParameterByNameAgent("agent")).thenReturn(agentParameter);
		when(taskManager.getTask(1L)).thenReturn(task );
		
		serverProcessorSchedulerSpy.processMessage(botName, messageBot);
		
		verifyPrivate(serverProcessorSchedulerSpy).invoke("verifyRetry", botName, messageBot, parameterRetry);
		verifyZeroInteractions(producer);
		verifyPrivate(serverProcessorSchedulerSpy).invoke("invalidateParameterCredential", botName, messageBot);
		verify(parameterResolver, times(1)).removeAgentParameter(agentParameter);
		verify(parameterResolver, times(1)).disableParameter(1L);
	}
	
	@Test
	public void authenticationBotExceptionWithoutParameterCredentialTest() throws Exception{
		int parameterRetry = 3;		
		
		Task task = new Task();
		task.setId(1L);
		
		AgentParameter agentParameter = new AgentParameter();
		Parameter parameter = new Parameter();
		parameter.setId(1L);
		agentParameter.setParameter(parameter);
		
		MessageBot messageBot = new MessageBot(1L, 1L, "teste", null);
		String botName = "ConsultaCEP";
		messageBot.setBotName(botName);
		messageBot.setNameAgent("agent");
		messageBot.setException(new AuthenticationBotException("Lancando exception authentication"));
		
		doReturn(parameterRetry).when(serverProcessorSchedulerSpy, "getParameter", botName, "retry");
		when(parameterResolver.hasNecessaryParameterFromBot(botName)).thenReturn(false);
		when(parameterResolver.findAgentParameterByNameAgent("agent")).thenReturn(agentParameter);
		when(taskManager.getTask(1L)).thenReturn(task );
		
		serverProcessorSchedulerSpy.processMessage(botName, messageBot);
		
		verifyPrivate(serverProcessorSchedulerSpy).invoke("verifyRetry", botName, messageBot, parameterRetry);
		verifyPrivate(serverProcessorSchedulerSpy).invoke("sendMessageBot", messageBot, "ConsultaCEP.1.IN");
		verifyPrivate(serverProcessorSchedulerSpy).invoke("invalidateParameterCredential", botName, messageBot);
		verify(parameterResolver, never()).removeAgentParameter(agentParameter);
		verify(parameterResolver, never()).disableParameter(1L);
	}
	
	@Test
	public void updateItemTaskTest() throws Exception{
		Task task = new Task();
		task.setId(1L);
		
		AgentParameter agentParameter = new AgentParameter();
		Parameter parameter = new Parameter();
		parameter.setId(1L);
		agentParameter.setParameter(parameter);
		
		MessageBot messageBot = new MessageBot(1L, 1L, "teste", null);
		String botName = "ConsultaCEP";
		messageBot.setBotName(botName);
		messageBot.setNameAgent("agent");
		messageBot.setStatusProcessItemTaskEnum(StatusProcessItemTaskEnum.SUCCESS);
		
		serverProcessorSchedulerSpy.processMessage(botName, messageBot);
		
		verify(taskManager, times(1)).updateItemTask(messageBot);
	}
}