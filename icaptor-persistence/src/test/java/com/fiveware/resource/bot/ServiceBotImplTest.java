package com.fiveware.resource.bot;

import com.fiveware.model.Bot;
import com.fiveware.model.BotFormatter;
import com.fiveware.repository.BotFormatterRepository;
import com.fiveware.repository.BotRepository;
import com.fiveware.service.bot.ServiceBotImpl;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;


public class ServiceBotImplTest {

    @Mock
    BotRepository botRepository;

    @Mock
    BotFormatterRepository botFormatterRepository;

    ServiceBotImpl serviceBot;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.serviceBot = new ServiceBotImpl(botRepository,botFormatterRepository);
    }

    @Test
    public void save() throws Exception {
        Bot bot=new Bot();
        bot.setNameBot(anyString());
        when(botRepository.findByNameBot(bot.getNameBot())).thenReturn(Optional.empty());
        when(botRepository.save(bot)).thenReturn(bot);

        assertEquals(serviceBot.save(bot),bot);
        verify(botRepository).findByNameBot(bot.getNameBot());
        verify(botRepository,times(1)).save(bot);
    }

    @Test
    public void notSave() throws Exception {
        Bot bot=new Bot();
        bot.setNameBot(anyString());
        when(botRepository.findByNameBot(bot.getNameBot())).thenReturn(Optional.of(bot));
        when(botRepository.save(bot)).thenReturn(bot);

        assertEquals(serviceBot.save(bot),bot);
        verify(botRepository).findByNameBot(bot.getNameBot());
        verify(botRepository,times(0)).save(bot);
    }

    @Test
    public void findAll() throws Exception {
        Bot bot=new Bot();
        when(botRepository.findAll()).thenReturn(Lists.newArrayList(bot));

        assertEquals(serviceBot.findAll().iterator().next(),bot);
        verify(botRepository,times(1)).findAll();
    }

    @Test
    public void findByNameBot() throws Exception {
        Bot bot=new Bot();
        bot.setNameBot(anyString());

        when(botRepository.findByNameBot(bot.getNameBot())).thenReturn(Optional.of(bot));

        assertEquals(serviceBot.findByNameBot(bot.getNameBot()).get().getNameBot(),bot.getNameBot());
        verify(botRepository,times(1)).findByNameBot(bot.getNameBot());
    }

    @Test
    public void findBotFormatter() throws Exception {

        BotFormatter formatterBot=new BotFormatter();
        when( botFormatterRepository.findByBot(anyString())).thenReturn(Lists.newArrayList(formatterBot));

        assertEquals(serviceBot.findByBot(anyString()).size(),1L);
        verify(botFormatterRepository,times(1)).findByBot(anyString());

    }

    @Test
    public void saveBotFormatter() throws Exception {
        BotFormatter formatterBot=new BotFormatter();
        when(botFormatterRepository.save(formatterBot)).thenReturn(formatterBot);

        assertEquals(serviceBot.save(formatterBot),formatterBot);
        verify(botFormatterRepository,times(1)).save(formatterBot);
    }

    @Test
    public void deleteBotFormatter() throws Exception {
        BotFormatter formatterBot=new BotFormatter();
        formatterBot.setId(anyLong());

        when(botFormatterRepository.findOne(formatterBot.getId())).thenReturn(formatterBot);

        serviceBot.deleteBotFormatter(Lists.newArrayList(formatterBot));

        verify(botFormatterRepository).delete(formatterBot);
    }

}