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
import java.util.List;
import java.util.TreeSet;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = IcaptorPersistenceApplication.class)
@ActiveProfiles("test")
public class BotRepositoryTest {


    @Autowired
    private BotRepository botRepository;

    @Autowired
    private InputFieldRepository inputFieldRepository;

    @Test
    public void a_bot() throws Exception {
        Bot bot = new Bot();
        bot.setNameBot("teste");

        List<InputField> listFields = Lists.newArrayList();
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
        assertEquals(save.getInputFields().iterator().next().getName(),"email");

    }

    @Test
    public void c_bot() throws Exception {
        Bot one = botRepository.findOne(1L);
        InputField inputField = inputFieldRepository.findAll().iterator().next();

        one.getInputFields().add(inputField);

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

    @Test(expected = DataIntegrityViolationException.class)
    public void e_bot() throws Exception {
        Bot bot = new Bot();
        bot.setNameBot("teste2");

        List<InputField> listFields = Lists.newArrayList();
        listFields.add(new InputField("nome"));
        listFields.add(new InputField("email"));
        listFields.add(new InputField("telefone"));
        bot.setInputFields(listFields);

        bot = botRepository.save(bot);

        List<InputField> all = (List<InputField>) inputFieldRepository.findAll();


        assertEquals(bot.getInputFields().size(),3);
        assertEquals(all.size(),3);
    }

    @Test
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void f_bot() throws Exception {
        Bot bot = new Bot();
        bot.setNameBot("teste3");
        Iterable<InputField> all1 = inputFieldRepository.findAll();
        List<InputField> all = Lists.newArrayList();

        all1.forEach((inputField -> all.add(inputField)));


        bot.setInputFields(all);

        bot = botRepository.save(bot);

        assertEquals(bot.getInputFields().size(),4);
        assertEquals(all.size(),4);
    }

}