package com.fiveware.config.agent;

import com.fiveware.loader.ClassLoaderConfig;
import com.fiveware.messaging.BrokerManager;
import com.fiveware.messaging.Producer;
import com.fiveware.model.message.MessageAgent;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;

@RunWith(PowerMockRunner.class)
@PrepareForTest(AgentConfig.class)
public class AgentConfigTest {

    @Mock
    private AgentConfigProperties data;

    @Mock
    private ClassLoaderConfig classLoaderConfig;

    @Mock
    private AgentListener agentListener;

    @Mock
    private BrokerManager brokerManager;

    @Mock
    private Producer<MessageAgent> producer;

    @InjectMocks
    private AgentConfig agentConfig;

    private AgentConfig agentConfigSpy;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        agentConfigSpy = PowerMockito.spy(agentConfig);
    }

    @Test
    public void initAgentTest() throws Exception {
        when(agentListener.getAgentPort()).thenReturn(8080);
        agentConfigSpy.initAgent();
        verifyPrivate(agentConfigSpy).invoke("bindQueueAgenteInTaskTopic", "topic-exchange", Lists.newArrayList(data.getAgentName()));
        verifyPrivate(agentConfigSpy).invoke("createQueueParameter");
        verifyPrivate(agentConfigSpy).invoke("notifyServerUpAgent");
    }

    @Test
    public void initAgentPortZeroTest() throws Exception {
        when(agentListener.getAgentPort()).thenReturn(0);
        agentConfigSpy.initAgent();
        verifyZeroInteractions(brokerManager);
        verifyZeroInteractions(producer);
    }
}
