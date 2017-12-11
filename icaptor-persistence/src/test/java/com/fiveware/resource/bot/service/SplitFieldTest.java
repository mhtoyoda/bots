package com.fiveware.resource.bot.service;

import com.fiveware.model.Bot;
import com.fiveware.repository.BotFormatterRepository;
import com.fiveware.repository.BotRepository;
import com.fiveware.repository.InputFieldRepository;
import com.fiveware.service.bot.ServiceBotImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(Parameterized.class)
public class SplitFieldTest {

    @Parameterized.Parameters(name = "input:{0} - output:{1}")
    public static Collection<Object[]> data() {
        Bot bot1 = new Bot();
        bot1.setSeparatorFile(",");
        bot1.setFieldsInput("nome,localidade");

        Bot bot2=new Bot();
        bot2.setSeparatorFile("|");
        bot2.setFieldsInput("cep");

        Bot bot3 = new Bot();
        bot3.setSeparatorFile(",");
        bot3.setFieldsInput("quantidade,descricao,valor");

        return Arrays.asList(new Object[][]{
                {bot1, 2},
                {bot3, 3},
                {bot2, 1}
        });
    }

    @Mock
    BotRepository botRepository;

    @Mock
    InputFieldRepository inputFieldRepository;

    @Mock
    BotFormatterRepository botFormatterRepository;

    @Mock
    ServiceBotImpl serviceBot;

    private final Bot input;

    private final int expected;

    public SplitFieldTest(Bot input, int expected) {
        this.input = input;
        this.expected = expected;
        MockitoAnnotations.initMocks(this);
        this.serviceBot = new ServiceBotImpl(botRepository, inputFieldRepository, botFormatterRepository);
    }

    @Test
    public void split(){

        when(botRepository.findByNameBot(input.getNameBot())).thenReturn(Optional.empty());

        when(serviceBot.save(input)).thenReturn(input);

        assertThat(input.totalInputFields(), is(equalTo(expected)));
    }
}
