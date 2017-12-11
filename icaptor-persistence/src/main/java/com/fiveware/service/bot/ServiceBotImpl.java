package com.fiveware.service.bot;

import com.fiveware.model.Bot;
import com.fiveware.model.BotFormatter;
import com.fiveware.model.InputField;
import com.fiveware.repository.BotFormatterRepository;
import com.fiveware.repository.BotRepository;
import com.fiveware.repository.InputFieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

@Service
@Transactional
public class ServiceBotImpl {

    @Autowired
    private final BotRepository botRepository;


    @Autowired
    private final InputFieldRepository inputFieldRepository;

    @Autowired
    private final BotFormatterRepository botFormatterRepository;

    public ServiceBotImpl(BotRepository botRepository,
                          InputFieldRepository inputFieldRepository,
                          BotFormatterRepository botFormatterRepository) {
        this.botRepository = botRepository;
        this.inputFieldRepository = inputFieldRepository;
        this.botFormatterRepository = botFormatterRepository;
    }

    public Bot save(Bot bot) {
        Optional<Bot> optional = botRepository.findByNameBot(bot.getNameBot());

            return optional.orElseGet(new Supplier<Bot>() {
                @Override
                public Bot get() {
                    if (!Objects.isNull(bot.getFieldsInput())){
                        String[] split = getSplit(bot);

                        List<String> fields = Arrays.asList(split);
                        fields.forEach((f)->addField(f,bot));
                    }
                    return botRepository.save(bot);
                }
            });
    }

    private String[] getSplit(Bot bot) {
        if (bot.getFieldsInput().indexOf(bot.getSeparatorFile())<=0)
             return new String[]{bot.getFieldsInput()};

        return bot.getFieldsInput().split(bot.getSeparatorFile());
    }


    public Iterable<Bot> findAll() {
        return botRepository.findAll();
    }

    public Optional<Bot> findByNameBot(String name) {
        return botRepository.findByNameBot(name);
    }

    public List<BotFormatter> findByBot(String nameBot) {
        return botFormatterRepository.findByBot(nameBot);
    }

    public BotFormatter save(BotFormatter botFormatter) {
        return botFormatterRepository.save(botFormatter);
    }

    public void deleteBotFormatter(@RequestBody List<BotFormatter> botFormatters) {
        botFormatters.forEach(botFormat -> {
            BotFormatter botFormatter = botFormatterRepository.findOne(botFormat.getId());
            botFormatterRepository.delete(botFormatter);
        });
    }

    public Bot findOne(Long id) {
        return botRepository.findOne(id);
    }


    private void addField(String field, Bot bot) {
        Optional<InputField> byName = Optional.ofNullable(inputFieldRepository.findByName(field));

        bot.addField(byName.orElse(new InputField(field)));

    }
}
