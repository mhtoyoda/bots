package com.fiveware.resource.bot.service;

import com.fiveware.IcaptorPersistenceApplication;
import com.fiveware.model.Bot;
import com.fiveware.model.InputField;
import com.fiveware.repository.BotRepository;
import com.fiveware.repository.InputFieldRepository;
import com.fiveware.service.bot.ServiceBotImpl;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = IcaptorPersistenceApplication.class)
@ActiveProfiles("test")
public class BotServiceTest {


    @Autowired
    private ServiceBotImpl serviceBot;


    @Autowired
    private BotRepository botRepository;

    @Autowired
    private InputFieldRepository inputFieldRepository;


    @Test
    public void a_bot() throws Exception {

        Bot bot=new Bot();
        bot.setNameBot("bot-1");
        bot.setClassloader("com.fiveware.Bot");
        bot.setDescription("descricao");
        bot.setFieldsOutput("teste,teste");
        bot.setTypeFileIn("csv");
        bot.setVersion("0.0.1");

        bot.setSeparatorFile(",");
        bot.setFieldsInput("nome,email,telefone");

        serviceBot.save(bot);

        assertEquals(inputFieldRepository.count(),3);
        assertEquals(botRepository.findByNameBot("bot-1").get().totalInputFields(),3);
        assertEquals(bot.totalInputFields(),3);

        assertEquals(botRepository.count(),1);

    }

    @Test
    public void b_bot() throws Exception {
        Bot bot=new Bot();
        bot.setNameBot("bot-2");
        bot.setSeparatorFile(",");

        bot.setFieldsInput("nome,email,telefone,endereco");

        serviceBot.save(bot);

        assertEquals(inputFieldRepository.count(),4);

        assertEquals(botRepository.findByNameBot("bot-2").get().totalInputFields(),4);
        assertEquals(bot.totalInputFields(),4);

        assertEquals(botRepository.count(),2);

    }


    @Test
    public void c_bot() throws Exception {
        Bot bot=botRepository.findByNameBot("bot-2").get();

        bot.setFieldsInput("nome,email,telefone,endereco");

        serviceBot.save(bot);

        assertEquals(inputFieldRepository.count(),4);

        assertEquals(botRepository.findByNameBot("bot-2").get().totalInputFields(),4);
        assertEquals(bot.totalInputFields(),4);

        assertEquals(botRepository.count(),2);

    }

    @Test
    public void d_bot() throws Exception {
        Bot bot=new Bot();
        bot.setNameBot("bot-3");
        bot.setSeparatorFile(",");

        bot.setFieldsInput("nome,cpf,cnpj,total");

        serviceBot.save(bot);

        assertEquals(inputFieldRepository.count(),7);

        assertEquals(botRepository.findByNameBot("bot-3").get().totalInputFields(),4);

        assertEquals(bot.totalInputFields(),4);


        assertEquals(botRepository.count(),3);

    }


    @Test
    public void e_bot() throws Exception {
        Bot bot=new Bot();
        bot.setNameBot("bot-4");
        bot.setSeparatorFile("|");

        bot.setFieldsInput("cep");

        serviceBot.save(bot);

        assertEquals(inputFieldRepository.count(),8);

        assertEquals(botRepository.findByNameBot("bot-4").get().totalInputFields(),1);

        assertEquals(bot.totalInputFields(),1);

        assertEquals(botRepository.count(),4);

        assertEquals(inputFieldRepository.findAll().iterator().next().getBots().size(),2);




    }



}