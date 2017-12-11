package com.fiveware.resource.bot.repository;

import com.fiveware.IcaptorPersistenceApplication;
import com.fiveware.model.Bot;
import com.fiveware.model.InputField;
import com.fiveware.repository.BotRepository;
import com.fiveware.repository.InputFieldRepository;
import com.google.common.collect.Sets;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.beans.Transient;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = IcaptorPersistenceApplication.class)
@ActiveProfiles("test")
@Transactional
public class BotRepositoryTest {


    @Autowired
    private BotRepository botRepository;

    @Autowired
    private InputFieldRepository inputFieldRepository;

    @Test
    public void a_bot() throws Exception {
        Bot bot = new Bot();
        bot.setNameBot("teste");

        bot.addField(new InputField("nome"));
        bot.addField(new InputField("email"));
        bot.addField(new InputField("telefone"));


        botRepository.save(bot);

        assertEquals(inputFieldRepository.count(),3);


    }

}