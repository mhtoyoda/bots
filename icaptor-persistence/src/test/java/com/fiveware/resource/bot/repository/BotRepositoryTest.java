package com.fiveware.resource.bot.repository;

import com.fiveware.IcaptorPersistenceApplication;
import com.fiveware.model.Bot;
import com.fiveware.model.InputField;
import com.fiveware.repository.BotRepository;
import com.fiveware.repository.InputFieldRepository;
import com.google.common.collect.Sets;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = IcaptorPersistenceApplication.class)
@ActiveProfiles("test")
public class BotRepositoryTest {


    @Autowired
    private BotRepository botRepository;

    @Test
    public void a_bot() throws Exception {
        Bot bot = new Bot();
        bot.setNameBot("teste");

        Set<InputField> listFields = new TreeSet<InputField>();
        listFields.add(new InputField("nome"));
        listFields.add(new InputField("email"));
        listFields.add(new InputField("telefone"));
        bot.setInputFields(listFields);

        bot = botRepository.save(bot);

        assertEquals(bot.getInputFields().size(),3);
    }

    @Test
    public void b_bot() throws Exception {

        Bot one = botRepository.findOne(1L);
        one.getInputFields().remove(one.getInputFields().iterator().next());

        Bot save = botRepository.save(one);

        assertNotNull(save);

        assertEquals(save.getInputFields().size(),2);
        assertEquals(save.getInputFields().iterator().next().getName(),"nome");

    }

    @Test
    public void c_bot() throws Exception {
        Bot one = botRepository.findOne(1L);
        one.getInputFields().add(new InputField("nome"));

        Bot save = botRepository.save(one);

        assertNotNull(save);

        assertEquals(save.getInputFields().size(),3);

    }

    @Test
    public void d_bot() throws Exception {
        Bot one = botRepository.findOne(1L);
        one.getInputFields().add(new InputField("endereco"));

        Bot save = botRepository.save(one);

        assertNotNull(save);

        assertEquals(save.getInputFields().size(),4);
    }


}